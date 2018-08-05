package nia.ch10.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 16:19 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {

    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        if (readableBytes > MAX_FRAME_SIZE){
            //检查缓冲区中是否超过 MAX_FRAME_SIZE 个字节
            in.skipBytes(readableBytes);
            throw new TooLongFrameException("Frame too long");
        }
        //do something
    }
}
