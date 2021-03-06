package service.interpreter

import model.{OffTable, OnTable, Position}
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSpec, Matchers}
import service.interpreter.TableService.position

class TableServiceSpec extends FunSpec with Matchers with PropertyChecks {

  describe("position") {
    it("should return OnTable when position is on table") {
      val onTableX = Gen.choose(0, 4)
      val onTableY = Gen.choose(0, 4)
      val table = model.Table(0 to 4)

      forAll(onTableX, onTableY) { (x: Int, y: Int) =>
        position(table, Position(x, y)) shouldBe OnTable(Position(x, y))
      }
    }

    it("should return OffTable when position is off table") {
      val offTableX = Gen.choose(4, 9)
      val offTableY = Gen.choose(5, 10)
      val table = model.Table(0 to 4)

      forAll(offTableX, offTableY) { (x: Int, y: Int) =>
        position(table, Position(x, y)) shouldBe OffTable
      }
    }
  }

}
