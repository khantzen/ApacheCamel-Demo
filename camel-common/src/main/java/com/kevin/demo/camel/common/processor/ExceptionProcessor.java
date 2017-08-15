package com.kevin.demo.camel.common.processor;

import com.kevin.demo.camel.common.constants.LogLevel;
import com.kevin.demo.camel.common.model.db.LogMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.util.Calendar;

import static com.kevin.demo.camel.common.constants.Constant.OWNER_TECHNICAL;

/**
 * Created by khantzen on 14/08/17.
 */
public class ExceptionProcessor implements Processor {
    private String sender;

    public ExceptionProcessor(String sender) {
        this.sender = sender;
    }

    @Override
    public void process(Exchange ex) throws Exception {
        Message in = ex.getIn();
        Throwable caused = ex.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);

        System.err.print(caused.getMessage());

        Exception e = in.getBody(Exception.class);

        LogMessage log = new LogMessage.Builder()
                .setLogLevel(LogLevel.INFO.getName())
                .setMessage(caused.getMessage())
                .setSender(this.sender)
                .setOwner(OWNER_TECHNICAL)
                .build();

        in.setBody(log);
    }
}
