import akka.actor.{ActorSystem, Props}

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
