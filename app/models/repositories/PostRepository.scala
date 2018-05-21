package models.repositories

import javax.inject.{Inject, Singleton}
import models.entities.{Category, Post}
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostRepository @Inject()(protected val categoryRepository: CategoryRepository, dbConfigProvider: DatabaseConfigProvider)(implicit val ec: ExecutionContext) extends SchemaHandler {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  import categoryRepository._
  import com.github.tototoshi.slick.MySQLJodaSupport._

  // case class Post(id: Long, categoryId: Option[Long], title: String, content: String, createdTime: DateTime, updatedTime: DateTime)
  class PostTableDef(tag: Tag) extends Table[Post](tag, "POST") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def categoryId = column[Option[Long]]("CategoryID")
    def title = column[String]("Title")
    def content = column[String]("Content")
    def createdTime = column[DateTime]("CreatedTime", O.Default(DateTime.now()))
    def updatedTime = column[DateTime]("UpdatedTime", O.Default(DateTime.now()))

    def category = foreignKey("Post_Category_FK", categoryId, CATEGORY)(_.id.?)

    override def * = (id, categoryId, title, content, createdTime, updatedTime) <> (Post.tupled, Post.unapply)
  }

  val POST = TableQuery[PostTableDef]

  override def createSchema: Future[Unit] = db.run(POST.schema.create)

  override def dropSchema: Future[Unit] = db.run(POST.schema.drop)
}
