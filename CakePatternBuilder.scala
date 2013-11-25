case class User (name:String)
case class Site (name:String, user: User, metaData: SiteMetaData)
case class SiteMetaData (color: String, id: String, contactInfo: ContactInfo)
case class ContactInfo (email: String)

trait UserProvider {
  def userName = "mike"
  def user: User = User(userName)
}

trait ContactInfoProvider {
  def contactInfoEmail = "my@email.com"
  def contactInfo = ContactInfo(contactInfoEmail)
}

trait SiteMetaDataProvider extends ContactInfoProvider{
  def siteMetaColor = "black"
  def siteMetaId = "1234-5678"
  def siteMetaData: SiteMetaData = SiteMetaData(siteMetaColor, siteMetaId, contactInfo)
}

trait SiteProvider extends UserProvider with SiteMetaDataProvider{
  def siteName = "my site"
  def site: Site = Site(siteName, user, siteMetaData)
}

trait Story extends SiteProvider
trait StoryAboutSiteWithNoId extends Story{
  override val siteMetaId = "no id :("
}

object test{
  def main (args : Array[String]) {
    val story = new Story{
      override val siteName = "my other site"
      override val userName = "cool keith"
      override val contactInfoEmail = "eeeeemail@ho.yeah"
    }
    val site = story.site
    println(site)
    println((new StoryAboutSiteWithNoId {}).site)

  }
}
