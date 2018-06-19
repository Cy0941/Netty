package nia.ch1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/13 23:11 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {

    //当一个新的连接被建立时，该方法将被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + " connected");
    }
}
