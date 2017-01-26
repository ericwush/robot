package service
package interpreter

import model._

class RobotServiceInterpreter extends RobotService[Table, Position, Direction, Robot] {

  def place(table: Table, position: Position, direction: Direction): Robot = {
    TableService.position(table, position) match {
      case OnTable(pos) => OnTableRobot(OnTable(pos), direction)
      case OffTable => OffTableRobot
    }
  }

  def move(table: Table)(robot: Robot): Robot = {
    def next(tablePosition: OnTable, direction: Direction) = {
      val position = tablePosition.position

      val nextPosition = direction match {
        case North => Position(position.x, position.y + 1)
        case South => Position(position.x, position.y - 1)
        case East => Position(position.x + 1, position.y)
        case West => Position(position.x - 1, position.y)
      }

      TableService.position(table, nextPosition) match {
        case OnTable(pos) => OnTableRobot(OnTable(pos), direction)
        case OffTable => robot
      }
    }

    robot match {
      case OnTableRobot(position, direction) => next(position, direction)
      case _ => OffTableRobot
    }
  }

  def left(robot: Robot): Robot = {
    def next(tablePosition: OnTable, direction: Direction) = {
      val nextDirection = direction match {
        case North => West
        case West => South
        case South => East
        case East => North
      }

      OnTableRobot(tablePosition, nextDirection)
    }

    robot match {
      case OnTableRobot(position, direction) => next(position, direction)
      case _ => OffTableRobot
    }
  }

  def right(robot: Robot): Robot = {
    def next(tablePosition: OnTable, direction: Direction) = {
      val nextDirection = direction match {
        case North => East
        case East => South
        case South => West
        case West => North
      }

      OnTableRobot(tablePosition, nextDirection)
    }

    robot match {
      case OnTableRobot(position, direction) => next(position, direction)
      case _ => OffTableRobot
    }
  }

  def report(robot: Robot): (Position, Direction) = ???

}

object RobotService extends RobotServiceInterpreter
