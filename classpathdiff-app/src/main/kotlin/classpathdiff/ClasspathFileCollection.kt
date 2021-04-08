package classpathdiff

import jardiff.FileCollection
import java.io.InputStream

class ClasspathFileCollection(files: List<String>) : FileCollection {
    private val collections : List<FileCollection> = files.map(FileCollection::openCollection)
    private val effectiveFiles : Map<String, Pair<FileCollection, String>>

    init {
        val filesMap = mutableMapOf<String, Pair<FileCollection, String>>()

        for(collection in collections) {
            for(file in collection.files().sorted()) {
                val effectiveFileName = file.split("!").last()

                if(filesMap.containsKey(effectiveFileName)) continue

                filesMap[effectiveFileName] = collection to file
            }
        }

        effectiveFiles = filesMap
    }

    override fun files(): List<String> = effectiveFiles.keys.toList()

    override fun content(file: String): InputStream {
        val (collection, fileName) = effectiveFiles[file]!!

        return collection.content(fileName)
    }
}