package dev.zt64.ffmpegkt.annotation

/**
 * Marks a declaration as internal to ffmpeg-kt.
 * Developers should not use this declaration for normal use.
 *
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@RequiresOptIn
annotation class FfmpegKtInternal