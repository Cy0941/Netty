package nia.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 12:22 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {

    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        //如果接收的某一帧消息大于规定的最大长度，该帧信息将被对其并抛出异常
        if (readableBytes > maxFrameSize){
            //discard the bytes
            in.clear();
            throw new TooLongFrameException("the data is too long and will be discarded");
        }
        ByteBuf buf = in.readBytes(readableBytes);
        out.add(buf);
    }
}
