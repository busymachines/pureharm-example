/*
 * Copyright 2021 BusyMachines
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

Global / organization     := "com.busymachines"
Global / organizationName := "BusyMachines"
Global / homepage         := Option(url("https://www.busymachines.com/"))
Global / startYear        := Some(2021)
Global / licenses         := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

//=============================================================================
//=============================================================================
//=============================================================================
// format: off

val mainScalaV           = "2.13.5"         //https://github.com/scala/scala/releases
val betterM4V            = "0.3.1"          //         
val kindProjectorV       = "0.11.3"         //

val pureharmCoreV        = "0.1.0"          //https://github.com/busymachines/pureharm-core/releases
val pureharmEffectsV     = "0.1.0"          //https://github.com/busymachines/pureharm-effects-cats/releases
val pureharmTestkitV     = "0.1.0"          //https://github.com/busymachines/pureharm-testkit/releases
val pureharmConfigV      = "0.1.0"          //https://github.com/busymachines/pureharm-config/releases
val pureharmJsonV        = "0.1.1"          //https://github.com/busymachines/pureharm-json-circe/releases
val pureharmDBCoreV      = "0.1.0"          //https://github.com/busymachines/pureharm-db-core/releases
val pureharmDBFlywayV    = "0.1.0"          //https://github.com/busymachines/pureharm-db-flyway/releases
val pureharmDBDoobieV    = "0.1.0"          //https://github.com/busymachines/pureharm-db-flyway/releases
val pureharmRestV        = "0.1.0"          //https://github.com/busymachines/pureharm-rest/releases
val pureharmAWSV         = "0.1.0"          //https://github.com/busymachines/pureharm-aws/releases
val ip4sV                = "2.0.0-RC1"      //https://github.com/Comcast/ip4s/releases 
val cirisV               = "1.2.1"          //https://github.com/vlovgr/ciris/releases
val http4sV              = "0.21.20"        //https://github.com/http4s/http4s/releases
val log4catsV            = "1.2.0"          //https://github.com/typelevel/log4cats/releases
val logbackV             = "1.2.3"          //java - https://github.com/qos-ch/logback/releases

//=============================================================================

val pureharmCore      = "com.busymachines"   %% "pureharm-core"          % pureharmCoreV     withSources()
val pureharmEffects   = "com.busymachines"   %% "pureharm-effects-cats"  % pureharmEffectsV  withSources()
val ip4s              = "com.comcast"        %% "ip4s-core"              % ip4sV             withSources()
val ciris             = "is.cir"             %% "ciris"                  % cirisV            withSources()
val log4cats          = "org.typelevel"      %% "log4cats-slf4j"         % log4catsV         withSources()
val emberServer       = "org.http4s"         %% "http4s-ember-server"    % http4sV           withSources()

val logback           = "ch.qos.logback"     %  "logback-classic"        % logbackV          withSources()

// format: on
//=============================================================================
//=============================================================================
//=============================================================================

lazy val root = Project(id = "pureharm-example", base = file("."))
  .settings(commonSettings)
  .aggregate(
    `phe-util-core`,
    `phe-util-config`,
    `phe-util-logging`,

    `phe-app-example`,
  )

//=============================================================================
//=================================== APPS ====================================
//=============================================================================

lazy val `phe-app-example` = appProject("example")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      emberServer,
    )
  )
  .dependsOn(
    `phe-util-core`,
    `phe-util-config`,
    `phe-util-logging`
  )

//=============================================================================
//=================================== APIS ====================================
//=============================================================================  

//=============================================================================
//================================ ORGANIZERS =================================
//=============================================================================  

//=============================================================================
//================================= ALGEBRAS ==================================
//=============================================================================

//=============================================================================
//================================== PORTS ====================================
//=============================================================================

//=============================================================================
//================================== TROVES ===================================
//=============================================================================

//=============================================================================
//================================== UTILS ====================================
//=============================================================================

lazy val `phe-util-logging` = utilProject("logging")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      log4cats,
      logback,
    )
  ).dependsOn(
    `phe-util-core`
  )

lazy val `phe-util-config` = utilProject("config")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      ciris,
      ip4s,
    )
  ).dependsOn(
    `phe-util-core`
  )

lazy val `phe-util-core` = utilProject("core")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      pureharmCore,
      pureharmEffects
    )
  )

//=============================================================================
//=============================================================================
//=============================================================================

def commonSettings: Seq[Setting[_]] = Seq(
    scalaVersion := mainScalaV,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % betterM4V),
    addCompilerPlugin(("org.typelevel" %% "kind-projector" % kindProjectorV).cross(CrossVersion.full)),
    scalacOptions ++= scala_2_13_Flags ++ betterForPluginCompilerFlags,

    /** Required if we want to use munit as our testing framework.
      * https://scalameta.org/munit/docs/getting-started.html
      */
    testFrameworks += new TestFramework("munit.Framework"),
  )

