package phe.logging

import phe._

object Logging {

  def resource[F[_]](implicit F: Sync[F]): Resource[F, Logging[F]] =
    new Logging[F] {
      implicit override protected val sync: Sync[F] = F
    }.pure[Resource[F, *]]
}

sealed trait Logging[F[_]] {
  implicit protected def sync: Sync[F]
  def of(a:    Any):    Logger[F] = Logger.getLoggerFromClass[F](a.getClass())
  def named(s: String): Logger[F] = Logger.getLoggerFromName[F](s)
}
