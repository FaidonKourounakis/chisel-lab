import chisel3._

class Count15 extends Module {
  val io = IO(new Bundle {
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****

  val countReg = RegInit(0.U(4.W))
  countReg := countReg + 1.U
  res := countReg

  // ***** your code ends here *****

  io.dout := res
}