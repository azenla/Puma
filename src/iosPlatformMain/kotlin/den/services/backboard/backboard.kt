package den.services.backboard

import den.services.PrivateLibrary
import den.services.PrivateLibraryLoader
import den.services.getPrivateFrameworkServicePath
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.invoke

class BackboardServices(handle: COpaquePointer) : PrivateLibrary(handle) {
  fun hasAmbientLightSensor(): Boolean {
    val function: CPointer<CFunction<() -> Int>> = symbol(
      "BKSHIDServicesAmbientLightSensorExists"
    )

    return function() == 1
  }

  fun setBacklightLevel(factor: Float) {
    val function: CPointer<CFunction<(Float, Int) -> Unit>> = symbol(
      "BKSHIDServicesSetBacklightFactorWithFadeDuration"
    )

    function(factor, 0)
  }

  companion object : PrivateLibraryLoader<BackboardServices>(
    getPrivateFrameworkServicePath("BackBoardServices")
  ) {
    override fun create(handle: COpaquePointer): BackboardServices =
      BackboardServices(handle)
  }
}
