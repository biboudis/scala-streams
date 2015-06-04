package microbenchmarks

import streams._

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.OutputTimeUnit
import java.util.concurrent.TimeUnit

class Ref(var num: Int = 0)

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@State(Scope.Thread)
@Fork(1)
class Pipelines {
  var N : Int = _
  var v : Array[Long] = _
  var vHi : Array[Long] = _
  var vLo : Array[Long] = _
  var refs : Array[Ref] = _
  
  @Setup
  def prepare() : Unit = {
    N    = 10000000
    v    = Array.tabulate(N)(i => i.toLong % 1000)
    vHi  = Array.tabulate(1000000)(_.toLong)
    vLo  = Array.tabulate(10)(_.toLong)
    refs = Array.tabulate(N)(x=>new Ref(x))
  }

  // Baselines
  @Benchmark
  def baseline_sum () : Long = {
    var i=0
    var sum=0L
    while (i < v.length) {
      sum += v(i)
      i += 1
    }
    sum
  }

  @Benchmark
  def baseline_sumOfSquares () : Long = {
    var i=0
    var sum=0L
    while (i < v.length) {
      sum += v(i) * v(i)
      i += 1
    }
    sum
  }

  @Benchmark
  def baseline_sumOfSquaresEven () : Long = {
    var i=0
    var sum=0L
    while (i < v.length) {
      if (v(i) % 2 == 0)
	sum += v(i) * v(i)
      i += 1
    }
    sum
  }

  @Benchmark
  def baseline_cart () : Long = {
    var d, dp=0
    var sum=0L
    while (d < vHi.length) {
      dp = 0
      while (dp < vLo.length) {
	sum += vHi(d) * vLo(dp)
	dp +=1
      }
      d += 1
    }
    sum
  }

  @Benchmark
  def baseline_ref () : Long = {
    var i=0
    var count=0
    while (i < refs.length) {
      if (refs(i).num % 5 == 0 && refs(i).num % 7 == 0)
	count += 1
      i += 1
    }
    count
  }

  // Scala Views
  @Benchmark
  def views_sum () : Long = {
    val sum : Long = v
      .view
      .sum
    sum
  }

  @Benchmark
  def views_sumOfSquares () : Long = {
    val sum : Long = v
      .view
      .map(d => d * d)
      .sum
    sum
  }

  @Benchmark
  def views_sumOfSquaresEven () : Long = {
    val res : Long = v
      .view
      .filter(x => x % 2 == 0)
      .map(x => x * x)
      .sum
    res
  }

  @Benchmark
  def views_cart () : Long = {
    val sum : Long = vHi
      .view
      .flatMap(d => vLo.view.map (dp => dp * d))
      .sum
    sum
  }

  @Benchmark
  def views_ref () : Long = {
    val res : Long = refs
      .view
      .filter(_.num % 5 == 0)
      .filter(_.num % 7 == 0)
      .size
    res
  }

  // Streams
  @Benchmark
  def streams_sum () : Long = {
    val sum : Long = Stream(v)
      .sum
    sum
  }

  @Benchmark
  def streams_sumOfSquares () : Long = {
    val sum : Long = Stream(v)
      .map(d => d * d)
      .sum
    sum
  }

  @Benchmark
  def streams_sumOfSquaresEven () : Long = {
    val res : Long = Stream(v)
      .filter(x => x % 2 == 0)
      .map(x => x * x)
      .sum
    res
  }

  @Benchmark
  def streams_cart () : Long = {
    val sum : Long = Stream(vHi)
      .flatMap(d => Stream(vLo).map (dp => dp * d))
      .sum
    sum
  }

  @Benchmark
  def streams_ref () : Long = {
    val res : Long = Stream(refs)
      .filter(_.num % 5 == 0)
      .filter(_.num % 7 == 0)
      .size
    res
  }
}
