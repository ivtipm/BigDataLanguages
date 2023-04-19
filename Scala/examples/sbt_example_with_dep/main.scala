// подключение java зависимости 
// файл JDBC драйвера скачивается сборщиком, см. build.sbt
import java.sql.*


@main
def main() = 

	val con_url = "jdbc:sqlite:./database.sqlite"

	// подключение к файлу БД, файл будет создан если не существует
	val conn = DriverManager.getConnection(con_url)
	
	print("DONE")

