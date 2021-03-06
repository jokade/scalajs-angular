package com.greencatsoft.angularjs.services

import com.greencatsoft.angularjs.InjectionTarget

import scala.scalajs.js

trait Parse extends js.Function1[String,js.Function]

object Parse {
  val Name = "$parse"
}

trait ParseAware extends InjectionTarget {
  implicit var parse: Parse = _

  override def dependencies = super.dependencies :+ Parse.Name

  override def inject(args: Seq[js.Any]) {
    super.inject(args)

    var index = dependencies.indexOf(Parse.Name) ensuring (_ >= 0)
    this.parse = args(index).asInstanceOf[Parse]
  }
}