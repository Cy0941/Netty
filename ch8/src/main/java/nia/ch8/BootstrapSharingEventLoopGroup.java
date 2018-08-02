package nia.ch8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function: 尽可能重用 EventLoop，以减少线程创建及上下文切换带来的性能损失<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/2 22:43 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class BootstrapSharingEventLoopGroup {

    public static void main(String[] args) {
        BootstrapSharingEventLoopGroup sharingEventLoopGroup = new BootstrapSharingEventLoopGroup();
        sharingEventLoopGroup.bootstrap();
    }

    /**
     * cxy 当前服务端可以同时既作为服务端又可以作为客户端；且两者之间公用 EventLoop (& Channel)
     */
    public void bootstrap() {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture future;

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        //连接完成后执行的操作
                        if (future.isDone()) {
                            //TODO do something other
                        }
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        //TODO 在当前服务端中引导一个客户端且重用 EventLoop
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(ctx.channel().eventLoop())//cxy 使用与已分配给已被接受的子 Channel 相同的 EventLoop
                                .channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        System.out.println("Received data");
                                    }
                                });
                        future = bootstrap.connect(new InetSocketAddress("www.baidu.com",80));
                    }
                });
    }
}
