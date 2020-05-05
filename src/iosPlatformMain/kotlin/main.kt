import den.crypto.Md5Hash
import den.crypto.Sha1Hash
import den.crypto.Sha224Hash
import den.crypto.Sha256Hash
import den.crypto.Sha384Hash
import den.crypto.Sha512Hash
import den.device.ImpactGenerator
import den.services.backboard.BackboardServices
import den.services.mobilegestalt.MobileGestalt
import den.services.mobilegestalt.mobileGestaltDatabase
import den.services.use
import kotlin.system.exitProcess
import kotlinx.cinterop.toKString
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import platform.posix.getenv
import puma.cmd.hashAndPrint
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

      hash-md5: Read from stdin and MD5 hash the content
      hash-sha1: Read from stdin and SHA1 hash the content
      hash-sha224: Read from stdin and SHA224 hash the content
      hash-sha256: Read from stdin and SHA256 hash the content
      hash-sha384: Read from stdin and SHA384 hash the content
      hash-sha512: Read from stdin and SHA512 hash the content

      gestalt-print-all: Read Valid Mobile Gestalt Values
  """.trimIndent())
  exitProcess(1)
}

fun main(args: Array<String>) {
  if (args.isEmpty()) {
    help()
  }

  val isJsonMode = (getenv("PUMA_OUTPUT_JSON")?.toKString() ?: "false").toBoolean()

  when (args[0]) {
    "urlsession-get" -> urlSessionGet(args.drop(1))
    "launch" -> launchApplication(args.drop(1))
    "vibrate" -> vibrate()
    "device-info" -> showDeviceInfo()
    "set-backlight-level" -> BackboardServices.open().use {
      setBacklightLevel(args[1].toFloat())
    }

    "hash-md5" -> hashAndPrint(Md5Hash)
    "hash-sha1" -> hashAndPrint(Sha1Hash)
    "hash-sha224" -> hashAndPrint(Sha224Hash)
    "hash-sha256" -> hashAndPrint(Sha256Hash)
    "hash-sha384" -> hashAndPrint(Sha384Hash)
    "hash-sha512" -> hashAndPrint(Sha512Hash)

    // Broken without UI mainScreen.
    "impact" -> ImpactGenerator().impact()

    "gestalt-print-all" -> MobileGestalt.open().use {
      val allKeyValues = mutableMapOf<String, String>()
      mobileGestaltDatabase.entries.sortedBy { it.value ?: it.key }.forEach { entry ->
        val value = read(entry.key)
        val mappedKey = entry.value ?: entry.key
        if (value != null) {
          allKeyValues[mappedKey] = value
          if (!isJsonMode) {
            println("$mappedKey: $value")
          }
        }
      }

      if (isJsonMode) {
        println(Json(JsonConfiguration.Stable.copy(
          prettyPrint = true,
          indent = "  "
        )).stringify(MapSerializer(
          String.serializer(),
          String.serializer()
        ), allKeyValues))
      }
    }

    else -> {
      println("Unknown Command.")
      help()
    }
  }
}
