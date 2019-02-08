package us.aharon.scalapbtest

import io.grpc.{ManagedChannelBuilder, ServerBuilder}
import org.scalatest.{FlatSpec, Matchers, Outcome}

import us.aharon.scalapbtest.api.hello.{GreeterGrpc, HelloReply, HelloRequest}

import scala.concurrent.ExecutionContext


class GreeterSpec extends FlatSpec with Matchers {

  // Start the server before the test.  Shutdown after.
  override def withFixture(test: NoArgTest): Outcome = {
    val server = ServerBuilder.forPort(8080)
      .addService(GreeterGrpc.bindService(new SayHello, ExecutionContext.global))
      .build
      .start
    try {
      super.withFixture(test)
    } finally {
      server.shutdown
    }
  }

  "A call to sayHello" should "return a greeting" in {
    val name = "Test"
    val expected = new HelloReply(message = s"Hello, $name!")

    // Perform RPC request.
    val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
      .usePlaintext
      .build
    val blockingStub = GreeterGrpc.blockingStub(channel)
    val response = blockingStub.sayHello(new HelloRequest(name))

    // Test response.
    assert(response.message == expected.message)
  }
}
