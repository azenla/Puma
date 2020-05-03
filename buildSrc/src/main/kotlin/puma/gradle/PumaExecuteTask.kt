package puma.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class PumaExecuteTask: DefaultTask() {
  @get:Input
  var executable: String? = "/usr/bin/puma.kexe"

  @get:Input
  @set:Option(option = "device-host", description = "iOS Device Host")
  var deviceHost: String? = System.getenv("PUMA_DEVICE_HOST")

  @get:Input
  @set:Option(option = "args", description = "iOS Execute Arguments")
  var args = listOf<String>()

  @TaskAction
  fun execute() {
    project.exec { exec ->
      exec.commandLine(
        "ssh",
        "root@${deviceHost}",
        executable,
        *args.toTypedArray()
      )
    }
  }
}
