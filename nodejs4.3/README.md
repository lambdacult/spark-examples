This example requires the [serverless framework](http://serverless.com) (tested with v1.6.1).

To get started, run:
* `git clone https://github.com/lambdacult/spark-examples.git` (this repository)
* `cd nodejs4.3`
* `serverless deploy`
* assign alias `spark-1` to the newly created version 1
* `serverless deploy`
* assign alias `spark-2` and `spark-current` to the newly created version 2

To configure the newly created lambda function with [Spark](https://lambdacult.com/spark), use the following warmup input:
```
{
  "action": "warmup",
  "minimumDurationMilliseconds": ${minimumDurationMilliseconds}
}
```
