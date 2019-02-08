package us.aharon.scalapbtest

import com.datastax.driver.core.Cluster

import us.aharon.scalapbtest.api.db._

import scala.concurrent.Future


class CassandraBackend extends DbGrpc.Db {

  private final val KEYSPACE = "test"
  private val db = Cluster.builder()
    .addContactPoint("localhost")
    .withPort(9142)
    .build
    .connect(KEYSPACE)


  override def write(request: WriteRequest): Future[WriteResponse] = {
    val query = db.prepare("INSERT INTO testTable (id, value) VALUES (:id, :value)")
        .bind
        .setString("id", request.key)
        .setString("value", request.data)
    db.execute(query)
    Future.successful(new WriteResponse)
  }

  override def read(request: ReadRequest): Future[ReadResponse] = {
    val query = db.prepare("SELECT id, value FROM testTable WHERE id = :id LIMIT 1")
      .bind
      .setString("id", request.key)
    val row = db.execute(query).one
    val response = new ReadResponse(
      key = row.getString("id"),
      data = row.getString("value"))
    Future.successful(response)
  }
}
