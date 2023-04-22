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
```scala
@main
def main() = 
	println( my_math.sum(2,2) )
```

**`my_lib.scala`**
```scala
package my_math

def sum(x: Int, y:Int) = 
	x + y

def sigmoid(x:Double) =
	1 / (1 + Math.exp( - x))
```

**`test.scala`**
```scala
import my_math._

// Класс для тестов
class TEST extends AnyFunSuite {

	// тестовая функция
	test("TEST.sum"){
		assert( sum(2,2) == 4)
	}

	test("TEST.sigmoid"){
		assert( sigmoid(10000) == 1.0)
	}
}
```


**Запуск тестов**
```bash
sbt test
```

Вывод
```
[info] TEST:                                                                                                                                                             
[info] - TEST.sum                                                                                                                                                        
[info] - TEST.sigmoid                                                                                                                                                    
[info] Run completed in 656 milliseconds.                                                                                                                                
[info] Total number of tests run: 2                                                                                                                                      
[info] Suites: completed 1, aborted 0                                                                                                                                    
[info] Tests: succeeded 2, failed 0, canceled 0, ignored 0, pending 0                                                                                                    
[info] All tests passed.  
```

