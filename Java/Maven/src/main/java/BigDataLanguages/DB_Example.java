package BigDataLanguages;
import java.sql.*;

/// Для примера компиляции проекта Maven из нескольких файлов
public class DB_Example{
	public static void create_db(){
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
	}
}