import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class Mux5Test extends AnyFlatSpec with ChiselScalatestTester {
  "Mux5 " should "pass" in {
    test(new Mux5()) { dut =>
    val map = Map(
      0.U -> dut.io.a, 
      1.U -> dut.io.b, 
      2.U -> dut.io.c,
      3.U -> dut.io.d,
      4.U -> dut.io.e
    )
    for ((i, x) <- map) x.poke(i)
    for ((i, x) <- map) {
      dut.io.sel.poke(i)
      dut.io.y.expect(i)
    }

    }
  }
}
