package com.dav

import scala.actors._

case object Ping
case object Pong
case object Stop

object servers {
  import log._
  case class Save(file: String, content: String)
  class FileServer(logger: Logger) extends Actor {
    def act() {
      loop {
        react {
          case Save(file, content) => {
            logger.log(s"saving file '$file'")
            // .. save file ..
            Thread.sleep(2000)
            logger.log(s"'$file' was saved")
          }
        }
      }
    }
    def save (file: String, content: String) = this ! Save(file, content)
  }
}

object log {
  case class LogMessage(msg: String)
  class Logger extends Actor{
    def act() {
      loop {
        react {
          case LogMessage(msg) => {
            println(msg)
          }
        }
      }
    }
    def log (msg: String) = this ! LogMessage(msg)
  }
}


object app extends App {
  import log._
  import servers._

  val logger = new Logger
  val fileServer = new FileServer(logger)
  logger start()
  fileServer start()
  fileServer save ("file.txt", "this is my file")
  fileServer save ("another.zip", "this is my file")
}
