package controllers

import javax.inject._
import models.repositories.CategoryRepository
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CategoryController @Inject()(protected val categoryRepository: CategoryRepository,
                                   cc: ControllerComponents)(implicit val ec: ExecutionContext) extends AbstractController(cc) {

  import models.entities.AngularBlogJsonWriters._

  def index = Action.async {
    categoryRepository.findAll.map(items => Json.toJson(items)).map(Ok(_))
  }

}
