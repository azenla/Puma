package den.services.mobilegestalt

import den.services.PrivateLibrary
import den.services.PrivateLibraryLoader
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.invoke
import platform.Foundation.NSString

typealias MGCopyAnswer = CPointer<CFunction<(NSString) -> NSString?>>

@Suppress("CAST_NEVER_SUCCEEDS")
class MobileGestalt(handle: COpaquePointer) :
  PrivateLibrary(handle) {
  fun read(key: String): String? {
    val function: MGCopyAnswer = symbol("MGCopyAnswer")
    return function(key as NSString) as? String
  }

  companion object : PrivateLibraryLoader<MobileGestalt>(
    "/usr/lib/libMobileGestalt.dylib"
  ) {
    override fun create(handle: COpaquePointer): MobileGestalt =
      MobileGestalt(handle)
  }
}
