package phe.app.example

import phe._

object PHEExampleServer {

  def runServer[F[_]](implicit F: ConcurrentEffect[F], timer: Timer[F], cs: ContextShift[F]): F[ExitCode] = {
    val serverWeave: Resource[F, PHEServerWeave[F]] = PHEServerWeave.resource[F]
    serverWeave.use(weave => weave.logger.info("successfully initalized pureharm-example. shutting down.").as(ExitCode.Success))
  }
}
