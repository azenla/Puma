import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import puma.gradle.PumaDeployTask
import puma.gradle.PumaExecuteTask

plugins {
  kotlin("multiplatform") version "1.3.72"
  kotlin("plugin.serialization") version "1.3.72"

  id("com.diffplug.gradle.spotless") version "3.27.2"

  id("puma")
}

repositories {
  jcenter()
}

val kotlinSerializationVersion = "0.20.0"
val kotlinCoroutinesVersion = "1.3.5"
val ktorVersion = "1.3.2"

fun kotlinx(name: String, version: String): String = "org.jetbrains.kotlinx:kotlinx-$name:$version"
fun ktor(name: String): String = "io.ktor:ktor-$name:$ktorVersion"

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(kotlin("stdlib-common"))
        api(kotlinx("serialization-runtime-common", kotlinSerializationVersion))
      }
    }
  }

  fun KotlinNativeTarget.configureIosTarget() {
    binaries {
      executable {}
    }

    compilations["main"].defaultSourceSet {
      kotlin.srcDir("src/iosPlatformMain/kotlin")
    }

    compilations["main"].apply {
      dependencies {
        api(kotlinx("serialization-runtime-native", kotlinSerializationVersion))
        api(kotlinx("coroutines-core-native", kotlinCoroutinesVersion))
      }
    }
  }

  iosArm32().configureIosTarget()
  iosArm64().configureIosTarget()
  iosX64().configureIosTarget()
}

spotless {
  format("misc") {
    target("**/*.md", "**/.gitignore")

    trimTrailingWhitespace()
    endWithNewline()
  }

  kotlin {
    target(fileTree("src") {
      include("**/*.kt")
    })

    ktlint().userData(mapOf(
      "indent_size" to "2",
      "continuation_indent_size" to "2"
    ))
    endWithNewline()
  }

  kotlinGradle {
    ktlint().userData(mapOf(
      "indent_size" to "2",
      "continuation_indent_size" to "2"
    ))
    endWithNewline()
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.3"
  distributionType = Wrapper.DistributionType.ALL
}

fun setupDeployAndExecute(systemTarget: String) {
  tasks.register<PumaDeployTask>("deploy$systemTarget") {
    from(tasks.getByName("linkDebugExecutable$systemTarget"))

    entitlements = "resources/puma.entitlements"
    target = "/usr/bin/puma.kexe"
    sign = true
  }

  tasks.register<PumaExecuteTask>("executeIos$systemTarget") {
    dependsOn(tasks.getByName("deployIos$systemTarget"))

    executable = "/usr/bin/puma.kexe"
  }
}

setupDeployAndExecute("IosArm32")
setupDeployAndExecute("IosArm64")
