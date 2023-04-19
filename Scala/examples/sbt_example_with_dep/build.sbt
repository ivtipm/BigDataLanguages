scalaVersion := "3.2.2"


// зависимости
// sbt скачивает их сам в общую для всех проектов пользователя папку [https://get-coursier.io/docs/cache]
libraryDependencies ++= Seq(
	// libraryDependencies += groupID % artifactID % revision
	"org.xerial" % "sqlite-jdbc" % "3.40.0.0"
	// JDBC драйвер для SQLite
)

// зависимости в виде локальных файлов (unmanaged dependencies) достаточно скопировать в папку lib
// sbt автоматически подключит их

