case class Person(name:String)
case class Car(color: String, driver: Person)

trait PersonUser {
  def person: Person = Person("person");
}
trait CarUser extends PersonUser{
  def car: Car = Car( "white", person )
}

object Builder{
  def car(personName:String = "man") = new CarUser with PersonUser{
    override val person = Person(personName)
  }.car
}

object test{
  def main (args : Array[String]) {
    val a = Builder.car("horse")
    println(a)
  }
}
