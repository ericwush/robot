package service

trait CommandService[Command, Direction, Position, Table, Robot] {

  def parsePosition(x: String, y: String): Option[Position]

  def parseDirection(direction: String): Option[Direction]

  def parseCommand(commandLine: String): Either[String, Command]

  def executeCommand(maybeRobot: Option[Robot], command: Command)(implicit table: Table): Either[String, Robot]

}
