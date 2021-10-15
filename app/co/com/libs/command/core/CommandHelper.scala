package co.com.libs.command.core

import play.api.libs.json.Reads

trait CommandHelper[D <: DependencyBase] {

  def name: String

  def commandReads: Reads[Command[D]]

}
