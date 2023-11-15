import Display.*

object DisplayTest extends weaver.SimpleIOSuite:
  pureTest("Display.show"):
    expect.all(
      Cleared.show() == "0",
      Input("1").show() == "1",
      Result(BigDecimal("1")).show() == "1",
    )

  pureTest("Display.appand"):
    expect.all(
      Cleared.append('1') == Input("1"),
      Input("1").append('2') == Input("12"),
      Result(BigDecimal("1")).append('1') == Input("1"),
    )

  pureTest("Display.appendDecimal"):
    expect.all(
      Cleared.appendDecimal() == Input("0."),
      Input("1").appendDecimal() == Input("1."),
      Input("1.").appendDecimal() == Input("1."),
      Input("1.0").appendDecimal() == Input("1.0"),
      Result(BigDecimal("1")).appendDecimal() == Input("0."),
    )

  pureTest("Display.readInput"):
    expect.all(
      Cleared.readInput() == None,
      Input("1").readInput() == Some(BigDecimal("1")),
      Result(BigDecimal("1")).readInput() == None,
    )

  pureTest("Display.clear"):
    expect.all(
      Cleared.clear() == Cleared,
      Input("1").clear() == Cleared,
      Result(BigDecimal("1")).clear() == Cleared,
    )
