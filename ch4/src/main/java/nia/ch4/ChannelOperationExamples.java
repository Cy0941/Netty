package nia.ch4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/24 23:26 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ChannelOperationExamples {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * 1、Netty 的 Channel 实现是线程安全的（一个Channel在其生命周期内只注册于一个EventLoop，一个EventLoop在其生命周期内只和一个Thread绑定）
     * 2、即使是多线程使用同一个Channel，消息将会保证按顺序发送
     */
    public static void writingToChannelFromManyThreads() {
        final Channel channel = CHANNEL_FROM_SOMEWHERE;
        //创建者要持有写数据的ByteBuf
        final ByteBuf buf = Unpooled.copiedBuffer("data\r\n",CharsetUtil.UTF_8).asReadOnly();
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(buf);
            }
        };
        //多线程执行
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.execute(writer);
    }

    public static void writingToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        //创建者要持有写数据的ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("data\r\n",CharsetUtil.UTF_8).asReadOnly();
        //cxy 写数据并将其冲刷到远程节点
        final ChannelFuture channelFuture = channel.writeAndFlush(buf);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("Write successful");
                }else {
                    System.err.println("Write error");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
