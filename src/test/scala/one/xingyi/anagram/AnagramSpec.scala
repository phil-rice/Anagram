package one.xingyi.anagram

import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import Mockito._

class AnagramSpec extends FlatSpec with Matchers with MockitoSugar {

  implicit def toRsourceName(s: String) = ResourceName(s)

  implicit def toWord(s: String) = Word(s)

  implicit def toKey(s: String) = Key(s)

  behavior of "Source"

  it should "be able to read the source file" in {
    SourceLoader("text.txt").toList shouldBe List[Word]("a", "b", "cde")
  }

  behavior of "StringKeyFinder"

  it should "return a key from the word" in {
    WordKeyFinder("abc") shouldBe Key("abc")
    WordKeyFinder("cba") shouldBe Key("abc")
  }

  behavior of "anagram store"

  def anagram(s: String*) = {
    val a = new Anagram
    s.foreach(w => a.add(Word(w)))
    a
  }

  it should "allow me to add words and it returns a map from the key to the worlds" in {
    anagram("ab", "ba").mapCopy shouldBe Map[Key, Set[Word]](Key("ab") -> Set[Word]("ab", "ba"))
    anagram("ab", "bacd").mapCopy shouldBe Map(Key("abcd") -> Set(Word("bacd")), Key("ab") -> Set(Word("ab")))
  }
  it should "get me the anagrams of a word" in {
    anagram("ab", "ba", "cc").apply("ab") shouldBe Set[Word]("ab", "ba")
    anagram("ab", "ba", "cc").apply("cc") shouldBe Set[Word]("cc")
    anagram("ab", "ba", "cc").apply("dd") shouldBe Set[Word]("dd")
  }

  behavior of "anagramloader"


  it should "load from a text file and add to the anagram " in {
    implicit val sourceLoader = mock[SourceLoader]
    val anagram = mock[Anagram]
    when(sourceLoader.apply("someResource")) thenReturn List[Word]("ab", "ba").iterator
    new AnagramLoader("someResource", anagram)
    verify(anagram, times(1)).add(Word("ab"))
    verify(anagram, times(1)).add(Word("ba"))
  }

}
