package jardiff

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarFile

class EarFileCollection(earFile: Path) : FileCollection {
    private val tempDir : Path = Files.createTempDirectory("jardiff")
    private val files : List<String>
    private val jarFiles: Map<String, JarFileCollection>

    init {
        val ear = JarFile(earFile.toFile())
        val allFiles = mutableListOf<String>()
        val allJarFiles = mutableMapOf<String, JarFileCollection>()

        for(entry in ear.entries()) {
            if(entry.isDirectory) {
                val dir = tempDir.resolve(entry.name)
                Files.createDirectory(dir)
            } else {
                val tmpFile = tempDir.resolve(entry.name)
                val content = ear.getInputStream(entry)
                Files.copy(content, tmpFile)

                if(JarFileCollection.isJarFile(tmpFile)) {
                    val jarFile = JarFileCollection(tmpFile)
                    allJarFiles.put(entry.name, jarFile)

                    for(file in jarFile.files()) {
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

        if(parts.size > 1) {
            val jarFile = jarFiles.get(parts[0])
            return jarFile!!.content(parts[1])
        } else {
            return Files.newInputStream(tempDir.resolve(file))
        }
    }

    companion object {
        fun isEarFile(file: Path): Boolean = Files.isRegularFile(file) && (file.fileName.toString().endsWith(".ear"))
    }
}