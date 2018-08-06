package nia.ch11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Function: 将多个消息合并为完整的 FullHttpRequest | FullHttpResponse<br/>
 * Reason: TODO 很多Http请求可能由许多部分组成；会有一定的性能开销。引入聚合的结果为不用再关心消息碎片<br/>
 * Date: 2018/8/5 22:43 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient){
            pipeline.addLast("codec",new HttpClientCodec());
        }else {
            pipeline.addLast("codec",new HttpServerCodec());
        }
        //将最大的消息大小为 512KB 的 HttpObjectAggregator 添加到 ChannelPipeline
        pipeline.addLast("aggregator",new HttpObjectAggregator(512 * 1024));
    }
}
