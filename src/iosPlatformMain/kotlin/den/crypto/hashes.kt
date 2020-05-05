package den.crypto

import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap
import platform.CoreCrypto.CC_MD2
import platform.CoreCrypto.CC_MD2_CTX
import platform.CoreCrypto.CC_MD2_DIGEST_LENGTH
import platform.CoreCrypto.CC_MD2_Final
import platform.CoreCrypto.CC_MD2_Init
import platform.CoreCrypto.CC_MD2_Update
import platform.CoreCrypto.CC_MD4
import platform.CoreCrypto.CC_MD4_CTX
import platform.CoreCrypto.CC_MD4_DIGEST_LENGTH
import platform.CoreCrypto.CC_MD4_Final
import platform.CoreCrypto.CC_MD4_Init
import platform.CoreCrypto.CC_MD4_Update
import platform.CoreCrypto.CC_MD5
import platform.CoreCrypto.CC_MD5_CTX
import platform.CoreCrypto.CC_MD5_DIGEST_LENGTH
import platform.CoreCrypto.CC_MD5_Final
import platform.CoreCrypto.CC_MD5_Init
import platform.CoreCrypto.CC_MD5_Update
import platform.CoreCrypto.CC_SHA1
import platform.CoreCrypto.CC_SHA1_CTX
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA1_Final
import platform.CoreCrypto.CC_SHA1_Init
import platform.CoreCrypto.CC_SHA1_Update
import platform.CoreCrypto.CC_SHA224
import platform.CoreCrypto.CC_SHA224_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA224_Final
import platform.CoreCrypto.CC_SHA224_Init
import platform.CoreCrypto.CC_SHA224_Update
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_CTX
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA256_Final
import platform.CoreCrypto.CC_SHA256_Init
import platform.CoreCrypto.CC_SHA256_Update
import platform.CoreCrypto.CC_SHA384
import platform.CoreCrypto.CC_SHA384_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA384_Final
import platform.CoreCrypto.CC_SHA384_Init
import platform.CoreCrypto.CC_SHA384_Update
import platform.CoreCrypto.CC_SHA512
import platform.CoreCrypto.CC_SHA512_CTX
import platform.CoreCrypto.CC_SHA512_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA512_Final
import platform.CoreCrypto.CC_SHA512_Init
import platform.CoreCrypto.CC_SHA512_Update

class Md2Hash : Hash<CC_MD2_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_MD2_Init(ctx) },
  update = { ctx, data, length -> CC_MD2_Update(ctx, data, length) },
  final = { digest, ctx -> CC_MD2_Final(digest, ctx) },
  sizeOfDigest = CC_MD2_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_MD2(data, length, digest) }
  )
}

class Md4Hash : Hash<CC_MD4_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_MD4_Init(ctx) },
  update = { ctx, data, length -> CC_MD4_Update(ctx, data, length) },
  final = { digest, ctx -> CC_MD4_Final(digest, ctx) },
  sizeOfDigest = CC_MD4_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_MD4(data, length, digest) }
  )
}

class Md5Hash : Hash<CC_MD5_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_MD5_Init(ctx) },
  update = { ctx, data, length -> CC_MD5_Update(ctx, data, length) },
  final = { digest, ctx -> CC_MD5_Final(digest, ctx) },
  sizeOfDigest = CC_MD5_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_MD5(data, length, digest) }
  )
}

class Sha1Hash : Hash<CC_SHA1_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA1_Init(ctx) },
  update = { ctx, data, length -> CC_SHA1_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA1_Final(digest, ctx) },
  sizeOfDigest = CC_SHA1_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_SHA1(data, length, digest) }
  )
}

class Sha224Hash : Hash<CC_SHA256_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA224_Init(ctx) },
  update = { ctx, data, length -> CC_SHA224_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA224_Final(digest, ctx) },
  sizeOfDigest = CC_SHA224_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_SHA224(data, length, digest) }
  )
}

class Sha256Hash : Hash<CC_SHA256_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA256_Init(ctx) },
  update = { ctx, data, length -> CC_SHA256_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA256_Final(digest, ctx) },
  sizeOfDigest = CC_SHA256_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_SHA256(data, length, digest) }
  )
}

class Sha384Hash : Hash<CC_SHA512_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA384_Init(ctx) },
  update = { ctx, data, length -> CC_SHA384_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA384_Final(digest, ctx) },
  sizeOfDigest = CC_SHA384_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_SHA384(data, length, digest) }
  )
}

class Sha512Hash : Hash<CC_SHA512_CTX>(
  context = nativeHeap.alloc(),
  init = { ctx -> CC_SHA512_Init(ctx) },
  update = { ctx, data, length -> CC_SHA512_Update(ctx, data, length) },
  final = { digest, ctx -> CC_SHA512_Final(digest, ctx) },
  sizeOfDigest = CC_SHA512_DIGEST_LENGTH
) {
  companion object : HashCompanion(
    digest = { data, length, digest -> CC_SHA512(data, length, digest) }
  )
}
