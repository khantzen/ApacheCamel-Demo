package com.kevin.demo.camel.simplefetch.route;

import com.kevin.demo.camel.common.constants.Queue;
import com.kevin.demo.camel.common.processor.ExceptionProcessor;
import com.kevin.demo.camel.common.processor.LogProcessor;
import com.kevin.demo.camel.simplefetch.processor.SaveUserProcessor;
import com.kevin.demo.camel.simplefetch.processor.WorkerProcessor;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static com.kevin.demo.camel.common.constants.Constant.SIMPLE_FETCH;

/**
 * Created by khantzen on 14/08/17.
 */
public class WorkingRoute extends RouteBuilder {
    private String mqIn;
    private String mqLog;

    private Processor workerProcessor;
    private Processor saveUserProcessor;

    private Processor logProcessorUserWillBeCreated;
    private Processor logProcessorUserIsCreated;
    private Processor exceptionProcessor;

    public WorkingRoute() {
        this.mqIn = Queue.SimpleFetch.toString();
        this.mqLog = Queue.Audit.toString();

        this.workerProcessor = new WorkerProcessor();
        this.saveUserProcessor = new SaveUserProcessor();

        this.logProcessorUserWillBeCreated = new LogProcessor("User will be created", SIMPLE_FETCH);
        this.logProcessorUserIsCreated = new LogProcessor("User has been created", SIMPLE_FETCH);
        this.exceptionProcessor = new ExceptionProcessor(SIMPLE_FETCH);
    }

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
        .process(exceptionProcessor)
        .to(mqLog);

        from(mqIn)
                .onCompletion().onCompleteOnly()
                    .useOriginalBody()
                    .process(saveUserProcessor)
                    .process(logProcessorUserIsCreated)
                    .to(mqLog)
                .end()
        .process(p -> System.out.println(p.getIn().getBody(String.class)))
        .process(workerProcessor)
        .process(logProcessorUserWillBeCreated)
        .to(mqLog);
    }
}
