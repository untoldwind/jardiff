package jardiff

import java.nio.file.Path

class SingleFileCollection(val filePath: Path) : FileCollection {
    override fun files(): List<String> = listOf(filePath.toString())

    override fun lines(file: String): List<String> =
            if (file == filePath.toString())
                ContentReader.readContent(file, filePath.toFile().inputStream())
            else
                emptyList()
}