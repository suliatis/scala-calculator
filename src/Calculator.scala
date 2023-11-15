import cats.syntax.all.*

case class Calculator(
    display: Display = Display.Cleared,
    input: Option[BigDecimal] = None,
    calculation: Option[Calculation] = None,
):

  def showDisplay(): String =
    display.show()

  def enterDigit(digit: Char): Calculator =
    val display_ = display.append(digit)
    this.copy(
      display = display_,
      input = display_.readInput(),
    )

  def enterDecimal(): Calculator =
    val display_ = display.appendDecimal()
    this.copy(
      display = display_,
      input = display_.readInput(),
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
          i <- input
          r = c.evaluateWith(i) match
            case None =>
              this.copy(
                display = Display.Error,
                input = None,
                calculation = None,
              )
            case Some(result) =>
              this.copy(
                display = Display.Result(result),
                calculation = Some(Calculation(result, operator)),
              )
        yield r
      .getOrElse:
        val i = input.getOrElse(BigDecimal(0))
        this.copy(
          display = Display.Result(i),
          calculation = Some(Calculation(i, operator)),
        )

  def enterEquals(): Calculator =
    (calculation, input) match
      case (Some(calculation), Some(input)) =>
        calculation.evaluateWith(input)
          .map: result =>
            this.copy(
              display = Display.Result(result),
              calculation = Some(calculation.copy(num = result)),
            )
          .getOrElse:
            this.copy(
              display = Display.Error,
              input = None,
              calculation = None,
            )
      case (_, _) =>
        this

  def showClear(): String =
    if display.isCleared() then
      "AC"
    else
      "C"

  def clear(): Calculator =
    if display.isCleared() then
      this.copy(
        input = None,
        calculation = None,
      )
    else
      this.copy(
        display = display.clear(),
      )
