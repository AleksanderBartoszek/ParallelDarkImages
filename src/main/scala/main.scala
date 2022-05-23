import akka.actor.{ActorSystem, Props}

/** Program entry point
 *
 * @param in path to folder with images to process
 * @param out path where to save processed images
 * @param threshold  minimal score for discarding image as too dark
 * @param func type of function used to calculate how bright the image is
 */
@main
def ParallelDarkImages(in: String, out: String, threshold: Int, func: String): Unit = {
  val system = ActorSystem("system")
  val manager = system.actorOf(Props(Manager()))

  val inputPath = in + "/"
  val outputPath = out + "/"
  val cutOff = threshold
  val function = func match {
    case "linear" => linear
    case "dark" => darkSteep
    case _ => commonSteep
  }

  for (e <- getDirectory(inputPath).listFiles) {
    manager ! ProcessImage(e, outputPath, cutOff, function)
  }
}
