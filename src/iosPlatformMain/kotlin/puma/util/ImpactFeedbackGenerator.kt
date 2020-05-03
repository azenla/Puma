package puma.util

import platform.Foundation.valueForKey
import platform.UIKit.UIDevice
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

enum class ImpactFeedbackStyle(val style: UIImpactFeedbackStyle) {
  Light(UIImpactFeedbackStyle.UIImpactFeedbackStyleLight),

  Medium(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium),

  Heavy(UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy),

  Soft(UIImpactFeedbackStyle.UIImpactFeedbackStyleSoft),

  Rigid(UIImpactFeedbackStyle.UIImpactFeedbackStyleRigid)
}

class ImpactFeedbackGenerator(val style: ImpactFeedbackStyle) {
  private val generator: UIImpactFeedbackGenerator = UIImpactFeedbackGenerator(style.style)

  fun impact() {
    generator.prepare()
    generator.impactOccurred()
  }

  companion object {
    val supported: Boolean
      get() = UIDevice.currentDevice.valueForKey("_feedbackSupportLevel").toString() != "0"
  }
}
