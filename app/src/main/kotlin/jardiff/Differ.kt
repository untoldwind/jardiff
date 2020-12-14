package jardiff

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils

class Differ(private val contentReader: ContentReader) {
    fun diff(leftCollection: FileCollection, rightCollection: FileCollection) {
        val leftFiles = leftCollection.files().toSet()
        val rightFiles = rightCollection.files().toSet()

        for (file in (leftFiles + rightFiles).sorted()) {
            if (!leftFiles.contains(file)) {
                println()
                println("Left missing: $file")
                continue
            }
            if (!rightFiles.contains(file)) {
                println()
                println("Right missing: $file")
                continue
            }

            val leftContent = contentReader.readContent(file, leftCollection.content(file))
            val rightContent = contentReader.readContent(file, rightCollection.content(file))
            val diff = DiffUtils.diff(leftContent, rightContent)

            if (!diff.deltas.isEmpty()) {
                println()
                UnifiedDiffUtils.generateUnifiedDiff("<left>/$file", "<right>/$file", leftContent, diff, 0).forEach { println(it) }
            }
        }
    }
}