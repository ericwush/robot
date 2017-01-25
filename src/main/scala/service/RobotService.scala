package service

trait RobotService[Position, Direction, Robot] {

  def place(position: Position, direction: Direction): Robot

  def move(robot: Robot): Robot

  def left(robot: Robot): Robot

  def right(robot: Robot): Robot

  def report(robot: Robot): (Position, Direction)

}
