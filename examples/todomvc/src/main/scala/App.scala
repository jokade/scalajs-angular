import com.greencatsoft.angularjs.{Module, angular}
import controllers.TodoCtrl
import directives.{TodoFocusDirective, TodoEscapeDirective}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

/**
 * Call `App()` from the HTML page to initializeand start the TodoMVC application.
 */
@JSExport
object App {

  val app = angular.module("todomvc", js.Array[String]())

  // Module(app) returns a proxy for our module that provides the bridge for integration
  // between standard AngularJS and our typesafe + 'fluent' scalajs-angular bindings
  Module(app)
    .controller(TodoCtrl)
    .directive(TodoEscapeDirective, TodoFocusDirective)
}
