package us.aharon.scalapbtest

import io.grpc.{Server, ServerBuilder}
import org.scalatest.{BeforeAndAfterEach, Suite}

import us.aharon.scalapbtest.api.hello.GreeterGrpc

import scala.concurrent.ExecutionContext


trait GreeterGrpcServerFixture extends BeforeAndAfterEach { this: Suite =>

  private var server: Server = _

  override def beforeEach(): Unit = {
    server = ServerBuilder.forPort(8080)
      .addService(GreeterGrpc.bindService(new Greeter, ExecutionContext.global))
      .build()
      .start()
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    server.shutdown()
    super.afterEach()
  }
}
