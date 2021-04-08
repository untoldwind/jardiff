package jardiff

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarFile

class NestedJarFileCollection(earFile: Path) : FileCollection {
    private val tempDir : Path = Files.createTempDirectory("jardiff")
    private val files : List<String>
    private val jarFiles: Map<String, FileCollection>

    init {
        val ear = JarFile(earFile.toFile())
        val allFiles = mutableListOf<String>()
        val allJarFiles = mutableMapOf<String, FileCollection>()

        for(entry in ear.entries()) {
            if(entry.isDirectory) {
                val dir = tempDir.resolve(entry.name)
                Files.createDirectories(dir)
            } else {
                val tmpFile = tempDir.resolve(entry.name)
                val content = ear.getInputStream(entry)
                Files.createDirectories(tmpFile.parent)
                Files.copy(content, tmpFile)

                if(JarFileCollection.isJarFile(tmpFile)) {
                    val jarFile = JarFileCollection(tmpFile)
                    allJarFiles[entry.name] = jarFile

                    for (file in jarFile.files()) {
                        allFiles.add("${entry.name}!$file")
                    }
                } else if(isNestedJarFile(tmpFile)) {
                    val nestedFile = NestedJarFileCollection(tmpFile)
                    allJarFiles[entry.name] = nestedFile

                    for (file in nestedFile.files()) {
                        allFiles.add("${entry.name}!$file")
                    }
                } else {
                    allFiles.add(entry.name)
                }
            }
        }

        files = allFiles
        jarFiles = allJarFiles
    }

    override fun files(): List<String> = files

    override fun content(file: String): InputStream {
        val parts = file.split('!')

        return if(parts.size > 1) jarFiles[parts[0]]!!.content(parts[1]) else Files.newInputStream(tempDir.resolve(file))
    }

    companion object {
        fun isNestedJarFile(file: Path): Boolean = Files.isRegularFile(file) && (file.fileName.toString().endsWith(".ear") || file.fileName.toString().endsWith(".war") || file.fileName.toString().endsWith(".sar"))
    }
}