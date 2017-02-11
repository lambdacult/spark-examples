package com.lambdacult.spark.example.java8;

public class WarmupResponse {

    private final String uptime;
    private final String bootId;
    private final String logStreamName;

    public WarmupResponse(String uptime, String bootId, String logStreamName) {
        this.uptime = uptime;
        this.bootId = bootId;
        this.logStreamName = logStreamName;
    }

    public String getUptime() {
        return uptime;
    }

    public String getBootId() {
        return bootId;
    }

    public String getLogStreamName() {
        return logStreamName;
    }
}
