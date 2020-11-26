package jardiff

class Dumper(val contentReader: ContentReader) {
    fun dumpContents(fileCollection: FileCollection) {
        fileCollection.files().sorted().forEach {
            println()
            println()
            println(it)
            println()
            contentReader.readContent(it, fileCollection.content(it)).forEach { println(it) }
        }
    }
}