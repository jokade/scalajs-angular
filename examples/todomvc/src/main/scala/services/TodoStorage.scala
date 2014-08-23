package services

import scala.scalajs.js
import js.Dynamic.literal
import scala.scalajs.js.{UndefOr, JSON}
import scala.scalajs.js.annotation.{JSExportDescendentObjects, JSExport}

/**
 * Service that persists and retrieves TODOs from LocalStorage
 */
object TodoStorage {
  trait Todo extends js.Object {
    var title : String = ???
    var completed : Boolean = ???
  }

  // define interface to DOM localStorage
  trait LocalStorage extends js.Object{
    def getItem(key: String) : String
    def setItem(key: String, value: String)
  }

  val STORAGE_ID = "todos-sjs-ng"
  val localStorage = js.Dynamic.global.localStorage.asInstanceOf[LocalStorage]

  def get() : js.Array[Todo] = {
    val v = localStorage.getItem(STORAGE_ID)
    JSON.parse( if(v==null) "[]" else v ).asInstanceOf[js.Array[Todo]]
  }

  def put(todos: js.Array[Todo]): Unit = {
    localStorage.setItem(STORAGE_ID, JSON.stringify(todos))
  }
}
