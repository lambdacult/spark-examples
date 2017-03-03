package com.lambdacult.spark.example.java8;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler implements RequestHandler<Map<String, Object>, Object> {
    private static final Logger LOG = Logger.getLogger(Handler.class);

    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received: " + input);
        return buildResponseBody(input, context);
        // Or as lambda-proxy response for API Gateway:
        // return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(buildResponseBody(input, context)).build();
    }

    private Object buildResponseBody(Map<String, Object> input, Context context) {
        try {
            if (input.get("action").equals("warmup")) {
                sleepMinimumDuration(((Number) input.get("minimumDurationMilliseconds")).longValue());
                String uptime = Files.readAllLines(Paths.get("/proc/uptime")).stream().collect(Collectors.joining());
                return new WarmupResponse(uptime, context.getLogStreamName());
            }
            return new Response("Go Serverless v1.x! Your function executed successfully!", input);
        } catch (Exception e) {
            LOG.error("Couldn't build Response", e);
            throw new RuntimeException(e);
        }
    }

    private void sleepMinimumDuration(long minimumDurationMilliseconds) throws InterruptedException {
        LOG.info("sleeping for " + minimumDurationMilliseconds + "ms");
        Thread.sleep(minimumDurationMilliseconds);
        LOG.info("sleeping done");
    }
}
