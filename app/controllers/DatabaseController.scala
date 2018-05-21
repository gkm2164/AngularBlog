package controllers

import javax.inject.{Inject, Singleton}
import models.repositories.{CategoryRepository, CommentRepository, PostRepository}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class DatabaseController @Inject()(protected val categoryRepository: CategoryRepository,
                                   protected val postRepository: PostRepository,
                                   protected val commentRepository: CommentRepository,
                                   cc: ControllerComponents)(implicit val ec: ExecutionContext) extends AbstractController(cc) {

  def index = Action.async {
    for {
      _ <- commentRepository.dropSchema
      _ <- postRepository.dropSchema
      _ <- categoryRepository.dropSchema
      _ <- categoryRepository.createSchema
      _ <- postRepository.createSchema
      _ <- commentRepository.createSchema
    } yield Ok("Finished to create schema")
  }
}
