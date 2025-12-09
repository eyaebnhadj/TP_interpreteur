package test
import pcf.PCF

object TestInterp extends App:
  test("green0")
  test("green1")
  test("green2")
  test("green3")
  test("green4")
  test("green5")
  test("green6")
  test("green7")
  test("green8")
  test("green9")

  test("blue0")
  test("blue1")
  test("blue2")
  test("blue3")
  test("blue4")
  test("blue5")
  test("blue6")
  test("blue7")
  test("blue8")
  test("blue9")
  test("blue10")

  test("red0")
  test("red1")
  test("red2")
  test("red3")
  test("red4")
  test("red5")
  test("red6")
  test("red7")
  test("red11")
  test("red13")
  test("red14")
  test("red15")
  test("red16")
  test("red17")
  test("red18")
  test("red19")
  test("red40")
  test("red41")
  test("red42")
  test("red43")

  test("black0")
  test("black1")
  test("black2")
  test("black3")
  // test("black4") // loops
  // test("black5") // loops

  private def test(filename: String): Unit =
    val dir = "test/"
    val args = Array(dir + filename + ".pcf", "-v", "-i")
    println(s"**********  ${args(0)}")
    try
      PCF.main(args)
    catch
      case ex: Exception => println(s"${ex.getClass} ${ex.getMessage}")