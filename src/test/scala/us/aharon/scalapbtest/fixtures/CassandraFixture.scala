package us.aharon.scalapbtest.fixtures

import org.cassandraunit.CQLDataLoader
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet
import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.scalatest.{BeforeAndAfterEach, Suite}


trait CassandraFixture extends BeforeAndAfterEach { this: Suite =>

  override def beforeEach(): Unit = {
    EmbeddedCassandraServerHelper.startEmbeddedCassandra()
    val dataloader = new CQLDataLoader(EmbeddedCassandraServerHelper.getSession)
    dataloader.load(new ClassPathCQLDataSet("dataset.cql", "test"))
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra()
    super.afterEach()
  }
}
