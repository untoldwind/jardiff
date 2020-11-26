package jardiff

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils

object Differ {
    fun diff(leftCollection: FileCollection, rightCollection: FileCollection) {
        val leftFiles = leftCollection.files().toSet()
        val rightFiles = rightCollection.files().toSet()

        for(file in (leftFiles + rightFiles).sorted()) {
            if(!leftFiles.contains(file)) {
                println()
                println("Left missing: $file")
                break
            }
            if(!rightFiles.contains(file)) {
                println()
                println("Right missing: $file")
                break
            }

            val leftContent = leftCollection.lines(file)
            val rightContent = rightCollection.lines(file)
            val diff = DiffUtils.diff(leftContent, rightContent)

            if(!diff.deltas.isEmpty()) {
                println()
                UnifiedDiffUtils.generateUnifiedDiff("<left>/$file", "<right>/$file", leftContent, diff, 0).forEach { println(it) }
            }
        }
    }
}