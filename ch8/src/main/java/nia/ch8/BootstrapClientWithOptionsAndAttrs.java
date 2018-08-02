package nia.ch8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/2 23:22 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class BootstrapClientWithOptionsAndAttrs {

    public static void main(String[] args){
        BootstrapClientWithOptionsAndAttrs bootstrapClientWithOptionsAndAttrs =
                new BootstrapClientWithOptionsAndAttrs();
        bootstrapClientWithOptionsAndAttrs.bootstrap();
    }

    public void bootstrap(){
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");//cxy 在多线程下可能会抛出异常；通常适用于初始化静态变量时
        //AttributeKey.valueOf("ID");//cxy 更加通用（线程安全）
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuffer>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
                        System.out.println("Received data");
                    }

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        Integer idValue = ctx.channel().attr(id).get();//获取已存储的属性值
                        //TODO
                    }
                });
        //TODO .option 将在 connect() | bind() 方法被调用时被设置到已经创建的 Channel 上
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
        bootstrap.attr(id,123456);//存储对应属性的值
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(80));
        future.syncUninterruptibly();
    }

}
