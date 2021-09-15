package co.com.libs.command.core

import cats.data.Reader
import play.api.mvc.Result
import zio.UIO

trait Command {

  def execute: Reader[DependencyBase, UIO[Result]]

}
