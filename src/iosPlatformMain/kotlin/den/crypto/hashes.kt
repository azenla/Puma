package den.crypto

import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap
import platform.CoreCrypto.CC_MD5_CTX
import platform.CoreCrypto.CC_MD5_DIGEST_LENGTH
import platform.CoreCrypto.CC_MD5_Final
import platform.CoreCrypto.CC_MD5_Init
import platform.CoreCrypto.CC_MD5_Update
import platform.CoreCrypto.CC_SHA1_CTX
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA1_Final
import platform.CoreCrypto.CC_SHA1_Init
import platform.CoreCrypto.CC_SHA1_Update
import platform.CoreCrypto.CC_SHA256_CTX
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA256_Final
import platform.CoreCrypto.CC_SHA256_Init
import platform.CoreCrypto.CC_SHA256_Update
import platform.CoreCrypto.CC_SHA512_CTX
import platform.CoreCrypto.CC_SHA512_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA512_Final
import platform.CoreCrypto.CC_SHA512_Init
import platform.CoreCrypto.CC_SHA512_Update

class Md5Hash : Hash<CC_MD5_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_MD5_Init(ctx) },
  update = { ctx, data, length -> CC_MD5_Update(ctx, data, length) },
  final = { digest, ctx -> CC_MD5_Final(digest, ctx) },
  sizeOfDigest = CC_MD5_DIGEST_LENGTH
)

class Sha1Hash : Hash<CC_SHA1_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA1_Init(ctx) },
  update = { ctx, data, length -> CC_SHA1_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA1_Final(digest, ctx) },
  sizeOfDigest = CC_SHA1_DIGEST_LENGTH
)

class Sha256Hash : Hash<CC_SHA256_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA256_Init(ctx) },
  update = { ctx, data, length -> CC_SHA256_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA256_Final(digest, ctx) },
  sizeOfDigest = CC_SHA256_DIGEST_LENGTH
)

class Sha512Hash : Hash<CC_SHA512_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA512_Init(ctx) },
  update = { ctx, data, length -> CC_SHA512_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA512_Final(digest, ctx) },
  sizeOfDigest = CC_SHA512_DIGEST_LENGTH
)
