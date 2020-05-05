package den.core

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

fun ByteArray.toNSData(): NSData =
  usePinned { pin ->
    NSData.dataWithBytes(pin.addressOf(0), size.convert())
  }
