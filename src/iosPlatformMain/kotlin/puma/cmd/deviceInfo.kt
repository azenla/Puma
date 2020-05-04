package puma.cmd

import den.services.springboard.SpringBoardServices
import kotlin.math.round
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.invoke
import kotlinx.cinterop.reinterpret
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceBatteryState
import platform.posix.RTLD_GLOBAL
import platform.posix.dlclose
import platform.posix.dlopen
import platform.posix.dlsym
import platform.posix.size_t

fun showDeviceInfo() {
  val device = UIDevice.currentDevice
  device.setBatteryMonitoringEnabled(true)

  println("Device Name: ${device.name}")
  println("Device Model: ${device.model}")
  println("System Name: ${device.systemName}")
  println("System Version: ${device.systemVersion}")
  val batteryStateString = when (device.batteryState) {
    UIDeviceBatteryState.UIDeviceBatteryStateUnknown -> "unknown"
    UIDeviceBatteryState.UIDeviceBatteryStateFull -> "full"
    UIDeviceBatteryState.UIDeviceBatteryStateUnplugged -> "unplugged"
    UIDeviceBatteryState.UIDeviceBatteryStateCharging -> "charging"
    else -> "Unknown"
  }
  println("Battery State: $batteryStateString")
  println("Battery Level: ${(device.batteryLevel * 100.0).roundTo(3)}%")

  val springboard = SpringBoardServices.open()
  try {
    val frontmost = springboard.getFrontmostApplicationIdentifier()
    if (frontmost != null) {
      println("Active Application: $frontmost")
    }
  } finally {
    springboard.close()
  }

  val libc = dlopen("libc.dylib", RTLD_GLOBAL)
  val function: CPointer<CFunction<() -> size_t>> =
    dlsym(libc, "os_proc_available_memory")!!.reinterpret()
  val memory = function()
  @Suppress("EXPERIMENTAL_API_USAGE")
  println("Available Memory: ${memory.toInt() / 1024 / 1024}MB")
  dlclose(libc)
}

fun Double.roundTo(decimals: Int): Double {
  var multiplier = 1.0
  repeat(decimals) { multiplier *= 10 }
  return round(this * multiplier) / multiplier
}
