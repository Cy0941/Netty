package nia.ch11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Function: LengthFieldBasedFrameDecoder:满足头部帧偏移的解码器<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/6 23:02 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //TODO 帧的长度被编码到了帧起始的前8个字节中
        pipeline.addLast(new LengthFieldBasedFrameDecoder(60 * 1024, 0, 8));
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            //TODO
        }
    }
}
