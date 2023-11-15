case class Calculation(num: BigDecimal, operator: Operator):

  def evaluateWith(other: BigDecimal): Option[BigDecimal] =
    try
      Some(operator.evaluate(num, other))
    catch
      case _: ArithmeticException =>
        None
