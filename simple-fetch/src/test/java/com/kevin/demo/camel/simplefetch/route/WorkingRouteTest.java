package com.kevin.demo.camel.simplefetch.route;

import com.kevin.demo.camel.common.model.db.LogMessage;
import com.kevin.demo.camel.common.processor.ExceptionProcessor;
import com.kevin.demo.camel.common.processor.LogProcessor;
import com.kevin.demo.camel.common.repository.UserRepository;
import com.kevin.demo.camel.common.utils.ConfigManager;
import com.kevin.demo.camel.simplefetch.processor.SaveUserProcessor;
import com.kevin.demo.camel.simplefetch.processor.WorkerProcessor;
import com.kevin.demo.camel.simplefetch.util.MockBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class WorkingRouteTest extends CamelTestSupport {

    MockEndpoint log;

    private UserRepository userRepo;

    @Override
    public void setUp() throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        config.loadConfig(new String[] {"camel-common/src/test/resources/config-dev.properties"});
        super.setUp();
    }

    @Override
    public RouteBuilder createRouteBuilder() throws Exception {

        this.userRepo = Mockito.mock(UserRepository.class);

        Mockito.doNothing().when(this.userRepo).storeUser(Mockito.any());

        Processor worker = new WorkerProcessor("mock:log");
        Processor saveUser = new SaveUserProcessor(userRepo);
        Processor logUserWillBeCreated = new LogProcessor("User will be created", "SimpleFetchUT");
        Processor logUserHasBeenCreated = new LogProcessor("User has been created", "SimpleFetchUT");
        Processor exceptionProcessor = new ExceptionProcessor("SimpleFetchUT");

        return new WorkingRoute("direct:in", "mock:log", worker, saveUser, logUserWillBeCreated, logUserHasBeenCreated, exceptionProcessor);
    }

    @Before
    public void setEndpoint() {
        this.log = getMockEndpoint("mock:log");
    }

    @Test
    public void testNominal() throws InterruptedException {
        this.log.expectedMessageCount(2);

        this.template.sendBody("direct:in", getNominalXml());

        log.assertIsSatisfied();
    }

    //param format
    /*
    * new String[] param = new String[] {"whatever", "firstname", "lastname", "birthdate", "email"}
    *
    * */

    @Test
    public void testRouteBlockingCase() throws InterruptedException {
        this.log.expectedMessageCount(4);

        String[] p = new String[] {"12345-54321", "unt1t", "t3st", "1997-01-01", "unit@test.ut"};

        this.template.sendBody("direct:in", MockBuilder.buildUserXml(p));

        log.assertIsSatisfied();
    }




    private String getNominalXml() {
        return  "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>"
                + "<info whatever='11111-11111'>"
                + "    <firstname>unit</firstname>"
                + "    <lastname>test</lastname>"
                + "    <birthdate>1991-01-01</birthdate>"
                + "    <email>unit-test@demo.ut</email>"
                + "</info>";
    }

    private Exchange buildExchange() {
        return new DefaultExchange(new DefaultCamelContext());
    }
}
