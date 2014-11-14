import org.scalacheck._
import streams.Stream

object StreamSpec extends Properties("Stream") {
  
  import Prop.forAll
  import Prop.BooleanOperators

  property("map") = forAll { (xs: Array[Int]) =>
    var x = xs.map(_*2)
    var y = Stream(xs).map(_*2).toArray()
    (x: Seq[Int]) == (y: Seq[Int])
  }
}
