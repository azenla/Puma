package puma.cmd

import puma.util.ImpactFeedbackGenerator
import puma.util.ImpactFeedbackStyle

fun impactHeavy() {
  if (ImpactFeedbackGenerator.supported) {
    ImpactFeedbackGenerator(ImpactFeedbackStyle.Heavy).impact()
  } else {
    println("Impact not supported.")
  }
}
