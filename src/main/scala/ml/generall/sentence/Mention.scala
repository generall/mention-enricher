// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package ml.generall.sentence



/** Mention description 
  *
  * @param id
  *   id of mention
  *   Required int64 id = 1; 
  * @param resolver
  *   Resolver name: elastic or wikimention
  *   Required
  * @param text
  *   Text of mention
  *   Required
  * @param position
  *   Position of mention
  *   Required
  * @param params
  *   Params of mention
  *   Required
  * @param context
  *   Context of mention
  *   Required
  * @param concepts
  *   Concepts
  */
@SerialVersionUID(0L)
final case class Mention(
    id: Long,
    resolver: scala.Option[String] = None,
    text: String,
    position: ml.generall.sentence.Position,
    params: scala.Option[ml.generall.sentence.Params] = None,
    context: scala.Option[ml.generall.sentence.Context] = None,
    concepts: scala.collection.Seq[ml.generall.sentence.Concept] = Nil
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[Mention] with com.trueaccord.lenses.Updatable[Mention] {
    @transient
    private[this] var __serializedSizeCachedValue: Int = 0
    private[this] def __computeSerializedValue(): Int = {
      var __size = 0
      __size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, id)
      if (resolver.isDefined) { __size += com.google.protobuf.CodedOutputStream.computeStringSize(2, resolver.get) }
      __size += com.google.protobuf.CodedOutputStream.computeStringSize(3, text)
      __size += 1 + com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(position.serializedSize) + position.serializedSize
      if (params.isDefined) { __size += 1 + com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(params.get.serializedSize) + params.get.serializedSize }
      if (context.isDefined) { __size += 1 + com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(context.get.serializedSize) + context.get.serializedSize }
      concepts.foreach(concepts => __size += 1 + com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(concepts.serializedSize) + concepts.serializedSize)
      __size
    }
    final override def serializedSize: Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: com.google.protobuf.CodedOutputStream): Unit = {
      _output__.writeInt64(1, id)
      resolver.foreach { __v =>
        _output__.writeString(2, __v)
      };
      _output__.writeString(3, text)
      _output__.writeTag(4, 2)
      _output__.writeUInt32NoTag(position.serializedSize)
      position.writeTo(_output__)
      params.foreach { __v =>
        _output__.writeTag(5, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      context.foreach { __v =>
        _output__.writeTag(6, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      concepts.foreach { __v =>
        _output__.writeTag(7, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
    }
    def mergeFrom(`_input__`: com.google.protobuf.CodedInputStream): ml.generall.sentence.Mention = {
      var __id = this.id
      var __resolver = this.resolver
      var __text = this.text
      var __position = this.position
      var __params = this.params
      var __context = this.context
      val __concepts = (scala.collection.immutable.Vector.newBuilder[ml.generall.sentence.Concept] ++= this.concepts)
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __id = _input__.readInt64()
          case 18 =>
            __resolver = Some(_input__.readString())
          case 26 =>
            __text = _input__.readString()
          case 34 =>
            __position = com.trueaccord.scalapb.LiteParser.readMessage(_input__, __position)
          case 42 =>
            __params = Some(com.trueaccord.scalapb.LiteParser.readMessage(_input__, __params.getOrElse(ml.generall.sentence.Params.defaultInstance)))
          case 50 =>
            __context = Some(com.trueaccord.scalapb.LiteParser.readMessage(_input__, __context.getOrElse(ml.generall.sentence.Context.defaultInstance)))
          case 58 =>
            __concepts += com.trueaccord.scalapb.LiteParser.readMessage(_input__, ml.generall.sentence.Concept.defaultInstance)
          case tag => _input__.skipField(tag)
        }
      }
      ml.generall.sentence.Mention(
          id = __id,
          resolver = __resolver,
          text = __text,
          position = __position,
          params = __params,
          context = __context,
          concepts = __concepts.result()
      )
    }
    def withId(__v: Long): Mention = copy(id = __v)
    def getResolver: String = resolver.getOrElse("")
    def clearResolver: Mention = copy(resolver = None)
    def withResolver(__v: String): Mention = copy(resolver = Some(__v))
    def withText(__v: String): Mention = copy(text = __v)
    def withPosition(__v: ml.generall.sentence.Position): Mention = copy(position = __v)
    def getParams: ml.generall.sentence.Params = params.getOrElse(ml.generall.sentence.Params.defaultInstance)
    def clearParams: Mention = copy(params = None)
    def withParams(__v: ml.generall.sentence.Params): Mention = copy(params = Some(__v))
    def getContext: ml.generall.sentence.Context = context.getOrElse(ml.generall.sentence.Context.defaultInstance)
    def clearContext: Mention = copy(context = None)
    def withContext(__v: ml.generall.sentence.Context): Mention = copy(context = Some(__v))
    def clearConcepts = copy(concepts = scala.collection.Seq.empty)
    def addConcepts(__vs: ml.generall.sentence.Concept*): Mention = addAllConcepts(__vs)
    def addAllConcepts(__vs: TraversableOnce[ml.generall.sentence.Concept]): Mention = copy(concepts = concepts ++ __vs)
    def withConcepts(__v: scala.collection.Seq[ml.generall.sentence.Concept]): Mention = copy(concepts = __v)
    def getField(__field: com.google.protobuf.Descriptors.FieldDescriptor): scala.Any = {
      __field.getNumber match {
        case 1 => id
        case 2 => resolver.getOrElse(null)
        case 3 => text
        case 4 => position
        case 5 => params.getOrElse(null)
        case 6 => context.getOrElse(null)
        case 7 => concepts
      }
    }
    override def toString: String = com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
    def companion = ml.generall.sentence.Mention
}

object Mention extends com.trueaccord.scalapb.GeneratedMessageCompanion[ml.generall.sentence.Mention] {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[ml.generall.sentence.Mention] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): ml.generall.sentence.Mention = {
    require(__fieldsMap.keys.forall(_.getContainingType() == descriptor), "FieldDescriptor does not match message type.")
    val __fields = descriptor.getFields
    ml.generall.sentence.Mention(
      __fieldsMap(__fields.get(0)).asInstanceOf[Long],
      __fieldsMap.get(__fields.get(1)).asInstanceOf[scala.Option[String]],
      __fieldsMap(__fields.get(2)).asInstanceOf[String],
      __fieldsMap(__fields.get(3)).asInstanceOf[ml.generall.sentence.Position],
      __fieldsMap.get(__fields.get(4)).asInstanceOf[scala.Option[ml.generall.sentence.Params]],
      __fieldsMap.get(__fields.get(5)).asInstanceOf[scala.Option[ml.generall.sentence.Context]],
      __fieldsMap.getOrElse(__fields.get(6), Nil).asInstanceOf[scala.collection.Seq[ml.generall.sentence.Concept]]
    )
  }
  def descriptor: com.google.protobuf.Descriptors.Descriptor = SentenceProto.descriptor.getMessageTypes.get(4)
  def messageCompanionForField(__field: com.google.protobuf.Descriptors.FieldDescriptor): com.trueaccord.scalapb.GeneratedMessageCompanion[_] = {
    require(__field.getContainingType() == descriptor, "FieldDescriptor does not match message type.")
    var __out: com.trueaccord.scalapb.GeneratedMessageCompanion[_] = null
    __field.getNumber match {
      case 4 => __out = ml.generall.sentence.Position
      case 5 => __out = ml.generall.sentence.Params
      case 6 => __out = ml.generall.sentence.Context
      case 7 => __out = ml.generall.sentence.Concept
    }
  __out
  }
  def enumCompanionForField(__field: com.google.protobuf.Descriptors.FieldDescriptor): com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__field)
  lazy val defaultInstance = ml.generall.sentence.Mention(
    id = 0L,
    text = "",
    position = ml.generall.sentence.Position.defaultInstance
  )
  implicit class MentionLens[UpperPB](_l: com.trueaccord.lenses.Lens[UpperPB, ml.generall.sentence.Mention]) extends com.trueaccord.lenses.ObjectLens[UpperPB, ml.generall.sentence.Mention](_l) {
    def id: com.trueaccord.lenses.Lens[UpperPB, Long] = field(_.id)((c_, f_) => c_.copy(id = f_))
    def resolver: com.trueaccord.lenses.Lens[UpperPB, String] = field(_.getResolver)((c_, f_) => c_.copy(resolver = Some(f_)))
    def optionalResolver: com.trueaccord.lenses.Lens[UpperPB, scala.Option[String]] = field(_.resolver)((c_, f_) => c_.copy(resolver = f_))
    def text: com.trueaccord.lenses.Lens[UpperPB, String] = field(_.text)((c_, f_) => c_.copy(text = f_))
    def position: com.trueaccord.lenses.Lens[UpperPB, ml.generall.sentence.Position] = field(_.position)((c_, f_) => c_.copy(position = f_))
    def params: com.trueaccord.lenses.Lens[UpperPB, ml.generall.sentence.Params] = field(_.getParams)((c_, f_) => c_.copy(params = Some(f_)))
    def optionalParams: com.trueaccord.lenses.Lens[UpperPB, scala.Option[ml.generall.sentence.Params]] = field(_.params)((c_, f_) => c_.copy(params = f_))
    def context: com.trueaccord.lenses.Lens[UpperPB, ml.generall.sentence.Context] = field(_.getContext)((c_, f_) => c_.copy(context = Some(f_)))
    def optionalContext: com.trueaccord.lenses.Lens[UpperPB, scala.Option[ml.generall.sentence.Context]] = field(_.context)((c_, f_) => c_.copy(context = f_))
    def concepts: com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[ml.generall.sentence.Concept]] = field(_.concepts)((c_, f_) => c_.copy(concepts = f_))
  }
  final val ID_FIELD_NUMBER = 1
  final val RESOLVER_FIELD_NUMBER = 2
  final val TEXT_FIELD_NUMBER = 3
  final val POSITION_FIELD_NUMBER = 4
  final val PARAMS_FIELD_NUMBER = 5
  final val CONTEXT_FIELD_NUMBER = 6
  final val CONCEPTS_FIELD_NUMBER = 7
}
