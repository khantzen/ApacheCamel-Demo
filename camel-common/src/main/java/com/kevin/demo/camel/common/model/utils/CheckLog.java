package com.kevin.demo.camel.common.model.utils;

import com.kevin.demo.camel.common.constants.LogLevel;

public class CheckLog {

    private LogLevel level;
    private String message;
    private Boolean finalize;

    public CheckLog(LogLevel level, String message) {
        this.level = level;
        this.message = message;
        this.finalize = false;
    }

    public CheckLog(LogLevel level, String message, Boolean finalize) {
        this.level = level;
        this.message = message;
        this.finalize = finalize;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isFinalize() {
        return finalize;
    }

    public void setFinalize(Boolean finalize) {
        this.finalize = finalize;
    }
}
