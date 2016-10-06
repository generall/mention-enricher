import java.io.{FileInputStream, FileOutputStream}

import com.google.protobuf.CodedOutputStream
import ml.generall.sentence.Concept


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

val inputStream = new FileInputStream("/tmp/msg_cat.pb")

Concept.streamFromDelimitedInput(inputStream).toList