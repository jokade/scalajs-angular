package directives

import com.greencatsoft.angularjs.directive.AbstractDirective
import com.greencatsoft.angularjs.services.TimeoutAware

import scala.scalajs.js
import scala.scalajs.js.UndefOr

object TodoFocusDirective extends AbstractDirective with TimeoutAware {
  override def link = (scope: js.Dynamic, elem: js.Dynamic, attrs: js.Dynamic) =>{
    scope.$watch(attrs.todoFocus, (newVal:UndefOr[js.Any]) =>{
      if(newVal.isDefined && newVal.get!=null)
        timeout(() => elem.asInstanceOf[js.Array[js.Dynamic]](0).focus(), 0, false)
    })
  }
}
