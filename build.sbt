name := "AngularBlog"
 
version := "1.0" 
      
lazy val `angularblog` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies += "com.typesafe.play" %% "play-json-joda" % "2.6.9"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  


