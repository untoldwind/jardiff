package jardiff

import org.apache.commons.io.HexDump
import org.objectweb.asm.ClassReader
import org.objectweb.asm.util.Textifier
import org.objectweb.asm.util.TraceClassVisitor
import java.io.*

class ContentReader(val ignoreClassVersion: Boolean) {
    fun readContent(fileName: String, input: InputStream): List<String> = when (extension(fileName)) {
        "mf", "xml", "properties" -> lineDump(input)
        "class" -> classDump(input)
        else -> hexDump(input)
    }

    private fun extension(fileName: String): String? {
        val idx = fileName.lastIndexOf('.')
        if (idx < 0) return null

        return fileName.substring(idx + 1).toLowerCase()
    }

    private fun lineDump(input: InputStream): List<String> = BufferedReader(InputStreamReader(input, Charsets.UTF_8)).readLines()

    private fun hexDump(input: InputStream): List<String> {
        val bytes = input.readAllBytes()

        if (bytes.isEmpty()) return emptyList()

        val os = ByteArrayOutputStream()

        HexDump.dump(bytes, 0, os, 0)

        return os.toString(Charsets.UTF_8).split('\n')
    }

    private fun classDump(input: InputStream): List<String> {
        val out = StringWriter()
        val visitor = TraceClassVisitor(null, Textifier(), PrintWriter(out))

        ClassReader(input).accept(visitor, ClassReader.SKIP_DEBUG)

        val lines = out.toString().split('\n')

        return if(ignoreClassVersion) lines.filter { !it.startsWith("// class version ") } else lines
    }
}