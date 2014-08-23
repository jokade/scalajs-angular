package com.greencatsoft.angularjs.controller

abstract class SingleController(_name: String = null) extends Controller {
  // ensure that this class can only be used with singleton objects
  //this: Singleton =>

  override lazy val name : String =
    if(_name == null)
      getClass.getName.split("\\.").last.split("\\$").last
    else _name
}
