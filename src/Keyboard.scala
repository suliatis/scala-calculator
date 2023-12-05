import cats.effect.IO
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.window
import tyrian.Sub

object Keyboard:

  def onKeyPressed(key: String): Sub[IO, KeyboardEvent] =
    onKeyEvent("keydown", key)

  def onKeyReleased(key: String): Sub[IO, KeyboardEvent] =
    onKeyEvent("keyup", key)

  private def onKeyEvent(eventName: String, key: String): Sub[IO, KeyboardEvent] =
    Sub.fromEvent[IO, KeyboardEvent, KeyboardEvent](eventName, window): event =>
      if event.key == key then
        println(event.key)
        Some(event)
      else
        None
