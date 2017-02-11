package com.lambdacult.spark.example.java8;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final Logger LOG = Logger.getLogger(Handler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received: " + input);
        Object responseBody = buildResponseForInput(input, context);
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(responseBody)
                .build();
    }

    private Object buildResponseForInput(Map<String, Object> input, Context context) {
        if (Actions.WARMUP.equals(input.get(Parameters.ACTION))) {
            return buildWarmupResponse(input, context);
        }
        return new Response("Go Serverless v1.x! Your function executed successfully!", input);
    }

    private Object buildWarmupResponse(Map<String, Object> input, Context context) {
        try {
            long minimumDurationMilliseconds = ((Number) input.get(Parameters.MINIMUM_DURATION_MILLISECONDS)).longValue();
            sleepMinimumDuration(minimumDurationMilliseconds);
            String uptime = readFileToString("/proc/uptime");
            String bootId = readFileToString("/proc/sys/kernel/random/boot_id");
            return new WarmupResponse(uptime, bootId, context.getLogStreamName());
        } catch (Exception e) {
            return new Response("Exception occurred: " + e.getMessage(), input);
        }
    }

    private void sleepMinimumDuration(long minimumDurationMilliseconds) throws InterruptedException {
        LOG.info("sleeping for " + minimumDurationMilliseconds + "ms");
        Thread.sleep(minimumDurationMilliseconds);
        LOG.info("sleeping done");
    }

    private String readFileToString(String path) throws IOException {
        return Files.readAllLines(Paths.get(path)).stream().collect(Collectors.joining());
    }

    private interface Parameters {
        String ACTION = "action";
        String MINIMUM_DURATION_MILLISECONDS = "minimumDurationMilliseconds";
    }

    private interface Actions {
        String WARMUP = "warmup";
    }
}
