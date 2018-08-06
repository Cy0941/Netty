package nia.ch11;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;

/**
 * Function: 在写大型数据时，需要准备好处理到远程节点的连接为慢速连接--导致内存释放延迟<br/>
 * Reason: TODO NIO的零拷贝特性，消除了将文件的内容从文件系统移动到网络栈的复制过程，推荐使用 FileRegion 接口<br/>
 * Date: 2018/8/6 23:10 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class FileRegionWriteHandler extends ChannelInboundHandlerAdapter {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final File FILE_FROM_SOMEWHERE = new File("");

    /**
     * 需要将数据从文件系统复制到用户内存中时，可以使用 ChunkedWriteHandler，支持异步写大型数据流而又不会消耗大量内存
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        File file = FILE_FROM_SOMEWHERE;
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        FileInputStream in = new FileInputStream(FILE_FROM_SOMEWHERE);
        FileRegion region = new DefaultFileRegion(in.getChannel(), 0, FILE_FROM_SOMEWHERE.length());
        channel.writeAndFlush(region).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()){
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
