package nia.ch2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/19 23:11 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host><port>");
            return;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }

    private void start() {
        final EchoClientHandler clientHandler = new EchoClientHandler();
        //1、创建EventLoopGroup cxy 使用的是NIO传输，所以指定NioEventLoopGroup来接受和处理新的连接
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2、创建Bootstrap
            Bootstrap client = new Bootstrap();
            //3、指定EventLoopGroup以处理客户端时间；需要适用于NIO的实现
            //cxy 为进行事件处理分配一个NioEventLoopGroup实例（包括创建新的连接、处理入站和出站数据）
            client.group(group)
                    //4、适用于NIO传输的Channel类型
                    .channel(NioSocketChannel.class)
                    //5、设置服务器的InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    //6、在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(clientHandler);
                        }
                    });
            //7、连接到远程节点，阻塞等待直到连接完成
            ChannelFuture future = client.connect().sync();
            //8、阻塞，直到Channel关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //9、关闭线程池并释放资源
            group.shutdownGracefully();
        }
    }
}
