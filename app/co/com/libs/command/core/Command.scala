package co.com.libs.command.core

import co.com.libs.error.AppError
import play.api.mvc.Result
import zio.ZIO

trait Command {

  def execute: ZIO[DependencyBase, AppError, Result]

}
