package nia.ch6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/7/3 23:41 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class SimpleDiscardHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //cxy 不需要任何显式的资源释放
        //No need to do anything special
    }
}
