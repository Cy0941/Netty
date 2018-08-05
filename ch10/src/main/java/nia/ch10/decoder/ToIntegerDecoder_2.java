package nia.ch10.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 15:30 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ToIntegerDecoder_2 extends ReplayingDecoder<Void> {

    /**
     * ReplayingDecoder 使得不必调用 readableBytes() 方法判断  ---  自定义 ReplayingDecoderByteBuf 在内部执行
     *
     * @param ctx
     * @param in  此处为 ReplayingDecoderByteBuf
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readInt());
        System.out.println("-----");
    }
}
