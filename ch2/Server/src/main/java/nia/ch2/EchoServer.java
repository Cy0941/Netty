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
 * Reason: TODO Server 端需要两个 EventLoopGroup：
 *  第一组：只包含一个ServerChannel，代表服务器自身的已绑定到某个本地端口的正在监听的套接字
 *  第二组：包含所有已创建的用来处理传入客户端的连接（对于每个服务器已经接受的连接都有一个）的 Channel
 *  说明：1、与 ServerChannel 相关联的 EventLoopGroup 将分配一个负责为传入连接请求创建 Channel 的 EventLoop
 *       2、第二个 EventLoopGroup 负责给他的 Channel 分配一个 EventLoop
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
        /**
         * EventLoop 是 Netty 的核心抽象，用于处理连接的生命周期中所发生的事件
         * 一个 EventLoopGroup 中包含一个或多个 EventLoop
         * 一个 EventLoop 在其生命周期内只和一个 Thread 绑定
         * 所有由 EventLoop 处理的 IO 事件都将在它专有的 Thread 上被处理
         * 一个 Channel 在它的生命周期内只注册于一个 EventLoop
         * 一个 EventLoop 可能会被分配给一个或多个 Channel
         * fixme 多个 Channel 可能被同一个 Thread 进行处理？？？
         */
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
