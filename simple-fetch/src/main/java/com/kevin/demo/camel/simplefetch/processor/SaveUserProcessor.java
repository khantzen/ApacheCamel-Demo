package com.kevin.demo.camel.simplefetch.processor;

import com.kevin.demo.camel.common.repository.UserRepository;
import com.kevin.demo.camel.simplefetch.model.jaxb.UserXml;
import com.kevin.demo.camel.common.model.db.User;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveUserProcessor implements Processor {
    private UserRepository userRepo;

    public SaveUserProcessor() {
        userRepo = new UserRepository();
    }

    //For unit test purpose
    public SaveUserProcessor(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();

        UserXml userXml = in.getBody(UserXml.class);

        User userDb = new User();

        userDb.setFirstName(userXml.getFirstName());
        userDb.setLastName(userXml.getLastName());
        userDb.setEmail(userXml.getEmail());

        String birthDate = userXml.getBirthDate();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.setTime(format.parse(birthDate));
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);


        userDb.setBirthDate(new Date(c.getTimeInMillis()));

        this.userRepo.storeUser(userDb);
    }
}
