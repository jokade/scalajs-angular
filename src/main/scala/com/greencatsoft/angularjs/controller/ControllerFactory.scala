package com.greencatsoft.angularjs.controller

import com.greencatsoft.angularjs.NamedTarget
import com.greencatsoft.angularjs.scope.Scope

import scala.scalajs.js

abstract class ControllerFactory(_name: String = null) extends NamedTarget {
  final override def initialize(): Unit = {}

  protected[angularjs] var _injected = false

  type DataType

  override lazy val name : String =
    if(_name == null)
      getClass.getName.split("\\.").last.split("\\$").last.replace("Factory","")
    else _name

  def controller : (Scope with DataType,js.Dynamic) => Unit
}

