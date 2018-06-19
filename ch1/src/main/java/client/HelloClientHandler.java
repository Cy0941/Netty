package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/10 15:17 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HelloClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("Server say : " + msg);
    }

    /**
     * channel 被启用时触发（建立连接时）
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active !");
        super.channelActive(ctx);
    }

    /**
     * channel 被动关闭调用（如服务器关闭等）
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close");
        super.channelInactive(ctx);
    }
}
