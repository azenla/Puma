import kotlin.system.exitProcess
import puma.util.vibrate

fun help(): Nothing {
  println("""
    Usage: puma <command> [args]
    
    Commands:
      urlsession-get <url>: Fetch content of a URL
      launch <application>: Launch an Application by Identifier
      device-info: Show Device Information
      vibrate: Play Vibration Alert
      impact: Play Impact Feedback at Heavy Level
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
    "impact" -> impact()

    else -> {
      println("Unknown Command.")
      help()
    }
  }
}
