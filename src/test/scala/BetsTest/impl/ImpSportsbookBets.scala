package BetsTest.impl

import java.util.logging.Logger

import Utils.Logging
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

/**
  * Created by dgollapudi on 09/03/2018.
  */
object ImpSportsbookBets {
  val sbUrl = System.getProperty(sbUrl)

  def generateDataCustRegSet(session: Session) = {
    session
      .set("reg_password", "tester55")
  }

  def generateDataCustRegGet = exec(session => {
    generateDataCustRegSet(session)
  })
    .exec(session => {
      session.get("reg_password") session
    })


  def sbOpenHomePage = {
    exec(http("SB:Open: Home Page")
      .get(sbUrl)
      .check(status.is(200))
      .check(headerRegex("Set-Cookie", "cust_lang=(.*);").saveAs("custLang"))
      .check(css("input[name='login_uid']", "value").find.saveAs("logInUid"))
      .check(css("#productSelected", "class").is("sports")))
      .exec(session => {
        Logging.info(this.getClass(), "Session Attribute: SB Open - Home Page: custLang: " + session.get("custLang").as[String])
        Logging.info(this.getClass(), "Session Attribute: SB Open - Home Page: logInUid: " + session.get("logInUid").as[String])
        session
      })
  }

  def sbHomePage = {
    exec(http("SB: Select: Home Page")
      .get(sbUrl)
      .check(status.is(200))
      .check(css("#productSelected", "class").is("sports")))
  }



  def sbLogIn = {
    var headers_logIn = Map("Origin" -> sbUrl,"Upgrade-Inseure-Requests" -> "1")
    exec(http("SB: Log In: Home Page")
      .post(sbUrl)
      .disableFollowRedirect
      .headers(headers_logIn)
      .formParam("ioBlackBoxCookie","")
      .formParam("password","Tester55")
      .formParam("username","${username}")
      .formParam("tmp_username","Username")
      .formParam("remember_me","1")
      .formParam("login_uid","${logInUid}")
      .formParam("action","DoLogin")
      .formParam("target_page",sbUrl)
      .formParam("remember_me_value","1")
      .check(status.is(301))
      .check(headerRegex("Set-Cookie","cust_auth=(.*); path=/").saveAs("custAuth"))
      .check(headerRegex("Set-Cookie","cust_ssl_login=(.*); path=/").saveAs("custSslLogIn"))
    ).exec(http("SB: Log In: Home Page Redirect 1")
      .post(sbUrl)
      .headers(headers_logIn)
      .check(status.is(200))
      .check(css(".mainBalance").exists)
      .check(headerRegex("Set-Cookie","cust_login=(.*); path=/").saveAs("custLogIn")))
      .exec(session => {
        Logging.info(this.getClass(),"Session Attribute: SB Log In - Home Page: username: "+session.get("username").as[String])
        Logging.info(this.getClass(),"Session Attribute: SB Log In - Home Page: logInUid: "+session.get("logInUid").as[String])
        Logging.info(this.getClass(),"Session Attribute: SB Log In - Home Page: custAuth: "+session.get("custAuth").as[String])
        Logging.info(this.getClass(),"Session Attribute: SB Log In - Home Page: custSslLogIn: "+session.get("custSslLogIn").as[String])
        Logging.info(this.getClass(),"Session Attribute: SB Log In - Home Page: custLogIn: "+session.get("custLogIn").as[String])
        session})
  }
}