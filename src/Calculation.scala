case class Calculation(num: BigDecimal, operator: Operator):

  def evaluateWith(other: BigDecimal): Option[BigDecimal] =
    operator.evaluate(num, other)
