package phe.app.example

import phe._

object PHEMain extends PureharmIOApp {

  override val ioRuntime: Later[(ContextShift[IO], Timer[IO])] = IORuntime.defaultMainRuntime("phe")

  override def run(args: List[String]): IO[ExitCode] =
    PHEExampleServer.runServer[IO](
      IO.ioConcurrentEffect(contextShift),
      timer,
      contextShift,
    )

}
