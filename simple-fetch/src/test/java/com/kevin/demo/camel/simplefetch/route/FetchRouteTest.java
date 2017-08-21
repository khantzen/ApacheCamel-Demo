package com.kevin.demo.camel.simplefetch.route;

import com.kevin.demo.camel.common.processor.ExceptionProcessor;
import com.kevin.demo.camel.common.processor.LogProcessor;
import com.kevin.demo.camel.common.utils.ConfigManager;
import com.kevin.demo.camel.simplefetch.model.jaxb.UserXml;
import com.kevin.demo.camel.simplefetch.processor.FetchProcessor;
import com.kevin.demo.camel.simplefetch.util.MockBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FetchRouteTest extends CamelTestSupport {

    private MockEndpoint out;
    private MockEndpoint archive;
    private MockEndpoint log;

    @Override
    public void setUp() throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        config.loadConfig(new String[] {"camel-common/src/test/resources/config-dev.properties"});
        super.setUp();
    }


    @Override
    public RouteBuilder createRouteBuilder() throws Exception {
        ExceptionProcessor exProc = new ExceptionProcessor("SimpleFetch_UT");
        LogProcessor logProc = new LogProcessor("unit_test", "SimpleFetch_UT");
        FetchProcessor fetchProc = new FetchProcessor();

        return new FetchRoute("direct:in", "mock:out", "mock:archive", "mock:log", fetchProc, exProc, logProc);
    }

    @Before
    public void setEndpoint() {
        this.out = getMockEndpoint("mock:out");
        this.archive = getMockEndpoint("mock:archive");
        this.log = getMockEndpoint("mock:log");
    }


    //param format
    /*
    * new String[] param = new String[] {"whatever", "firstname", "lastname", "birthdate", "email"}
    *
    * */
    @Test
    public void nominalTest() throws InterruptedException {
        String[] userParam = new String[] {"11111-11111", "unit", "test", "1997-01-01", "unit-test@demo.ut"};
        UserXml xml = MockBuilder.buildUserXml(userParam);

        this.out.expectedMessageCount(1);
        this.archive.expectedMessageCount(1);
        this.log.expectedMessageCount(1);


        this.template.sendBody("direct:in", xml);

        this.out.assertIsSatisfied();
        this.archive.assertIsSatisfied();
        this.log.assertIsSatisfied();
    }


}
