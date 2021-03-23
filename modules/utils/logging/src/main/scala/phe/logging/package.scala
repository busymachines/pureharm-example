package phe

import org.typelevel.log4cats

package object logging {
  type Logger[F[_]] = log4cats.StructuredLogger[F]
  val Logger: log4cats.slf4j.Slf4jLogger.type = log4cats.slf4j.Slf4jLogger
}
