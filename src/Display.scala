enum Display:
  case Cleared
  case Error
  case Input(value: String)
  case Result(value: BigDecimal)

  def show(): String =
    this match
      case Cleared =>
        "0"
      case Error =>
        "ERROR"
      case Input(value) =>
        value
      case Result(value) =>
        value.toString()

  def append(digit: Char): Display =
    this match
      case Cleared | Error | Result(_) =>
        Input(digit.toString())
      case Input(value) =>
        Input(value + digit)

  def appendDecimal(): Display =
    this match
      case Cleared | Error | Result(_) =>
        Input("0.")
      case Input(value) if !value.contains(".") =>
        Input(value + ".")
      case show @ Input(_) =>
        show

  def readInput(): Option[BigDecimal] =
    this match
      case Cleared | Error | Result(_) =>
        None
      case Input(value) =>
        Some(BigDecimal(value))

  def clear(): Display =
    Cleared

  def isCleared(): Boolean =
    this match
      case Cleared | Error =>
        true
      case Input(_) | Result(_) =>
        false

  def isResult(): Boolean =
    this match
      case Result(_) =>
        true
      case _ =>
        false
