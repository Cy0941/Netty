package nia.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 11:56 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

    private static final int INT_LENGTH = 4;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        while (msg.readableBytes() >= INT_LENGTH) {
            int value = Math.abs(msg.readInt());
            out.add(value);
        }
    }
}
