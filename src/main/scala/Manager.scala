import akka.actor.{Actor, ActorRef, Props}
import akka.routing.{ ActorRefRoutee, RoundRobinRoutingLogic, Router }

class Manager extends Actor {

  var router: Router = {
    val routees = Vector.fill(8) {
      val r = context.actorOf(Props[PhotoActor]())
      context.watch(r)
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def receive: Receive = {
    case f: Forward => router.route(ProcessImage(f.file, f.pathOut, f.threshold, f.func), sender())
    case _ => println("Unknown message to manager")
  }
}
