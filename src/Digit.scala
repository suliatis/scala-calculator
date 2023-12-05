import scala.compiletime.error
import scala.compiletime.ops.string.*
import scala.compiletime.requireConst

opaque type Digit = Char

extension (self: Digit)

  def show(): String =
    self.toString()

  def key(): String =
    self.toString()

  def asInt(): Int =
    self.asDigit

object Digit:

  inline def apply(char: Char): Digit =
    requireConst(char)
    inline char match
      case '0' => '0'
      case '1' => '1'
      case '2' => '2'
      case '3' => '3'
      case '4' => '4'
      case '5' => '5'
      case '6' => '6'
      case '7' => '7'
      case '8' => '8'
      case '9' => '9'
      case _ =>
        error("")
