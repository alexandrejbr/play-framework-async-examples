package models

import play.api.libs.json.Json

/**
  * Created by alexandrejbr on 11/05/16.
  */
case class Film (title: String, director: String)

object Film {
  implicit val format = Json.format[Film]
}
