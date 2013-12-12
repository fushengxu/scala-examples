package com.dav

object execute{
  def main(args: Array[String]) {
    import scala.sys.process._
    val lines2 = "ping 8.8.8.8".lines_!
    lines2.foreach( println(_))
    println("OK")
  }
}
