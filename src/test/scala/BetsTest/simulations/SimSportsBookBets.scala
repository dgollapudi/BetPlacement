package BetsTest.simulations

import io.gatling.core.Predef.rampUsers
import io.gatling.http.Predef.http
import test.scala.BetsTest.scenarios.ScnSportsbookBets
import io.gatling.core.Predef._
import io.gatling._
import scala.concurrent.duration._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

/**
  * Created by dgollapudi on 09/03/2018.
  */
class SimSportsBookBets extends Simulation {
  val sbUrl = System.getProperty(sbUrl)

  val VU: Int = System.getProperty("VU", "").toInt
  val lengthOfTime: Int = System.getProperty("lengthOfTime").toInt

  var httpConf = http
    .baseURL(sbUrl)
    .acceptHeader("""text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8""")
    .acceptLanguageHeader("en-GB,en-US;q=0.8,en;q=0.6")
    .acceptEncodingHeader("gzip, deflate, sdch")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36")

  setUp(
    ScnSportsbookBets.scn_sbBrowsing.inject(rampUsers(VU) over (lengthOfTime minutes))
  ).protocols(httpConf)
}
