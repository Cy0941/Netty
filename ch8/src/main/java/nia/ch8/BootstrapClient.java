package nia.ch8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/7/30 23:46 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class BootstrapClient {

    public static void main(String[] args){
        BootstrapClient bootstrapClient = new BootstrapClient();
        bootstrapClient.bootstrap();
    }

    /**
     * 引导一个客户端
     */
    public void bootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.baidu.com", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }

}
