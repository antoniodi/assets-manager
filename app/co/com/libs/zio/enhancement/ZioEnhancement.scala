package co.com.libs.zio.enhancement

import zio.ZIO.{ foreach, none }
import zio.{ BuildFrom, IO, ZIO }

object ZioEnhancement {
  implicit class ZIOOps( private val zio: ZIO.type ) extends AnyVal {
    def sequence[R, E, A, Collection[+Element] <: Iterable[Element]](
      in: Collection[ZIO[R, E, A]]
    )( implicit bf: BuildFrom[Collection[ZIO[R, E, A]], A, Collection[A]] ): ZIO[R, E, Collection[A]] =
      foreach( in )( identity )

    def sequence[R, E, A]( in: Option[ZIO[R, E, A]] ): ZIO[R, E, Option[A]] =
      foreach( in )( identity )
  }

  implicit class IOOps( private val io: IO.type ) extends AnyVal {
    def sequence[E, A, Collection[+Element] <: Iterable[Element]](
      in: Collection[IO[E, A]]
    )( implicit bf: BuildFrom[Collection[IO[E, A]], A, Collection[A]] ): IO[E, Collection[A]] =
      foreach( in )( identity )

    def sequence[E, A]( in: Option[IO[E, A]] ): IO[E, Option[A]] =
      foreach( in )( identity )

  }
}
