package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.test.TestResources
import okio.Buffer
import kotlin.test.Test
import kotlin.test.fail

class DecodeVideoTest {
    @Test
    fun decodeVideo() {
        // First get the ID
        val codecId = AVCodecID.MPEG4
        val codec = AVCodec.findDecoder(codecId)!!
        val parser = CodecParserContext(codecId)

        val codecContext = VideoDecoder(codec)

        codecContext.open(codec)

        val buf = Buffer()
        buf.write(TestResources.RESOURCE_1.readBytes())

        var data = ByteArray(4096 + 64)

        //     do {
        //         /* read raw data from the input file */
        //         data_size = fread(inbuf, 1, INBUF_SIZE, f);
        //         if (ferror(f))
        //             break;
        //         eof = !data_size;
        //
        //         /* use the parser to split the data into frames */
        //         data = inbuf;
        //         while (data_size > 0 || eof) {
        //             ret = av_parser_parse2(parser, c, &pkt->data, &pkt->size,
        //                                    data, data_size, AV_NOPTS_VALUE, AV_NOPTS_VALUE, 0);
        //             if (ret < 0) {
        //                 fprintf(stderr, "Error while parsing\n");
        //                 exit(1);
        //             }
        //             data      += ret;
        //             data_size -= ret;
        //
        //             if (pkt->size)
        //                 decode(c, frame, pkt, outfilename);
        //             else if (eof)
        //                 break;
        //         }
        //     } while (!eof);
        var eof = false
        while (!eof) {
            val buffer = Buffer()
            var dataSize = buf.read(buffer, byteCount = 4096)

            eof = dataSize == -1L
            data = buffer.readByteArray() + ByteArray(64)

            while (dataSize > 0 || eof) {
                val parsedPacket = parser.parse(codecContext, data, dataSize.toInt())
                val packet = parsedPacket.packet

                data += ByteArray(parsedPacket.bytesRead)
                dataSize -= parsedPacket.bytesRead

                if (packet.size > 0) {
                    codecContext.decode(packet)
                } else if (eof) {
                    break
                }
            }
        }

        // flush
        codecContext.decode(null)

        codecContext.close()
        parser.close()
        buf.close()
    }
}

// static void decode(AVCodecContext *dec_ctx, AVFrame *frame, AVPacket *pkt,
//                    const char *filename)
// {
//     char buf[1024];
//     int ret;
//
//     ret = avcodec_send_packet(dec_ctx, pkt);
//     if (ret < 0) {
//         fprintf(stderr, "Error sending a packet for decoding\n");
//         exit(1);
//     }
//
//     while (ret >= 0) {
//         ret = avcodec_receive_frame(dec_ctx, frame);
//         if (ret == AVERROR(EAGAIN) || ret == AVERROR_EOF)
//             return;
//         else if (ret < 0) {
//             fprintf(stderr, "Error during decoding\n");
//             exit(1);
//         }
//
//         printf("saving frame %3"PRId64"\n", dec_ctx->frame_num);
//         fflush(stdout);
//
//         /* the picture is allocated by the decoder. no need to
//            free it */
//         snprintf(buf, sizeof(buf), "%s-%"PRId64, filename, dec_ctx->frame_num);
//         pgm_save(frame->data[0], frame->linesize[0],
//                  frame->width, frame->height, buf);
//     }
// }

fun VideoDecoder.decode(packet: AVPacket?) {
    try {
        sendPacket(packet)
    } catch (e: Exception) {
        fail("Error sending a packet for decoding", e)
    }

    while (true) {
        val ret = receiveFrame() ?: break

        println("saving frame ${ret.pts}")
    }
}