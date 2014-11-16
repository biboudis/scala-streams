package streams

import scala.reflect.ClassTag
import scala.Array
import scala.collection.mutable.ArrayBuffer

final class Stream[T: ClassTag](val streamf: (T => Boolean) => Unit) {

  @inline def toArray(): Array[T] = 
    foldLeft(new ArrayBuffer[T])((a: ArrayBuffer[T], value: T) => a += value).toArray

  @inline def filter(p: T => Boolean): Stream[T] =
    new Stream(iterf => streamf(value => !p(value) || iterf(value)))

  @inline def map[R: ClassTag](f: T => R): Stream[R] = 
    new Stream(iterf => streamf(value => iterf(f(value))))

  @inline def takeWhile(p: T => Boolean): Stream[T] = 
    new Stream(iterf => streamf(value => if (p(value)) iterf(value) else false))

  @inline def skipWhile(p: T => Boolean): Stream[T] = 
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

  @inline def skip(n: Int): Stream[T] = {
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

  @inline def take(n: Int): Stream[T] =  {
    var count = 0
    new Stream(iterf => streamf(value => {
      count += 1
      if (count <= n) {
	iterf(value)
      }
      else {
	false
      }
    }))
  }

  @inline def flatMap[R: ClassTag](f: T => Stream[R]): Stream[R] = 
    new Stream(iterf => streamf(value => {
	val innerf = f(value).streamf
	innerf(iterf)
	true
    }))

  @inline def foldLeft[A](a: A)(op: (A, T) => A): A = {
    var acc = a
    streamf(value => {
      acc = op(acc, value)
      true
    })
    acc
  }

  @inline def fold(z: T)(op: (T, T) => T): T = foldLeft(z)(op)

  @inline def size(): Long = foldLeft(0)((a: Int, _) => a + 1)

  @inline def sum[N >: T](implicit num: Numeric[N]): N = foldLeft(num.zero)(num.plus)
}

object Stream {
  @inline def apply[T: ClassTag](xs: Array[T]) = {
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
