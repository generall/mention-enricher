package ml.generall

import ml.generall.enricher.{Reader, Worker}

/**
  * Created by generall on 08.10.16.
  */
object ConvertApp extends App{


  case class Config(
                     var threads: Int = 1,
                     var limit: Int = -1,
                     var offset:Int = 0
                   )


  val parser = new scopt.OptionParser[Config]("scopt") {
    head("Wikimention converter", "3.x")

    opt[Int]('t', "threads").action( (x, c) => {
      c.threads = x
      c
    } ).text("Number of threads")

    opt[Int]('l', "limit").action( (x, c) => {
      c.limit = x
      c
    } ).text("Limit sentences per worker")

    opt[Int]('o', "offset").action( (x, c) => {
      c.offset = x
      c
    } ).text("Offset sentences")

    help("help").text("prints this usage text")

  }

  // parser.parse returns Option[C]
  parser.parse(args, Config()) match {
    case Some(config) =>
      if(config.offset > 0) {
        Reader.skipMany(config.offset)
      }
      (1 to config.threads).par.foreach( x => {
        val worker = new Worker(s"worker_$x")
        val stat = worker.work(config.limit)
        println(stat.toString)
      })

    case None =>
    // arguments are bad, error message will have been displayed
  }


}
