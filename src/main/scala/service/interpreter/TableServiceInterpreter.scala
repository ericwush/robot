package service
package interpreter

import model._

class TableServiceInterpreter extends TableService[Table, Position, TablePosition] {

  def position(table: Table, position: Position): TablePosition = {
    if ((table.x contains position.x) && (table.y contains position.y))
      OnTable(position)
    else
      OffTable
  }

}

object TableService extends TableServiceInterpreter
