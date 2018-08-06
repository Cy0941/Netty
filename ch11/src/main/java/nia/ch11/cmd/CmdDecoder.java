package nia.ch11.cmd;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 23:42 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class CmdDecoder extends LineBasedFrameDecoder {

    private static final byte SPACE = (byte) ' ';

    public CmdDecoder(int maxLength) {
        super(maxLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
        if (null == frame) {
            return null;
        }
        int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);
        return new Cmd(frame.slice(frame.readerIndex(), index), frame.slice(index + 1, frame.writerIndex()));
    }
}
