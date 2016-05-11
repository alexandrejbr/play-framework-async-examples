package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.ws._
import models._
import models.Person._
import models.Film._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(ws: WSClient)(implicit exec: ExecutionContext) extends Controller {

  def index(number: Int) = Action.async {
    val futures =
      for (i <- 1 to number)
        yield
          ws
            .url(s"http://swapi.co/api/people/$i")
            .get()
            .map(resp => resp.json.as[Person])

    Future.sequence(futures).map(characters => Ok(Json.toJson(characters)))
  }

  def films(characterId: Int) = Action.async {
    ws
      .url(s"http://swapi.co/api/people/$characterId")
      .get()
      .flatMap(resp => {
        val futures: Seq[Future[Film]] =
          for (filmUrl <- (resp.json \ "films").as[Array[String]])
            yield
              ws
                .url(filmUrl)
                .get()
                .map(innerResp => innerResp.json.as[Film])
        Future.sequence(futures).map(films => Ok(Json.toJson(films)))
      })
  }
}
