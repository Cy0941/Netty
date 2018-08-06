package nia.ch11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Function: 心跳检测及发送心跳包<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 23:19 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //IdleStateHandler 被触发时将发送一个 IdleStateEvent 事件
        pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        //心跳处理 Handler
        pipeline.addLast(new HeartBeatHandler());
    }

    public static final class HeartBeatHandler extends ChannelInboundHandlerAdapter{
        private static final ByteBuf HEART_BEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
                "HEART_BEAT", CharsetUtil.ISO_8859_1));

        /**
         * 发送心跳
         * @param ctx
         * @param evt
         * @throws Exception
         */
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            //发送心跳且在发送失败后关闭通道
            if (evt instanceof IdleStateEvent){
                ctx.writeAndFlush(HEART_BEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else {
                //不是 IdleStateEvent 则传递给下一个 ChannelHandler
                super.userEventTriggered(ctx,evt);
            }
        }
    }
}
