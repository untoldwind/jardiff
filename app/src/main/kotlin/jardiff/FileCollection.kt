package jardiff

import java.nio.file.Files
import java.nio.file.Paths

interface FileCollection {
    fun files() : List<String>

    fun lines(file: String): List<String>

    companion object {
        fun openCollection(fileName: String) : FileCollection {
            val file = Paths.get(fileName)

            if(!Files.exists(file)) error("$fileName does not exists")

            if(Files.isDirectory(file))
                return DirectoryFileCollection(file)
            else if(JarFileCollection.isJarFile(file))
                return JarFileCollection(file)
            else
                return SingleFileCollection(file)
        }
    }
}