package nia.ch6;

import io.netty.channel.*;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/7/16 23:39 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class OutboundExceptionHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()){
                    future.cause().printStackTrace();
                    future.channel().close();
                }
            }
        });
    }
}
