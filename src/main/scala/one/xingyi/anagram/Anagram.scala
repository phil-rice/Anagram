package one.xingyi.anagram

import java.util.concurrent.atomic.AtomicReference

import scala.collection.concurrent.TrieMap

class ShardedMap[K, V](count: Int) {
  val array = List.fill(count)(new TrieMap[K, V])

  def getOrElseUpdate(k: K, v: V) = array(k.hashCode % count).getOrElseUpdate(k, v)

  def get(k: K) = array(k.hashCode % count).get(k)
}

class Anagram(implicit wordKeyFinder: WordKeyFinder) extends (Word => Set[Word]) {
  def atomicRefOfSetofWordsToSetOfStrings: AtomicReference[Set[Word]] => Set[String] = _.get.map(_.s)
  def anagrams: Iterable[Set[String]] = map.values.map(atomicRefOfSetofWordsToSetOfStrings).filter(_.size>1)

  val map = TrieMap[Key, AtomicReference[Set[Word]]]()

  def apply(str: Word): Set[Word] = map.get(str).fold(Set(str))(_.get)

  def mapCopy: Map[Key, Set[Word]] = map.mapValues(_.get()).toMap

  def add(word: Word) =
    map.getOrElseUpdate(word, new AtomicReference(Set.empty[Word])).updateAndGet(_ + word)

}
