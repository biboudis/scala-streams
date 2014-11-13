import org.scalatest._
import streams.Stream

class SimplePipelineSpec extends FlatSpec with Matchers {

  def prepare() : Unit = {


  }

  def fixture =
    new {
      var N : Int = 10000000
      var v : Array[Long] = (0 until N).map(i => i.toLong % 1000).toArray
      var vHi : Array[Long] = (0 until 1000000).map(i => i.toLong).toArray
      var vLo : Array[Long] = (0 until 10).map(i => i.toLong).toArray
      var refs : Array[Ref] = (0 until N).map(i => new Ref(i)).toArray
    }

  "Filter" should "apply predicate for each element" in {
    
    
  }

  // it should "throw NoSuchElementException if an empty stack is popped" in {
  //   val emptyStack = new Stack[Int]
  //   a [NoSuchElementException] should be thrownBy {
  //     emptyStack.pop()
  //   } 
  // }
}
