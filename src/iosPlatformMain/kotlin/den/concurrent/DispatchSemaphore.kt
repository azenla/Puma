package den.concurrent

import platform.darwin.DISPATCH_TIME_FOREVER
import platform.darwin.dispatch_semaphore_create
import platform.darwin.dispatch_semaphore_signal
import platform.darwin.dispatch_semaphore_t
import platform.darwin.dispatch_semaphore_wait

class DispatchSemaphore {
  private val handle: dispatch_semaphore_t = dispatch_semaphore_create(0)

  fun signal() = handle.signal()
  fun wait() = handle.wait()
}

class DispatchSemaphoreSignaler(val semaphore: DispatchSemaphore) {
  fun signal() = semaphore.signal()
}

fun waitForSignal(block: DispatchSemaphoreSignaler.() -> Unit) {
  val semaphore = DispatchSemaphore()
  block(DispatchSemaphoreSignaler(semaphore))
  semaphore.wait()
}

fun dispatch_semaphore_t.wait() {
  dispatch_semaphore_wait(this, DISPATCH_TIME_FOREVER)
}

fun dispatch_semaphore_t.signal() {
  dispatch_semaphore_signal(this)
}
