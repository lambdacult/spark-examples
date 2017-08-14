This example requires the [serverless framework](http://serverless.com) (tested with v1.6.1).

To get started, run:
* `git clone https://github.com/lambdacult/spark-examples.git` (this repository)
* `cd java8`
* `mvn clean install`
* `serverless deploy`
* assign alias `lambdaspark-1` to the newly created version 1
* `mvn clean install` (.jar file needs to change, otherwise AWS won't accept a new version)
* `serverless deploy`
* assign alias `lambdaspark-2` and `lambdaspark-current` to the newly created version 2

To configure the newly created lambda function with [Spark](https://lambdacult.com/spark), use the following warmup input:
```
{
  "action": "warmup",
  "minimumDurationMilliseconds": ${minimumDurationMilliseconds}
}
```
