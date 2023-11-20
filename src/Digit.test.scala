import scala.compiletime.testing.Error
import scala.compiletime.testing.typeCheckErrors
import scala.compiletime.testing.typeChecks

object DigitTest extends weaver.SimpleIOSuite:

  pureTest("Digit.apply"):
    expect.all(
      typeChecks("""Digit('0')"""),
      typeChecks("""Digit('1')"""),
      typeChecks("""Digit('2')"""),
      typeChecks("""Digit('3')"""),
      typeChecks("""Digit('4')"""),
      typeChecks("""Digit('5')"""),
      typeChecks("""Digit('6')"""),
      typeChecks("""Digit('7')"""),
      typeChecks("""Digit('8')"""),
      typeChecks("""Digit('9')"""),
      typeCheckErrors("""val a = '9'; Digit(a)""") != Nil,
      typeCheckErrors("""Digit('a')""") != Nil,
      typeCheckErrors("""Numeral("123.45.6")""") != Nil,
    )
