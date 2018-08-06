package nia.ch11;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/6 23:45 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ProtobufInitializer extends ChannelInitializer<Channel> {

    private final MessageLite lite;

    public ProtobufInitializer(MessageLite lite) {
        this.lite = lite;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //TODO 必须在ProtobufEncoder前添加ProtobufVarint32FrameDecoder以编码进帧长度信息
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(lite));
        pipeline.addLast(new ObjectHandler());
    }

    public final static class ObjectHandler extends SimpleChannelInboundHandler<Object>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            //TODO
        }
    }
}
