package app

import model.{Robot, Table}
import service.interpreter.CommandService._

import scala.io.StdIn

object App {

  def main(args: Array[String]): Unit = {
    val tableX = 0 to 4
    val tableY = 0 to 4
    implicit val table = Table(tableX, tableY)

    // Wanted to use State Monad but didn't fully get it
    var history: List[Option[Robot]] = List[Option[Robot]](None)
    while(true) {
      val commandString = StdIn.readLine()
      val messageOrRobot: Either[String, Robot] = executeCommandLine(commandString, history.head)

      messageOrRobot match {
        case scala.util.Right(r) => history = Some(r) :: history
        case scala.util.Left(m) => println(m)
      }
    }
  }

}
