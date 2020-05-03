import puma.util.ImpactFeedbackGenerator
import puma.util.ImpactFeedbackStyle

fun impact() {
  if (ImpactFeedbackGenerator.supported) {
    ImpactFeedbackGenerator(ImpactFeedbackStyle.Heavy).impact()
  } else {
    println("Impact not supported.")
  }
}
