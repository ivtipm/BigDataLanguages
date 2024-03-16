# Интернеет запросы. HTTPUrlConnection, URL

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

При необходимости можно настроить время ожидания соединения и получения данных - таймауты:
```java
con.setConnectTimeout(5000);
con.setReadTimeout(5000);
// время задаётся в миллисекундах
```

Сервер может блокировать запрос, если он поступает не от браузера. Задание типичных полей запроса, например User-Agent может помочь:
```java
con.addRequestProperty("User-Agent", "Mozilla");
```

См. также класс `java.net.URLConnection` - абстрактный базовый класс, который предоставляет интерфейс для работы с URL-адресами. Он может быть использован для работы с любым типом URL: HTTP, FTP и др.



Чтобы не создавать сильной нагруки на сервер и избежать блокировки стоит делать паузы между запросами. Задежка выполнения (текущего потока)
```java
    // import java.lang.Thread; подключается автоматически
    Thread.sleep(5_000);       // ожидание 5000 миллисекунд  
    // Бросает исключение InterruptedException, если поток был прерван
```

**Скачивание бинарного файла**
```java
String image_url = "https://apod.nasa.gov/apod/image/2403/FullPlantonMoon_Horalek_1022.jpg";
String destination_file = "FullPlantonMoon_Horalek_1022.jpg";

try (
    InputStream in = new URL(image_url).openStream();          // объект для чтения входного потока из URL
    OutputStream out = new FileOutputStream(destination_file)   // объект для записи данных в файл
        )     
        {
            // Буфер для получения данных
            byte[] buffer = new byte[1024];
            int length;

            while ((    length = in.read(buffer)) != -1 ) {
                out.write(buffer, 0, length);
            }

            System.out.println("Изображение успешно скачано!");

        } catch (IOException e) {
        }
```

Для объектов внутри круглых скобок try будет автоматически вызван метод закрытия соединения и файла, если возникнет исключениие (см. try-with-resources) 


# JSoup
Библиотека Jsoup - это мощный инструмент для работы с HTML в Java. Она предоставляет очень удобный API для извлечения и манипулирования данных, используя DOM, CSS и jquery-подобные методы.

JSoup не входит в стандартную библиотеку Java.

Важные возможности и особенности библиотеки Jsoup:
- Парсинг HTML и XML документов, локальных и по URL
- Использование CSS или jquery-подобных селекторов для выборка элементов документа.
- Изменение HTML документов: изменить текст элемента, добавить или удалить атрибуты, и т.д.
- Очистка входных данных. Например чтобы предотвратить атаки через внедрение кода (XSS attacks).
- Поддержка обработки ошибок: Jsoup хорошо справляется с некорректным HTML и предоставляет удобные методы для обработки ошибок.
- Преобразование документа в тест


Указание зависимости в файле конфигурации maven:
<details>
```xml
<!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.17.2</version>
</dependency>
```
</details>

<br>

**Пример использования библиотеки JSoup для парсинга HTML документа (вывода всех ссылок)**
```java
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Получаем HTML-документ страницы
            Document doc = Jsoup.connect("http://example.com").get();

            // Заголовок страницы
            String title = doc.title();
            System.out.println("Title: " + title);

            // Получаем все ссылки на странице
            Elements links = doc.select("a[href]");

            // Перебор коллекции всех найденных элементов
            for (Element link : links) {
                // Выводим текст ссылки и саму ссылку
                System.out.println("\nТекст: " + link.text());
                System.out.println("Ссылка: " + link.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Задание параметров запроса
```java
// Отправляем запрос с параметрами
Document doc = Jsoup.connect(url)
                .data("q", searchQuery) // Здесь "q" - это имя параметра запроса, а searchQuery - его значение
                .userAgent("Mozilla")  // Устанавливаем User-Agent
                .timeout(5000)         // Устанавливаем таймаут соединения
                .get();
```


Важные классы и методы
- `Jsoup.connect(String url)` используется для подключения к веб-сайту и получения HTML-документа.
- класс `Document` представляет HTML-документ и методы для его обработки. Создание обхектов: `Jsoup.parse(String html)` или `Jsoup.connect(String url).get()`.
- класс `Element`элемент HTML-документа, содержит (помимо прочего) методы для получения и изменения данных элемента (HTML, атрибуты и т.д.)
- класс `Elements` -список элементов Element.
- класс `Connection`  используется для создания HTTP-запроса к веб-сайту. Можно настроить запрос, используя методы `userAgent(String userAgent)`, `timeout(int millis)`, `data(String key, String value)` и т.д.
- класс `Selector` предоставляет методы для выборки элементов из документа с использованием CSS-селекторов.
- классы `TextNode` и `Comment`: Эти классы представляют текстовые узлы и комментарии в HTML-документе.
- класс `ParseException` - исключение, которое может быть выброшено при ошибке разбора HTML.



# См. также
- Файл `robots.txt`, обычно находится в корневом каталоге сайта.
- HTTP протокол, структура HTTP запроса и ответа, метода POST и GET
    - HTTP запросы можно просмотреть прямо в браузере, обычно в режиме разработчика.
    - Postman - приложение, которое позволяет создавать и отправлять HTTP запросы
    - `curl` консольное приложение для создания и отправки HTTP запросов
- Нескоторые серверы предоставляют API для запроса данных. Это может сильно упростить процесс получения данных.
- HTML и CSS - структура HTML документа, теги, CSS классы и селекторы на основе HTML и CSS.
- Для работы с динамическим содержимым (JavaScript) можно использовать библиотеку Selenium для управления браузером (в том числе в фоновом режиме).

# Ссылки
- https://github.com/chubin/wttr.in#usage -- информация о сайте wttr.in выдающем погоду в Plain Text.
- https://habr.com/ru/articles/page2/