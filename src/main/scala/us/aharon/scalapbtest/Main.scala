package us.aharon.scalapbtest

import io.grpc.ServerBuilder

import us.aharon.scalapbtest.api.db.DbGrpc
import us.aharon.scalapbtest.api.hello.GreeterGrpc

import scala.concurrent.ExecutionContext
import java.util.logging.Logger


object Main extends App {

  final val PORT = 8080
  val log = Logger.getLogger("Main")

  val server = ServerBuilder.forPort(PORT)
    .addService(GreeterGrpc.bindService(new Greeter, ExecutionContext.global))
    .addService(DbGrpc.bindService(new CassandraBackend, ExecutionContext.global))
    .build
    .start
  log.info(s"Server listening on port $PORT")
  sys.addShutdownHook {
    System.err.println("Shutting down server...")
    server.shutdown
  }
  server.awaitTermination()
}
