import den.core.hex
import den.crypto.Sha1Hash
import kotlin.system.exitProcess
import platform.posix.EOF
import platform.posix.getchar
import puma.cmd.launchApplication
import puma.cmd.showDeviceInfo
import puma.cmd.urlSessionGet
import puma.util.vibrate

fun help(): Nothing {
  println("""
    Usage: puma <command> [args]
    
    Commands:
      urlsession-get <url>: Fetch content of a URL
      launch <application>: Launch an Application by Identifier
      device-info: Show Device Information
      vibrate: Play Vibration Alert
      hash-sha1: Read from stdin and SHA1 hash the content
  """.trimIndent())
  exitProcess(1)
}

fun main(args: Array<String>) {
  if (args.isEmpty()) {
    help()
  }

  when (args[0]) {
    "urlsession-get" -> urlSessionGet(args.drop(1))
    "launch" -> launchApplication(args.drop(1))
    "vibrate" -> vibrate()
    "device-info" -> showDeviceInfo()
    "hash-sha1" -> {
      val content: MutableList<Byte> = mutableListOf()
      while (true) {
        val c = getchar()
        if (c == EOF) {
          break
        }
        content.add(c.toByte())
      }
      val hash = Sha1Hash()
      hash.update(content.toByteArray())
      println(hash.digest().hex())
    }

    else -> {
      println("Unknown Command.")
      help()
    }
  }
}
