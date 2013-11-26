case class User (name:String)
case class Site (name:String, user: User, metaData: SiteMetaData)
case class SiteMetaData (color: String, id: String, contactInfo: ContactInfo)
case class ContactInfo (email: String)

trait UserProvider {
  import user._
  protected object user {
    var name = "mike"
  }
  def aUser = User( name )
}

trait ContactInfoProvider {
  import contactInfo._
  protected object contactInfo {
    var email = "my@email.com"
  }
  def aContactInfo = ContactInfo(email)
}

trait SiteMetaDataProvider extends ContactInfoProvider{
  import siteMetaData._
  object siteMetaData {
    var color = "black"
    var id = "1234-5678"
  }
  def aSiteMetaData = SiteMetaData( color, id, aContactInfo)
}

trait SiteProvider extends UserProvider with SiteMetaDataProvider{
  import site._
  object site {
    var name = "my site"
  }
  def aSite = Site( name, aUser, aSiteMetaData )
}

trait Story extends SiteProvider

object test{

  val story = new Story {
    user.name = "cool keith"
    contactInfo.email = "eeeeemail@ho.yeah"
    site.name = "my other site"
  }
  import story._

  def main (args : Array[String]) {
    val site = aSite
    println(site)
  }
}
