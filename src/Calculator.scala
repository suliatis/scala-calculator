import cats.syntax.all.*

case class Calculator(
    display: Display = Display.Cleared,
    input: Numeral = Numeral.zero,
    calculation: Option[Calculation] = None,
    numpad: NumPad = NumPad(),
):

  def showDisplay(): String =
    display.show()

  def onKeyPressed(key: NumKey): Calculator =
    copy(
      numpad = numpad.press(key),
    )

  def onKeyReleased(key: NumKey): Calculator =
    val next = key match
      case NumKey.OperatorKey(operator) =>
        enterOperator(operator)
      case NumKey.DigitKey(digit) =>
        enterDigit(digit)
      case NumKey.EqualsKey =>
        enterEquals()
      case NumKey.DecimalKey =>
        enterDecimal()
      case NumKey.ClearKey =>
        clear()
      case NumKey.AllClearKey =>
        allClear()

    next.copy(
      numpad = numpad.release(),
    )

  def enterDigit(digit: Digit): Calculator =
    val input_ = display match
      case Display.Result(_) | Display.Cleared | Display.Error =>
        Numeral.zero.append(digit)
      case _ =>
        input.append(digit)

    this.copy(
      display = Display.Input(input_),
      input = input_,
      numpad = NumPad(isCleared = false),
    )

  def enterDecimal(): Calculator =
    val input_ = display match
      case Display.Result(_) | Display.Cleared | Display.Error =>
        Numeral.zero.appendDecimal()
      case _ =>
        input.appendDecimal()

    this.copy(
      display = Display.Input(input_),
      input = input_,
      numpad = NumPad(isCleared = false),
    )

  def enterOperator(operator: Operator): Calculator =
    calculation
      .filter: _ =>
        display.isResult()
      .map: calculation_ =>
        this.copy(
          calculation = Some(calculation_.copy(operator = operator)),
        )
      .orElse:
        for
          c <- calculation if c.operator != operator
          r = c.evaluateWith(input.asDecimal()) match
            case None =>
              this.copy(
                display = Display.Error,
                input = Numeral.zero,
                calculation = None,
                numpad = NumPad(isCleared = false),
              )
            case Some(result) =>
              this.copy(
                display = Display.Result(result),
                calculation = Some(Calculation(result, operator)),
                numpad = NumPad(isCleared = false),
              )
        yield r
      .getOrElse:
        this.copy(
          display = Display.Result(input.asDecimal()),
          calculation = Some(Calculation(input.asDecimal(), operator)),
          numpad = NumPad(isCleared = false),
        )

  def enterEquals(): Calculator =
    calculation match
      case Some(calculation) =>
        calculation.evaluateWith(input.asDecimal())
          .map: result =>
            this.copy(
              display = Display.Result(result),
              calculation = Some(calculation.copy(num = result)),
              numpad = NumPad(isCleared = false),
            )
          .getOrElse:
            this.copy(
              display = Display.Error,
              input = Numeral.zero,
              calculation = None,
              numpad = NumPad(isCleared = false),
            )
      case _ =>
        this

  def clear(): Calculator =
    this.copy(
      display = display.clear(),
      numpad = NumPad(isCleared = true),
    )

  def allClear(): Calculator =
    this.copy(
      input = Numeral.zero,
      calculation = None,
    )
