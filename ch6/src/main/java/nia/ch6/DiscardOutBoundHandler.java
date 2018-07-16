package nia.ch6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/7/3 23:58 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
@ChannelHandler.Sharable
public class DiscardOutBoundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ReferenceCountUtil.release(msg);
        //cxy 通知 ChannelPromise 数据已经被处理了
        promise.setSuccess();
    }
}
