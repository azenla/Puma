package den.services.springboard

import den.core.asCFStringRef
import den.core.toKString
import den.services.PrivateLibrary
import den.services.PrivateLibraryLoader
import den.services.getPrivateFrameworkServicePath
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.invoke
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.FALSE
import platform.CoreFoundation.TRUE

private typealias SBLaunchApplicationWithIdentifier = CPointer<CFunction<(CFStringRef, Int) -> Int>>
private typealias SBLaunchingErrorString = CPointer<CFunction<(Int) -> CFStringRef>>
private typealias SBCopyFrontmostApplicationDisplayIdentifier = CPointer<CFunction<() -> CFStringRef?>>
private typealias SBCopyStatusBarOperatorName = CPointer<CFunction<() -> CFStringRef?>>

class SpringBoardServices(handle: COpaquePointer): PrivateLibrary(handle) {
  fun launchApplicationWithIdentifier(identifier: String, suspend: Boolean = false) {
    val function: SBLaunchApplicationWithIdentifier =
      symbol("SBSLaunchApplicationWithIdentifier")
    val identifierRef = identifier.asCFStringRef()
    val result = function(identifierRef, if (suspend) TRUE else FALSE)

    if (result != 0) {
      val error = getLaunchError(result)

      throw RuntimeException("Failed to launch application with identifier $identifier: $error")
    }
  }

  fun getLaunchError(code: Int): String {
    val function: SBLaunchingErrorString =
      symbol("SBSApplicationLaunchingErrorString")
    return function(code).toKString()
  }

  fun getFrontmostApplicationIdentifier(): String? {
    val function: SBCopyFrontmostApplicationDisplayIdentifier =
      symbol("SBSCopyFrontmostApplicationDisplayIdentifier")
    return function().toKString()
  }

  companion object: PrivateLibraryLoader<SpringBoardServices>(
    getPrivateFrameworkServicePath("SpringBoardServices")
  ) {
    override fun create(handle: COpaquePointer): SpringBoardServices =
      SpringBoardServices(handle)
  }
}
