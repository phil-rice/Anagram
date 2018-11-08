package one.xingyi.anagram

object Time {

  def apply[X](x: => X): (X, Long) = {
    val startTime = System.nanoTime()
    val result = x
    (result, System.nanoTime() - startTime)
  }

  def took[X](title: String, x: X)= {
    val (result, duration) = apply(x)
    println(title + " took " + duration)
    result
  }
}
