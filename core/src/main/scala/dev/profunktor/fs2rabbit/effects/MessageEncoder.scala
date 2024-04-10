package dev.profunktor.fs2rabbit.effects

import cats.Applicative
import cats.data.Kleisli
import cats.syntax.all._
import dev.profunktor.fs2rabbit.model.{AmqpEnvelope, AmqpMessage}

object MessageEncoder extends MessageEncoderInstances
private sealed trait MessageEncoderInstances{

  implicit def id[F[_]: Applicative]: MessageEncoder[F, AmqpMessage[Array[Byte]]] =
    Kleisli(_.pure[F])

  implicit def bytesEnvelope[F[_]: Applicative]: MessageEncoder[F, AmqpEnvelope[Array[Byte]]] =
    Kleisli(e => AmqpMessage(e.payload, e.properties).pure[F])
}
