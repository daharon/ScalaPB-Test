package us.aharon.scalapbtest

import io.grpc.ManagedChannelBuilder
import org.scalatest.{FlatSpec, Matchers}

import us.aharon.scalapbtest.api.db.{DbGrpc, ReadRequest, WriteRequest}


class CassandraBackendSpec extends FlatSpec
  with Matchers
  with CassandraBackendGrpcServerFixture
  with CassandraFixture
{

  "A read call" should "read from Cassandra" in {
    val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
      .usePlaintext
      .build
    val client = DbGrpc.blockingStub(channel)
    val response = client.read(new ReadRequest("myKey01"))
    assert(response.data == "myValue01")
  }

  "A write call" should "write to Cassandra" in {
    val key = "abc123"
    val data = "xyz789"

    // Write the data via the gRPC server.
    val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
      .usePlaintext
      .build
    val client = DbGrpc.blockingStub(channel)
    client.write(new WriteRequest(key, data))

    // Read it back via the gRPC server.
    val response = client.read(new ReadRequest(key))
    assert(key == response.key)
  }
}
