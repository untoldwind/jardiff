package classpathdiff

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

    if(listOnly ?: false) {
        leftCollection.files().forEach {
            println(it)
        }
    }
}