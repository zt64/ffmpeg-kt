# ffmpeg-kt

![Maven Central Version](https://img.shields.io/maven-central/v/dev.zt64/ffmpeg)
![GitHub License](https://img.shields.io/github/license/zt64/ffmpeg-kt)

Kotlin multiplatform wrapper for FFmpeg. This library aims to provide a Kotlin-friendly API for
FFmpeg with a similar API to the original FFmpeg library.

## Supported Platforms

- Linux (x86_64, arm64)
- Windows (x86_64)
- macOS (x86_64, arm64)
- Android (armeabi-v7a, arm64-v8a, x86, x86_64)
- JVM

## Setup

FFmpegKt is split up into multiple modules to keep modularization and dependencies clean.

### Gradle

<details>
<summary>Version Catalog</summary>

```toml
[versions]
ffmpeg-kt = "x.y.z"

[libraries]
# To keep things simple, use the complete module to add all modules
ffmpeg-kt-complete = { module = "dev.zt64:ffmpeg-kt-complete", version.ref = "ffmpeg-kt" }

# Or use each module as needed
ffmpeg-kt-avutil = { module = "dev.zt64:ffmpeg-kt-avutil", version.ref = "ffmpeg-kt" }
ffmpeg-kt-avcodec = { module = "dev.zt64:ffmpeg-kt-avcodec", version.ref = "ffmpeg-kt" }
ffmpeg-kt-avformat = { module = "dev.zt64:ffmpeg-kt-avformat", version.ref = "ffmpeg-kt" }
ffmpeg-kt-avfilter = { module = "dev.zt64:ffmpeg-kt-avfilter", version.ref = "ffmpeg-kt" }
ffmpeg-kt-avdevice = { module = "dev.zt64:ffmpeg-kt-avdevice", version.ref = "ffmpeg-kt" }
ffmpeg-kt-swscale = { module = "dev.zt64:ffmpeg-kt-swscale", version.ref = "ffmpeg-kt" }
ffmpeg-kt-swresample = { module = "dev.zt64:ffmpeg-kt-swresample", version.ref = "ffmpeg-kt" }
ffmpeg-kt-postproc = { module = "dev.zt64:ffmpeg-kt-postproc", version.ref = "ffmpeg-kt" }
```

</details>

## Contributing

Contributions are welcome and appreciated

## License

This project is licensed under the GNU GPL v3.0 License - see the [LICENSE](LICENSE) file for
details
