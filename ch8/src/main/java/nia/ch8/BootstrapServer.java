package nia.ch8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/2 22:27 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class BootstrapServer {

    public static void main(String[] args){
        BootstrapServer server = new BootstrapServer();
        server.bootstrap();
    }

    public void bootstrap(){
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parentGroup)
                .channel(NioServerSocketChannel.class)
                //cxy 设置用于处理已被接受的子 Channel 的 IO 及数据的 ChannelInboundHandler
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("Server bound");
                }else {
                    System.err.println("Bound attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }


}
