import cats.syntax.all.*

case class Calculator(
    display: Display = Display.Cleared,
    input: Numeral = Numeral.zero,
    calculation: Option[Calculation] = None,
):

  def showDisplay(): String =
    display.show()

  def enterDigit(digit: Digit): Calculator =
    val input_ = display match
      case Display.Result(_) | Display.Cleared | Display.Error =>
        Numeral.zero.append(digit)
      case _ =>
        input.append(digit)

    this.copy(
      display = Display.Input(input_),
      input = input_,
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
              )
            case Some(result) =>
              this.copy(
                display = Display.Result(result),
                calculation = Some(Calculation(result, operator)),
              )
        yield r
      .getOrElse:
        this.copy(
          display = Display.Result(input.asDecimal()),
          calculation = Some(Calculation(input.asDecimal(), operator)),
        )

  def enterEquals(): Calculator =
    calculation match
      case Some(calculation) =>
        calculation.evaluateWith(input.asDecimal())
          .map: result =>
            this.copy(
              display = Display.Result(result),
              calculation = Some(calculation.copy(num = result)),
            )
          .getOrElse:
            this.copy(
              display = Display.Error,
              input = Numeral.zero,
              calculation = None,
            )
      case _ =>
        this

  def showClear(): String =
    if display.isCleared() then
      "AC"
    else
      "C"

  def clear(): Calculator =
    if display.isCleared() then
      this.copy(
        input = Numeral.zero,
        calculation = None,
      )
    else
      this.copy(
        display = display.clear(),
      )
