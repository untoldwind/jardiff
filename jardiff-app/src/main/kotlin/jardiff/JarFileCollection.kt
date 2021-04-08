package jardiff

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarFile

class JarFileCollection(file: Path) : FileCollection {
    private val jarFile = JarFile(file.toFile())

    override fun files(): List<String> = jarFile.entries().asSequence().filter { !it.isDirectory }.map { it.name }.toList()

    override fun content(file: String): InputStream = jarFile.getInputStream(jarFile.getEntry(file))

    companion object {
        fun isJarFile(file: Path): Boolean = Files.isRegularFile(file) && file.fileName.toString().endsWith(".jar")
    }
}