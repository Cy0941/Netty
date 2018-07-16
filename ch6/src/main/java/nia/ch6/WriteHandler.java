package nia.ch6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/7/16 21:41 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class WriteHandler extends ChannelHandlerAdapter {

    /**
     * 存储到 ChannelHandlerContext 的引用供稍后使用
     */
    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    /**
     * 可能被其他线程执行？？？
     * @param msg
     */
    public void send(Object msg){
        ctx.writeAndFlush(msg);
    }
}
