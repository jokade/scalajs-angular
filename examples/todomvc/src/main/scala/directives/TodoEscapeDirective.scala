package directives

import com.greencatsoft.angularjs.directive.AbstractDirective

import scala.scalajs.js

object TodoEscapeDirective extends AbstractDirective(
  _link = (scope: js.Dynamic, elem: js.Dynamic, attrs: js.Dynamic) => {
    elem.bind("keydown", (event:js.Dynamic) => {
      if(event.keyCode.asInstanceOf[Int] == 27) {
        scope.$apply(attrs.todoEscape)
      }
    })
  }
)
