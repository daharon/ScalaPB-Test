name := "scalapb-test"

version := "0.2.0"

scalaVersion := "2.12.8"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies ++= Seq(
  // ScalaPB
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",

  // gRPC
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,

  // Database
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",

  // Test
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.cassandraunit" % "cassandra-unit" % "3.5.0.1" % "test",
)

