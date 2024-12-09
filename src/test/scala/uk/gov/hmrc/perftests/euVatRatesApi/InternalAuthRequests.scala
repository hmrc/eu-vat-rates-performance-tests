/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.euVatRatesApi

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.{Materializer, SystemMaterializer}
import io.gatling.http.HeaderNames
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import play.api.libs.ws.JsonBodyWritables._
import play.api.libs.ws.StandaloneWSRequest
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import uk.gov.hmrc.performance.conf.ServicesConfiguration

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object InternalAuthRequests extends ServicesConfiguration {

  private val logger                               = LoggerFactory.getLogger(classOf[InternalAuthRequests.type])
  private implicit val system: ActorSystem         = ActorSystem()
  private implicit val materializer: Materializer  = SystemMaterializer(system).materializer
  private lazy val wsClient: StandaloneAhcWSClient = StandaloneAhcWSClient()

  private val baseUrl: String  = baseUrlFor("internal-auth")
  private val tokenUrl: String = "/test-only/token"

  private val token = sys.env("INTERNAL_AUTH_TOKEN")

  private val requestBody = Json.obj(
    "token"       -> token,
    "principal"   -> "test",
    "permissions" -> Seq(
      Json.obj(
        "resourceType"     -> "eu-vat-rates",
        "resourceLocation" -> "*",
        "actions"          -> List("*")
      )
    )
  )

  def ensureToken(): Any =
    Await.result(
      checkToken.map(response =>
        if (response.status == 200) {
          println("Token is valid")
        } else {
          println("Token not valid, creating")
          createToken
        }
      ),
      5.seconds
    )

  private def checkToken: Future[StandaloneWSRequest#Self#Response] =
    wsClient
      .url(s"$baseUrl$tokenUrl")
      .withHttpHeaders(HeaderNames.Authorization.toString -> token)
      .get()

  private def createToken: Future[StandaloneWSRequest#Self#Response] =
    wsClient
      .url(s"$baseUrl$tokenUrl")
      .withHttpHeaders(HeaderNames.Authorization.toString -> token)
      .post(requestBody)

}
