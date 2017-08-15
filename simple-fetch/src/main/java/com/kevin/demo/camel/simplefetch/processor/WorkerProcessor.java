package com.kevin.demo.camel.simplefetch.processor;

import com.kevin.demo.camel.common.constants.LogLevel;
import com.kevin.demo.camel.common.constants.Queue;
import com.kevin.demo.camel.common.model.utils.CheckLog;
import com.kevin.demo.camel.simplefetch.model.jaxb.UserXml;
import com.kevin.demo.camel.common.model.db.LogMessage;
import com.kevin.demo.camel.common.utils.Regex;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.kevin.demo.camel.common.constants.Constant.OWNER_BUSINESS;
import static com.kevin.demo.camel.common.constants.Constant.SIMPLE_FETCH;

/**
 * Created by khantzen on 14/08/17.
 */
public class WorkerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();

        UserXml xml = in.getBody(UserXml.class);

        List<CheckLog> checkList = this.checkUserData(xml);

        checkList.forEach(cl -> {
            LogMessage log = new LogMessage.Builder()
                    .setLogLevel(cl.getLevel().getName())
                    .setMessage(cl.getMessage())
                    .setOwner(OWNER_BUSINESS)
                    .setSender(SIMPLE_FETCH)
                    .build();

            exchange.getContext().createProducerTemplate().sendBody(Queue.Audit.toString(), log);
        });

        if (checkList.stream().anyMatch(cl -> cl.isFinalize())) {
            exchange.setProperty(Exchange.ROUTE_STOP, true);
        }
    }

    //Usually for LogLevel Error it's better to have a personnal exception object that can take a string list of message
    //as argument and handle this list in exception processor, but it would be too much for this demo, so we just set
    //finalize to true in case of error
    public List<CheckLog> checkUserData(UserXml xml) {
        List<CheckLog> checkList = new ArrayList<>();


        if (!Regex.doesMatch(xml.getFirstName(), "\\w+")) {
            checkList.add(new CheckLog(LogLevel.ERROR, "Invalid first name format", true));
        }

        if (!Regex.doesMatch(xml.getFirstName(), "\\w+")) {
            checkList.add(new CheckLog(LogLevel.ERROR, "Invalid last name format", true));
        }


        if (!Regex.doesMatch(xml.getBirthDate(), "\\d{4}-\\d{2}-\\d{2}")) {
            checkList.add(new CheckLog(LogLevel.ERROR, "Invalid birth date format", true));
        }

        //You can also add checks to see if the birdate is valid one, or whatever

        //Yes I'm lazy to cast this string as a date...
        List<String> g = Regex.capture(xml.getBirthDate(), "(\\d{4})");

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        int year = Integer.parseInt(g.get(0));

        if (currentYear - year < 21) {
            checkList.add(new CheckLog(LogLevel.WARN, "This user is not an international adult"));
        }


        if (!Regex.doesMatch(xml.getEmail(), ".+?@.+?\\..+")) {
            checkList.add(new CheckLog(LogLevel.ERROR, "Invalid email format", true));
        }



        return checkList;
    }
}
