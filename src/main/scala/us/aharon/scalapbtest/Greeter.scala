package us.aharon.scalapbtest

import us.aharon.scalapbtest.api.hello.{GreeterGrpc, HelloReply, HelloRequest}

import scala.concurrent.Future


class Greeter extends GreeterGrpc.Greeter {

  override def sayHello(request: HelloRequest): Future[HelloReply] = {
    val reply = HelloReply(message = s"Hello, ${request.name}!")
    Future.successful(reply)
  }
}
