package puma.util

import den.concurrent.waitForSignal
import kotlin.native.concurrent.freeze
import platform.AudioToolbox.AudioServicesPlayAlertSoundWithCompletion
import platform.AudioToolbox.kSystemSoundID_Vibrate

fun vibrate() = waitForSignal {
  AudioServicesPlayAlertSoundWithCompletion(kSystemSoundID_Vibrate, {
    signal()
  }.freeze())
}
