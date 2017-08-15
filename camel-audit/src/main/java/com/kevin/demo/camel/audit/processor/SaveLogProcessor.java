package com.kevin.demo.camel.audit.processor;

import com.kevin.demo.camel.common.model.db.LogMessage;
import com.kevin.demo.camel.common.repository.LogRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SaveLogProcessor implements Processor {

    private LogRepository logRepo;

    public SaveLogProcessor() {
        logRepo = new LogRepository();
    }

    //for unit test purpose
    public SaveLogProcessor(LogRepository logRepo) {
        this.logRepo = logRepo;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        LogMessage log = exchange.getIn().getBody(LogMessage.class);

        this.logRepo.storeLog(log);
    }
}
