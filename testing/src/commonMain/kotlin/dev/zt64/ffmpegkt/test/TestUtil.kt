package dev.zt64.ffmpegkt.test

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

const val OUTPUT_BASE_PATH = "./build/test-output"
expect val PLATFORM: String

object TestUtil {
    /**
     * Resolves a path within the test output directory for the current platform.
     *
     * @param path The relative path to resolve.
     * @return The full resolved path for the test output directory.
     */
    fun resolvePath(path: String) = "$OUTPUT_BASE_PATH/$PLATFORM/$path"

    /**
     * Returns the output path for test files.
     *
     * This method creates a directory structure for test outputs and cleans up any previous runs.
     *
     * @param path The relative path within the test output directory.
     * @return The full path to the output directory for tests.
     */
    fun getOutputPath(path: String) = "$OUTPUT_BASE_PATH/$PLATFORM/$path".toPath().apply {
        FileSystem.SYSTEM.deleteRecursively(this) // Clean up any previous test runs
        FileSystem.SYSTEM.createDirectories(this)
    }
}