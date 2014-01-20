package com.dav

// this example is inspired by [https://gist.github.com/wfaler/3932552]

object SimpleCakePattern {

  case class User (name: String)

  trait Database[A, B]{
    def save(entity: A): B
  }

  trait SqlDatabase extends Database[User, Long]{
    def save(entity: User): Long = {
      1 // .. saves the user and returns id 1 .. or whatever
    }
  }

  trait UserService{ this: Database[User, Long] =>
    // use self type annotation so the "save" function won't percolate into whoever extends UserService
    def addUser(user: User): Long = save(user)
  }

  class Application {
    val userService = new UserService with SqlDatabase
  }

}