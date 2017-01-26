package model

sealed trait Direction
case object North extends Direction
case object South extends Direction
case object East extends Direction
case object West extends Direction

case class Position(x: Int, y: Int)

sealed trait Command
case class Place(position: Position, direction: Direction) extends Command
case object Move extends Command
case object Left extends Command
case object Right extends Command
case object Report extends Command

case class Table(x: Range, y: Range)

sealed trait TablePosition
case class OnTable(position: Position) extends TablePosition
case object OffTable extends TablePosition

sealed trait Robot
case class OnTableRobot(tablePosition: OnTable, direction: Direction) extends Robot
case object OffTableRobot extends Robot
