package Filter

import Image.{AsciiArt}

class FlipFilter(val axis: String) extends AsciiArtFilter {

  override def apply(image: AsciiArt): AsciiArt = {
    axis.toLowerCase match {
      case "x"|"vertical" => flipVertically(image)
      case "y"|"horizontal" => flipHorizontally(image)
      case _ => throw new IllegalArgumentException("Axis provided for the flip filter must be 'x' or 'y'")
    }
  }

  private def flipVertically(image: AsciiArt): AsciiArt = {
    val newData = image.getData().reverse
    new AsciiArt(image.getWidth(), image.getHeight(), newData)
  }

  private def flipHorizontally(image: AsciiArt): AsciiArt = {
    val newData = image.getData().grouped(image.getWidth()).flatMap(_.reverse).toArray
    new AsciiArt(image.getWidth(), image.getHeight(), newData)
  }
}
