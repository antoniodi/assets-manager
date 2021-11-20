import scalariform.formatter.preferences.{AlignSingleLineCaseStatements, DanglingCloseParenthesis, DoubleIndentConstructorArguments, Preserve, SpaceInsideParentheses, SpacesWithinPatternBinders}

val msName = "ms-assets-manager"

mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

// documentation about the assembly plugin
// https://github.com/sbt/sbt-assembly#merge-strategy
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case x if x.contains("module-info.class")                          => MergeStrategy.rename
  case x if x.contains("scala-collection-compat.properties")         => MergeStrategy.concat
  case x if x.contains("package.class")         => MergeStrategy.concat
    case x if x.contains("package$.class")         => MergeStrategy.first
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

assemblyJarName in assembly := s"$msName.jar"

target in assembly := file("target")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := msName,
    version := "2.8.8",
    scalaVersion := "2.13.4",
//    artifactName := { (_, _, artifact) => s"$msName.${artifact.extension}" },
//    crossTarget := baseDirectory.value / "target",
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
      "org.typelevel"               %% "squants"                  % "1.6.0",
      "dev.zio"                     %% "zio-test-sbt"             % "2.0.0-M2"  % Test,
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
    coverageExcludedPackages := ".*ErrorHandler.*;.*Filters.*;.*Routes.*;.*Reverse.*;.*net.gmc.phoenix.*;.*BuildInfo.*.",
    coverageHighlighting := true,
    scalacOptions ++= Seq("-encoding", "utf8"),
    scalariformPreferences := scalariformPreferences.value
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DoubleIndentConstructorArguments, true)
      .setPreference(SpaceInsideParentheses, true)
      .setPreference(SpacesWithinPatternBinders, true)
      .setPreference(DanglingCloseParenthesis, Preserve)
  )