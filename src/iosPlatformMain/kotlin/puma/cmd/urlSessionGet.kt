package puma.cmd

import den.concurrent.DispatchSemaphore
import den.core.toNSURL
import den.network.urlsession.dataTaskWithUrl
import help
import kotlinx.cinterop.reinterpret
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSURLResponse
import platform.Foundation.NSURLSession
import platform.Foundation.stringWithCString

fun urlSessionGet(args: List<String>) {
  if (args.size != 1) {
    help()
  }

  val semaphore = DispatchSemaphore()

  val task = NSURLSession.sharedSession.dataTaskWithUrl(
    args[0].toNSURL()) { data: NSData?, _: NSURLResponse?, error: NSError? ->
    if (error != null) {
      throw RuntimeException(error.localizedDescription)
    } else if (data != null) {
      println(NSString.stringWithCString(data.bytes?.reinterpret()))
    }

    semaphore.signal()
  }

  task.resume()
  semaphore.wait()
}
