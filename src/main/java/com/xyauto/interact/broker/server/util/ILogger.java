package com.xyauto.interact.broker.server.util;

import com.alibaba.fastjson.JSON;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import static org.fusesource.jansi.Ansi.ansi;
import org.springframework.stereotype.Component;

@Component
public interface ILogger {

    default void log(Object message) {
        String msg = StringUtils.EMPTY;
        if (message instanceof String) {
            msg = message.toString();
        }else {
            msg = JSON.toJSONString(message, true);
        }
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
//        String className = stes[3].getClassName();
//        String methodName = stes[3].getMethodName();
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(day)+"  "+/*className+"-->"+methodName+*/":  %s";
        System.out.println(ansi().render(String.format(format, msg)));
    }
    
    default void info(Object message) {
        this.log("@|CYAN "+message+"|@");
        
    }
    
    default void error(Object message) {
        this.log("@|RED "+message+"|@");
    }
    
    default void warn(Object message) {
        this.log("@|YELLOW "+message+"|@");
    }
    
    default void debug(Object message) {
        this.log("@|MAGENTA "+message+"|@");
    }

}
