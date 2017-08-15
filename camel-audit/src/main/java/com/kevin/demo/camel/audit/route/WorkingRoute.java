package com.kevin.demo.camel.audit.route;

import com.kevin.demo.camel.common.constants.Queue;
import com.kevin.demo.camel.audit.processor.SaveLogProcessor;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class WorkingRoute extends RouteBuilder {

    private String mqIn;

    private Processor saveLogProcessor;

    public WorkingRoute() {
        this.mqIn = Queue.Audit.toString();
        this.saveLogProcessor = new SaveLogProcessor();
    }

    //For unit test purpose
    public WorkingRoute(String mqIn, Processor saveLogProcessor) {
        this.mqIn = mqIn;
        this.saveLogProcessor = saveLogProcessor;
    }

    @Override
    public void configure() throws Exception {

        from(mqIn)
        .process(saveLogProcessor);


    }

}
