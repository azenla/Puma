package puma.cmd

import den.services.mobilegestalt.MobileGestalt
import den.services.springboard.SpringBoardServices
import den.services.use
import kotlin.math.abs
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

  MobileGestalt.open().use {
    println("Device Marketing Name: ${read("MarketingProductName")}")
    println("Device Product Type: ${read("ProductType")}")
    println("Device Serial Number: ${read("SerialNumber")}")
    println("Device Unique Identifier: ${read("UniqueDeviceID")}")
  }

  println("System Name: ${device.systemName}")
  println("System Version: ${device.systemVersion}")
  val batteryStateString = when (device.batteryState) {
    UIDeviceBatteryState.UIDeviceBatteryStateUnknown -> "unknown"
    UIDeviceBatteryState.UIDeviceBatteryStateFull -> "full"
    UIDeviceBatteryState.UIDeviceBatteryStateUnplugged -> "unplugged"
    UIDeviceBatteryState.UIDeviceBatteryStateCharging -> "charging"
    else -> "unknown"
  }
  println("Battery State: $batteryStateString")
  println("Battery Level: ${(device.batteryLevel * 100).toInt()}%")

  val libc = dlopen("libc.dylib", RTLD_GLOBAL)
  val function: CPointer<CFunction<() -> size_t>>? =
    dlsym(libc, "os_proc_available_memory")?.reinterpret()
  if (function != null) {
    val memory = function()
    @Suppress("EXPERIMENTAL_API_USAGE")
    println("Available Memory: ${abs(memory.toInt()) / 1024 / 1024}MB")
    dlclose(libc)
  }

  SpringBoardServices.open().use {
    val frontmost = getFrontmostApplicationIdentifier()
    if (frontmost != null) {
      println("Active Application: $frontmost")
    }
  }
}
