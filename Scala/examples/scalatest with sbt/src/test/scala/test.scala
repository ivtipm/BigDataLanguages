package test


import org.scalatest.funsuite.AnyFunSuite
// добавить в build.sbt
// libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
// libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
// запуск тестов, из корневой папки проекта: sbt test

import process_log._

class TEST extends AnyFunSuite {

	test("TEST.get_uniq_items_ips"){
		val TEST_FILE = "test.log"
		val ip_reg = raw"[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}".r

		// подсчёт уникальных IP
		val ip_list = get_uniq_items_ips(TEST_FILE, ip_reg)
		assert( ip_list.size == 7 )
	}
}