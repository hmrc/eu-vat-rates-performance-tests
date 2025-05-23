import sbt._

object Dependencies {

  private val gatlingVersion = "3.6.1"

  val test = Seq(
    "com.typesafe"          % "config"                    % "1.4.3"         % Test exclude ("org.playframework", "akka-actor"),
    "uk.gov.hmrc"          %% "performance-test-runner"   % "6.1.0"         % Test,
    "org.playframework"    %% "play-ahc-ws-standalone"    % "3.0.7"         % Test,
    "org.playframework"    %% "play-ws-standalone-json"   % "3.0.7"         % Test,
    "org.slf4j"             % "slf4j-simple"              % "2.0.17"        % Test
  )

}
