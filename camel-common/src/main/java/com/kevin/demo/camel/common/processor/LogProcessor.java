package com.kevin.demo.camel.common.processor;

import com.kevin.demo.camel.common.constants.LogLevel;
import com.kevin.demo.camel.common.model.db.LogMessage;
import com.kevin.demo.camel.common.constants.Constant;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.util.Calendar;

/**
 * Created by khantzen on 14/08/17.
 */
public class LogProcessor implements Processor {

    private String message;
    private String sender;

    public LogProcessor(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    @Override
    public void process(Exchange ex) throws Exception {
        Message in = ex.getIn();
        LogMessage log = new LogMessage.Builder()
                        .setLogLevel(LogLevel.INFO.getName())
                        .setMessage(this.message)
                        .setSender(this.sender)
                        .setOwner(Constant.OWNER_BUSINESS)
                        .build();

        in.setBody(log);
    }
}
