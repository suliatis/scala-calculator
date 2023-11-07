enum Calculator:
  case Init(input: String = "0")
  case Result(result: BigDecimal, operator: Operator, input: BigDecimal)
  case OperationPending(result: BigDecimal, operator: Operator, input: String)
  case Error

  def showDisplay(): String =
    this match
      case Init(input) =>
        input
      case Result(result, operator, input) =>
        result.toString
      case OperationPending(result, operator, input) =>
        input
      case Error =>
        "ERROR"

  def enterDigit(digit: Char): Calculator =
    this match
      case Init(input) =>
        if input == "0" then
          Init("" + digit)
        else
          Init(input + digit)
      case Result(result, operator, input) =>
        OperationPending(result, operator, "" + digit)
      case OperationPending(result, operator, input) =>
        if input == "0" then
          OperationPending(result, operator, "" + digit)
        else
          OperationPending(result, operator, input + digit)
      case Error =>
        Init("" + digit)

  def enterDecimal(): Calculator =
    this match
      case new_ @ Init(input) =>
        if input.contains(".") then
          new_
        else
          Init(input + ".")
      case Result(result, operator, input) =>
        OperationPending(result, operator, "0.")
      case pending @ OperationPending(result, operator, input) =>
        if input.contains(".") then
          pending
        else
          OperationPending(result, operator, input + ".")
      case Error =>
        Init("0.")

  def enterOperator(operator: Operator): Calculator =
    this match
      case Init(input) =>
        val result = BigDecimal(input)
        Result(result, operator, result)
      case Result(result, _, input) =>
        Result(result, operator, result)
      case OperationPending(result, operator, input) =>
        try
          val input_ = BigDecimal(input)
          val result_ = operator.evaluate(result, input_)
          Result(result_, operator, input_)
        catch
          case _ =>
            Error
      case Error =>
        Error

  def enterEquals(): Calculator =
    this match
      case init: Init =>
        init
      case Result(result, operator, input) =>
        val result_ = operator.evaluate(result, input)
        Result(result_, operator, input)
      case OperationPending(result, operator, input) =>
        try
          val input_ = BigDecimal(input)
          val result_ = operator.evaluate(result, input_)
          Result(result_, operator, input_)
        catch
          case _ =>
            Error
      case Error =>
        Error

  def showClear(): String =
    this match
      case Init("0") =>
        "AC"
      case Init(_) =>
        "C"
      case Result(_, _, _) =>
        "C"
      case OperationPending(_, _, "0") =>
        "AC"
      case OperationPending(_, _, _) =>
        "C"
      case Error =>
        "AC"

  def clear(): Calculator =
    this match
      case Init(input) =>
        Init()
      case Result(result, operator, input) =>
        OperationPending(result, operator, "0")
      case OperationPending(result, operator, "0") =>
        Init()
      case OperationPending(result, operator, _) =>
        OperationPending(result, operator, "0")
      case Error =>
        Init()
