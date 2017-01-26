package service.interpreter

import model._
import org.scalatest.{FunSpec, Matchers}
import service.interpreter.CommandService._

import scala.collection.immutable.Range.Inclusive

class CommandServiceSpec extends FunSpec with Matchers {

  describe("parse direction") {
    it("should return East") {
      parseDirection("EAST").get shouldBe East
    }

    it("should return West") {
      parseDirection("WEST").get shouldBe West
    }

    it("should return North") {
      parseDirection("NORTH").get shouldBe North
    }

    it("should return South") {
      parseDirection("SOUTH").get shouldBe South
    }

    it("should return None for invalid direction") {
      parseDirection("FOO") shouldBe None
    }
  }

  describe("parse position") {
    it("should return parsed position") {
      parsePosition("-1", "7").get shouldBe Position(-1, 7)
    }

    it("should return None for invalid position") {
      parsePosition("-1", "a") shouldBe None
    }
  }

  describe("parse command") {
    describe("parse Place command") {
      it("should return failure for invalid args") {
        parseCommand("PLACE").left.get shouldBe "Wrong number of argument for PLACE command"
        parseCommand("PLACE 1,2,3,WEST").left.get shouldBe "Wrong number of argument for PLACE command"
        parseCommand("PLACE 1,2,FOO").left.get shouldBe "Invalid arguments for PLACE command"
        parseCommand("PLACE 1,a,WEST").left.get shouldBe "Invalid arguments for PLACE command"
        parseCommand("PLACE 1,2").left.get shouldBe "Wrong number of argument for PLACE command"
        parseCommand("PLACE 1,2,WEST FOO").left.get shouldBe "Wrong number of argument for PLACE command"
      }

      it("should return Place command") {
        parseCommand("PLACE 1,2,WEST").right.get shouldBe Place(Position(1, 2), West)
      }
    }

    it("should return Move command") {
      parseCommand("MOVE").right.get shouldBe Move
    }

    it("should return Left command") {
      parseCommand("LEFT").right.get shouldBe Left
    }

    it("should return Right command") {
      parseCommand("RIGHT").right.get shouldBe Right
    }

    it("should return Report command") {
      parseCommand("REPORT").right.get shouldBe Report
    }

    it("should return failure for invalid command") {
      parseCommand("FOO").left.get shouldBe "Invalid command"
      parseCommand(" ").left.get shouldBe "Invalid command"
      parseCommand("MOVE FOO ").left.get shouldBe "Wrong number of argument for MOVE command"
      parseCommand("LEFT FOO ").left.get shouldBe "Wrong number of argument for LEFT command"
      parseCommand("RIGHT FOO ").left.get shouldBe "Wrong number of argument for RIGHT command"
      parseCommand("REPORT FOO ").left.get shouldBe "Wrong number of argument for REPORT command"
    }
  }

  describe("execute command") {
    val tableX: Inclusive = 0 to 4
    val tableY: Inclusive = 0 to 4
    val table = model.Table(tableX, tableY)

    it("should execute PLACE command") {
      val placeCommand = Place(Position(1, 2), North)
      val triedRobot = executeCommand(None, placeCommand)(table)
      triedRobot.isRight shouldBe true
      triedRobot.right.get shouldBe OnTableRobot(OnTable(Position(1, 2)), North)
    }

    it("should discard MOVE command if no robot") {
      val triedRobot = executeCommand(None, Move)(table)
      triedRobot.isLeft shouldBe true
      triedRobot.left.get shouldBe "Command discarded, must first place the robot"
    }

    it("should execute MOVE command") {
      val robot = OnTableRobot(OnTable(Position(1, 2)), North)
      val triedRobot = executeCommand(Some(robot), Move)(table)
      triedRobot.isRight shouldBe true
      triedRobot.right.get shouldBe OnTableRobot(OnTable(Position(1, 3)), North)
    }

    it("should discard LEFT command if no robot") {
      val triedRobot = executeCommand(None, Left)(table)
      triedRobot.isLeft shouldBe true
      triedRobot.left.get shouldBe "Command discarded, must first place the robot"
    }

    it("should execute LEFT command") {
      val robot = OnTableRobot(OnTable(Position(1, 2)), North)
      val triedRobot = executeCommand(Some(robot), Left)(table)
      triedRobot.isRight shouldBe true
      triedRobot.right.get shouldBe OnTableRobot(OnTable(Position(1, 2)), West)
    }

    it("should discard RIGHT command if no robot") {
      val triedRobot = executeCommand(None, Right)(table)
      triedRobot.isLeft shouldBe true
      triedRobot.left.get shouldBe "Command discarded, must first place the robot"
    }

    it("should execute RIGHT command") {
      val robot = OnTableRobot(OnTable(Position(1, 2)), North)
      val triedRobot = executeCommand(Some(robot), Right)(table)
      triedRobot.isRight shouldBe true
      triedRobot.right.get shouldBe OnTableRobot(OnTable(Position(1, 2)), East)
    }

    it("should discard REPORT command if no robot") {
      val triedRobot = executeCommand(None, Report)(table)
      triedRobot.isLeft shouldBe true
      triedRobot.left.get shouldBe "Command discarded, must first place the robot"
    }

    it("should execute REPORT command") {
      val robot = OnTableRobot(OnTable(Position(1, 2)), North)
      val triedRobot = executeCommand(Some(robot), Report)(table)
      triedRobot.isLeft shouldBe true
      triedRobot.left.get shouldBe "1,2,NORTH"
    }
  }

}
