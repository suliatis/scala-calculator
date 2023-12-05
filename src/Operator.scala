enum Operator:
  case Plus extends Operator
  case Minus extends Operator
  case Multiply extends Operator
  case Divide extends Operator

  def evaluate(left: BigDecimal, right: BigDecimal): Option[BigDecimal] =
    try
      val result = this match
        case Plus =>
          left + right
        case Minus =>
          left - right
        case Multiply =>
          left * right
        case Divide =>
          left / right
      Some(result)
    catch
      case _: ArithmeticException =>
        None

  def showSign(): String =
    this match
      case Plus =>
        "+"
      case Minus =>
        "-"
      case Multiply =>
        "×"
      case Divide =>
        "÷"

  def key(): String =
    this match
      case Plus =>
        "+"
      case Minus =>
        "-"
      case Multiply =>
        "*"
      case Divide =>
        "/"
