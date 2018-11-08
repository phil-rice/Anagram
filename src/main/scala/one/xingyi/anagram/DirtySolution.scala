package one.xingyi.anagram

package one.xingyi.anagram

import java.util.concurrent.atomic.AtomicReference

import scala.collection.concurrent.TrieMap
import scala.io.Source

class DirtySolution extends (String => Set[String]) {
  var map = Map[String, Set[String]]()

  def apply(str: String): Set[String] = map.get(str).getOrElse(Set(str))

  def mapCopy: Map[String, Set[String]] = map.map(x => x).toMap

  def add(word: String) = {
    val key = word.sorted
    val set = map.getOrElse(key, Set.empty[String])
    map = map + (key -> (set + word))
  }
}

object DirtySolution extends App {
  val a = new DirtySolution
  Time.took("Dirty solution",
    Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("wordlist.txt"), "ISO-8859-1").getLines().foreach(a.add)
  )
//    a.map.keySet.toList.sorted.foreach(k => println(a.map(k)))
  println("result")
  println(a("ab"))
  println(a.map.size)
}
