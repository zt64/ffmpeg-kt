package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.avutil.PixelFormat
import dev.zt64.ffmpegkt.avutil.LibAVUtil
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.test.Test

class ScaleTest {
    @Test
    fun scaleVideo() {
        val srcW = 320
        val srcH = 240
        val srcFormat = PixelFormat.YUV420P
        val dstW = 1280
        val dstH = 720
        val dstFormat = PixelFormat.RGB24

        val srcData = ByteArray(4)
        val dstData = ByteArray(4)
        val srcLinesize = IntArray(4)
        val dstLinesize = IntArray(4)

        val context = SwsContext(
            srcW,
            srcH,
            srcFormat,
            dstW,
            dstH,
            dstFormat,
            listOf(SwsFlag.BICUBIC)
        )

        val file = FileSystem.SYSTEM.appendingSink("scaleVideo.rgb".toPath())

        LibAVUtil.imageAlloc(
            srcData,
            srcLinesize,
            srcW,
            srcH,
            srcFormat,
            16
        )

        LibAVUtil.imageAlloc(
            dstData,
            dstLinesize,
            dstW,
            dstH,
            dstFormat,
            1
        )

        // repeat(100) { i ->
        //     fillYuvImage(arrayOf(srcData), srcLinesize, srcW, srcH, i)
        //
        //     context.scale(
        //         srcData,
        //         srcLinesize,
        //         0,
        //         srcH,
        //         dstData,
        //         dstLinesize
        //     )
        //
        //     val buffer = Buffer()
        //
        //     for (y in 0 until dstH) {
        //         buffer.write(dstData, y * dstLinesize[0], dstW * 3)
        //     }
        //
        //     file.write(buffer, buffer.size)
        // }

        file.close()
    }
}

fun fillYuvImage(
    data: Array<ByteArray>,
    linesize: IntArray,
    width: Int,
    height: Int,
    frameIndex: Int
) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            data[0][y * linesize[0] + x] = (x + y + frameIndex * 3).toByte()
        }
    }

    for (y in 0 until height / 2) {
        for (x in 0 until width / 2) {
            data[1][y * linesize[1] + x] = (128 + y + frameIndex * 2).toByte()
            data[2][y * linesize[2] + x] = (64 + x + frameIndex * 5).toByte()
        }
    }
}