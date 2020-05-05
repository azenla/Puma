package puma.cmd

import den.core.hex
import den.crypto.HashAlgorithm
import platform.posix.EOF
import platform.posix.getchar

fun hashAndPrint(hash: HashAlgorithm) {
  val content: MutableList<Byte> = mutableListOf()
  while (true) {
    val c = getchar()
    if (c == EOF) {
      break
    }
    content.add(c.toByte())
  }
  val bytes = hash.digest(content.toByteArray())
  println(bytes.hex())
}
