package test.scala.BetsTest.scenarios

import BetsTest.impl.ImpSportsbookBets
import io.gatling.core.Predef._
import io.gatling.core.scenario._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by dgollapudi on 09/03/2018.
  */
object ScnSportsbookBets extends Simulation {
  val env = System.getProperty("env")
  val userfeeder = csv(env + "/" + "Users.csv")

  val scn_sbBrowsing = scenario("SB: Browsing")
    .exec(ImpSportsbookBets.sbOpenHomePage, pause(1, 3))
    .exec(ImpSportsbookBets.sbHomePage, pause(1, 3))

  var scn_betPlacement_Direct = scenario("bet placement - direct")
    .feed(userfeeder)
   // .feed(events.random.circular)
    .exec(
    ImpSportsbookBets.sbOpenHomePage, pause(1, 3)
      ,ImpSportsbookBets.sbHomePage, pause(1, 3)
      ,ImpSportsbookBets.sbLogIn,pause(1, 3)
    //,ImpSportsbookBets.navigateToFootball, pause(1, 3)
    )




}
