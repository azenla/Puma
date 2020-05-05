package den.services.mobilegestalt

import den.core.toNSData
import den.crypto.Md5Hash
import platform.Foundation.base64Encoding

fun calculateObfuscatedKey(key: String): String {
  val pre = "MGCopyAnswer$key"
  val digest = pre.encodeToByteArray()
  val hash = Md5Hash.digest(digest).toNSData()
  return hash.base64Encoding().substring(0, 22)
}
