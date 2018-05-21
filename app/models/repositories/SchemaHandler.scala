package models.repositories

import scala.concurrent.Future

trait SchemaHandler {
  def createSchema: Future[Unit]
  def dropSchema: Future[Unit]
}
