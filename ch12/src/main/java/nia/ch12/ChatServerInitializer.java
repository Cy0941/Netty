package nia.ch12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/7 23:41 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        /**
         * HttpServerCodec 将字节解码为HttpRequest|HttpContent|LastHttpContent；并将HttpRequest|HttpContent|LastHttpContent编码为字节
         * ChunkedWriteHandler 写入一个文件的内容
         * HttpObjectAggregator 聚合HttpMessage & N*HttpContent
         *      为完整的FullHttpRequest|FullHttpResponse；Pipeline中下一个Handler只会接收到完整的HTTP请求
         * HttpRequestHandler 处理 FullHttpRequest（不发送到/ws的请求）
         * WebSocketServerProtocolHandler 按照 WebSocket 规范要求处理 WebSocket 升级握手等
         * TextWebSocketFrameHandler 处理 TextWebSocketFrame 和握手完成事件
         *
         * 说明：
         *  1、WebSocketServerProtocolHandler 处理了所有委托管理的 WebSocket 帧类型以及升级本身；如果握手成功，必要的 ChannelHandler 将会被添加到
         *    ChannelPipeline 中，不必要的 ChannelHandler 将会被移除；
         *  2、升级之前：
         *      HttpRequestDecoder HttpResponseEncoder HttpObjectAggregator HttpRequestHandler
         *      WebSocketServerProtocolHandler TextWebSocketFrameHandler
         *  3、升级之后
         *      WebSocketFrameDecoder WebSocketEncoder WebSocketServerProtocolHandler TextWebSocketFrameHandler
         *      (Decoder | Encoder 对应的版本由 Netty 根据客户端【浏览器】版本确定)
         */
        ChannelPipeline pipeline = ch.pipeline();
        //cxy HttpRequestDecoder & HttpResponseEncoder
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new HttpRequestHandler("/ws"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}
