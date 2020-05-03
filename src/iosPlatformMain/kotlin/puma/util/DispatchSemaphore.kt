package puma.util

import platform.darwin.DISPATCH_TIME_FOREVER
import platform.darwin.dispatch_semaphore_create
import platform.darwin.dispatch_semaphore_signal
import platform.darwin.dispatch_semaphore_t
import platform.darwin.dispatch_semaphore_wait

class DispatchSemaphore(val value: Long = 0) {
  private val handle: dispatch_semaphore_t = dispatch_semaphore_create(value)

  fun signal() {
    dispatch_semaphore_signal(handle)
  }

  fun wait() {
    dispatch_semaphore_wait(handle, DISPATCH_TIME_FOREVER)
  }
}

class DispatchSemaphoreSignaler(val semaphore: DispatchSemaphore) {
  fun signal() = semaphore.signal()
}

fun waitForSignal(block: DispatchSemaphoreSignaler.() -> Unit) {
  val semaphore = DispatchSemaphore()
  block(DispatchSemaphoreSignaler(semaphore))
  semaphore.wait()
}
