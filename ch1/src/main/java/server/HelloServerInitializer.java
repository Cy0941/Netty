package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/10 14:15 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        /**
         * DelimiterBasedFrameDecoder 以（"\n"）为结尾分隔的解码器
         * cxy 根据 \n 符号位分隔符的解码器，所以每条消息的最后必须添加 \n，否则无法识别并解码
         */
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));

        //字符串节码和编码
        pipeline.addLast("decoder",new StringDecoder());
        pipeline.addLast("encoder",new StringEncoder());

        //自定义逻辑的 Handler
        pipeline.addLast("handler",new HelloServerHandler());
    }
}
