package nia.ch11;

import io.netty.channel.*;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.io.Serializable;

/**
 * Function: 使用 JBoss Marshalling 进行序列化，比JDK序列化最多快三倍<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/6 23:37 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class MarshallingInitializer extends ChannelInitializer<Channel> {

    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //MarshallingDecoder 将 ByteBuf 转换为 POJO
        pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
        //MarshallingEncoder 将 POJO 转换为 ByteBuf
        pipeline.addLast(new MarshallingEncoder(marshallerProvider));
        //处理普通的实现了 Serializable 接口的 POJO
        pipeline.addLast(new ObjectHandler());
    }

    public final static class ObjectHandler extends SimpleChannelInboundHandler<Serializable>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {
            //TODO
        }
    }
}
