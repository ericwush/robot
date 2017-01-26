package service
package interpreter

import model._

import scala.util.Try

class CommandServiceInterpreter extends CommandService[Command, Direction, Position] {

  def parsePosition(x: String, y: String): Option[Position] =
    Try(Position(x.toInt, y.toInt)).toOption

  def parseDirection(direction: String): Option[Direction] =
    direction match {
      case "EAST" => Some(East)
      case "SOUTH" => Some(South)
      case "WEST" => Some(West)
      case "NORTH" => Some(North)
      case _ => None
  }

  def parseCommand(commandLine: String): Option[Command] = {
    Try({
      val command = commandLine.trim.split(" ")
      if (command.size > 2)
        throw new IllegalArgumentException("Too many arguments")
      command(0) match {
        case "PLACE" => parsePlaceCommand(command(1))
        case "MOVE" => Some(Move)
        case "LEFT" => Some(Left)
        case "RIGHT" => Some(Right)
        case "REPORT" => Some(Report)
        case _ => None
      }
    }).toOption getOrElse None
  }

  private def parsePlaceCommand(argString: String): Option[Place] = {
    Try({
      val args = argString.split(",")
      for {
        position <- parsePosition(args(0).trim, args(1).trim)
        direction <- parseDirection(args(2).trim)
        placeCommand <- Some(Place(position, direction))
      } yield placeCommand
    }).toOption getOrElse None
  }


}

object CommandService extends CommandServiceInterpreter
