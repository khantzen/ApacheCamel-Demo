package com.kevin.demo.camel.simplefetch.route;

import com.kevin.demo.camel.common.processor.ExceptionProcessor;
import com.kevin.demo.camel.common.processor.LogProcessor;
import com.kevin.demo.camel.simplefetch.processor.FetchProcessor;
import com.kevin.demo.camel.common.constants.Queue;
import com.kevin.demo.camel.common.utils.ConfigManager;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static com.kevin.demo.camel.common.constants.Constant.SIMPLE_FETCH;
import static com.kevin.demo.camel.common.utils.ConfigManager.SIMPLE_FETCH_FTP_ARCHIVE;
import static com.kevin.demo.camel.common.utils.ConfigManager.SIMPLE_FETCH_FTP_IN;

/**
 * Created by khantzen on 14/08/17.
 */
public class FetchRoute extends RouteBuilder {
    private String in;
    private String out;

    private String archive;
    private String mqLog;

    private Processor fetchProcessor;

    private Processor exceptionProcessor;
    private Processor logProcessor;

    public FetchRoute() {
        ConfigManager config = ConfigManager.getInstance();

        this.in = config.getPropertyAsString(SIMPLE_FETCH_FTP_IN);
        this.out = Queue.SimpleFetch.toString();

        this.archive = config.getPropertyAsString(SIMPLE_FETCH_FTP_ARCHIVE);
        this.mqLog = Queue.Audit.toString();

        this.fetchProcessor = new FetchProcessor();

        this.exceptionProcessor = new ExceptionProcessor(SIMPLE_FETCH);
        this.logProcessor = new LogProcessor("File successfully fetched", SIMPLE_FETCH);
    }

    public FetchRoute(String in, String out, String archive, String mqLog, Processor fetchProcessor, Processor exceptionProcessor, Processor logProcessor) {
        this.in = in;
        this.out = out;
        this.archive = archive;
        this.mqLog = mqLog;
        this.fetchProcessor = fetchProcessor;
        this.exceptionProcessor = exceptionProcessor;
        this.logProcessor = logProcessor;
    }

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
        .process(exceptionProcessor)
        .to(mqLog);

        from(in)
        .process(ex -> System.out.println(ex.getIn().getBody(String.class))) //Use this "trick" with caution.
        .process(fetchProcessor)
        .to(out)
        .to(archive)
        .process(logProcessor)
        .to(mqLog);

    }
}
