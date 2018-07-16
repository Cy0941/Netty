package nia.ch6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Function: 因为一个 ChannelHandler 可以从属于多个 ChannelPipeline ，所以它也可以绑定到多个 ChannelHandlerContext 实例；<br/>
 * 对应的 ChannelHandler 必须要使用 @Sharable 注解标注  --  <strong>这样的 ChannelHandler 必须是线程安全的</strong><br/>
 * Reason: 每一个新创建的 Channel 都将会被分配一个新的 ChannelPipeline；这项关联 是永久的；Channel 既不能附加另外一个 Pipeline 也不能分离当前的<br/>
 * Date: 2018/7/16 21:46 <br/>
 * cxy 只有在确定了线程安全的时候才能使用 @Sharable 注解
 * @author: cx.yang
 * @since: yangcx.xin
 */
@ChannelHandler.Sharable
public class SharableHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Channel read msg  :: "+msg);
        ctx.fireChannelRead(msg);
    }
}
