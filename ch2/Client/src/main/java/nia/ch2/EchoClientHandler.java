package nia.ch2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Function: TODO
 * Reason: 扩展自 SimpleChannelInboundHandler，当channelRead0()
 * 方法完成时，已经得到了传入消息并且已经处理完成了；cxy 该方法返回时，SimpleChannelInboundHandler负责释放指向保存该消息的ByteBuf的内存引用
 * cxy ChannelHandlerContext 代表了 ChannelHandler 和 ChannelPipeline 之间的绑定（可以被用于获取 Channel，主要被用于写出站数据）
 * Date: 2018/6/19 22:51 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     *  每当接收数据时，都会调用这个方法
     *  Netty5中已被关闭
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Client received : " + msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 当被通知的Channel是活跃的时候，发送一条消息
     * cxy 将在一个连接被建立时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Server refused our connection");
        cause.printStackTrace();
        ctx.close();
    }
}
