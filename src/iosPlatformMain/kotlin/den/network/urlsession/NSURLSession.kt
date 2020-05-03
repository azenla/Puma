package den.network.urlsession

import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLResponse
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionDataTask
import platform.Foundation.dataTaskWithURL
import kotlin.native.concurrent.freeze

fun NSURLSession
  .dataTaskWithUrl(
  url: NSURL,
  completionHandler: (NSData?, NSURLResponse?, NSError?) -> Unit
): NSURLSessionDataTask =
  dataTaskWithURL(url, completionHandler = completionHandler.freeze())
