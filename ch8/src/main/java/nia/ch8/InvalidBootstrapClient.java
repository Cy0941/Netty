package nia.ch8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function: Channel 与 EventLoopGroup 必须保持兼容性，不能混用<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/2 22:13 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class InvalidBootstrapClient {

    public static void main(String[] args){
        InvalidBootstrapClient client = new InvalidBootstrapClient();
        client.bootstrap();
    }

    /**
     * cxy EventLoopGroup 必须与 Channel 对应
     * fixme IllegalStateException
     */
    public void bootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();//fixme NIO
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(OioSocketChannel.class)//fixme OIO
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.baidu.com", 80));
        future.syncUninterruptibly();
    }

}
