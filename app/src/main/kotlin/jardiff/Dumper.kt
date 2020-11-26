package jardiff

object Dumper {
    fun dumpContents(fileCollection: FileCollection) {
        fileCollection.files().sorted().forEach {
            println()
            println()
            println(it)
            println()
            fileCollection.lines(it).forEach { println(it) }
        }
    }
}