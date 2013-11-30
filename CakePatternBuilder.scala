case class User (name:String)
case class Site (name:String, user: User, metaData: SiteMetaData)
case class SiteMetaData (color: String, id: String, contactInfo: ContactInfo)
case class ContactInfo (email: String)

trait UserStory {
  import aboutUser._
  protected object aboutUser {
    var name = "mike"
  }
  def user = User( name )
}

trait ContactInfoStory {
  import aboutContactInfo._
  protected object aboutContactInfo {
    var email = "my@email.com"
  }
  def contactInfo = ContactInfo(email)
}

trait SiteMetaDataStory extends ContactInfoStory{
   protected object aboutSiteMetaData {
    var color = "black"
    var id = "1234-5678"
  }
  import aboutSiteMetaData._
  def siteMetaData = SiteMetaData( color, id, contactInfo)
}

trait SiteStory extends UserStory with SiteMetaDataStory{
  import aboutSite._
  object aboutSite {
    var name = "my site"
  }
  def site = Site( name, user, siteMetaData )
}

object StoryBuilder {
  val story = new SiteStory {
    aboutSiteMetaData color = "blue"
    aboutUser name = "cool keith"
    aboutContactInfo email = "eeeeemail@ho.yeah"
    aboutSite name = "my other site"
  }

  def main (args : Array[String]) {
    val site = story.site
    println(site)
  }
}
