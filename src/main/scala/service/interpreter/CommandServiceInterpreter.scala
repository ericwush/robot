package service
package interpreter

import model._
import service.interpreter.RobotService._

import scala.util.{Success, Try}

class CommandServiceInterpreter extends CommandService[Command, Direction, Position, Table, Robot] {

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

  def parseCommand(commandLine: String): Try[Command] = {
    Try({
      val command = commandLine.trim.split(" ")
      command(0) match {
        case "PLACE" => parsePlaceCommand(command)
        case "MOVE" => checkNonArguments(command, Move)
        case "LEFT" => checkNonArguments(command, Left)
        case "RIGHT" => checkNonArguments(command, Right)
        case "REPORT" => checkNonArguments(command, Report)
        case _ => throw new IllegalArgumentException("Invalid command")
      }
    })
  }

  private def checkNonArguments(commandString: Array[String], command: Command): Command = {
    if (commandString.length > 1)
      throw new IllegalArgumentException(s"Wrong number of argument for ${command.toString.toUpperCase} command")
    command
  }

  private def parsePlaceCommand(commandString: Array[String]): Place = {
    if (commandString.length != 2)
      throw new IllegalArgumentException("Wrong number of argument for PLACE command")
    val args = commandString(1).split(",")
    if (args.length != 3)
      throw new IllegalArgumentException("Wrong number of argument for PLACE command")
    val maybePlaceCommand = for {
      position <- parsePosition(args(0).trim, args(1).trim)
      direction <- parseDirection(args(2).trim)
      placeCommand <- Some(Place(position, direction))
    } yield placeCommand
    maybePlaceCommand match {
      case Some(placeCommand) => placeCommand
      case None => throw new IllegalArgumentException("Invalid arguments for PLACE command")
    }
  }

  def executeCommand(maybeRobot: Option[Robot], command: Command): Table => Try[Robot] =
    table => {
      command match {
        case Place(position, direction) => Success(place(table, position, direction))
        case Move => action(maybeRobot, move(table))
        case Left => action(maybeRobot, left)
        case Right => action(maybeRobot, right)
      }
    }

  private def action(maybeRobot: Option[Robot], action: Robot => Robot): Try[Robot] = {
    Try({
      maybeRobot match {
        case Some(robot) => action(robot)
        case None => throw new IllegalStateException("Command discarded, must first place the robot")
      }
    })
  }
}

object CommandService extends CommandServiceInterpreter
