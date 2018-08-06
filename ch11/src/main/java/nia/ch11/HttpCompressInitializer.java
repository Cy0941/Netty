package nia.ch11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Function: 自动压缩HTTP<br/>
 * Reason: TODO 特别对于文本数据优势非常明显；会带来一些CPU时钟上的开销<br/>
 * Date: 2018/8/5 22:49 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HttpCompressInitializer extends ChannelInitializer<Channel> {

    private final boolean isClient;

    public HttpCompressInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient){
            pipeline.addLast("codec",new HttpClientCodec());
            pipeline.addLast("decompressor",new HttpContentCompressor());
        }else {
            pipeline.addLast("codec",new HttpServerCodec());
            pipeline.addLast("compressor",new HttpContentCompressor());
        }
    }
}
