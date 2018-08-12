package nia.ch13.decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/12 16:08 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class LogEventMonitor {

    private static final int PORT = 8888;

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public LogEventMonitor(InetSocketAddress address){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LogEventDecoder());
                        pipeline.addLast(new LogEventHandler());
                    }
                })
                //指定Channel要绑定的本地address。如果没有提供，OS会选择一个随机的地址(从你网卡中选择)，另外，你可以通过bind()或connect()来指定address
                .localAddress(address);
    }

    public static void main(String[] args) throws InterruptedException {
        InetSocketAddress address = new InetSocketAddress(PORT);
        LogEventMonitor monitor = new LogEventMonitor(address);
        try {
            Channel channel = monitor.bind();
            System.out.println("LogEventMonitor running");
            channel.closeFuture().sync();
        } finally {
            monitor.stop();
        }
    }

    public Channel bind(){
        //TODO 绑定 Channel -- DatagramChannel 是无连接的
        return bootstrap.bind().syncUninterruptibly().channel();
    }

    public void stop(){
        group.shutdownGracefully();
    }

}
