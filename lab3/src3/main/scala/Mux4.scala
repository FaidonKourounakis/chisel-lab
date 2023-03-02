import chisel3._

/**
  * Use Mux2 components to build a 4:1 multiplexer
  */

class Mux4 extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val c = Input(UInt(1.W))
    val d = Input(UInt(1.W))
    val sel = Input(UInt(2.W))
    val y = Output(UInt(1.W))
  })

  // ***** your code starts here *****

  // create a Mux4 component out of Mux2 components
  // and connect the input and output ports.
  val ab = Module(new Mux2)
  ab.io.a := io.a
  ab.io.b := io.b
  ab.io.sel := io.sel(0)
  val cd = Module(new Mux2)
  cd.io.a := io.c
  cd.io.b := io.d
  cd.io.sel := io.sel(0)
  val abcd = Module(new Mux2)
  abcd.io.a := ab.io.y
  abcd.io.b := cd.io.y
  abcd.io.sel := io.sel(1)
  io.y := abcd.io.y


  // ***** your code ends here *****
}