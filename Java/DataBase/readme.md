# Базы данных в Java

Для работы с конкретной СУБД требуется специальный JDBC (Java DataBase Connectivity) драйвер, реализующий программный интерфейс согласно стандарту JDBC.
Иначе возникает ошибка времени выполнения, например `java.sql.SQLException: No suitable driver found for jdbc:sqlite:news.db`.

Драйверы можно скачать на сайтах СУБД. Например sqlite https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

```java
import java.sql.*;
```

Пример работы с SQLite
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
// ещё нужен JDИВ драйвер для SQLite: IntelliJ IDEA: project structure > Modules > search jdbc sqlite > select xerial/sqlite-jdbc

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

Запросы
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


# Ссылки
- шпарагалка по SQL: 
