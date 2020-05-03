import kotlin.system.exitProcess
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

    else -> {
      println("Unknown Command.")
      help()
    }
  }
}
