package jardiff

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class DirectoryFileCollection(private val directory: Path) : FileCollection {
    private val files: List<String> = Files.walk(directory).filter(Files::isRegularFile).map { directory.relativize(it).toString() }.toList()

    override fun files(): List<String> = files

    override fun content(file: String): InputStream = Files.newInputStream(directory.resolve(file))
}