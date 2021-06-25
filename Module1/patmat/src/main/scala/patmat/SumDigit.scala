import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._

object SumDigit {

  // Complete the superDigit function below.
  def superDigit(n: String, k: Int): Int = {
    def sumDigit(n1: String): Int = {
      val num = n1.map(_.toString.toInt).sum
      if(num <= 9) num else sumDigit(num.toString)
    }

    val sum = sumDigit(n) * sumDigit(k.toString)
    sumDigit(sum.toString)

  }

  def main(args: Array[String]) {
    val stdin = scala.io.StdIn

    val printWriter = new PrintWriter(sys.env("OUTPUT_PATH"))

    val nk = stdin.readLine.split(" ")

    val n = nk(0)

    val k = nk(1).trim.toInt

    val result = superDigit(n, k)

    printWriter.println(result)

    printWriter.close()
  }
}
