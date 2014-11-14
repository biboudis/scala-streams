package streams

import scala.reflect.ClassTag
import scala.Array
import scala.collection.mutable.ArrayBuffer

final class Stream[T: ClassTag](val streamf: (T => Boolean) => Unit) {

  def toArray(): Array[T] = 
    foldLeft(new ArrayBuffer[T])((a: ArrayBuffer[T], value: T) => a += value).toArray

  def filter(p: T => Boolean): Stream[T] =
    new Stream(iterf => streamf(value => !p(value) || iterf(value)))

  def map[R: ClassTag](f: T => R): Stream[R] = 
    new Stream(iterf => streamf(value => iterf(f(value))))

  def takeWhile(p: T => Boolean): Stream[T] = 
    new Stream(iterf => streamf(value => if (p(value)) iterf(value) else false))

  def skipWhile(p: T => Boolean): Stream[T] = 
    new Stream(iterf => streamf(value => {
      var shortcut = true;
      if (!shortcut && p(value)) {
	true
      }
      else {
	shortcut = true
	iterf(value)
      }
    }))

  def skip(n: Int): Stream[T] = {
    var count = 0
    new Stream(iterf => streamf(value => {
      count += 1
      if (count > n) {
	iterf(value)
      }
      else {
	true
      }
    }))
  }

  def take(n: Int): Stream[T] =  {
    var count = 0
    new Stream(iterf => streamf(value => {
      count += 1
      if (count < n) {
	iterf(value)
      }
      else {
	false
      }
    }))
  }

  def flatMap[R: ClassTag](f: T => Stream[R]): Stream[R] = 
    new Stream(iterf => streamf(value => {
	val innerf = f(value).streamf
	innerf(iterf)
	true
    }))

  def foldLeft[A](a: A)(op: (A, T) => A): A = {
    var acc = a
    streamf(value => {
      acc = op(acc, value)
      true
    })
    acc
  }

  def fold(z: T)(op: (T, T) => T): T = foldLeft(z)(op)

  def size(): Long = foldLeft(0)((a: Int, _) => a + 1)

  def sum[N >: T](implicit num: Numeric[N]): N = foldLeft(num.zero)(num.plus)
}

object Stream {
  def apply[T: ClassTag](xs: Array[T]) = {
    val gen = (iterf: T => Boolean) => {
      var counter = 0
      var cont = true
      val size = xs.length
      while (counter < size && cont) {
	cont = iterf(xs(counter))
	counter += 1
      }
    }
    new Stream(gen)
  }
}
