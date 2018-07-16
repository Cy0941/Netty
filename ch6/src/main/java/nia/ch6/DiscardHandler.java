package nia.ch6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Function: 释放消息资源
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/7/3 23:38 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
@Deprecated
@ChannelHandler.Sharable
public class DiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //丢弃接收的消息
        ReferenceCountUtil.release(msg);
    }
}
