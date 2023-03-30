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


Connection conn = null;
try {
    // строка соединения, с указанием вида JDBC драйвера
    // Добавлен JDBC драйвер для SQLite: project structure > Modules > search jdbc sqlite > select xerial/sqlite-jdbc
    String url = "jdbc:sqlite:news.db";
    
    // если файл БД не существует, то при создании соединения он будет создан
    conn = DriverManager.getConnection(url);        //  throws SQLException
        
    String sql = "CREATE TABLE IF NOT EXISTS News (\n"
                    + "	id UUID PRIMARY KEY,\n"
                    + "	title text NOT NULL,\n"
                    + "	date text\n"
                    + "	note text\n"
                    + ");";
    
    // объект для выполнения запросов
    Statement st = conn.createStatement();
    st.execute( sql );
    
} catch (SQLException e) {
    throw new RuntimeException(e);  }
finally {
    if ( conn!=null )   conn.close();           //  throws SQLException
}
```