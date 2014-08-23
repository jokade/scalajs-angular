package controllers

import com.greencatsoft.angularjs.Controller
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
object TodoCtrl extends Controller("TodoCtrl") with LocationAware {

  trait TodoScope extends js.Object {
    var todos: js.Array[Todo]
    var newTodo: String
    var editedTodo: Todo
    var location: Location
    var allChecked: Boolean
    var remainingCount: Int
    var originalTodo: Todo

    var addTodo: js.ThisFunction
    var editTodo: js.ThisFunction
    var doneEditing: js.ThisFunction1[DataType,Todo,Unit]
    var removeTodo: js.ThisFunction1[DataType,Todo,Unit]
    var revertEditing: js.ThisFunction
    var todoCompleted: js.ThisFunction
    var clearCompletedTodos: js.ThisFunction
    var markAll: js.ThisFunction
  }

  override type DataType = TodoScope

  override def initialize(): Unit = {
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

    scope.addTodo = addTodo _

    scope.editTodo = editTodo _

    scope.doneEditing = doneEditing _

    scope.revertEditing = revertEditing _

    scope.removeTodo = removeTodo _

    scope.todoCompleted = todoCompleted _

    scope.clearCompletedTodos = clearCompletedTodos _

    scope.markAll = markAll _
  }


  def addTodo(scope:DataType) {
    val newTodo = scope.newTodo.trim

    if(newTodo == "")
      return

    scope.todos.push( literal( title = newTodo, completed = false).asInstanceOf[Todo]  )
    TodoStorage.put(scope.todos)

    scope.newTodo = ""
    scope.remainingCount += 1
  }


  def editTodo(scope: DataType, todo: Todo): Unit = {
    scope.editedTodo = todo
    // Clone the original todo to restore on demand.
    scope.originalTodo = angular.extend(literal(),todo).asInstanceOf[Todo]
  }

  def doneEditing(scope: DataType, todo: Todo): Unit = {
    scope.editedTodo = null
    todo.title = todo.title.trim

    if(todo.title == "")
      scope.removeTodo(scope,todo)

    TodoStorage.put(scope.todos)
  }

  def revertEditing(scope: DataType, todo: Todo): Unit = {
    scope.todos(scope.todos.indexOf(todo)) = scope.originalTodo
    scope.doneEditing(scope,scope.originalTodo)
  }

  def removeTodo(scope: DataType, todo: Todo) : Unit = {
    scope.remainingCount -= (if(todo.completed) 1 else 0)
    scope.todos.splice( scope.todos.indexOf(todo) )
    TodoStorage.put(scope.todos)
  }

  // TODO: error, the provided scope is not the correct one!
  def todoCompleted(scope: DataType, todo: Todo): Unit = {
    scope.remainingCount += ( if(todo.completed) -1 else 1 )
    js.Dynamic.global.console.log(scope)
    TodoStorage.put(scope.todos)
  }

  def clearCompletedTodos(scope: DataType): Unit = {
    scope.todos.filter( (v:Todo) => !v.completed )
    TodoStorage.put(scope.todos)
  }

  def markAll(scope: DataType, completed: Boolean): Unit = {
    scope.todos.foreach( (todo:Todo) => todo.completed = !completed )
    scope.remainingCount = if(completed) scope.todos.length else 0
  }
}
