package den.device

import platform.UIKit.UIImpactFeedbackGenerator

class ImpactGenerator {
  private val generator: UIImpactFeedbackGenerator = UIImpactFeedbackGenerator()

  fun impact() {
    generator.prepare()
    generator.impactOccurred()
  }
}
