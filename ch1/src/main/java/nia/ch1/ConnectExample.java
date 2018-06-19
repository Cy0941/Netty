package nia.ch1;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Function: 异步建立连接
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/13 23:19 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ConnectExample {

    private static final Channel CHANNEL = new NioSocketChannel();

    public static void connect(){
        InetSocketAddress address = new InetSocketAddress("192.168.0.1", 25);
        //异步连接到远程节点
        ChannelFuture future = CHANNEL.connect(address);
        //cxy 注册 ChannelFutureListener 以便在操作完成时获取通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    //如果操作成功，创建 ByteBuf 持有数据
                    ByteBuf buf = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    //使用相同的 Channel 将数据异步推送到远程
                    ChannelFuture writeFuture = future.channel().writeAndFlush(buf);
                    //...
                }else {
                    //操作失败，获取异常原因
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }

            }
        });
    }

}
