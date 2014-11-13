import scala.Array

class Stream[T] {
  self =>
    def ofArray(source: Array[T]): Stream[T] = ???
    def filter(p: T => Boolean, s: Stream[T]): Stream[T] = ???
    def map[R](f: T => R, s: Stream[T]): Stream[R] = ???
    def takeWhile(p: T => Boolean, s: Stream[T]): Stream[T] = ???
    def skipWhile(p: T => Boolean, s: Stream[T]): Stream[T] = ???
    def skip(n: Integer, s: Stream[T]): Stream[T] = ???
    def take(n: Integer, s: Stream[T]): Stream[T] = ???
    def flatMap[R](f: T => Stream[R], s: Stream[T]): Stream[R] = ???
    def fold[A](f: A => T => A, acc: A, s: Stream[T]): A = ???
    def size(s: Stream[T]): Long = ???
    def sum[N >: T](implicit num: Numeric[N]): N = ???
}
