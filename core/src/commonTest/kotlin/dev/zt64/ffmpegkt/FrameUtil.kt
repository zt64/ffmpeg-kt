package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.codec.AudioEncoder
import dev.zt64.ffmpegkt.codec.Packet
import dev.zt64.ffmpegkt.codec.VideoEncoder
import kotlin.math.PI
import kotlin.math.sin

object FrameUtil {
    fun VideoEncoder.generateFrames(
        count: Int,
        encode: (Packet) -> Unit
    ) {
        val frame = createFrame()
        for (i in 0 until count) {
            val frameData = frame.data
            val (linesize0, linesize1, linesize2) = frame.linesize

            // Y plane
            val yPlane = ByteArray(height * linesize0)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    yPlane[y * linesize0 + x] = (x + y + i * 3).toByte()
                }
            }
            frameData[0] = yPlane

            // Cb plane
            val cbPlane = ByteArray(height / 2 * linesize1)
            for (y in 0 until height / 2) {
                for (x in 0 until width / 2) {
                    cbPlane[y * linesize1 + x] = (128 + y + i * 2).toByte()
                }
            }
            frameData[1] = cbPlane

            // Cr plane
            val crPlane = ByteArray(height / 2 * linesize2)
            for (y in 0 until height / 2) {
                for (x in 0 until width / 2) {
                    crPlane[y * linesize2 + x] = (64 + x + i * 5).toByte()
                }
            }
            frameData[2] = crPlane
            frame.pts = i.toLong()

            println("Send frame ${frame.pts}")

            encode(frame).forEach { packet -> encode(packet) }
        }
    }

    fun AudioEncoder.generateSineWave(
        lengthSeconds: Int,
        encode: (Packet) -> Unit
    ) {
        val frame = createFrame()
        var t = 0.0
        val numFrames = (sampleRate * lengthSeconds) / frameSize
        val startFreq = 440.0
        val peakFreq = 16000.0
        val peakFrame = numFrames / 2

        repeat(numFrames) { i ->
            // sweep frequency from startFreq to peakFreq and back
            val freq = if (i < peakFrame) {
                val progress = i.toDouble() / peakFrame
                startFreq + (peakFreq - startFreq) * progress
            } else {
                val progress = (i - peakFrame).toDouble() / (numFrames - peakFrame)
                peakFreq - (peakFreq - startFreq) * progress
            }

            val tincr = 2 * PI * freq / sampleRate

            for (k in 0 until channelLayout.nbChannels) {
                // 4 bytes per 32-bit integer sample
                val planeData = ByteArray(frameSize * 4)

                for (j in 0 until frameSize) {
                    val sampleValue = (sin(t + j * tincr) * (Int.MAX_VALUE / 10)).toInt()
                    val index = j * 4

                    // Write the 32-bit sample value into the plane (little-endian)
                    planeData[index] = (sampleValue and 0xFF).toByte()
                    planeData[index + 1] = (sampleValue shr 8 and 0xFF).toByte()
                    planeData[index + 2] = (sampleValue shr 16 and 0xFF).toByte()
                    planeData[index + 3] = (sampleValue shr 24 and 0xFF).toByte()
                }
                frame.data[k] = planeData
            }

            // Increment phase for the next frame
            t += tincr * frameSize

            encode(frame).forEach { packet -> encode(packet) }
        }
    }
}