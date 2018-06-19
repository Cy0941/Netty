package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/10 14:25 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //接收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

        //返回客户端消息
        ctx.writeAndFlush("Received your msg !\n");
    }

    /**
     * channel 被启用时触发（建立连接时）<br>
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " is active !");

        //cxy writeAndFlush写入Buffer并刷入，普通的write方法不会发送消息，需要手动再flush()一次
        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service !\n");

        super.channelActive(ctx);
    }

    /**
     * channel 被动关闭调用（如客户端终端等）
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server closed");
        super.channelInactive(ctx);
    }
}
