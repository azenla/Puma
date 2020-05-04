package den.core

import kotlinx.cinterop.convert
import platform.CoreFoundation.CFMutableStringRef
import platform.CoreFoundation.CFStringAppendFormat
import platform.CoreFoundation.CFStringCreateMutable
import platform.CoreFoundation.kCFAllocatorDefault

fun ByteArray.hex(): String {
  val buffer: CFMutableStringRef = CFStringCreateMutable(
    kCFAllocatorDefault,
    (size * 2).convert()
  ) ?: throw RuntimeException("Failed to allocate CFMutableString")

  val format = "%02x".toCFStringRef()
  forEach { b ->
    @Suppress("EXPERIMENTAL_API_USAGE")
    CFStringAppendFormat(
      buffer,
      null,
      format,
      b.toUByte()
    )
  }

  return buffer.toKString()
}
