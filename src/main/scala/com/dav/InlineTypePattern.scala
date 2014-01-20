package com.dav

/*
This pattern is nice to use when you have a DTO and you want to define
an action upon it somewhere else in the code, it this example, i will define
a custom printer
 */


// first, define the dto's
case class MusicAlbum(title: String, artist: String)
case class MusicTrack(album: MusicAlbum, title: String)

// then somewhere else in the code, define a printers
object Printers extends {
  implicit val musicAlbumPrinter = new Printer[MusicAlbum] {
    def print(t: MusicAlbum): String = s"${t.artist} - ${t.title}"
  }
  implicit val musicTrackPrinter = new Printer[MusicTrack] {
    def print(t: MusicTrack): String = s"${t.album.artist} - ${t.album.title} - ${t.title}"
  }
}


// and then just use it!
object InlineTypePattern {
  import Printers._
  def main(args: Array[String]) {
    val ma = MusicAlbum("Discovery", "Daft Punk")
    val mt = MusicTrack(ma, "One More Time")
    println( Printer.print(ma) )
    println( Printer.print(mt) )
    // notice we use the printers without being concerned about what type is needed to be printed
    // if we will try to print an object that has no printer available it WON'T COMPILE - awesome!
  }
}


object Printer {
  def print[T](t:T)(implicit p:Printer[T])= p.print(t)
}
trait Printer[T]{
  def print(t:T):String
}
