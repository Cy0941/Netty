package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/10 14:03 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HelloServer {

    /**
     * 服务端监听地址
     */
    private static final int SERVER_PORT_NUM = 7878;

    public static void main(String[] args) throws Exception {
        //Server 服务启动器
        //cxy 工作线程 EventLoopGroup 是4.x新增的，用于 channel 的管理；服务端需要两个；一个是boss线程，一个是worker线程
        //fixme worker 用于管理线程，为 boss 服务
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //TODO 处理 channel 中的事件和IO操作
            bootstrap.group(bossGroup,workerGroup);
            //fixme
            bootstrap.channel(NioServerSocketChannel.class);
            //cxy 添加相关channel - 设置责任链路
            bootstrap.childHandler(new HelloServerInitializer());

            //channel 选项设置
            //bootstrap.option(ChannelOption.SO_KEEPALIVE,true);

            //服务端绑定端口监听
            //TODO bind() 会将 childHandler 添加到责任链路中
            ChannelFuture future = bootstrap.bind(SERVER_PORT_NUM).sync();
            //监听服务器关闭监听
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
