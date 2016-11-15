package ml.generall.enricher

import java.io.FileOutputStream

import ml.generall.elastic.{ConceptVariant, Sentence, Mention}
import ml.generall.isDebug
import ml.generall.ner.elements.ContextElementConverter
import ml.generall.resolver._
import ml.generall.sentence._

import scala.collection.mutable.ListBuffer

/**
  * -> read -> parse -> search -> protobuf -> repeat
  */
class Worker(
              val storagePrefix: String
            ) {

  val builder = new ExamplesBuilder

  val reader = Reader

  val analyser = new SentenceAnalizer

  val contextSize = 7

  val stat = WorkerStat()

  val protobufOutputStream = new FileOutputStream(storagePrefix ++ "_sentences.pb")

  def conceptVariantToPB(conceptVariant: ConceptVariant): Concept = {
    Concept(conceptVariant.concept,
      hits = Some(conceptVariant.count),
      avgNorm = Some(conceptVariant.avgNorm),
      avgScore = Some(conceptVariant.avgScore),
      avgSoftMax = Some(conceptVariant.avgSoftMax),
      maxScore = Some(conceptVariant.maxScore),
      minScore = Some(conceptVariant.minScore)
    )
  }

  def processMention(mention: Mention): Sentente = {
    val sent = new Sentence(mention)

    val conceptVariant = List(ConceptVariant(
      concept = mention.href,
      count = 1,
      avgScore = 1.0,
      avgNorm = 1.0,
      avgSoftMax = 1.0,
      maxScore = 1.0,
      minScore = 1.0
    ))


    sent.chunks match {
      case List(firstChunk, middleChunk, lastChunk) =>

        val firstChunkText = firstChunk.text.replaceAll("\\P{InBasic_Latin}", "")
        val middleChunkText = middleChunk.text.replaceAll("\\P{InBasic_Latin}", "")
        val lastChunkText = lastChunk.text.replaceAll("\\P{InBasic_Latin}", "")

        val startMentionPos = firstChunkText.length + 1
        val endMentionPos = middleChunkText.length + startMentionPos + 1

        val text = firstChunkText ++ " " ++ middleChunkText ++ " " ++ lastChunkText

        try {
          val sentenceRange = builder.splitter.getSentence(text, (startMentionPos, endMentionPos)) match {
            case None =>
              throw new UnparsableException(text, startMentionPos, endMentionPos)
            case x => x.get
          }
          val (from, to) = sentenceRange

          val prevSent = text.substring(0, from)
          val sentence = text.substring(from, to)
          val postSent = text.substring(to)

          val sourceMention = ml.generall.sentence.Mention(0,
            position = Some(Position(startMentionPos - sentenceRange._1, endMentionPos - sentenceRange._1)),
            concepts = conceptVariant.map(conceptVariantToPB),
            text = Some(middleChunk.text),
            resolver = Some("wikilink"),
            context = Some(
              Context(-1,
                left = Some(firstChunkText),
                right = Some(lastChunkText)
              )
            ))


          val parseRes = analyser.parser.process(sentence)

          val groups = parseRes.zipWithIndex
            .groupBy({ case (record, idx) => (record.parseTag, 0/*record.ner*/, record.groupId) })
            .toList
            .sortBy(x => x._2.head._2)
            .map(pair => (s"${pair._1._1}" /* _${pair._1._2} */ , pair._2.map(_._1))) // creation of state


          val train = groups.map(group => {
            val (state, tokens) = group
            val pattern = "^(NP.*)".r
            state match {
              case pattern(c) =>
                val text = tokens.map(_.word).mkString(" ")
                val weights = tokens.map(Builder.weightFu)
                val variants = if (Builder.mentionFilter.filter(tokens, weights))
                  Builder.searcher.findMentions(text).stats
                else {
                  if (isDebug()) println(s"Filter mention: $text")
                  Nil
                }
                (tokens.zip(weights), variants)
              case _ =>
                val weights = tokens.map(Builder.weightFu)
                (tokens.zip(weights), Nil)
            }
          })

          var mentionIdx: Int = 1
          val trainWithContext = ContextElementConverter.convertContext(train, contextSize)
          val mentionsPB: ListBuffer[ml.generall.sentence.Mention] = ListBuffer(sourceMention)

          val tokensWithMentions = trainWithContext.flatMap(x => {
            val (tokens, concepts) = x._2

            val mention = if (concepts.nonEmpty) {
              mentionIdx += 1

              val leftContext = x._1.map(_._1.map(_._1.word).mkString(" ")).mkString(" ")
              val rightContext = x._3.map(_._1.map(_._1.word).mkString(" ")).mkString(" ")

              val context = Context(contextSize / 2,
                left = Some(leftContext),
                right = Some(rightContext)
              )

              val position = Position(tokens.head._1.beginPos, tokens.last._1.endPos)

              val text = tokens.map(_._1.word).mkString(" ")

              val pbConcepts = concepts.map(conceptVariantToPB).toSeq
              val weights = tokens.map(_._2)

              val param = Params(
                sumWeight = Some(weights.sum),
                avgWight = Some(weights.sum / weights.size),
                maxWight = Some(weights.max),
                wordCount = Some(weights.size)
              )

              val mention = ml.generall.sentence.Mention(mentionIdx,
                resolver = Some("elastic"),
                context = Some(context),
                position = Some(position),
                text = Some(text),
                concepts = pbConcepts,
                params = Some(param)
              )

              mentionsPB += mention

              Some(mention)
            } else {
              None
            }

            tokens.map(y => (y._1, mention))
          }).toList


          val tokensPB = tokensWithMentions.map({ case (token, maybeMention) =>
            var mentionIdList: List[Int] = Nil

            maybeMention match {
              case Some(m) => mentionIdList = m.id :: mentionIdList
              case None =>
            }

            if (token.beginPos >= sourceMention.getPosition.fromPos && token.endPos <= sourceMention.getPosition.toPos)
              mentionIdList = sourceMention.id :: mentionIdList

            Token(
              token = Some(token.word),
              group = Some(token.groupId),
              lemma = Some(token.lemma),
              parserTag = Some(token.parseTag),
              posTag = Some(token.pos),
              mentions = mentionIdList
            )
          })

          Sentente(
            sent = Some(sentence),
            parseResult = tokensPB,
            mentions = mentionsPB,
            nextSentence = Some(postSent),
            prevSentence = Some(prevSent),
            parserName = Some("CoreNLP")
          )
        } catch {
          /**
            * Handle unparsable sentences. Produce minimal output
            */
          case _ : UnparsableException | _ : AssertionError =>

            val sourceMention = ml.generall.sentence.Mention(0,
              position = Some(Position(startMentionPos, endMentionPos)),
              concepts = conceptVariant.map(conceptVariantToPB),
              text = Some(middleChunk.text),
              resolver = Some("wikilink"),
              context = Some(
                Context(-1,
                  left = Some(firstChunkText),
                  right = Some(lastChunkText)
                )
              ))

            Sentente(
              sent = Some(text),
              parserName = Some("None"),
              mentions = Seq(sourceMention)
            )
        }
    }
  }

  def work(lim: Int = -1): WorkerStat = {

    stat.reset
    var mention: Option[Mention] = reader.dispatch()
    while (mention.nonEmpty) {
      try {

        val t0 = System.currentTimeMillis()

        val sent: Sentente = processMention(mention.get)

        val t1 = System.currentTimeMillis()
        val deltaTime = t1 - t0

        stat.processed += 1
        stat.totalTimeMs += deltaTime

        if(deltaTime > stat.maxTimeMs) stat.maxTimeMs = deltaTime
        stat.avgTime = stat.totalTimeMs / stat.processed
        stat.mentionCount += sent.mentions.size

        sent.parserName match {
          case Some("None") => stat.skipCount += 1
          case _ =>
        }

        if(stat.processed % 1000 == 0){
          FileLogger.logToFile(s"${storagePrefix}_log_stat.txt", stat.toString)
        }

        sent.writeDelimitedTo(protobufOutputStream)

        if(stat.processed == lim)
          return stat


      } catch {
        case ex: Exception => {
          println(ex.getMessage)
          ex.printStackTrace()
          FileLogger.logToFile(s"${storagePrefix}_parse_error.txt", ex.getMessage)
        }
      }
      mention = reader.dispatch()
    }
    stat
  }

}
