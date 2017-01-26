package service

trait CommandService[Command, Direction, Position] {

  def parsePosition(x: String, y: String): Option[Position]

  def parseDirection(direction: String): Option[Direction]

  def parseCommand(commandLine: String): Option[Command]

}
