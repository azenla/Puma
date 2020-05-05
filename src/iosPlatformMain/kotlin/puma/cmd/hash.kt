package puma.cmd

import den.core.hex
import den.crypto.Hash
import platform.posix.EOF
import platform.posix.getchar

fun hashAndPrint(hash: Hash<*>) {
  val content: MutableList<Byte> = mutableListOf()
  while (true) {
    val c = getchar()
    if (c == EOF) {
      break
    }
    content.add(c.toByte())
  }
  hash.update(content.toByteArray())
  println(hash.digest().hex())
}
