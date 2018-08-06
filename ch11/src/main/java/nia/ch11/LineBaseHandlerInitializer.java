package nia.ch11;

import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 23:32 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class LineBaseHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(64 * 1024));
        //添加以接收帧
        pipeline.addLast(new FrameHandler());
    }


    public final static class FrameHandler extends SimpleChannelInboundHandler<ByteBuffer>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
            //TODO 传入了单个帧的内容
        }
    }
}
