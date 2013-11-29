case class User (name:String)
case class Site (name:String, user: User, metaData: SiteMetaData)
case class SiteMetaData (color: String, id: String, contactInfo: ContactInfo)
case class ContactInfo (email: String)

trait UserProvider {
  import User._
  protected object User {
    var name = "mike"
  }
  def user = User( name )
}

trait ContactInfoProvider {
  import ContactInfo._
  protected object ContactInfo {
    var email = "my@email.com"
  }
  def contactInfo = ContactInfo(email)
}

trait SiteMetaDataProvider extends ContactInfoProvider{
   protected object SiteMetaData {
    var color = "black"
    var id = "1234-5678"
  }
  import SiteMetaData._
  def siteMetaData = SiteMetaData( color, id, contactInfo)
}

trait SiteProvider extends UserProvider with SiteMetaDataProvider{
  import Site._
  object Site {
    var name = "my site"
  }
  def site = Site( name, user, siteMetaData )
}

trait Story extends SiteProvider

object main{

  val story = new Story {
    SiteMetaData.color
    User.name = "cool keith"
    ContactInfo.email = "eeeeemail@ho.yeah"
    Site.name = "my other site"
  }

  def main (args : Array[String]) {
    val site = site
    println(site)
  }
}
