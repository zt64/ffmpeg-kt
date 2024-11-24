package dev.zt64.ffmpegkt.avutil

import java.awt.Image
import java.awt.image.BufferedImage
import java.nio.ByteBuffer

@OptIn(ExperimentalUnsignedTypes::class)
public fun VideoFrame.toImage(): Image {
    return when (format) {
        PixelFormat.YUV420P -> {
            val yPlane = ByteBuffer.wrap(data[0].toUByteArray().asByteArray())
            val uPlane = ByteBuffer.wrap(data[0].toUByteArray().asByteArray())
            val vPlane = ByteBuffer.wrap(data[0].toUByteArray().asByteArray())
            val yStride = this.linesize[0]
            val uStride = this.linesize[1]
            val vStride = this.linesize[2]

            val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            val rgbArray = IntArray(width * height)

            for (y in 0 until height) {
                for (x in 0 until width) {
                    val yIndex = y * yStride + x
                    val uIndex = (y / 2) * uStride + (x / 2)
                    val vIndex = (y / 2) * vStride + (x / 2)

                    val yValue = yPlane[yIndex].toInt() and 0xFF
                    val uValue = uPlane[uIndex].toInt() and 0xFF
                    val vValue = vPlane[vIndex].toInt() and 0xFF

                    val r = (yValue + 1.402 * (vValue - 128)).toInt().coerceIn(0, 255)
                    val g = (yValue - 0.344136 * (uValue - 128) - 0.714136 * (vValue - 128)).toInt().coerceIn(0, 255)
                    val b = (yValue + 1.772 * (uValue - 128)).toInt().coerceIn(0, 255)

                    rgbArray[y * width + x] = (r shl 16) or (g shl 8) or b
                }
            }

            bufferedImage.setRGB(0, 0, width, height, rgbArray, 0, width)
            bufferedImage
        }

        else -> throw UnsupportedOperationException("Unsupported pixel format: $format")
    }
}