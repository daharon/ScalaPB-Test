package us.aharon.scalapbtest.fixtures

import io.grpc.{Server, ServerBuilder}
import org.scalatest.{BeforeAndAfterEach, Suite}

import us.aharon.scalapbtest.CassandraBackend
import us.aharon.scalapbtest.api.db.DbGrpc

import scala.concurrent.ExecutionContext


trait CassandraBackendGrpcServerFixture extends BeforeAndAfterEach { this: Suite =>

  private var server: Server = _

  override def beforeEach(): Unit = {
    server = ServerBuilder.forPort(8080)
      .addService(DbGrpc.bindService(new CassandraBackend, ExecutionContext.global))
      .build()
      .start()
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    server.shutdown()
    super.afterEach()
  }
}
