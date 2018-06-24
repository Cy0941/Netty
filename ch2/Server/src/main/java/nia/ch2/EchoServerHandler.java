package nia.ch2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;


/**
 * Function: TODO
 * Reason: 扩展自ChannelInboundHandlerAdapter，服务端接收到消息后仍然需要回送消息给客户端，而write()是异步的，直到channelRead0()
 * 方法返回可能仍然没有完成，ChannelInboundHandlerAdapter在这个时间点不会释放消息
 * cxy ChannelHandlerContext 代表了 ChannelHandler 和 ChannelPipeline 之间的绑定（可以被用于获取 Channel，主要被用于写出站数据）
 * Date: 2018/6/19 7:42 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
@ChannelHandler.Sharable //cxy 标识一个ChannelHandler可以被多个Channel安全的共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 处理所有接收到的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        //将消息记录到控制台
        System.out.println("Server received : "+in.toString(CharsetUtil.UTF_8));
        //cxy 将接收到的消息写给发送者，而不冲刷出站消息 异步
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //cxy 将未决消息冲刷到远程节点，并且关闭该节点
        //未决消息：指目前暂存于ChannelOutboundBuffer中的消息，在下一次调用flush()|writeAndFlush()方法时将会尝试写出到套接字
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
