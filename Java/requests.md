# Интернеет запросы

Пример простого запроса к сайту http://icanhazip.com/.
Этот сайт выдаёт в виде простого текста внешний IP устройства, с которого на него зашли.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

public class TChannelScraper{

    public void get_public_ip() throws IOException {
        // Объект для хранения URL адреса
        URL url = new URL("http://icanhazip.com/");
        
        // класс для отправки запросов к HTTP серверу
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // Тип запроса - GET (см. также POST)
        con.setRequestMethod("GET");

        // Метаинформация ответа (response)
        System.out.println("Connection Response Message : "+con.getResponseMessage());  // Текстовый статус
        System.out.println("Connection Response Code :    "+con.getResponseCode());     // Код. Если всё ОК, то должен быть 200

        // Получение тела ответа - длительная процедура. Для чтения ответа используется класс BufferedReader,
        // который умеет читать потоковые данные
        // InputStreamReader конвертирует полученные данные в набор символов
        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

        // Строки в Java неизменяемые.
        // Поэтому используем StringBuilder, чтобы склеить набор символов в строку без потери производительности
        StringBuilder sb = new StringBuilder();

        // склеивание набора символов в строку
        String output = br.lines().collect(Collectors.joining());

        System.out.println("Connection Response Body :    "+output);
        con.disconnect();
    }
}   
    ```


Пример вывода программы:
```text
Connection Response Message : OK
Connection Response Code :    200
Connection Response Body :    8.8.8.8
```

При необходимости можно настроить время ожидания соединения и получения данных
```java
con.setConnectTimeout(5000);
con.setReadTimeout(5000);
```

Сервер может блокировать запрос, если он поступает не от браузера. Задание типичных полей запроса, например User-Agent может помочь:
```java
con.addRequestProperty("User-Agent", "Mozilla");
```


# Ссылки
- https://github.com/chubin/wttr.in#usage -- информация о сайте wttr.in выдающем погоду в Plain Text.
- https://habr.com/ru/articles/page2/