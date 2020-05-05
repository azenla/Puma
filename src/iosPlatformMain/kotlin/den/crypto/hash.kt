package den.crypto

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
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

typealias CCHashDigestFunc = (CValuesRef<*>, CC_LONG, CValuesRef<UByteVar>) -> CPointer<UByteVar>?

@Suppress("EXPERIMENTAL_API_USAGE")
abstract class HashContext<T : CPointed>(
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

  fun update(data: ByteArray, offset: Int, length: Int) {
    data.usePinned { pin ->
      checkHashCall(update.invoke(context.ptr, pin.addressOf(offset), length.convert()))
    }
  }

  fun update(data: ByteArray) = update(data, 0, data.size)

  fun digest(data: ByteArray) = digest(data, 0, data.size)

  fun digest(data: ByteArray, offset: Int, length: Int): ByteArray {
    update(data, offset, length)
    return digest()
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

@Suppress("EXPERIMENTAL_API_USAGE")
abstract class HashAlgorithm(val sizeOfDigest: Int, private val digest: CCHashDigestFunc) {
  fun digest(data: ByteArray): ByteArray {
    val dataSize = data.size
    val messageDigest = UByteArray(sizeOfDigest)

    data.usePinned { dataPin ->
      messageDigest.usePinned { messageDigestPin ->
        digest.invoke(
          dataPin.addressOf(0),
          dataSize.convert(),
          messageDigestPin.addressOf(0)
        ) ?: throw RuntimeException("Failed to digest input data.")
      }
    }

    return messageDigest.toByteArray()
  }
}
