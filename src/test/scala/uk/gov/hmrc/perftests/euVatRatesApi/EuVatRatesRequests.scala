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

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object EuVatRatesRequests extends ServicesConfiguration {

  val baseUrl: String     = baseUrlFor("eu-vat-rates")
  val getRatesUrl: String = "/eu-vat-rates/vat-rate"
  val countryCode: String = "AT"
  val startDate: String   = "2023-10-01"
  val endDate: String     = "2023-10-31"

  def getEuVatRates: ChainBuilder =
    exec(
      http("Get EU Vat Rates for country code, start date and end date")
        .get(s"$baseUrl$getRatesUrl/$countryCode?startDate=$startDate&endDate=$endDate")
        .check(status.is(200))
    )

}
