package dev.zt64.ffmpegkt.test

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

object TestUtil {
    /**
     * Returns the output path for test files.
     *
     * This method creates a directory structure for test outputs and cleans up any previous runs.
     *
     * @param path The relative path within the test output directory.
     * @return The full path to the output directory for tests.
     */
    fun getOutputPath(path: String) = "./build/test-output/$path".toPath().apply {
        FileSystem.SYSTEM.deleteRecursively(this) // Clean up any previous test runs
        FileSystem.SYSTEM.createDirectories(this)
    }
}