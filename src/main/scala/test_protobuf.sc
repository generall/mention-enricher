import java.io.{FileInputStream, FileOutputStream}

import ml.generall.sentence.Concept
import ml.generall.sentence.Sentente

def foo() = {
  val msg1 = Concept("link1").withHits(17)
  val msg2 = Concept("link2").withHits(27)
  val msg3 = Concept("link3").withHits(37)
  val msg4 = Concept("link4").withHits(47)
  val outStream1 = new FileOutputStream("/tmp/msg1.pb")
  val outStream2 = new FileOutputStream("/tmp/msg2.pb")
  msg1.writeDelimitedTo(outStream1)
  msg2.writeDelimitedTo(outStream1)
  msg3.writeDelimitedTo(outStream2)
  msg4.writeDelimitedTo(outStream2)

  outStream1.flush()
  outStream1.close()

  outStream2.flush()
  outStream2.close()

  val inputStream = new FileInputStream("/tmp/msg2.pb")
  val c1 = Concept.streamFromDelimitedInput(inputStream).toList
  println(c1)
}

def foo2() = {
  val msg1 = Concept("link1").withHits(17)
  val msg2 = Concept("link2").withHits(27)
  val msg3 = Concept("link3").withHits(37)
  val msg4 = Concept("link4").withHits(47)
  val outStream1 = new FileOutputStream("/tmp/msg1.pb")
  val outStream2 = new FileOutputStream("/tmp/msg2.pb")
  msg1.writeTo(outStream1)
  msg2.writeTo(outStream1)
  msg3.writeTo(outStream2)
  msg4.writeTo(outStream2)

  outStream1.flush()
  outStream1.close()

  outStream2.flush()
  outStream2.close()

  val inputStream = new FileInputStream("/tmp/msg2.pb")


  val c1 = Concept.parseFrom(inputStream)
  val c2 = Concept.parseFrom(inputStream)
  println(c1.toString)
  println(c2.toString)
}

val inputStream = new FileInputStream("/home/generall/sources/Sci/ml/mention-enricher/src/main/resources/worker_6_sentences.pb")
val s = Sentente.streamFromDelimitedInput(inputStream).head
s.nextSentence
//val outStream = new FileOutputStream("/tmp/st_temp.pb")
//s.writeTo(outStream)

