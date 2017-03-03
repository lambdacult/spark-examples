package com.lambdacult.spark.example.java8;

public class WarmupResponse {

    private final String uptime;
    private final String logStreamName;

    public WarmupResponse(String uptime, String logStreamName) {
        this.uptime = uptime;
        this.logStreamName = logStreamName;
    }

    public String getUptime() {
        return uptime;
    }

    public String getLogStreamName() {
        return logStreamName;
    }
}
