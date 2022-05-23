import akka.actor.ActorRef
import java.io.File

sealed trait Response
case class Result(score: Int) extends Response

sealed trait Message
case class Forward(file: File, pathOut: String, threshold: Int, func: Double => Int) extends Message

sealed trait Task
case class ProcessImage(file: File, pathOut: String, threshold: Int, func: Double => Int) extends Task