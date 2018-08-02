package nia.ch8.ChannelInitializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * Function: 统一管理 ChannelHandler<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/2 23:02 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ChannelInitializerImpl extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
    }
}
