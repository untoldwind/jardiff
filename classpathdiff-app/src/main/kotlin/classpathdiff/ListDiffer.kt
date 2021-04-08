package classpathdiff

import jardiff.FileCollection

object ListDiffer {
    fun diff(leftCollection: FileCollection, rightCollection: FileCollection) {
        val leftFiles = leftCollection.files().toSet()
        val rightFiles = rightCollection.files().toSet()

        for (file in (leftFiles + rightFiles).sorted()) {
            if (!leftFiles.contains(file)) {
                println("Left missing: $file")
                continue
            }
            if (!rightFiles.contains(file)) {
                println("Right missing: $file")
                continue
            }
        }
    }
}