package patmat


class Person {

  var age: Int = 0

  def this(initialAge:Int) = {
    // Add some more code to run some checks on initialAge
    this()
    initialAge match {
      case x if x > 0 => age = x
      case _ => {
        println("Age is not valid, setting age to 0.")
        0
      }
    }

  }

  def amIOld(): Unit = {
    age match {
      case x if x < 13 => println("You are young.")
      case x if 13 to 18 contains x => println("You are teenager.")
      case _ => println("You are old.")
    }
    // Do some computations in here and print out the correct statement to the console

  }

  def yearPasses(): Unit = {
    // Increment the age of the person in here
    age = age +1
  }

}

object AgeClass {

  def main(args: Array[String]) {
    val T=scala.io.StdIn.readInt()
    for(i <- 1 to T){
      val age=scala.io.StdIn.readInt()
      val p =new Person(age)
      p.amIOld()
      for(j <- 1 to 3){
        p.yearPasses()
      }
      p.amIOld()
      System.out.println()

    }


  }
}