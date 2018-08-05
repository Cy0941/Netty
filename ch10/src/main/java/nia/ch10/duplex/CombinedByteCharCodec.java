package nia.ch10.duplex;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Function: 抽象的编解码器可能会对代码的重用性造成影响<br/>
 * Reason: TODO CombinedChannelDuplexHandler 提供编解码器的组合使用而不对代码重用性造成任何影响<br/>
 * Date: 2018/8/5 17:17 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecorder,CharToByteEncoder> {

    /**
     * 将委托实例传递给父类
     * @param inboundHandler
     * @param outboundHandler
     */
    public CombinedByteCharCodec(ByteToCharDecorder inboundHandler, CharToByteEncoder outboundHandler) {
        super(new ByteToCharDecorder(),new CharToByteEncoder());
    }
}
