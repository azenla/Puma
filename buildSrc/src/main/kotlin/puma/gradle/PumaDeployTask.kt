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
    logger.lifecycle("Puma Deployment: Device Host is $deviceHost")

    upload(executableTask?.outputs?.files?.first()?.listFiles()?.first {
      it.name.endsWith(".kexe")
    }?.relativeTo(project.projectDir)?.path!!, target!!)

    if (sign) {
      var deviceEntitlementPath = ""

      if (entitlements.isNotEmpty()) {
        deviceEntitlementPath = "/tmp/puma.entitlements"
        upload(entitlements, deviceEntitlementPath)
      }

      execute(
        "ldid",
        "-S$deviceEntitlementPath",
        target!!
      )
    }
  }

  fun execute(vararg cmd: String) {
    logger.lifecycle("Puma Deployment: Device Execute ${cmd.joinToString(" ")}")

    project.exec { exec ->
      exec.commandLine(
        "ssh",
        "-o",
        "BatchMode=yes",
        "-o",
        "LogLevel=ERROR",
        "-o",
        "StrictHostKeyChecking=no",
        "-o",
        "UserKnownHostsFile=/dev/null",
        "root@${deviceHost}",
        *cmd
      )
    }
  }

  fun upload(source: String, target: String) {
    logger.lifecycle("Puma Deployment: Device Upload $source -> $target")
    project.exec { exec ->
      exec.commandLine(
        "scp",
        "-o",
        "BatchMode=yes",
        "-o",
        "LogLevel=ERROR",
        "-o",
        "StrictHostKeyChecking=no",
        "-o",
        "UserKnownHostsFile=/dev/null",
        source,
        "root@$deviceHost:$target"
      )
    }
  }

  fun from(task: Task) {
    dependsOn(task)
    executableTask = task
  }
}
