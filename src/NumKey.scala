import tyrian.Html
import tyrian.Html.*

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
    onClick(NumPad.Msg.KeyReleased(this))

  def view(): Html[NumPad.Msg] =
    button(
      `class` := kind(),
      onEnter(),
    )(showLabel())
