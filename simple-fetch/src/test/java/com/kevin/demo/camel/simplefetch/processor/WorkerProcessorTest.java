package com.kevin.demo.camel.simplefetch.processor;

import com.kevin.demo.camel.common.constants.LogLevel;
import com.kevin.demo.camel.common.model.utils.CheckLog;
import com.kevin.demo.camel.common.utils.ConfigManager;
import com.kevin.demo.camel.simplefetch.model.jaxb.UserXml;
import com.kevin.demo.camel.simplefetch.util.MockBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WorkerProcessorTest {

    private WorkerProcessor p = new WorkerProcessor("mock:log");


    @Before
    public void setUp() throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        config.loadConfig(new String[] {"camel-common/src/test/resources/config-dev.properties"});
    }

    //param format
    /*
    * new String[] param = new String[] {"whatever", "firstname", "lastname", "birthdate", "email"}
    *
    * */

    //Nominal Test
    @Test
    public void testUserNominal() {
        String[] param = new String[] {"1425-8554", "unit", "test", "1991-10-07", "utest@demo.ut"};

        UserXml u = MockBuilder.buildUserXml(param);

        List<CheckLog> checkList = p.checkUserData(u);

        Assertions.assertThat(checkList).isEmpty();
    }

    @Test
    public void testXmlParser() {
        Exchange ex = this.buildExchange();
        ex.getIn().setBody(this.getNominalXml());

        UserXml u = p.getUser(ex.getIn());

        Assertions.assertThat(u.getFirstName()).isEqualTo("unit");
    }

    @Test
    public void testExchangeNominal() throws Exception {
        Exchange ex = this.buildExchange();
        ex.getIn().setBody(this.getNominalXml());

        this.p.process(ex);

        Assertions.assertThat((Boolean)ex.getProperty(Exchange.ROUTE_STOP, false)).isFalse();
    }

    //param format
    /*
    * new String[] param = new String[] {"whatever", "firstname", "lastname", "birthdate", "email"}
    *
    * */

    //Test erreur
    @Test
    public void testExchangeError() throws Exception {
        String[] param = new String[] {"1425-8554", "unit;:é", "test", "1991-10-07", "ut%@est@deémo.ut"};
        UserXml u = MockBuilder.buildUserXml(param);
        Exchange ex = this.buildExchange();

        ex.getIn().setBody(u);

        this.p.process(ex);

        Assertions.assertThat((Boolean)ex.getProperty(Exchange.ROUTE_STOP, false)).isTrue();
    }


    @Test
    public void testUserWithWrongFirstNameFormat() {
        String[] param = new String[] {"1425-8554", "unit;:é", "test", "1991-10-07", "utest@demo.ut"};

        UserXml u = MockBuilder.buildUserXml(param);

        List<CheckLog> checkList = p.checkUserData(u);

        Assertions.assertThat(checkList.stream()
                        .anyMatch(l -> l.getLevel() == LogLevel.ERROR && l.isFinalize() && l.getMessage().contains("Invalid first")))
                        .isTrue();
    }

    @Test
    public void testUserWithWrongLastNameFormat() {
        String[] param = new String[] {"1425-8554", "unit", "téest_58-/", "1991-10-07", "utest@demo.ut"};

        UserXml u = MockBuilder.buildUserXml(param);

        List<CheckLog> checkList = p.checkUserData(u);

        Assertions.assertThat(checkList.stream()
                .anyMatch(l -> l.getLevel() == LogLevel.ERROR && l.isFinalize() && l.getMessage().contains("Invalid last")))
                .isTrue();
    }

    @Test
    public void testUserWithWrongBirthdateFormat() {
        String[] param = new String[] {"1425-8554", "unit", "test", "191-102-07", "utest@demo.ut"};

        UserXml u = MockBuilder.buildUserXml(param);

        List<CheckLog> checkList = p.checkUserData(u);

        Assertions.assertThat(checkList.stream()
                .anyMatch(l -> l.getLevel() == LogLevel.ERROR && l.isFinalize() && l.getMessage().contains("Invalid birth")))
                .isTrue();
    }

    @Test
    public void testUserWithUnderAgeUser() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -20);

        Date d = c.getTime();

        String[] param = new String[] {"1425-8554", "unit", "test", df.format(d), "utest@demo.ut"};

        UserXml u = MockBuilder.buildUserXml(param);

        List<CheckLog> checkList = p.checkUserData(u);

        Assertions.assertThat(checkList.stream()
                .anyMatch(l -> l.getLevel() == LogLevel.WARN && !l.isFinalize() && l.getMessage().contains("This user is not an inte")))
                .isTrue();
    }

    @Test
    public void testInvalidEmailUser() {
        String[] param = new String[] {"1425-8554", "unit", "test", "1991-10-07", "utest%ù*;@demo.ut"};

        UserXml u = MockBuilder.buildUserXml(param);

        List<CheckLog> checkList = p.checkUserData(u);

        Assertions.assertThat(checkList.stream()
                .anyMatch(l -> l.getLevel() == LogLevel.ERROR && l.isFinalize() && l.getMessage().contains("Invalid email")))
                .isTrue();
    }

    private String getNominalXml() {
        return  "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>"
                + "<info whatever='11111-11111'>"
                + "    <firstname>unit</firstname>"
                + "    <lastname>test</lastname>"
                + "    <birthdate>1997-01-01</birthdate>"
                + "    <email>unit-test@demo.ut</email>"
                + "</info>";
    }

    private Exchange buildExchange() {
        return new DefaultExchange(new DefaultCamelContext());
    }
}
