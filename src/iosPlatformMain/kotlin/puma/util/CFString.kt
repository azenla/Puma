package puma.util

import kotlinx.cinterop.toKStringFromUtf8
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.CFStringGetCStringPtr
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFStringEncodingUTF8

fun String.asCFStringRef(): CFStringRef = CFStringCreateWithCString(
  kCFAllocatorDefault,
  this,
  kCFStringEncodingUTF8
) ?: throw RuntimeException("Failed to create CFStringRef.")

fun CFStringRef.toKString(): String =
  CFStringGetCStringPtr(this, kCFStringEncodingUTF8)!!.toKStringFromUtf8()
