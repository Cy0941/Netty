package nia.ch8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Function: 引导 DatagramChannel  --  UDP<br/>
 * Reason: connect()  --  TCP  &&  bind()  --  UDP
 * Date: 2018/8/2 23:35 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class BootstrapDatagramChannel {

    public static void main(String[] args){
        BootstrapDatagramChannel bootstrapDatagramChannel = new BootstrapDatagramChannel();
        bootstrapDatagramChannel.bootstrap();
    }

    public void bootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        //TODO
                    }
                });
        //cxy 因为该协议是无连接的，调用 bind 方法
        ChannelFuture future = bootstrap.bind(80);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isDone()){
                    System.out.println("Channel bound");
                }else {
                    System.err.println("Attempt bound failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }

}
