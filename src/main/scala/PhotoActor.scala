import javax.imageio.ImageIO
import akka.actor.Actor

class PhotoActor extends Actor {
  override def receive: Receive = {
    case p: ProcessImage => process(p)
    case _ => println("Unknown message to photoActor")
  }

  def process(p: ProcessImage): Unit = {
    val image = ImageIO.read(p.file)
    val data = bufferedImageToData(image)
    val pixelLightness = lightness(data)
    val points = score(p.func, mean(pixelLightness), median(pixelLightness))

    val format = p.file.getName.split("[.]+").last
    val name = p.file.getName.split("[.]+").head
    val outDir = getDirectory(p.pathOut)

    if (points < p.threshold)
      saveImageToFile(image, outDir, name + "_bright_" + points + "." + format, format)
    else
      saveImageToFile(image, outDir, name + "_dark_" + points + "." + format, format)
  }
}
