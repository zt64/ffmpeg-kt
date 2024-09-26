package dev.zt64.ffmpegkt.avutil

import android.graphics.*

/**
 * Converts the video frame to a bitmap.
 *
 * @return
 */
@OptIn(ExperimentalUnsignedTypes::class)
public fun VideoFrame.toBitmap(): Bitmap {
    return when (format) {
        AVPixelFormat.YUV420P -> {
            val concatenatedData = data[0].toUByteArray() + data[1].toUByteArray() + data[2].toUByteArray()

            val img = YuvImage(
                concatenatedData.asByteArray(),
                ImageFormat.YUV_420_888,
                width,
                height,
                null
            )

            BitmapFactory.decodeByteArray(img.yuvData, 0, img.yuvData.size)
        }

        else -> throw UnsupportedOperationException("Unsupported pixel format: $format")
    }
}