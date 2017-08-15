package com.kevin.demo.camel.simplefetch.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by khantzen on 14/08/17.
 */
public class FetchProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.print("hello");
    }
}
