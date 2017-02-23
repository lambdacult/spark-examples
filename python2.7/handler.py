import json
import logging
import time

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def read_file_to_string(path):
    return open(path, 'r').read()

def build_response_body(event, context):
    if event['action'] == 'warmup':
        minimum_duration_milliseconds = event['minimumDurationMilliseconds']
        logger.info('sleeping for {}ms'.format(minimum_duration_milliseconds))
        time.sleep(minimum_duration_milliseconds / 1000.0)
        logger.info('sleeping done')
        return {
            "uptime": read_file_to_string('/proc/uptime'),
            "bootId": read_file_to_string('/proc/sys/kernel/random/boot_id'),
            "logStreamName": context.log_stream_name
        }
    return {
        "message": "Go Serverless v1.0! Your function executed successfully!",
        "input": event
    }

def handle_request(event, context):
    logger.info('received {}'.format(event))
    return build_response_body(event, context)
    # Or as lambda-proxy response for API Gateway:
    # return {
    #     "statusCode": 200,
    #     "body": json.dumps(build_response_body(event, context))
    # }
