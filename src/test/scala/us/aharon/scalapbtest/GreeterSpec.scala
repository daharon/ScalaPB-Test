package us.aharon.scalapbtest

import io.grpc.ManagedChannelBuilder
import org.scalatest.{FlatSpec, Matchers}

import us.aharon.scalapbtest.api.hello.{GreeterGrpc, HelloRequest}
import us.aharon.scalapbtest.fixtures.GreeterGrpcServerFixture


class GreeterSpec extends FlatSpec
  with Matchers
  with GreeterGrpcServerFixture
{

  "A call to sayHello" should "return a greeting" in {
    val name = "Test"
    val expected = s"Hello, $name!"

    // Perform RPC request.
    val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
      .usePlaintext
      .build
    val blockingStub = GreeterGrpc.blockingStub(channel)
    val response = blockingStub.sayHello(new HelloRequest(name))

    // Test response.
    assert(response.message == expected)
  }
}
