package com.kevin.demo.camel.simplefetch;

import com.kevin.demo.camel.common.utils.ConfigManager;
import com.kevin.demo.camel.simplefetch.route.FetchRoute;
import com.kevin.demo.camel.simplefetch.route.WorkingRoute;
import org.apache.camel.main.Main;

/**
 * Created by khantzen on 14/08/17.
 */

public class SimpleFetchMain {
    //The simple fetch module purpose is to recover a specific xml file from a ftp server
    //This xml contains simple user data (firstname, lastname, birthdate etc... etc...)
    //The goal is to recover those data and then store them in our database

    //For development args should contain your config file path at position 0
    public static void main(String[] args) throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        config.loadConfig(args);

        Main main = new Main();

        main.addRouteBuilder(new FetchRoute());
        main.addRouteBuilder(new WorkingRoute());

        main.run();
    }
}
