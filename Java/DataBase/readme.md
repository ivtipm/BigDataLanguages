# Базы данных в Java
JDBC - API для работы с СУБД (реляционным, noSQL и др). Последняя версия 4.3 (на апрель 2023).
Для работы с конкретной СУБД требуется специальный JDBC (Java DataBase Connectivity) драйвер, реализующий программный интерфейс согласно стандарту JDBC.
Иначе возникает ошибка времени выполнения, например `java.sql.SQLException: No suitable driver found for jdbc:sqlite:news.db`.
Такие модули (драйверы) не входят в JDK.

Драйверы можно скачать на сайтах СУБД. Например sqlite https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

<img src=https://cdn.javarush.com/images/article/8098e9c9-e40c-4aad-b7c5-93987199aa61/512.webp width=400></img>

Драйверы - это пакеты java, упакованные в jar файл.
Путь к этим файлам нужно указывать при компиляции с помощью ключа classpath, который используется и для указания путей к другим зависимостям
`java -classpath путь_к_файлу_драйвера; путь_к_классу_программы  главный_класс_программы`

Основные классы и интерфесы:
- class DriverManager отвечает за создание соединения с  СУБД.
- interface Connection - создание запросов и управление созданным соединением.
  - `Statement createStatement()` — cоздает объект Statement, который используется для выполнения SQL-запросов к базе данных.
  - `String getSchema()` — возвращает имя текущей схемы базы данных.
  - `PreparedStatement prepareStatement(String sql)` — Создает объект PreparedStatement, который предварительно компилирует SQL-запрос и позволяет многократно выполнять его с разными параметрами.
  - `close` — закрывает соединение с базой данных.
- `Statement`, `PreparedStatement` - запрос, запрос с подстановкой параметров. [doc](https://docs.oracle.com/en/java/javase/20/docs/api/java.sql/java/sql/Statement.html#executeQuery(java.lang.String))
  - `boolean execute(String sql)`
  - `int executeUpdate(String sql)` выполняет INSERT, UPDATE, DELETE или другую операция, котора ничего не возвращает; метод возвращает количество записей, которые были изменены.
  - `ResultSet executeQuery(String sql)` выполняет запрос, который возвращает результат
- interface ResultSet - даёт доступ к результатам выполнения запроса, имеет последовательный интерфейс.
- SQLException

Компиляция запроса — предварительный анализ и преобразование во внутренний формат, который понимает СУБД.


**Statement vs PreparedStatement**
- `Statement`:
  - Statement используется для выполнения статических SQL-запросов.
  - При каждом выполнении запроса, он полностью компилируется заново.
  - Не поддерживает параметризованные запросы (т.е. запросы с переменными значениями).
  - Может быть подвержен SQL-инъекциям, так как значения не экранируются автоматически.

- `PreparedStatement`:
  - PreparedStatement предназначен для выполнения динамических SQL-запросов.
  - Запрос предварительно компилируется при создании объекта PreparedStatement.
  - Поддерживает параметризованные запросы, что позволяет передавать параметры безопасным образом.
  - Защищает от SQL-инъекций, так как значения параметров экранируются автоматически.


```java
import java.sql.*;
```

Пример работы с SQLite
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
// ещё нужен JDBC драйвер для SQLite: IntelliJ IDEA: project structure > Modules > search jdbc sqlite > select xerial/sqlite-jdbc

import java.util.Random;
```

Подключение, создание таблиц
```java
// строка для соединения, где указан вид драйвера (sqlite) и (в данном случае) имя файла
String DB_URL = "jdbc:sqlite:news.db";
// объект для взаимодействия с СУБД
Connection conn = null;

try {
  // если файл БД sqlite не существует, то при создании соединения он будет создан
  conn = DriverManager.getConnection(DB_URL);        //  throws SQLException

  // для создания таблицы
  String sql = """
  create table if not exists News (
  id integer primary key autoincrement
  ,title text not null
  ,date text
  ,note text
  );""";

  // объект для выполнения запросов
  Statement st = conn.createStatement();
  st.execute( sql );

} catch (SQLException e) {
    throw new RuntimeException(e);  }
finally {
    //            if ( conn!=null )   conn.close();           //  throws SQLException
}

```
Примеры JDBС connection URL (connection string):
- SQLIte `jdbc:sqlite:MY_DBFILE_NAME`
- MySQL `jdbc:mysql://HOST/DATABASE`, класс com.mysql.jdbc.Driver
  - `DriverManager.getConnection(url, "username", "password");`
- Postgresql `jdbc:postgresql://HOST/DATABASE`, класс org.postgresql.Driver
  - `DriverManager.getConnection(url,"username", "password");`
- Microsoft SQL Server `jdbc:microsoft:sqlserver://HOST:1433;DatabaseName=DATABASE`,  класс com.microsoft.jdbc.sqlserver.SQLServerDriver
- DB2 	`jdbc:as400://HOST/DATABASE`, класс com.ibm.as400.access.AS400JDBCDriver


Выполнение запросов на добавление
```java
String[] words = {"Breaking News", "meteorite", "seen", "today", "Bear", "Chita", "in", "near", "nothing happened"};
Random rnd = new Random();

Statement insert = conn.createStatement();

for (int i = 0; i < 1000; i++) {
    String title = words[rnd.nextInt(words.length)] +" " + words[rnd.nextInt(words.length)] + " " + words[rnd.nextInt(words.length)];

    insert.execute( "insert into News (title) values ('%s');".formatted( title ) );
}
```

Запрос SELECT
```java
Statement select = conn.createStatement();

// для select используется метод executeQuery
ResultSet result = select.executeQuery( "select * from News;");

// чтение результата по запясям, разбор записи на поля
while ( result.next() ){
      // далее обращение к result - это обращение к одной записи

      // обращение к полю текущей записи по имени поля
      String title = result.getString("title");
//            String id = result.getString("id");     // в БД id - это integer, но можно преобразовать и в строку
      int id = result.getInt("id");

      System.out.printf("%6d %s\n", id, title);
}

conn.close();
```

Запрос UPDATE
```java

```
# Пример работы с SQL БД

```sql
-- создание БД
CREATE DATABASE store;

-- создание таблицы
CREATE TABLE products (Id INT PRIMARY KEY AUTO_INCREMENT, ProductName VARCHAR(20), Price INT);

-- вставка данных в таблицу
INSERT Products(ProductName, Price) VALUES ('iPhone X', 76000), ('Galaxy S9', 45000), ('Nokia 9', 36000);

-- обновление записи в таблице
UPDATE Products SET Price = Price - 5000 WHERE Price > 10000;

-- Удаление записи из таблицы
DELETE FROM Products WHERE Id = 3
```

# Перечень СУБД (Database Management Systems, DBMS)
![](dbms_rating.png)\
Источник: https://insights.stackoverflow.com/survey/2021#section-most-popular-technologies-databases



# См. также
- ORM фреимворки

# Ссылки
- шпаргалка по SQL: https://www.sqltutorial.org/wp-content/uploads/2016/04/SQL-cheat-sheet.pdf, https://learnsql.com/blog/sql-basics-cheat-sheet/sql-basics-cheat-sheet-a4.pdf
**SQLite**
- Интерактивная демка SQLite: https://sqlite.org/fiddle/index.html
- Шпаргалка: https://vhernando.github.io/sqlite3-cheat-sheet
