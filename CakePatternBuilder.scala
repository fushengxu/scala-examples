case class User (name:String)
case class Site (name:String, user: User, metaData: SiteMetaData)
case class SiteMetaData (color: String, id: String)

trait UserProvider {
  def userName = "mike"
  def user: User = User(userName)
}

trait SiteMetaDataProvider {
  def siteMetaColor = "black"
  def siteMetaId = "1234-5678"
  def siteMetaData: SiteMetaData = SiteMetaData(siteMetaColor, siteMetaId)
}

trait SiteProvider extends UserProvider with SiteMetaDataProvider{
  def siteName = "my site"
  def site: Site = Site(siteName, user, siteMetaData)
}

trait Story extends SiteProvider

object test{
  def main (args : Array[String]) {
    val story = new Story{
      override val siteName = "my other site"
      override val userName = "cool keith"
    }
    val site = story.site
    println(site)
  }
}
