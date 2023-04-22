# Пример проекта с тестов на основе scala test

Структура проекта
```
.
|- build.sbt  (файл сборки)
|- scr
|--- main (папка с исходниками)
|----- scala
|------- main.scala
|--- test (папка с тестами)
|----- scala
|------- test.scala
```

**`build.sbt`**
```
scalaVersion := "3.2.2"

// зависимости для проекта с тестом
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
```

**`main.scala`** (не используется в тесте)



**`test.scala`**





