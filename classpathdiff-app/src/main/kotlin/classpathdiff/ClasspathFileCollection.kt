package classpathdiff

import jardiff.FileCollection
import java.io.InputStream
import java.nio.file.Path

class ClasspathFileCollection(files: List<Path>) : FileCollection {
    override fun files(): List<String> {
        TODO("Not yet implemented")
    }

    override fun content(file: String): InputStream {
        TODO("Not yet implemented")
    }
}