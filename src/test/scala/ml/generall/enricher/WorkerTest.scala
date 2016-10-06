package ml.generall.enricher

import ml.generall.elastic.Mention
import org.scalatest.FunSuite

/**
  * Created by generall on 05.10.16.
  */
class WorkerTest extends FunSuite {

  test("testProcessMention") {
    val worker = new Worker("test_")

    val m = Mention(
      "This is left context. I will go to",
      "Kirov City",
      "on the next New Year. This is right context.",
      "http://kirov.ru",
      1.0,
      ""
    )

    val s = worker.processMention(m)

    println(s.toString)

  }

}
