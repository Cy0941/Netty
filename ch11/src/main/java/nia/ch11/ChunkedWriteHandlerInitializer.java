package nia.ch11;

import io.netty.channel.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.net.ssl.SSLEngine;
import java.io.File;
import java.io.FileInputStream;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/6 23:25 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {

    private final File file;
    private final SslContext context;

    public ChunkedWriteHandlerInitializer(File file, SslContext context) {
        this.file = file;
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = context.newEngine(ch.alloc());
        pipeline.addLast(new SslHandler(engine));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WriteStreamHandler());
    }


    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            //TODO 连接建立时，使用 ChunkedStream 写文件数据
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
            //ctx.writeAndFlush(new ChunkedFile(file));
        }
    }
}
