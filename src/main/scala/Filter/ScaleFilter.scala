package Filter

import Image.AsciiArt

/**
 * Scales an AsciiArt image.
 *
 * @param scale Scaling factor for the image.
 */
class ScaleFilter(val scale: Double) extends AsciiArtFilter {

  /**
   * Applies scaling to the given AsciiArt image.
   *
   * @param image The AsciiArt image to scale.
   * @return Scaled AsciiArt image.
   * @throws IllegalArgumentException if scale is not positive.
   */
  override def apply(image: AsciiArt): AsciiArt = {
    if (scale <= 0) throw new IllegalArgumentException("Scale must be positive")

    if (scale >= 1) {
      scaleDown(image)
    }

    else {
      scaleUp(image)
    }
  }

  //Scales down the image
  private def scaleDown(image: AsciiArt): AsciiArt = {

    val newWidth = (image.getWidth() / scale).toInt
    val newHeight = (image.getHeight() / scale).toInt

    val newData = (0 until newHeight).flatMap { y =>
      (0 until newWidth).map { x =>
        val originalX = x * scale.toInt
        val originalY = y * scale.toInt
        image.getData()(originalY * image.getWidth() + originalX)
      }
    }.toArray

    new AsciiArt(newWidth, newHeight, newData)
  }

  // Scales up the image
  private def scaleUp(image: AsciiArt): AsciiArt = {
    val pixelRatio = (1 / scale).toInt
    val newWidth = (image.getWidth() / scale).toInt
    val newHeight = (image.getHeight() / scale).toInt
    val newData = Array.ofDim[Int](newWidth * newHeight)

    for {
      y <- 0 until image.getHeight()
      x <- 0 until image.getWidth()
      dy <- 0 until pixelRatio
      dx <- 0 until pixelRatio
    } {
      val newIndex = ((y * pixelRatio + dy)* newWidth) + (x * pixelRatio + dx)
      newData(newIndex) = image.getData()(y * image.getWidth() + x)
    }


    new AsciiArt(newWidth, newHeight, newData)
  }
}
