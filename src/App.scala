import cats.effect.IO
import tyrian.Cmd
import tyrian.Html
import tyrian.Html.*
import tyrian.Location
import tyrian.Routing
import tyrian.Sub
import tyrian.TyrianApp

case object Ignore

type Msg = Ignore.type | NumKey.Msg

type Model = Calculator

object App extends TyrianApp[Msg, Model]:

  override def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    Calculator()
      -> Cmd.None

  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case NumKey.Msg.KeyPressed(_) =>
      model ->
        Cmd.None
    case NumKey.Msg.KeyReleased(key) =>
      model.onKeyReleased(key) ->
        Cmd.None
    case Ignore =>
      model ->
        Cmd.None

  override def view(model: Model): Html[Msg] =
    div(`class` := "base")(
      div(`class` := "display")(
        span()(model.showDisplay()),
      ),
      model.numpad.view(),
    )

  override def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.Batch(
      model.numpad.subscriptions(),
    )

  override def router: Location => Msg =
    Routing.none(Ignore)

  @main def launchApp(): Unit =
    launch("app")
