package puma.cmd

import kotlin.math.round
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceBatteryState
import den.services.springboard.SpringBoardServices

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
}

fun Double.roundTo(decimals: Int): Double {
  var multiplier = 1.0
  repeat(decimals) { multiplier *= 10 }
  return round(this * multiplier) / multiplier
}
