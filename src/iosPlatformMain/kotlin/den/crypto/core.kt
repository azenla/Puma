package den.crypto

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_LONG
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH

typealias CCHashInitFunc<T> = (CValuesRef<T>) -> Int
typealias CCHashUpdateFunc<T> = (CValuesRef<T>, CValuesRef<*>, CC_LONG) -> Int
typealias CCHashFinalFunc<T> = (CValuesRef<UByteVar>, CValuesRef<T>) -> Int

@Suppress("EXPERIMENTAL_API_USAGE")
abstract class Hash<T : CPointed>(
  private val context: T,
  init: CCHashInitFunc<T>,
  private val update: CCHashUpdateFunc<T>,
  private val final: CCHashFinalFunc<T>,
  private val sizeOfDigest: Int
) {
  private val digest: UByteArray = UByteArray(CC_SHA1_DIGEST_LENGTH)

  init {
    checkHashCall(init.invoke(context.ptr))
  }

  fun update(data: ByteArray) {
    data.usePinned { pin ->
      checkHashCall(update.invoke(context.ptr, pin.addressOf(0), data.size.convert()))
    }
  }

  fun digest(): ByteArray {
    digest.usePinned { pin ->
      checkHashCall(final.invoke(pin.addressOf(0), context.ptr))
    }

    return digest.toByteArray()
  }

  private fun checkHashCall(code: Int) {
    if (code != 1) {
      throw RuntimeException("CommonCrypto call failed.")
    }
  }
}
