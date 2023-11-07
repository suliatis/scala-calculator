enum Operator(val evaluate: (BigDecimal, BigDecimal) => BigDecimal):
  case Plus extends Operator(_ + _)
  case Minus extends Operator(_ - _)
  case Multiply extends Operator(_ * _)
  case Divide extends Operator(_ / _)
