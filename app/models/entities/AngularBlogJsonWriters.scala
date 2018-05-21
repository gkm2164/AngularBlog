package models.entities

import org.joda.time.DateTime
import play.api.libs.json._

object AngularBlogJsonWriters {
  val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

  implicit val jodaFormatter = JodaWrites.jodaDateWrites(pattern)

  implicit val categoryJsonWriter = new Writes[Category] {
    override def writes(o: Category): JsValue = Json.obj(
      "id" -> o.id,
      "name" -> o.name,
      "slug" -> o.slug,
      "parentId" -> o.parentId
    )
  }

  implicit val postJsonWriter = new Writes[Post] {
    override def writes(p: Post): JsValue = Json.obj(
      "id" -> p.id,
      "categoryId" -> p.categoryId,
      "title" -> p.title,
      "content" -> p.content,
      "createdTime" -> p.createdTime,
      "updatedTime" -> p.updatedTime
    )
  }

  //(id: Long, postId: Long, parentId: Option[Long], text: String, createdTime: DateTime, updatedTime: DateTime)
  implicit val commentJsonWriter = new Writes[Comment] {
    override def writes(o: Comment): JsValue = Json.obj(
      "id" -> o.id,
      "postId" -> o.postId,
      "parentId" -> o.parentId,
      "text" -> o.text,
      "createdTime" -> o.createdTime,
      "updatedTime" -> o.updatedTime
    )
  }
}
