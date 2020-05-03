package den.services

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import platform.posix.RTLD_GLOBAL
import platform.posix.dlclose
import platform.posix.dlopen
import platform.posix.dlsym

abstract class PrivateLibrary(val handle: COpaquePointer) {
  protected inline fun <reified T : CPointed> symbol(name: String): CPointer<T> =
    dlsym(handle, name)!!.reinterpret<T>()

  fun close() {
    val code = dlclose(handle)
    if (code != 0) {
      throw RuntimeException("Failed to close service.")
    }
  }
}

abstract class PrivateLibraryLoader<T: PrivateLibrary>(val path: String) {
  abstract fun create(handle: COpaquePointer): T

  fun open(): T = create(
    dlopen(path, RTLD_GLOBAL) ?:
      throw RuntimeException("Failed to load service $path")
  )
}

fun <T: PrivateLibrary> T.use(block: T.() -> Unit) {
  try {
    block(this)
  } finally {
    close()
  }
}

internal fun getPrivateFrameworkPath(framework: String): String =
  "/System/Library/PrivateFrameworks/${framework}.framework"

internal fun getPrivateFrameworkServicePath(framework: String): String =
  "${getPrivateFrameworkPath(framework)}/${framework}"
