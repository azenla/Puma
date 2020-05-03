package puma.cmd

import den.services.springboard.SpringBoardServices
import den.services.use

fun launchApplication(args: List<String>) {
  val identifier = args.single()

  SpringBoardServices.open().use {
    launchApplicationWithIdentifier(identifier)
  }
}
