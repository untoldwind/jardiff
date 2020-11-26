package jardiff

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class DirectoryFileCollection(val directory: Path) : FileCollection {
    private val files: List<String> = Files.walk(directory).filter(Files::isRegularFile).map { directory.relativize(it).toString() }.toList()

    override fun files(): List<String> = files

    override fun lines(file: String): List<String> = ContentReader.readContent(file, File(directory.toFile(), file).inputStream())
}