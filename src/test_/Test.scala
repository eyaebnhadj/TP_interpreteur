package test
import pcf.PCF.main

@main
def test(): Unit =
  test("test/test0.calc")

def test(file: String): Unit =
  val args = Array(file)
  main(args)


