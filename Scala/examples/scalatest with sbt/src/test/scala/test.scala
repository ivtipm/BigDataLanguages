package test

import org.scalatest.funsuite.AnyFunSuite
// добавить в build.sbt
// libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
// libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
// запуск тестов, из корневой папки проекта: sbt test

// включение пространства имён тестируемой библиотеки
// она находится в другой папке, но в рамках стандартной структуры проекта sbt, поэтому подключается
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

/*
Запуск тестов
sbt test

Вывод
[info] TEST:                                                                                                                                                             
[info] - TEST.sum                                                                                                                                                        
[info] Run completed in 627 milliseconds.                                                                                                                                
[info] Total number of tests run: 1                                                                                                                                      
[info] Suites: completed 1, aborted 0                                                                                                                                    
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0                                                                                                    
[info] All tests passed. 
*/