package com.greencatsoft.angularjs

import scala.scalajs.js

trait Angular extends js.Object {

  def bind(self: js.Object, fn: js.Function, args: js.Any*) : js.Function = ???

  // TODO: return type Injector instead of Dynamic
  def bootstrap(element: js.Object, modules: js.Array[String] = null) : js.Dynamic = ???

  def copy(source: js.Any, destination: js.Any = null) : js.Dynamic = ???

  // TODO: return type jqLite instead of Dynamic
  def element(element: js.Any) : js.Dynamic = ???

  def equals(o1: js.Any, o2: js.Any) : Boolean = ???

  def extend(dst: js.Object, src: js.Object) : js.Dynamic = ???

  def forEach(obj: js.Any, iterator: js.Function, context: js.Object = null) : js.Dynamic = ???

  def fromJson(json: String) : js.Dynamic = ???

  // TODO: return type for injector function instead of Dynamic
  def injector(modules: js.Array[js.Any]) : js.Dynamic = ???

  def isArray(value: js.Any) : Boolean = ???

  def isDate(value: js.Any) : Boolean = ???

  def isDefined(value: js.Any) : Boolean = ???

  def isElement(value: js.Any) : Boolean = ???

  def isFunction(value: js.Any) : Boolean = ???

  def isNumber(value: js.Any) : Boolean = ???

  def isObject(value: js.Any) : Boolean = ???

  def isString(value: js.Any) : Boolean = ???

  def isUndefined(value: js.Any) : Boolean = ???

  def lowercase(string: String) : String = ???

  def module(name: String, require: js.Array[String] = null): Module = ???

  def toJson(obj: js.Any, pretty: Boolean = false) : String = ???

  def uppercase(string: String) : String = ???
}
