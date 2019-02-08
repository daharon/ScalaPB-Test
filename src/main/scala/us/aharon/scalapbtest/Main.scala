package us.aharon.scalapbtest

import java.util.logging.Logger

import io.grpc.ServerBuilder
import us.aharon.scalapbtest.api.hello.GreeterGrpc

import scala.concurrent.ExecutionContext


object Main extends App {

  final val PORT = 8080
  val log = Logger.getLogger("Main")
  val server = ServerBuilder.forPort(8080)
    .addService(GreeterGrpc.bindService(new SayHello, ExecutionContext.global))
    .build
    .start
  log.info(s"Server listening on port $PORT")
  sys.addShutdownHook {
    System.err.println("Shutting down server...")
    server.shutdown
  }
  server.awaitTermination()
}
