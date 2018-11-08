package one.xingyi.anagram

case class AnagramLoader(resourceName: ResourceName, anagram: Anagram)(implicit sourceLoader: SourceLoader) {
  sourceLoader(resourceName).foreach(anagram.add)


}

object AnagramLoader extends App{
 val anagram = new Anagram()
  Time.took("Anagram loader",   new AnagramLoader(ResourceName("wordlist.txt"), anagram))
 }
