import cats.effect.IO
import tyrian.Cmd
import tyrian.Html
import tyrian.Html.*
import tyrian.Location
import tyrian.Routing
import tyrian.Sub
import tyrian.TyrianApp

enum Msg:
  case EnterOperator(operator: Operator)
  case EnterEquals
  case EnterDecimal
  case Enter(input: Char)
  case Clear
  case Ignore

type Model = Calculator

object App extends TyrianApp[Msg, Model]:

  override def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    Calculator()
      -> Cmd.None

  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Msg.EnterOperator(operator) =>
      model.enterOperator(operator)
        -> Cmd.None
    case Msg.EnterEquals =>
      model.enterEquals()
        -> Cmd.None
    case Msg.Enter(digit) =>
      model.enterDigit(digit)
        -> Cmd.None
    case Msg.EnterDecimal =>
      model.enterDecimal()
        -> Cmd.None
    case Msg.Clear =>
      model.clear()
        -> Cmd.None
    case Msg.Ignore =>
      model
        -> Cmd.None

  override def view(model: Model): Html[Msg] =
    div(`class` := "base")(
      div(`class` := "display")(
        span()(model.showDisplay()),
      ),
      div(`class` := "buttons")(
        button(
          `class` := "operator",
          onClick(Msg.EnterOperator(Operator.Plus)),
        )("+"),
        button(
          `class` := "operator",
          onClick(Msg.EnterOperator(Operator.Minus)),
        )("-"),
        button(
          `class` := "operator",
          onClick(Msg.EnterOperator(Operator.Multiply)),
        )("×"),
        button(
          `class` := "operator",
          onClick(Msg.EnterOperator(Operator.Divide)),
        )("÷"),
        button(
          `class` := "equals",
          onClick(Msg.EnterEquals),
        )("="),
        button(onClick(Msg.Enter('7')))("7"),
        button(onClick(Msg.Enter('8')))("8"),
        button(onClick(Msg.Enter('9')))("9"),
        button(onClick(Msg.Enter('4')))("4"),
        button(onClick(Msg.Enter('5')))("5"),
        button(onClick(Msg.Enter('6')))("6"),
        button(onClick(Msg.Enter('1')))("1"),
        button(onClick(Msg.Enter('2')))("2"),
        button(onClick(Msg.Enter('3')))("3"),
        button(onClick(Msg.Enter('0')))("0"),
        button(onClick(Msg.EnterDecimal))("."),
        button(onClick(Msg.Clear))(model.showClear()),
      ),
    )

  override def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None

  override def router: Location => Msg =
    Routing.none(Msg.Ignore)

  @main def launchApp(): Unit =
    launch("app")
