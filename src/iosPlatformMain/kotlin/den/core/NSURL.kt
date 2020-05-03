package den.core

import platform.Foundation.NSURL

fun String.toNSURL(): NSURL = NSURL(string = this)
fun NSURL.toKString(): String? = absoluteString
