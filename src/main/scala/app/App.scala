package app

import model.{Robot, Table}
import service.interpreter.CommandService._

import scala.io.StdIn

object App {

  def main(args: Array[String]): Unit = {
    val tableX = 0 to 4
    val tableY = 0 to 4
    val table = Table(tableX, tableY)

    // Wanted to use State Monad but didn't fully get it
    var history: List[Option[Robot]] = List[Option[Robot]](None)
    while(true) {
      val commandString = StdIn.readLine()
      val robot: Either[String, Robot] = for {
        c <- parseCommand(commandString)
        r <- executeCommand(history.head, c)(table)
      } yield r

      robot match {
        case scala.util.Right(r) => history = Some(r) :: history
        case scala.util.Left(m) => println(m)
      }
    }
  }

}
