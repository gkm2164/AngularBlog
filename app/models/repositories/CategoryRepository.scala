package models.repositories

import javax.inject.{Inject, Singleton}
import models.entities.Category
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit val ec: ExecutionContext) extends SchemaHandler {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class CategoryTableDef(tag: Tag) extends Table[Category](tag, "CATEGORY") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def slug = column[String]("Slug")
    def name = column[String]("Name")
    def parentId = column[Option[Long]]("ParentID")

    def parent =
      foreignKey("Category_CategoryFK", parentId, CATEGORY)(_.id.?, onDelete = ForeignKeyAction.SetNull)

    override def * = (id, slug, name, parentId) <> (Category.tupled, Category.unapply)
  }

  val CATEGORY = TableQuery[CategoryTableDef]

  def findAll: Future[Seq[Category]] = db.run(CATEGORY.result)

  override def createSchema: Future[Unit] = db.run(CATEGORY.schema.create)

  override def dropSchema: Future[Unit] = db.run(CATEGORY.schema.drop)
}
