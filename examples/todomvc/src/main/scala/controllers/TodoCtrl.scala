package controllers

import com.greencatsoft.angularjs.controller.ControllerFactory
import com.greencatsoft.angularjs.location.{Location, LocationAware}
import com.greencatsoft.angularjs.angular
import services.TodoStorage
import services.TodoStorage.Todo

import scala.scalajs.js
import js.Dynamic.literal

/**
 * The main controller for the app.
 *
 * The controller
 * <ul>
 *   <li>retrieves and persiststhe model via the [[services.TodoStorage]] service</li>
 *   <li>exposes the model to the template and provides event handlers</li>
 * </ul>
 *
 * ''Note: instead of providing the required dependencies as arguments to a constructor function,
 * we mix in traits like `LocationAware` to request these dependencies''
 */
object TodoCtrlFactory extends ControllerFactory with LocationAware {

  trait TodoScope extends js.Object {
    var todos: js.Array[Todo]
    var newTodo: String
    var editedTodo: Todo
    var location: Location
    var allChecked: Boolean
    var remainingCount: Int
    var originalTodo: Todo

    var addTodo: js.Function
    var editTodo: js.Function
    var doneEditing: js.Function1[Todo,Unit]
    var removeTodo: js.Function1[Todo,Unit]
    var revertEditing: js.Function
    var todoCompleted: js.Function
    var clearCompletedTodos: js.Function
    var markAll: js.Function
  }

  override type DataType = TodoScope

  override def controller = { (scope,dynamicScope) =>
    scope.todos = TodoStorage.get()
    scope.remainingCount = scope.todos.count( (v:Todo) => !v.completed )
    scope.newTodo = ""
    scope.editedTodo = null

    // this reference to the AngularJS $location service is provided by the mixin LocationAware
    if( location.path() == "" )
      location.path("/")

    scope.location = location

    scope.$watch("location.path()", (path:String) => path match {
      case "/active" => dynamicScope.statusFilter = literal(completed = false)
      case "/completed" => dynamicScope.statusFilter = literal(completed = true)
      case _=> dynamicScope.statusFilter = literal()
    })

    scope.$watch("remainingCount == 0", (v:Boolean) => scope.allChecked = v)

    scope.addTodo = () => {
      val newTodo = scope.newTodo.trim
      if (newTodo != "") {
              scope.todos.push(literal(title = newTodo, completed = false).asInstanceOf[Todo])
        TodoStorage.put(scope.todos)

        scope.newTodo = ""
        scope.remainingCount += 1
      }
    }

    scope.editTodo = (todo: Todo) => {
      scope.editedTodo = todo
      // Clone the original todo to restore on demand.
      scope.originalTodo = angular.extend(literal(),todo).asInstanceOf[Todo]
    }

    scope.doneEditing = (todo: Todo) => {
      scope.editedTodo = null
      todo.title = todo.title.trim

      if(todo.title == "")
        scope.removeTodo(todo)

      TodoStorage.put(scope.todos)
    }

    scope.revertEditing = (todo: Todo) => {
      scope.todos(scope.todos.indexOf(todo)) = scope.originalTodo
      scope.doneEditing(scope.originalTodo)
    }

    scope.removeTodo = (todo: Todo) => {
      scope.remainingCount -= (if(todo.completed) 1 else 0)
      scope.todos.splice( scope.todos.indexOf(todo) )
      TodoStorage.put(scope.todos)
    }

    scope.todoCompleted = (todo: Todo) => {
      scope.remainingCount += ( if(todo.completed) -1 else 1 )
      TodoStorage.put(scope.todos)
    }

    scope.clearCompletedTodos = () => {
      scope.todos.filter( (v:Todo) => !v.completed )
      TodoStorage.put(scope.todos)
    }

    scope.markAll = (completed:Boolean) => {
      scope.todos.foreach( (todo:Todo) => todo.completed = !completed )
      scope.remainingCount = if(completed) scope.todos.length else 0
    }
  }

}
