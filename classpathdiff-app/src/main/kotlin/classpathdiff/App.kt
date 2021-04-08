package classpathdiff

import jardiff.ContentReader
import jardiff.Differ
import jardiff.Dumper
import jardiff.FileCollection
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.optional

fun main(args: Array<String>) {
    val parser = ArgParser("classpathdiff")
    val ignoreClassVersion by parser.option(ArgType.Boolean, "ignore-version", "v", "Ignore class version")
    val listOnly by parser.option(ArgType.Boolean, "list-only", "l", "List classes only")
    val left by parser.argument(ArgType.String, "left-side")
    val right by parser.argument(ArgType.String, "right-side").optional()

    parser.parse(args)
    val leftCollection = ClasspathFileCollection(left.split(":"))

    if(listOnly == true) {
        right.let {
            if(it == null) {
                leftCollection.files().forEach {
                    println(it)
                }
            } else {
                val rightCollection = ClasspathFileCollection(it.split(":"))

                ListDiffer.diff(leftCollection, rightCollection)
            }
        }
    } else {
        val contentReader = ContentReader(ignoreClassVersion ?: false)

        right.let {
            if(it == null) {
                Dumper(contentReader).dumpContents(leftCollection)
            } else {
                val rightCollection = ClasspathFileCollection(it.split(":"))

                Differ(contentReader).diff(leftCollection, rightCollection)
            }
        }
    }
}