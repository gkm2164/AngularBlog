package models.repositories

import javax.inject.{Inject, Singleton}
import models.entities.Comment
import org.joda.time.DateTime
import play.api.db.DatabaseConfig
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentRepository @Inject()(protected val postRepository: PostRepository,
                                  dbConfigProvider: DatabaseConfigProvider)(implicit val ec: ExecutionContext) extends SchemaHandler {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import postRepository._

  import com.github.tototoshi.slick.MySQLJodaSupport._

  //case class Comment(id: Long, postId: Long, parentId: Option[Long], text: String, createdTime: DateTime, updatedTime: DateTime)
  class CommentTableDef(tag: Tag) extends Table[Comment](tag, "COMMENT") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def postId = column[Long]("PostID")
    def parentId = column[Option[Long]]("ParentID")
    def text = column[String]("Text")
    def createdTime = column[DateTime]("CreatedTime", O.Default(DateTime.now()))
    def updatedTime = column[DateTime]("UpdatedTime", O.Default(DateTime.now()))

    def post = foreignKey("COMMENT_POST_FK", postId, POST)(_.id)
    def parent = foreignKey("COMMENT_COMMENT_FK", parentId, COMMENT)(_.id.?)

    override def * = (id, postId, parentId, text, createdTime, updatedTime) <> (Comment.tupled, Comment.unapply)
  }

  val COMMENT = TableQuery[CommentTableDef]

  override def createSchema: Future[Unit] = db.run(COMMENT.schema.create)

  override def dropSchema: Future[Unit] = db.run(COMMENT.schema.drop)
}
