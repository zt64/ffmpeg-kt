package dev.zt64.ffmpegkt.avutil

/**
 * A rational number with a numerator and a denominator.
 */
public expect class AVRational

/**
 * The numerator of the AVRational.
 */
public expect val AVRational.num: Int

/**
 * The denominator of the AVRational.
 */
public expect val AVRational.den: Int

/**
 * Create a new AVRational with the given numerator and denominator.
 *
 * @param num The numerator.
 * @param den The denominator.
 */
public expect fun AVRational(num: Int, den: Int): AVRational