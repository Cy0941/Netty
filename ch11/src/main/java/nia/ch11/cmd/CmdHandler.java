package nia.ch11.cmd;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 23:48 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class CmdHandler extends SimpleChannelInboundHandler<Cmd> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
        //TODO
    }
}
