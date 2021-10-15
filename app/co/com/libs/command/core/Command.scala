package co.com.libs.command.core

import play.api.mvc.Result
import zio.URIO

trait Command[D <: DependencyBase] {

  def execute: URIO[D, Result]

}
