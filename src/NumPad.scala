import tyrian.Html
import tyrian.Html.*

case class NumPad(
    isCleared: Boolean = true,
):

  def view(): Html[NumPad.Msg] =
    div(`class` := "buttons")(
      NumPad.keys(isCleared).map:
        _.view(),
    )

object NumPad:

  enum Msg:
    case KeyPressed(key: NumKey)
    case KeyReleased(key: NumKey)

  def keys(isCleared: Boolean): List[NumKey] =
    List(
      NumKey.OperatorKey(Operator.Plus),
      NumKey.OperatorKey(Operator.Minus),
      NumKey.OperatorKey(Operator.Multiply),
      NumKey.OperatorKey(Operator.Divide),
      NumKey.DigitKey(Digit('7')),
      NumKey.DigitKey(Digit('8')),
      NumKey.DigitKey(Digit('9')),
      NumKey.DigitKey(Digit('4')),
      NumKey.DigitKey(Digit('5')),
      NumKey.DigitKey(Digit('6')),
      NumKey.DigitKey(Digit('1')),
      NumKey.DigitKey(Digit('2')),
      NumKey.DigitKey(Digit('3')),
      NumKey.DigitKey(Digit('0')),
      NumKey.EqualsKey,
      NumKey.DecimalKey,
      if isCleared then NumKey.AllClearKey else NumKey.ClearKey,
    )
