package jardiff

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

interface FileCollection {
    fun files() : List<String>

    fun content(file: String): InputStream

    companion object {
        fun openCollection(fileName: String) : FileCollection {
            val file = Paths.get(fileName)

            if(!Files.exists(file)) error("$fileName does not exists")

            if(Files.isDirectory(file))
                return DirectoryFileCollection(file)
            else if(NestedJarFileCollection.isNestedJarFile(file))
                return NestedJarFileCollection(file)
            else if(JarFileCollection.isJarFile(file))
                return JarFileCollection(file)
            else
                return SingleFileCollection(file)
        }
    }
}