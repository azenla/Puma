import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.invoke
import kotlinx.cinterop.reinterpret
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.FALSE
import platform.CoreFoundation.TRUE
import platform.posix.RTLD_GLOBAL
import platform.posix.dlclose
import platform.posix.dlopen
import platform.posix.dlsym
import puma.util.asCFStringRef
import puma.util.toKString

private typealias SBLaunchApplicationWithIdentifier = CPointer<CFunction<(CFStringRef, Int) -> Int>>
private typealias SBLaunchingErrorString = CPointer<CFunction<(Int) -> CFStringRef>>
private typealias SBCopyFrontmostApplicationDisplayIdentifier = CPointer<CFunction<() -> CFStringRef>>
private typealias SBCopyStatusBarOperatorName = CPointer<CFunction<() -> CFStringRef>>

class SpringBoardServices(val handle: COpaquePointer) {
  fun launchApplicationWithIdentifier(identifier: String, suspend: Boolean = false) {
    val function: SBLaunchApplicationWithIdentifier = symbol("SBSLaunchApplicationWithIdentifier")
    val identifierRef = identifier.asCFStringRef()
    val result = function(identifierRef, if (suspend) TRUE else FALSE)

    if (result != 0) {
      val error = getLaunchError(result)

      throw RuntimeException("Failed to launch application with identifier $identifier: $error")
    }
  }

  fun getLaunchError(code: Int): String {
    val function: SBLaunchingErrorString = symbol("SBSApplicationLaunchingErrorString")
    return function(code).toKString()
  }

  fun getStatusBarOperatorName(): String {
    val function: SBCopyStatusBarOperatorName = symbol("SBSCopyStatusBarOperatorName")
    return function().toKString()
  }

  fun getFrontmostApplicationIdentifier(): String {
    val function: SBCopyFrontmostApplicationDisplayIdentifier = symbol("SBSCopyFrontmostApplicationDisplayIdentifier")
    return function().toKString()
  }

  fun close() {
    val result = dlclose(handle)
    if (result != 0) {
      throw RuntimeException("Failed to close SpringBoard services.")
    }
  }

  private inline fun <reified T : CPointed> symbol(name: String): CPointer<T> =
    dlsym(handle, name)!!.reinterpret<T>()

  companion object {
    fun open(): SpringBoardServices = SpringBoardServices(dlopen(
      "/System/Library/PrivateFrameworks/SpringBoardServices.framework/SpringBoardServices",
      RTLD_GLOBAL
    ) ?: throw RuntimeException("Failed to open SpringBoard services."))
  }
}
