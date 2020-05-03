package puma.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class PumaDeployTask: DefaultTask() {
  @get:Input
  var executableTask: Task? = null

  @get:Input
  var target: String? = null

  @get:Input
  var entitlements: String = ""

  @get:Input
  var sign: Boolean = true

  @get:Input
  @set:Option(option = "device-host", description = "iOS Device Host")
  var deviceHost: String? = System.getenv("PUMA_DEVICE_HOST")

  @TaskAction
  fun deploy() {
    project.exec { exec ->
      exec.commandLine(
        "scp",
        executableTask?.outputs?.files?.first()?.listFiles()?.first {
          it.name.endsWith(".kexe")
        }?.absolutePath,
        "root@${deviceHost}:${target}"
      )
    }

    if (sign) {
      var entitlementMaybePath = ""

      if (entitlements.isNotEmpty()) {
        entitlementMaybePath = "/tmp/puma.entitlements"
        project.exec { exec ->
          exec.commandLine(
            "scp",
            entitlements,
            "root@${deviceHost}:$entitlementMaybePath"
          )
        }
      }

      project.exec { exec ->
        exec.commandLine(
          "ssh",
          "root@${deviceHost}",
          "ldid",
          "-S$entitlementMaybePath",
          target
        )
      }
    }
  }

  fun from(task: Task) {
    dependsOn(task)
    executableTask = task
  }
}
