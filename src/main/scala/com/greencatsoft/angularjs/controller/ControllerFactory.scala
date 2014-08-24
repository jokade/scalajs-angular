package com.greencatsoft.angularjs.controller

import com.greencatsoft.angularjs.NamedTarget
import com.greencatsoft.angularjs.scope.Scope

import scala.scalajs.js

/**
 * Factory base class for AngularJS controllers.
 *
 * @param _name the name under which the controller type will be registered to Angular.
 *              Default: the name of the factory object (without the suffix 'Factory', if present).
 *
 * @example{{{
 * // This factory create controllers with name 'CounterCtrl'
 * object CounterCtrlFactory extends ControllerFactory with HttpServiceAware {
 *   // define data vars + functions available on each scope instance
 *   trait CounterData extends js.Object {
 *     // stores the current counter value
 *     var count: Int
 *     // increments the counter on each call
 *     var inc : js.Function
 *     // returns the sum of all counters created by this factory
 *     var sum : js.Function0[Int]
 *   }
 *
 *   override type DataType = CounterData
 *
 *   // global var available to all controller instances (but not outside of this factory)
 *   private var _sum = 0
 *
 *   // initialize the controller scope
 *   override def controller = { (scope,dynamicScope) =>
 *     // initialize counter
 *     scope.count = 0
 *
 *     scope.inc = () => {
 *       scope.count += 1
 *       _sum += 1
 *       // reference to httpService available due to the mixin 'HttpServiceAware'
 *       http.put( /* ... */ )
 *     }
 *
 *     scope.sum = () => _sum
 *   }
 * }
 * }}}
 */
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

