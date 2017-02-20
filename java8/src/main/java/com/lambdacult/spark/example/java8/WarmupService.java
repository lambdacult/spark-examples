package com.lambdacult.spark.example.java8;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class WarmupService {
    private static final Logger LOG = Logger.getLogger(WarmupService.class);

    public WarmupResponse warmup(long minimumDurationMilliseconds, String logStreamName) throws Exception {
        sleepMinimumDuration(minimumDurationMilliseconds);
        String uptime = readFileToString("/proc/uptime");
        String bootId = readFileToString("/proc/sys/kernel/random/boot_id");
        return new WarmupResponse(uptime, bootId, logStreamName);
    }

    private void sleepMinimumDuration(long minimumDurationMilliseconds) throws InterruptedException {
        LOG.info("sleeping for " + minimumDurationMilliseconds + "ms");
        Thread.sleep(minimumDurationMilliseconds);
        LOG.info("sleeping done");
    }

    private String readFileToString(String path) throws IOException {
        return Files.readAllLines(Paths.get(path)).stream().collect(Collectors.joining());
    }
}
