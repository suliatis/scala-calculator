case class Calculator(
    display: Display = Display.Cleared,
    calculation: Calculation = Calculation.Empty,
):

  def showDisplay(): String =
    display.show()

  def enterDigit(digit: Char): Calculator =
    val display_ = display.append(digit)
    this.copy(
      display = display_,
    )

  def enterDecimal(): Calculator =
    val display_ = display.appendDecimal()
    this.copy(
      display = display_,
    )

  def enterOperator(operator: Operator): Calculator =
    display.readInput()
      .map: right =>
        val result = calculation.evaluate(right)
        result match
          case Left(_) =>
            this.copy(
              display = Display.Error,
              calculation = Calculation.Empty,
            )
          case Right(value) =>
            this.copy(
              display = Display.Result(value),
              calculation = Calculation.Repeatable(
                value,
                operator,
                right,
              ),
            )
      .getOrElse:
        this.copy(
          calculation = calculation.withOperator(operator),
        )
  end enterOperator

  def enterEquals(): Calculator =
    display.readInput()
      .map: right =>
        val result = calculation.repeat()
        result match
          case Some(Left(_)) =>
            this.copy(
              display = Display.Error,
              calculation = Calculation.Empty,
            )
          case Some(Right(value)) =>
            this.copy(
              display = Display.Result(value),
              calculation = calculation.withOperands(value, right),
            )
          case None =>
            this
      .getOrElse:
        val result = calculation.repeat()
        result match
          case Some(Left(_)) =>
            this.copy(
              display = Display.Error,
              calculation = Calculation.Empty,
            )
          case Some(Right(value)) =>
            this.copy(
              display = Display.Result(value),
              calculation = calculation.withLeft(value),
            )
          case None =>
            this
  end enterEquals

  def showClear(): String =
    if display.isCleared() then
      "AC"
    else
      "C"

  def clear(): Calculator =
    if display.isCleared() then
      this.copy(
        calculation = Calculation.Empty,
      )
    else
      this.copy(
        display = display.clear(),
      )
