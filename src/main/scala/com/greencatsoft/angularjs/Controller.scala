package com.greencatsoft.angularjs

import com.greencatsoft.angularjs.scope.ScopeAware

/**
 * Base class for Angular.js controllers.
 *
 * @param _name the name under which the controller will be registered to Angular.
 *              Default: qualified name of the class or object
 *
 * @example {{{package foo
 *
 * // a controller type named "foo.CountCtrl"
 * object CountCtrl extends Controller {
 *   // defines data vars + functions defined on the controller scope
 *   trait CountCtrlData extends js.Object {
 *     // stores the current counter value
 *     var count : Int
 *     // increments the counter on each call
 *     var inc : js.ThisFunction   // <-- important: js.Function will not work as expected
 *                                 //                if there is more than one instance of this controller!
 *   }
 *
 *   override type DataType = CountCtrlData
 *
 *   // initialize the controller scope
 *   override def initialize() {
 *     scope.count = 0
 *     scope.inc = (scope:DataType) => scope.count += 1  // we use the provided scope instead of closing over the outer scope
 *                                                       // (see https://github.com/greencatsoft/scalajs-angular/issues/2)
 *   }
 *
 * }
 *
 * // another controller type with name "foo.Bar"
 * object BarCtrl extends Controller("foo.Bar") {
 *   /* ... */
 * }
 * }}}
 */
abstract class Controller(_name: String = null) extends NamedTarget with ScopeAware {

  override lazy val name : String =
    if(_name==null) this.getClass.getName.split("\\$").last
    else _name

}