def betterForPluginCompilerFlags: Seq[String] = Seq(
    "-P:bm4:no-filtering:y",      //see https://github.com/oleg-py/better-monadic-for#desugaring-for-patterns-without-withfilters--pbm4no-filteringy
    "-P:bm4:no-map-id:y",         //see https://github.com/oleg-py/better-monadic-for#final-map-optimization--pbm4no-map-idy
    "-P:bm4:no-tupling:y",        //see https://github.com/oleg-py/better-monadic-for#desugar-bindings-as-vals-instead-of-tuples--pbm4no-tuplingy
    "-P:bm4:implicit-patterns:y", //see https://github.com/oleg-py/better-monadic-for#define-implicits-in-for-comprehensions-or-matches
  )

 def scala_2_13_Flags: Seq[String] = betterForPluginCompilerFlags ++ Seq(
    //"-Xfatal-warnings",            // Fail the compilation if there are any warnings.
    "-deprecation",                  // Emit warning and location for usages of deprecated APIs.
    "-encoding",                     // yeah, it's part of the "utf-8" thing, two flags
    "utf-8",                         // Specify character encoding used by source files.
    "-explaintypes",                 // Explain type errors in more detail.
    "-feature",                      // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",        // Existential types (besides wildcard types) can be written and inferred
    "-language:higherKinds",         // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-unchecked",                    // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                   // Wrap field accessors to throw an exception on uninitialized access.
    "-Xlint:adapted-args",           // Warn if an argument list is modified to match the receiver.
    "-Xlint:constant",               // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",     // Selecting member of DelayedInit.
    "-Xlint:doc-detached",           // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",           // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",              // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",   // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-unit",           // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",        // Option.apply used implicit view.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",         // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",            // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",  // A local type parameter shadows a type already in scope.
    "-Wdead-code",                   // Warn when we have dead code
    "-Ywarn-extra-implicit",         // Warn when more than one implicit parameter section is defined.
    "-Ywarn-numeric-widen",          // Warn when numerics are widened.
    "-Ywarn-unused:implicits",       // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",         // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",          // Warn if a local definition is unused.
    "-Wunused:params",               // Warn if a value parameter is unused.
    "-Wunused:synthetics",           // Warn if context boud is not used
    "-Ywarn-unused:patvars",         // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",        // Warn if a private member is unused.
    "-Ywarn-value-discard",          // Warn when non-Unit expression results are unused.
    "-Wconf:any:warning-verbose",    // Gives extra information about warning,
    "-Ytasty-reader",                // Allows depending on libraries written in Scala 3
  )

//=============================================================================
//=============================================================================
//=============================================================================

// format: off
def utilProject      (name: String): Project = Project(s"phe-util-$name", file(s"modules/utils/$name"))
def troveProject     (name: String): Project = Project(s"phe-trove-$name", file(s"modules/troves/$name"))
def portProject      (name: String): Project = Project(s"phe-port-$name", file(s"modules/ports/$name"))
def algebraProject   (name: String): Project = Project(s"phe-algebra-$name", file(s"modules/algebras/$name"))
def organizerProject (name: String): Project = Project(s"phe-organizer-$name", file(s"modules/organizers/$name"))
def apiProject       (name: String): Project = Project(s"phe-api-$name", file(s"modules/apis/$name"))
def appProject       (name: String): Project = Project(s"phe-app-$name", file(s"modules/apps/$name"))
// format: on

/** See SBT docs:
  * https://www.scala-sbt.org/release/docs/Multi-Project.html#Per-configuration+classpath+dependencies
  *
  * or an example:
  * {{{
  * val testModule = project
  *
  * val prodModule = project
  *   .dependsOn(asTestingLibrary(testModule))
  * }}}
  * To ensure that testing code and dependencies
  * do not wind up in the "compile" (i.e.) prod part of your
  * application.
  */
def asTestingLibrary(p: Project): ClasspathDependency = p % "test -> compile"

