package jardiff

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.optional

fun main(args: Array<String>) {
    val parser = ArgParser("jardiff")
    val ignoreClassVersion by parser.option(ArgType.Boolean, "ignore-version", "v", "Ignore class version")
    val left by parser.argument(ArgType.String, "left-side")
    val right by parser.argument(ArgType.String, "right-side").optional()

    parser.parse(args)

    val contentReader = ContentReader(ignoreClassVersion ?: false)
    val leftCollection = FileCollection.openCollection(left)
    right.let {
        if(it == null) {
            Dumper(contentReader).dumpContents(leftCollection)
        } else {
            val rightCollection = FileCollection.openCollection(it)

            Differ(contentReader).diff(leftCollection, rightCollection)
        }
    }
}
