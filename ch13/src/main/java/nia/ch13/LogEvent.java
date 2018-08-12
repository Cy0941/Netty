package nia.ch13;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.InetSocketAddress;

/**
 * Function: 消息组件<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/12 15:01 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
@AllArgsConstructor
@Getter
public class LogEvent {

    public static final byte SEPARATOR = ':';
    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long received;

    public LogEvent(String logFile, String msg) {
        this(null, logFile, msg, -1);
    }
}
