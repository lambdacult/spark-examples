This example requires the [serverless framework](http://serverless.com) (tested with v1.6.1).

To get started, run:
* `git clone https://github.com/lambdacult/spark-examples.git` (this repository)
* `cd dotnetcore1.0`
* `./build.sh`
* `serverless deploy`
* assign alias `lambdaspark-1` to the newly created version 1
* `rm -R bin` (.zip file needs to change, otherwise AWS won't accept a new version)
* `./build.sh`
* `serverless deploy`
* assign alias `lambdaspark-2` and `lambdaspark-current` to the newly created version 2

To configure the newly created lambda function with [Spark](https://lambdacult.com/spark), use the following warmup input:
```
{
  "action": "warmup",
  "minimumDurationMilliseconds": ${minimumDurationMilliseconds}
}
```
