package nia.ch2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/19 22:24 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args){
        if (args.length != 1){
            System.err.println("Usage:"+EchoServer.class.getSimpleName()+" <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //1、创建EventLoopGroup cxy 使用的是NIO传输，所以指定NioEventLoopGroup来接受和处理新的连接
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2、创建ServerBootstrap
            ServerBootstrap server = new ServerBootstrap();
            server.group(group)
                    //3、指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    //4、使用指定的端口设置套接字地址 cxy 服务器将绑定到这个地址并监听新的连接请求
                    .localAddress(new InetSocketAddress(port))
                    //5、添加一个EchoServerHandler到子Channel的ChannelPipeline
                    //cxy 当一个新的连接被接受时，一个新的子 Channel 将会被创建并加入到该 Channel 的 ChannelPipeline 中
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(serverHandler);
                        }
                    });
            //6、异步的绑定服务器，调用 cxy sync()方法阻塞等待直到绑定完成
            ChannelFuture future = server.bind().sync();
            //7、获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //8、关闭EventLoopGroup释放所有资源
            group.shutdownGracefully();
        }
    }
}
