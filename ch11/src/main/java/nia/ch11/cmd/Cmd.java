package nia.ch11.cmd;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 23:39 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */

@Getter
@AllArgsConstructor
@ToString
public class Cmd {

    private final ByteBuf name;
    private final ByteBuf args;

}
