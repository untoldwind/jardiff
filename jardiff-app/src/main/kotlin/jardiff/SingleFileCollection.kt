package jardiff

import java.io.InputStream
import java.nio.file.Path

class SingleFileCollection(private val filePath: Path) : FileCollection {
    override fun files(): List<String> = listOf(filePath.toString())

    override fun content(file: String): InputStream = filePath.toFile().inputStream()
}