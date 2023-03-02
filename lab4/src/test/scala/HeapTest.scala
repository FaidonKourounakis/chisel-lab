
import heap._
import Heap.Operation
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class HeapTest extends AnyFlatSpec with ChiselScalatestTester {

  behavior of "Heap"

  it should "present the currently largest number on io.root while ready and not empty" in {
    test(new TestHeap) { dut =>

      // setup a new operation
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(220.U)
      dut.io.valid.poke(1.B)

      // step to start operation
      dut.clock.step()

      // the operation has been initiated and we can deassert valid
      dut.io.valid.poke(0.B)

      // wait for the dut to get ready again
      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      // the inserted value should appear as the largest value for now
      dut.io.root.expect(220.U)

      // write more test code here

    }
  }

  it should "assert empty after all numbers have been removed" in {
    test(new TestHeap) { dut =>
      // fill with numbers 1-8
      val a = Range.inclusive(1, 8)
      dut.io.op.poke(Operation.Insert)
      for (n <- a) {
        dut.io.valid.poke(true)
        dut.io.newValue.poke(n)
        dut.clock.step()
        dut.io.valid.poke(false)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }
      // empty it
      dut.io.op.poke(Operation.RemoveRoot)
      for (n <- a) {
        dut.io.empty.expect(false)
        dut.io.valid.poke(true)
        dut.clock.step()
        dut.io.valid.poke(false)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }
      dut.io.empty.expect(true)
    }
  }

  it should "assert full when 8 numbers have been inserted" in {
    test(new TestHeap) { dut =>
      // fill with numbers 1-8
      val a = Range.inclusive(1, 8)
      dut.io.op.poke(Operation.Insert)
      for (n <- a) {
        dut.io.valid.poke(true)
        dut.io.newValue.poke(n)
        dut.clock.step()
        dut.io.valid.poke(false)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }
      dut.io.full.expect(true)
      
      // wait for the dut to get ready again
    }
  }

  it should "deassert full after one number is removed when it was full" in {
    test(new TestHeap) { dut =>
      // fill it up
      val a = Range.inclusive(1, 8)
      dut.io.op.poke(Operation.Insert)
      for (n <- a) {
        dut.io.valid.poke(true)
        dut.io.newValue.poke(n)
        dut.clock.step()
        dut.io.valid.poke(false)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }
      // remove one and check
      dut.io.valid.poke(true)
      dut.io.op.poke(Operation.RemoveRoot)
      dut.clock.step()
      dut.io.valid.poke(true)
      while (!dut.io.ready.peekBoolean()) dut.clock.step()
      dut.io.full.expect(false)
    }
  }

  it should "not change the sequence if new insertions are issued when it is full" in {
    test(new TestHeap) { dut =>
      // fill it up
      val a = Range.inclusive(1, 9)
      dut.io.op.poke(Operation.Insert)
      for (n <- a) {
        dut.io.valid.poke(true)
        dut.io.newValue.poke(n)
        dut.clock.step()
        dut.io.valid.poke(false)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }
      dut.io.root.expect(8) //not 9
    }
  }

}
