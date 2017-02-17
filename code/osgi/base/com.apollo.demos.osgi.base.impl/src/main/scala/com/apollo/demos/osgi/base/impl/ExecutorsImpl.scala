package com.apollo.demos.osgi.base.impl

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.callable

import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Deactivate
import org.apache.felix.scr.annotations.Service

import com.apollo.demos.osgi.base.api.scala.{ Executors => ExecutorsApi }
import com.apollo.demos.osgi.base.impl.thread.ComplexThreadPoolExecutor
import com.apollo.demos.osgi.base.impl.thread.NamedCallable
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor

@Component
@Service
class ExecutorsImpl extends ExecutorsApi {
  private val common = newThreadPool("ApolloCommonPool", 100)

  def namedCallable(name: String, task: Runnable): Callable[Object] = named(name, callable(task))
  def namedCallable[T](name: String, task: Runnable, result: T): Callable[T] = named(name, callable(task, result))
  def named[T](name: String, task: Callable[T]) = if (task.isInstanceOf[NamedCallable[T]]) task else NamedCallable(name, task)

  def submit(name: String, task: Runnable) = common.submit(namedCallable(name, task))
  def submit[T](name: String, task: Runnable, result: T) = common.submit(namedCallable(name, task, result))
  def submit[T](name: String, task: Callable[T]) = common.submit(named(name, task))

  def newThreadPool(name: String, count: Int): ExecutorService = ComplexThreadPoolExecutor(name, count)
  def newFixedThreadPool(name: String, count: Int): ExecutorService = ThreadPoolExecutor(name, count)

  @Deactivate def deactivate() { common.shutdown }
}

object Executors extends ExecutorsImpl
