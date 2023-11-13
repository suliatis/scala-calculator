enum Calculation:
  case Empty
  case Repeatable(left: BigDecimal, operator: Operator, right: BigDecimal)

  def evaluate(right: BigDecimal): Either[ArithmeticException, BigDecimal] =
    this match
      case Empty =>
        Right(right)
      case Repeatable(left, operator, _) =>
        evaluate(left, operator, right)

  def repeat(): Option[Either[ArithmeticException, BigDecimal]] =
    this match
      case Empty =>
        None
      case Repeatable(left, operator, right) =>
        Some(evaluate(left, operator, right))

  private def evaluate(
      left: BigDecimal,
      operator: Operator,
      right: BigDecimal,
  ): Either[ArithmeticException, BigDecimal] =
    try
      Right(operator.evaluate(left, right))
    catch
      case ex: ArithmeticException =>
        Left(ex)

  def withOperator(operator: Operator): Calculation =
    this match
      case Empty =>
        Empty
      case Repeatable(left, _, right) =>
        Repeatable(left, operator, right)

  def withOperands(left: BigDecimal, right: BigDecimal): Calculation =
    this match
      case Empty =>
        Empty
      case Repeatable(_, operator, _) =>
        Repeatable(left, operator, right)

  def withLeft(left: BigDecimal): Calculation =
    this match
      case Empty =>
        Empty
      case Repeatable(_, operator, right) =>
        Repeatable(left, operator, right)
