package nia.ch10.duplex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 17:14 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ByteToCharDecorder extends ByteToMessageDecoder {

    private static final int CHAR_LENGTH = 2;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= CHAR_LENGTH) {
            out.add(in.readChar());
        }
    }
}
