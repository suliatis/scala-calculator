import scala.compiletime.testing.Error
import scala.compiletime.testing.typeCheckErrors
import scala.compiletime.testing.typeChecks

object NumeralTest extends weaver.SimpleIOSuite:

  pureTest("Numeral.apply"):
    expect.all(
      typeChecks("""Numeral("0")"""),
      typeChecks("""Numeral("123")"""),
      typeChecks("""Numeral("123.")"""),
      typeChecks("""Numeral("123.01")"""),
      typeCheckErrors("""val a = "1"; Numeral(a)""") != Nil,
      typeCheckErrors("""Numeral("a")""") != Nil,
      typeCheckErrors("""Numeral("123.45.6")""") != Nil,
    )

  pureTest("Numeral.append"):
    expect.all(
      Numeral("0").append(Digit('1')) == Numeral(1),
      Numeral("1").append(Digit('1')) == Numeral(11),
      Numeral("1.").append(Digit('1')) == Numeral(1.1),
    )

  pureTest("Numeral.appendDecimal"):
    expect.all(
      Numeral("0").appendDecimal() == Numeral("0."),
      Numeral("12").appendDecimal() == Numeral("12."),
      Numeral("1.0").appendDecimal() == Numeral("1.0"),
    )
