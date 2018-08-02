package nia.ch8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/2 23:43 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class GracefulShutdown {

    public static void main(String[] args){
        //TODO
    }

    public void bootstrap(){
        //创建处理 I/O 的EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        //创建一个 Bootstrap 类的实例并配置它
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                //...
                .handler(
                        new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(
                                    ChannelHandlerContext channelHandlerContext,
                                    ByteBuf byteBuf) throws Exception {
                                System.out.println("Received data");
                            }
                        }
                );
        bootstrap.connect(new InetSocketAddress("www.manning.com", 80)).syncUninterruptibly();
        //cxy shutdownGracefully() 将处理任何挂起的事件和任务，并且随后释放所有活动的线程
        //cxy 是一个异步操作，必须等待其阻塞完成或者通过注册监听器获取通知
        Future<?> future = group.shutdownGracefully();
        future.syncUninterruptibly();
    }

}
