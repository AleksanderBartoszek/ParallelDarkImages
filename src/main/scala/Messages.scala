import java.io.File

/**
 * case classes for messaging between actors
 * (currently only one used)
 */
sealed trait Message
case class ProcessImage(file: File, pathOut: String, threshold: Int, func: Double => Int) extends Message