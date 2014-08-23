package com.greencatsoft.angularjs.controller

import com.greencatsoft.angularjs.NamedTarget
import com.greencatsoft.angularjs.scope.Scope

import scala.scalajs.js

abstract class ControllerFactory[ScopeType<:js.Object](_name: String = null) extends NamedTarget {
  final override def initialize(): Unit = {}

  override lazy val name : String =
    if(_name == null)
      getClass.getName.split("\\.").last.split("\\$").last.replace("Factory","")
    else _name

  def controller : (this.type,Scope with ScopeType) => Unit
}

