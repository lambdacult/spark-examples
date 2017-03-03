'use strict';

const fs = require('fs');

const buildResponseBody = function(event, context, responseCallback) {
  if (event.action === 'warmup') {
    console.log('sleeping for ' + event.minimumDurationMilliseconds + 'ms');
    setTimeout(function() {
      console.log('sleeping done');
      responseCallback({
        uptime: fs.readFileSync('/proc/uptime', { encoding: 'utf-8' }),
        logStreamName: context.logStreamName
      });
    }, event.minimumDurationMilliseconds);
    return;
  }
  responseCallback({
    message: 'Go Serverless v1.0! Your function executed successfully!',
    input: event,
  });
};

module.exports.handleRequest = (event, context, callback) => {
  console.log('received', event);
  buildResponseBody(event, context, function(responseBody) {
    callback(null, responseBody);
    // Or as lambda-proxy response for API Gateway:
    // callback(null, {
    //   statusCode: 200,
    //   body: JSON.stringify(responseBody),
    // });
  });
};
