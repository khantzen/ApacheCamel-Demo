package com.kevin.demo.camel.common.utils;

import java.io.*;
import java.util.Properties;

/**
 * Created by khantzen on 15/08/17.
 */
public class ConfigManager {
    private static ConfigManager ourInstance = new ConfigManager();

    public static ConfigManager getInstance() {
        return ourInstance;
    }

    //SimpleFetch
    public static String SIMPLE_FETCH_FTP_IN = "demo.simplefetch.ftp.in";
    public static String SIMPLE_FETCH_FTP_ARCHIVE = "demo.simplefetch.ftp.archive";

    //RabbitMq
    public static String RABBIT_MQ_SERVER = "demo.rabbitmq.server";
    public static String RABBIT_MQ_USER = "demo.rabbitmq.user";
    public static String RABBIT_MQ_PASSWORD = "demo.rabbitmq.password";

    private Properties p;

    private ConfigManager() {
        p = new Properties();
    }


    public String getPropertyAsString(String s) {
        return p.getProperty(s);
    }

    public void loadConfig(String[] args) throws IOException {
        String configPath;

        //Config path is pass in args
        if (args.length > 0) {
            configPath = args[0];
        } else { //Config file is in the jar folder
            configPath = "config.properties";
        }

        File file = new File(configPath);

        if (file.exists()) {
            try(InputStream fis = new FileInputStream(file)) {
                p.load(fis);
            }
        }
    }

    public Properties getProperties() {
        return p;
    }
}
