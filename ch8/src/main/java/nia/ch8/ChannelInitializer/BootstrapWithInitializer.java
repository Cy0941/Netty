package nia.ch8.ChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/2 23:00 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class BootstrapWithInitializer {

    public static void main(String[] args){
        //TODO
    }

    public void bootstrap() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());
        ChannelFuture future = serverBootstrap.bind(8080);
        future.sync();
    }

}
