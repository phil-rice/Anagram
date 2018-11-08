package one.xingyi.anagram

case class Key(s: String) extends AnyVal

object WordKeyFinder extends WordKeyFinder{
  implicit val default = this
}

trait WordKeyFinder extends (Word => Key) {
  override def apply(v1: Word): Key = Key(v1.s.sorted)

}
