package com.kevin.demo.camel.common.constants;

/**
 * Created by khantzen on 14/08/17.
 */
public enum LogLevel {
    DEBUG(0, "DEBUG"),
    INFO(1, "INFO"),
    WARN(2, "WARNING"),
    ERROR(3, "ERROR");


    private int level;
    private String name;

    LogLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
}
