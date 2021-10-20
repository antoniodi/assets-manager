import scalariform.formatter.preferences.{AlignSingleLineCaseStatements, DanglingCloseParenthesis, DoubleIndentConstructorArguments, Preserve, SpaceInsideParentheses, SpacesWithinPatternBinders}

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """assets-manager""",
    version := "2.8.8",
    scalaVersion := "2.13.4",
    libraryDependencies ++= Seq(
      ws,
      filters,
      guice,
      evolutions,
      "com.typesafe.play"           %% "play-slick"               % "5.0.0",
      "com.typesafe.play"           %% "play-slick-evolutions"    % "5.0.0",
      "org.typelevel"               %% "cats-core"                % "2.2.0",
      "org.typelevel"               %% "cats-kernel"              % "2.2.0",
      "org.typelevel"               %% "cats-macros"              % "2.1.1",
      "io.monix"                    %% "monix"                    % "3.3.0",
      "com.softwaremill.quicklens"  %% "quicklens"                % "1.6.1",
      "org.scalariform"             %% "scalariform"              % "0.2.10",
      "org.postgresql"              % "postgresql"                % "42.2.5",
      "com.h2database"              % "h2"                        % "1.4.199",
      "dev.zio"                     %% "zio"                      % "2.0.0-M2",
      "dev.zio"                     %% "zio-streams"              % "2.0.0-M2",
      "dev.zio"                     %% "zio-test-sbt"             % "2.0.0-M2"    % Test,
      "org.scalatest"               %% "scalatest"                % "3.2.7"     % Test,
      "org.scalamock"               %% "scalamock"                % "5.1.0"     % Test,
      specs2 % Test
    ),
    resolvers ++= Seq(
      "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    ),
    PlayKeys.devSettings := Seq("play.server.http.port" -> "9000"),
    coverageMinimum := 80,
    coverageExcludedPackages := ".*ErrorHandler.*;.*Filters.*;.*Routes.*;.*Reverse.*;.*net.gmc.phoenix.*;.*BuildInfo.*;.*ControladorDeComandos*.",
    coverageHighlighting := true,
    scalacOptions ++= Seq("-encoding", "utf8"),
    scalariformPreferences := scalariformPreferences.value
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DoubleIndentConstructorArguments, true)
      .setPreference(SpaceInsideParentheses, true)
      .setPreference(SpacesWithinPatternBinders, true)
      .setPreference(DanglingCloseParenthesis, Preserve)
  )