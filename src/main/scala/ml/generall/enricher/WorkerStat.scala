package ml.generall.enricher

/**
  * Created by generall on 07.10.16.
  */
case class WorkerStat(
                       var processed: Int = 0,
                       var mentionCount: Int = 0,
                       var skipCount: Int = 0,
                       var maxTimeMs: Long = 0,
                       var totalTimeMs: Long = 0,
                       var avgTime: Long = 0
                     ) {

  def reset = {
    processed = 0
    mentionCount = 0
    skipCount = 0
    maxTimeMs = 0
    totalTimeMs = 0
    avgTime = 0
  }

}
