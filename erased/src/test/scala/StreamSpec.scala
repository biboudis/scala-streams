import org.scalacheck._
import streams.Stream

object StreamSpec extends Properties("Stream") {
  
  import Prop.forAll
  import Prop.BooleanOperators

  property("map") = forAll { (xs: Array[Int]) =>
    var x = xs.map(_*2)
    var y = Stream(xs).map(_*2).toArray()
    x sameElements y
  }

  property("filter") = forAll { (xs: Array[Int]) =>
    var x = xs.filter(_*2==0)
    var y = Stream(xs).filter(_*2==0).toArray()
    x sameElements y
  }

  property("fold") = forAll { (xs: Array[Int]) =>
    var x = xs.map(_*2).fold(0)(_+_)
    var y = Stream(xs).map(_*2).fold(0)(_+_)
    x == y
  }

  property("sum") = forAll { (xs: Array[Int]) =>
    var x = xs.map(_*2).sum
    var y = Stream(xs).map(_*2).sum
    x == y
  }

  property("size") = forAll { (xs: Array[Int]) =>
    var x = xs.map(_*2).filter(_%2==0).size
    var y = Stream(xs).map(_*2).filter(_%2==0).size
    x == y
  }

  property("takeWhile") = forAll { (xs: Array[Int]) =>
    var x = xs.takeWhile(_%2==0).size
    var y = Stream(xs).takeWhile(_%2==0).size
    x == y
  }

  property("take") = forAll { (xs: Array[Int], n: Int) =>
    var x = xs.take(n).size
    var y = Stream(xs).take(n).size
    x == y
  }

  property("flatMap") = forAll { (lines: Array[String]) =>
    var x = lines.flatMap(line => line split "\\W+").toArray
    var y = Stream(lines).flatMap(line => Stream(line split "\\W+")).toArray
    x sameElements y
  }
}
