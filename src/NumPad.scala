import cats.effect.IO
import tyrian.Html
import tyrian.Html.*
import tyrian.Sub

case class NumPad(
    isCleared: Boolean = true,
):

  def view(): Html[NumKey.Msg] =
    div(`class` := "buttons")(
      NumPad.keys(isCleared).map:
        _.view(),
    )

  def subscriptions(): Sub[IO, NumKey.Msg] =
    Sub.Batch(
      NumPad.keys().map:
        _.subscriptions(),
    )

object NumPad:

  def keys(): List[NumKey] =
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
      NumKey.AllClearKey,
      NumKey.ClearKey,
    )

  def keys(isCleared: Boolean): List[NumKey] =
    keys().filter: key =>
      key match
        case NumKey.AllClearKey =>
          isCleared
        case NumKey.ClearKey =>
          !isCleared
        case _ =>
          true
