scala-streams
=============
[![Build Status](https://travis-ci.org/biboudis/scala-streams.svg?branch=master)](https://travis-ci.org/biboudis/scala-streams)

### Benchmarks

- Erased version:
```
[info] Pipelines.streams_cart              avgt    5  194.429 ± 24.364  ms/op
[info] Pipelines.streams_sum               avgt    5  100.922 ±  2.530  ms/op
[info] Pipelines.streams_sumOfSquares      avgt    5  181.236 ±  6.725  ms/op
[info] Pipelines.streams_sumOfSquaresEven  avgt    5  121.014 ± 13.290  ms/op
```
- Miniboxed version:
```
[info] Pipelines.streams_cart              avgt    5  95.994 ± 10.312  ms/op
[info] Pipelines.streams_sum               avgt    5  12.489 ±  1.780  ms/op
[info] Pipelines.streams_sumOfSquares      avgt    5  12.460 ±  1.074  ms/op
[info] Pipelines.streams_sumOfSquaresEven  avgt    5  48.866 ±  8.710  ms/op
```

### References
* Inspired by our work on [Clash of the Lambdas](http://biboudis.github.io/clashofthelambdas/)
* [Nessos/Streams](https://github.com/nessos/Streams) in F#
* [sml-streams](https://github.com/biboudis/sml-streams) in SML/MLton
* [lightweight-streams](https://github.com/biboudis/lightweight-streams) in Java (reimplemented with lambdas only)
