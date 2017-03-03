using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using Amazon.Lambda.Core;
using Newtonsoft.Json;

namespace AwsDotnetCsharp
{
    public class Handler
    {
        public object HandleRequest(IDictionary<string, object> request, ILambdaContext context)
        {
            Log("received: " + string.Join(",", request.Select(entry => entry.Key + "=" + entry.Value).ToArray()));
            return BuildResponseBody(request, context);
            // Or as lambda-proxy response for API Gateway:
            // return new ApiGatewayResponse(200, JsonConvert.SerializeObject(BuildResponseBody(request, context)));
        }

        private object BuildResponseBody(IDictionary<string, object> request, ILambdaContext context)
        {
            if (request["action"].Equals("warmup"))
            {
                int minimumDurationMilliseconds = (int)(long) request["minimumDurationMilliseconds"];
                Log("sleeping for " + minimumDurationMilliseconds + "ms");
                Thread.Sleep(minimumDurationMilliseconds);
                Log("sleeping done");
                return new WarmupResponse(File.ReadAllText("/proc/uptime"), context.LogStreamName);
            }
            return new Response("Go Serverless v1.0! Your function executed successfully!", request);
        }

        private void Log(string text)
        {
            LambdaLogger.Log(DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ss.fffZ") + " " + text + "\n");
        }
    }

    public class Response
    {
        public string Message { get; }
        public IDictionary<string, object> Request { get; }

        public Response(string message, IDictionary<string, object> request)
        {
            Message = message;
            Request = request;
        }
    }

    public class WarmupResponse
    {
        [JsonProperty("uptime")]
        public string Uptime { get; }

        [JsonProperty("logStreamName")]
        public string LogStreamName { get; }

        public WarmupResponse(string uptime, string logStreamName)
        {
            Uptime = uptime;
            LogStreamName = logStreamName;
        }
    }

    public class ApiGatewayResponse
    {
        [JsonProperty("statusCode")]
        public int StatusCode { get; }

        [JsonProperty("body")]
        public string Body { get; }

        public ApiGatewayResponse(int statusCode, string body) {
            StatusCode = statusCode;
            Body = body;
        }
    }
}
