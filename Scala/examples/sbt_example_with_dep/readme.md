# Пример сборки scala проекта с зависимостями

Сборщик полагается на заранее созданные файлы:
- `build.sbt`
	- Пример содержимого: `scalaVersion := "3.2.2"`
- `project\bildp.roperties`
	- Пример содержимого: `sbt.version=1.6.1`

Запуск сборщика в корневой папаке проекта, файл сборки будет найден автоматически
```bash
sbt compile
```

Зависимости описаны в файле сборки проекта:
```
// зависимости
// sbt скачивает их сам
libraryDependencies ++= Seq(
	// libraryDependencies += groupID % artifactID % revision
	"org.xerial" % "sqlite-jdbc" % "3.20.0"
	// JDBC драйвер для SQLite
)
```

Проект будет скомпилирован в папку `target`.


Например, запуск jar файла проекта: `scala target/scala-3.2.2/sbt_example_3-0.1.0-SNAPSHOT.jar`