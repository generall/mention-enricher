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

    val badM = Mention(
      "",
      "I am very clever, push everything in one sentence",
      "",
      "http://stupid.com",
      1.0,
      ""
    )

    val veryBadM = Mention(
      "I want to",
      "desptoy this good analyser. Because I am",
      "cookhacker",
      "http://stupid.com",
      1.0,
      ""
    )

    val list = List(m, badM, veryBadM).par

    val mList = list.map(x => worker.processMention(x)).toList

    println(mList)

  }


  test("testDispatcher"){
    val worker = new Worker("test_")

    (0 to 20).par.foreach(x => {
      worker.reader.dispatch() match {
        case Some(m) => println(x.toString ++ " " ++ m.href)
      }
    })

  }


  test("testWorker"){
    (0 to 3).par.foreach( x => {
      val worker = new Worker(s"test_$x")
      val stat = worker.work(40)
      println(stat.toString)
    })

  }

}
