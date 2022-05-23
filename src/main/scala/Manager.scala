import akka.actor.{Actor, Props}
import akka.routing.{ ActorRefRoutee, RoundRobinRoutingLogic, Router }

/** Single actor responsible for load balancing work for other actors
 *  Currently set to 8 actors processing images, could be changed to suit user
 *  Currently set routing to RoundRobin, can be swapped for any other algorithm
 */
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
    case f: ProcessImage => router.route(f, sender())
    case _ => println("Unknown message to manager")
  }
}
