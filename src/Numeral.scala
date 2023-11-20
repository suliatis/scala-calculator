import scala.compiletime.constValue
import scala.compiletime.error
import scala.compiletime.ops.string.*

opaque type Numeral = String

extension (self: Numeral)

  def asDecimal(): BigDecimal =
    BigDecimal(self)

  def append(digit: Digit): Numeral =
    if self == Numeral.zero then
      "" + digit
    else
      self + digit

  def appendDecimal(): Numeral =
    if self.contains(".") then
      self
    else
      self + "."

object Numeral:
  val zero = Numeral("0")
  val zeroWithDecimal = Numeral("0.")

  // def apply(string: String): Option[Numeral] =
  //   val integerPattern = """^(\d+)(\.)?$""".r
  //   val decimalPattern = """^(\d+)(\.\d+)$""".r
  //   string match
  //     case integerPattern(_, _) =>
  //       Some(string)
  //     case decimalPattern(_, _) =>
  //       Some(string)
  //     case _ =>
  //       None

  private inline val integerPattern = """^(\d+)(\.)?$"""
  private inline val decimalPattern = """^(\d+)(\.\d+)$"""

  inline def apply(string: String): Numeral =
    inline if constValue[Matches[string.type, integerPattern.type]] then
      string
    else if constValue[Matches[string.type, decimalPattern.type]] then
      string
    else
      error(s"Got a string that is not a numeric literal: $string")

  def apply(decimal: BigDecimal): Numeral =
    decimal.toString()
