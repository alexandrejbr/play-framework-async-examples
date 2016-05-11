package models

import play.api.libs.json.Json

/**
  * Created by alexandrejbr on 11/05/16.
  */
case class Person(name: String, gender: String)

object Person {
  implicit val format = Json.format[Person]
}