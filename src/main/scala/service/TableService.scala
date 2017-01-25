package service

trait TableService[Table, Position, TablePosition] {

  def position(table: Table, position: Position): TablePosition

}
