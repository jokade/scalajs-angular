package com.greencatsoft.angularjs.services

import com.greencatsoft.angularjs.InjectionTarget

import scala.scalajs.js

object Window {
  val Name = "$window"
}

trait WindowAware extends InjectionTarget {
  implicit var window: org.scalajs.dom.Window = _

  override def dependencies = super.dependencies :+ Window.Name

  override def inject(args: Seq[js.Any]) {
    super.inject(args)

    var index = dependencies.indexOf(Window.Name) ensuring (_ >= 0)
    this.window = args(index).asInstanceOf[org.scalajs.dom.Window]
  }
}