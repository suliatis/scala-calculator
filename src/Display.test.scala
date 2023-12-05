import Display.*

object DisplayTest extends weaver.SimpleIOSuite:

  pureTest("Display.show"):
    expect.all(
      Cleared.show() == "0",
      Error.show() == "ERROR",
      Input("1").show() == "1",
      Result(BigDecimal("1")).show() == "1",
    )

  pureTest("Display.readInput"):
    expect.all(
      Cleared.readInput() == None,
      Error.readInput() == None,
      Input("1").readInput() == Some(BigDecimal("1")),
      Result(BigDecimal("1")).readInput() == None,
    )

  pureTest("Display.clear"):
    expect.all(
      Cleared.clear() == Cleared,
      Error.clear() == Cleared,
      Input("1").clear() == Cleared,
      Result(BigDecimal("1")).clear() == Cleared,
    )

  pureTest("Display.isResult"):
    expect.all(
      Cleared.isResult() == false,
      Error.isResult() == false,
      Input("1").isResult() == false,
      Result(BigDecimal("1")).isResult() == true,
    )
