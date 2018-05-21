package models

import org.joda.time.DateTime

package object entities {
  case class Category(id: Long, name: String, slug: String, parentId: Option[Long])
  case class Post(id: Long, categoryId: Option[Long], title: String, content: String, createdTime: DateTime, updatedTime: DateTime)
  case class Comment(id: Long, postId: Long, parentId: Option[Long], text: String, createdTime: DateTime, updatedTime: DateTime)
  case class AngularBlogOption(rootCategoryId: Long)
}
