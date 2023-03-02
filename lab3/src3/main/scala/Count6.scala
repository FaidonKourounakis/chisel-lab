import chisel3._

class Count6 extends Module {
  val io = IO(new Bundle {
    val dout = Output(UInt(8.W))
  })

  val res = RegInit(0.U(3.W))
  res := res + 1.U
  when(res === 6.U) {
    res := 0.U
  }

  // ***** your code ends here *****

  io.dout := res
}