package com.kevin.demo.camel.audit;

import com.kevin.demo.camel.audit.route.WorkingRoute;
import com.kevin.demo.camel.common.utils.ConfigManager;
import org.apache.camel.main.Main;

import java.io.IOException;

public class AuditMain {

    //All the app log registration system is configured in this module
    //Why create a module just for that and not insert directly the log in database ?
    //Of course for this demo project it would be better but in a bigger project with a more complex log object
    //If you have one day to push some Events Notifications for each Business Error Log you will be happy to have
    //a module where all of your log treatment is

    //For development args should contain your config file path at position 0
    public static void main(String[] args) throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        config.loadConfig(args);

        Main m = new Main();
        m.addRouteBuilder(new WorkingRoute());
        m.run();
    }
}
