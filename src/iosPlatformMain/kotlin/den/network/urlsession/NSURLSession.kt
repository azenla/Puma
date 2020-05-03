package den.network.urlsession

import kotlin.native.concurrent.freeze
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLResponse
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionDataTask
import platform.Foundation.dataTaskWithURL

fun NSURLSession
  .dataTaskWithUrl(
    url: NSURL,
    completionHandler: (NSData?, NSURLResponse?, NSError?) -> Unit
  ): NSURLSessionDataTask =
  dataTaskWithURL(url, completionHandler = completionHandler.freeze())
