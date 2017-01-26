package service.interpreter

import model._
import org.scalatest.{FunSpec, Matchers}

class CommandServiceSpec extends FunSpec with Matchers {

  describe("parse direction") {
    it("should return East") {
      CommandService.parseDirection("EAST").get shouldBe East
    }

    it("should return West") {
      CommandService.parseDirection("WEST").get shouldBe West
    }

    it("should return North") {
      CommandService.parseDirection("NORTH").get shouldBe North
    }

    it("should return South") {
      CommandService.parseDirection("SOUTH").get shouldBe South
    }

    it("should return None for invalid direction") {
      CommandService.parseDirection("FOO") shouldBe None
    }
  }

  describe("parse position") {
    it("should return parsed position") {
      CommandService.parsePosition("-1", "7").get shouldBe Position(-1, 7)
    }

    it("should return None for invalid position") {
      CommandService.parsePosition("-1", "a") shouldBe None
    }
  }

  describe("parse command") {
    describe("parse Place command") {
      it("should return None for invalid args") {
        CommandService.parseCommand("PLACE") shouldBe None
        CommandService.parseCommand("PLACE 1,2,3,WEST") shouldBe None
        CommandService.parseCommand("PLACE 1,2,FOO") shouldBe None
        CommandService.parseCommand("PLACE 1,a,WEST") shouldBe None
        CommandService.parseCommand("PLACE 1,2") shouldBe None
        CommandService.parseCommand("PLACE 1,2,WEST FOO") shouldBe None
      }

      it("should return Place command") {
        CommandService.parseCommand("PLACE 1,2,WEST").get shouldBe Place(Position(1, 2), West)
      }
    }

    it("should return Move command") {
      CommandService.parseCommand("MOVE").get shouldBe Move
    }

    it("should return Left command") {
      CommandService.parseCommand("LEFT").get shouldBe Left
    }

    it("should return Right command") {
      CommandService.parseCommand("RIGHT").get shouldBe Right
    }

    it("should return Report command") {
      CommandService.parseCommand("REPORT").get shouldBe Report
    }

    it("should return None for invalid command") {
      CommandService.parseCommand("FOO") shouldBe None
      CommandService.parseCommand(" ") shouldBe None
      CommandService.parseCommand(null) shouldBe None
    }
  }

}
