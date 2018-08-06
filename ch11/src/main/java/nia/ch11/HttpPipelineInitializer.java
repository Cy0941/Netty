package nia.ch11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Function: 添加 Http/Https（不考虑请求|响应分段） 支持到 ChannelPipeline<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 22:34 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private final boolean isClient;

    public HttpPipelineInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //客户端（请求），需要对请求进行解码且对响应进行编码
        if (isClient){
            pipeline.addLast("decoder",new HttpResponseDecoder());
            pipeline.addLast("encoder",new HttpRequestEncoder());
        }else {
            pipeline.addLast("decoder",new HttpRequestDecoder());
            pipeline.addLast("encoder",new HttpResponseEncoder());
        }
    }
}
