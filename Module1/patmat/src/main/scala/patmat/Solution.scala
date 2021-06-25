package patmat

import scala.util.Try

object Solution {
  def main(args: Array[String]): Unit = {
    /*val i = 4
    val d = 4.0
    val s = "HackerRank "

    print("Enter Integer Value : ")
    val i1 = scala.io.StdIn.readLine().toInt
    print("Enter Double Value : ")
    val d1 = scala.io.StdIn.readLine().toDouble
    print("Enter String Value : ")
    val s1 = scala.io.StdIn.readLine().toString

    println("Sum of Integer : " + (i+i1))
    println("Sum of Double : " + (d+d1))
    println("Concatenate Strings : " + (s+s1))*/

    val stdin = scala.io.StdIn

    val arr = Array.ofDim[Int](6, 6)

    for (i <- 0 until 6) {
      arr(i) = stdin.readLine.split(" ").map(_.trim.toInt)

    }
    var A = scala.collection.mutable.ArrayBuffer.empty[Int]

    for (i <- 0 to 3) {
      for (j <- 0 to 3) {
        A += arr(i)(j) + arr(i)(j+1) + arr(i)(j+2) + arr(i+1)(j+1) + arr(i+2)(j) + arr(i+2)(j+1) + arr(i+2)(j+2)
      }
    }
    println(A.sorted.max)
  }
}
