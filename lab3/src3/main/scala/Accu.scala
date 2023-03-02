import chisel3._

class Accu extends Module {
  val io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val setZero = Input(Bool())
    val dout = Output(UInt(8.W))
  })

  val reg = RegInit(0.U(8.W))

  reg := reg + io.din

  when (io.setZero) {
    reg := 0.U
  }

  io.dout := reg
}