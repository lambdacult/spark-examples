This example requires the [serverless framework](http://serverless.com) (tested with v1.6.1).

To get started, run:
* `git clone https://github.com/lambdacult/spark-examples.git` (this repository)
* `cd python2.7`
* `serverless deploy`
* assign alias `lambdaspark-1` to the newly created version 1
* `serverless deploy`
* assign alias `lambdaspark-2` and `lambdaspark-current` to the newly created version 2

To configure the newly created lambda as a spλrk, use the following warmup input:
```
{
  "action": "warmup",
  "minimumDurationMilliseconds": ${minimumDurationMilliseconds}
}
```
