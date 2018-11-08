package one.xingyi.anagram

import scala.io.Source

case class ResourceName(s: String) extends AnyVal

case class Word(s: String) extends AnyVal

object SourceLoader extends SourceLoader{
  implicit  val default = this
}

trait SourceLoader extends (ResourceName => Iterator[Word]) {
  override def apply(s: ResourceName): Iterator[Word] =
    Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(s.s), "ISO-8859-1").getLines().map(Word.apply)
}
