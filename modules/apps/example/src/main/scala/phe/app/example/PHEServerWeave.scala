package phe.app.example

import phe._
import phe.logging._

object PHEServerWeave {

  @scala.annotation.nowarn
  def resource[F[_]](implicit
    F:     ConcurrentEffect[F],
    timer: Timer[F],
    cs:    ContextShift[F],
  ): Resource[F, PHEServerWeave[F]] = for {
    implicit0(logging: Logging[F]) <- Logging.resource[F]
    implicit0(weaveLogger: Logger[F]) <- logging.named("phe.weave").pure[Resource[F, *]]
    implicit0(weaveLoggerR: Logger[Resource[F, *]]) <- weaveLogger.mapK(Resource.liftK).pure[Resource[F, *]]
    _ <- weaveLoggerR.info(s"Initialized loggers, continuing onto every thing else")

  } yield PHEServerWeave(
    logger = weaveLogger
  )
}

final case class PHEServerWeave[F[_]](
  logger: Logger[F]
)
