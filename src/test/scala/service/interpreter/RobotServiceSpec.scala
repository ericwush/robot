package service.interpreter

import model._
import org.scalatest.{FunSpec, Matchers}

import scala.collection.immutable.Range.Inclusive

class RobotServiceSpec extends FunSpec with Matchers {

  val tableX: Inclusive = 0 to 4
  val tableY: Inclusive = 0 to 4
  val table = model.Table(tableX, tableY)

  describe("place") {
    it("return OffTableRobot when position is off table") {
      RobotService.place(table, Position(5, 0), South) shouldBe OffTableRobot
    }

    it("return OnTableRobot when position is on table") {
      RobotService.place(table, Position(0, 0), South) shouldBe OnTableRobot(OnTable(Position(0, 0)), South)
    }
  }

  describe("move") {
    it("should be ignored by off table robot") {
      RobotService.move(table, OffTableRobot) shouldBe OffTableRobot
    }

    describe("on table robot") {
      describe("should return same robot when moving position is off table") {
        it("when facing South") {
          val robot = OnTableRobot(OnTable(Position(0, 0)), South)
          RobotService.move(table, robot) shouldBe robot
        }

        it("when facing North") {
          val robot = OnTableRobot(OnTable(Position(0, 4)), North)
          RobotService.move(table, robot) shouldBe robot
        }

        it("when facing East") {
          val robot = OnTableRobot(OnTable(Position(4, 0)), East)
          RobotService.move(table, robot) shouldBe robot
        }

        it("when facing West") {
          val robot = OnTableRobot(OnTable(Position(0, 0)), West)
          RobotService.move(table, robot) shouldBe robot
        }
      }

      describe("should return new robot when moving position is on table") {
        it("when facing South") {
          val robot = OnTableRobot(OnTable(Position(0, 1)), South)
          val newRobot = OnTableRobot(OnTable(Position(0, 0)), South)
          RobotService.move(table, robot) shouldBe newRobot
        }

        it("when facing North") {
          val robot = OnTableRobot(OnTable(Position(0, 3)), North)
          val newRobot = OnTableRobot(OnTable(Position(0, 4)), North)
          RobotService.move(table, robot) shouldBe newRobot
        }

        it("when facing East") {
          val robot = OnTableRobot(OnTable(Position(3, 0)), East)
          val newRobot = OnTableRobot(OnTable(Position(4, 0)), East)
          RobotService.move(table, robot) shouldBe newRobot
        }

        it("when facing West") {
          val robot = OnTableRobot(OnTable(Position(1, 0)), West)
          val newRobot = OnTableRobot(OnTable(Position(0, 0)), West)
          RobotService.move(table, robot) shouldBe newRobot
        }
      }
    }
  }

  describe("left") {
    it("should be ignored by off table robot") {
      RobotService.left(OffTableRobot) shouldBe OffTableRobot
    }

    describe("on table robot should return new robot") {
      val position = OnTable(Position(0, 0))

      it("when facing South") {
        val robot = OnTableRobot(position, South)
        val newRobot = OnTableRobot(position, East)
        RobotService.left(robot) shouldBe newRobot
      }

      it("when facing North") {
        val robot = OnTableRobot(position, North)
        val newRobot = OnTableRobot(position, West)
        RobotService.left(robot) shouldBe newRobot
      }

      it("when facing East") {
        val robot = OnTableRobot(position, East)
        val newRobot = OnTableRobot(position, North)
        RobotService.left(robot) shouldBe newRobot
      }

      it("when facing West") {
        val robot = OnTableRobot(position, West)
        val newRobot = OnTableRobot(position, South)
        RobotService.left(robot) shouldBe newRobot
      }
    }
  }

  describe("right") {
    it("should be ignored by off table robot") {
      RobotService.right(OffTableRobot) shouldBe OffTableRobot
    }

    describe("on table robot should return new robot") {
      val position = OnTable(Position(0, 0))

      it("when facing South") {
        val robot = OnTableRobot(position, South)
        val newRobot = OnTableRobot(position, West)
        RobotService.right(robot) shouldBe newRobot
      }

      it("when facing North") {
        val robot = OnTableRobot(position, North)
        val newRobot = OnTableRobot(position, East)
        RobotService.right(robot) shouldBe newRobot
      }

      it("when facing East") {
        val robot = OnTableRobot(position, East)
        val newRobot = OnTableRobot(position, South)
        RobotService.right(robot) shouldBe newRobot
      }

      it("when facing West") {
        val robot = OnTableRobot(position, West)
        val newRobot = OnTableRobot(position, North)
        RobotService.right(robot) shouldBe newRobot
      }
    }
  }

}
