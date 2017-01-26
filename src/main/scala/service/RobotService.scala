package service

trait RobotService[Table, Position, Direction, Robot] {

  def place(table: Table, position: Position, direction: Direction): Robot

  def move(table: Table, robot: Robot): Robot

  def left(robot: Robot): Robot

  def right(robot: Robot): Robot

  def report(robot: Robot): (Position, Direction)

}
