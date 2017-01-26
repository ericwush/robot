package service

import scala.util.Try

trait CommandService[Command, Direction, Position, Table, Robot] {

  def parsePosition(x: String, y: String): Option[Position]

  def parseDirection(direction: String): Option[Direction]

  def parseCommand(commandLine: String): Try[Command]

  def executeCommand(maybeRobot: Option[Robot], command: Command): Table => Try[Robot]

}
