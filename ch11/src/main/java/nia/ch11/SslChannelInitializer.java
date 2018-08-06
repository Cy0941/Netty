package nia.ch11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Function: 将 SslHandler 添加到  ChannelPipeline<br/>
 * Reason: TODO 大多数情况下，SslHandler 将是 ChannelPipeline 中的第一个 ChannelHandler；确保只有在所有其他 ChannelHandler
 * TODO 将他们的逻辑应用到数据后，才会进行加密<br/>
 * Date: 2018/8/5 22:23 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;

    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine = context.newEngine(ch.alloc());
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("ssl",new SslHandler(engine,startTls));
    }
}
