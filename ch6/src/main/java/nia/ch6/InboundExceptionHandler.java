package nia.ch6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Function: 异常会按照入站方向流动，所以应该确保重写了 exceptionCaught 方法的 Handler 位于 Pipeline 链的最后<br/>
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/7/16 21:56 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class InboundExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
