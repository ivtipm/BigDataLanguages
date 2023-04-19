scalaVersion := "3.2.2"

// зависимости
// sbt скачивает их сам
libraryDependencies ++= Seq(
	// libraryDependencies += groupID % artifactID % revision
	"org.xerial" % "sqlite-jdbc" % "3.20.0"
	// JDBC драйвер для SQLite
)