package dev.zt64.ffmpegkt.avutil

/**
 * Rational number used as wrapper around FFmpeg AVRational class
 *
 * @property num the numerator
 * @property den the denominator
 */
public data class Rational(val num: Int, val den: Int)