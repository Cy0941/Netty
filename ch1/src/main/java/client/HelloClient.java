package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/10 14:58 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HelloClient {

    private static String host = "127.0.0.1";
    private static final int CLIENT_PORT_NUM = 7878;

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            //连接服务端
            Channel channel = bootstrap.connect(host, CLIENT_PORT_NUM).sync().channel();

            //控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = in.readLine();
                if (null == line) {
                    continue;
                }
                /**
                 * 向服务端发送在控制台输入的信息，必须用 \r\n 结尾
                 * 因为在 handler 中添加了 DelimiterBasedFrameDecoder 帧解码
                 */
                channel.writeAndFlush(line + "\r\n");
            }
        } finally {
            //The connection is closed automatically on shutdown
            group.shutdownGracefully();
        }
    }

}
