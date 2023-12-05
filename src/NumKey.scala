import cats.effect.IO
import tyrian.Html
import tyrian.Html.*
import tyrian.Sub

enum NumKey:
  case OperatorKey(operator: Operator)
  case DigitKey(digit: Digit)
  case EqualsKey
  case DecimalKey
  case ClearKey
  case AllClearKey

  def showLabel(): String =
    this match
      case OperatorKey(operator) =>
        operator.showSign()
      case DigitKey(digit) =>
        digit.show()
      case EqualsKey =>
        "="
      case DecimalKey =>
        "."
      case ClearKey =>
        "C"
      case AllClearKey =>
        "AC"

  def kind(): String =
    this match
      case OperatorKey(operator) =>
        "operator"
      case DigitKey(digit) =>
        "digit"
      case EqualsKey =>
        "equals"
      case DecimalKey =>
        "decimal"
      case ClearKey =>
        "clear"
      case AllClearKey =>
        "all_clear"

  def onEnter() =
    onClick(NumKey.Msg.KeyReleased(this))

  def view(): Html[NumKey.Msg] =
    button(
      `class` := kind(),
      onEnter(),
    )(showLabel())

  def keys(): List[String] =
    this match
      case OperatorKey(operator) =>
        List(operator.key())
      case DigitKey(digit) =>
        List(digit.key())
      case EqualsKey =>
        List("=", "Enter")
      case DecimalKey =>
        List(".", ",")
      case ClearKey | AllClearKey =>
        List("Backspace", "Delete")

  def subscriptions(): Sub[IO, NumKey.Msg] =
    Sub.Combine(
      Sub.Batch(
        keys().map: key =>
          Keyboard.onKeyPressed(key).map: _ =>
            NumKey.Msg.KeyPressed(this),
      ),
      Sub.Batch(
        keys().map: key =>
          Keyboard.onKeyReleased(key).map: _ =>
            NumKey.Msg.KeyReleased(this),
      ),
    )

object NumKey:
  enum Msg:
    case KeyPressed(key: NumKey)
    case KeyReleased(key: NumKey)
