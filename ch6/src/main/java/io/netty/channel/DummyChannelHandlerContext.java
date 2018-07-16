package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/30 22:15 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class DummyChannelHandlerContext extends AbstractChannelHandlerContext {

    public static ChannelHandlerContext DUMMY_INSTANCE = new DummyChannelHandlerContext(
            null,
            null,
            null,
            true,
            true
    );
    public DummyChannelHandlerContext(DefaultChannelPipeline pipeline,
                                      EventExecutor executor,
                                      String name, boolean inbound, boolean outbound) {
        super(pipeline, executor, name, inbound, outbound);
    }

    @Override
    public ChannelHandler handler() {
        return null;
    }
}
