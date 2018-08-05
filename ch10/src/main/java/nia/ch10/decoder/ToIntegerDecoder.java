package nia.ch10.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 15:24 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {

    private static final int INT_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //检查是否至少有4个字节可读
        if (in.readableBytes() >= INT_LENGTH) {
            out.add(in.readInt());
        }
    }
}
