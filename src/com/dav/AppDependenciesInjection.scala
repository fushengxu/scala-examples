package com.dav


///// MODULES //////
trait HttpClientModule {
  def get(url: String): String
}

trait CurlModule {
  def httpClient: HttpClientModule
  def printSite(url:String): String
}

///// IMPLEMENTATIONS /////
trait DefaultHttpClient extends HttpClientModule {
  def get(url: String): String = io.Source.fromURL(url).mkString
}
trait DefaultCurl extends CurlModule {
  def printSite(url: String): String = httpClient.get(url)
}

///// INSTANCES /////
trait NetworkBeans { beans =>
  val httpClient: HttpClientModule = new DefaultHttpClient {}
  val curl: CurlModule = new DefaultCurl {
    val httpClient: HttpClientModule = beans.httpClient
  }
}

///// APPLICATION /////
object MyApp extends App with NetworkBeans{
  val site = curl.printSite("http://example.com")
  println(site)
}
