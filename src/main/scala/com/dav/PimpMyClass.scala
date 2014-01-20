package com.dav

object PimpMyClass {
  implicit class LeetOps(val s: String) extends AnyVal {
    def leet = s.replace("l","1").replace("e","3").replace("t","7").replace("o","0")
  }

  def main(args: Array[String]) {
    print( "to leet".leet)
  }
}