# Языки программирования для работы с большими данными
## Содержание
### Основы Java
1. О курсе. Основы языка программирования Java. Примеры использования технологий BigData. Типы данных в Java. Работа со строками и числами.  Структура JVM
   - https://docs.google.com/presentation/d/1pmOlWlulw2prFhPjn73f3SE6KCyYtW2jYux-aKugVcA/edit?usp=sharing
2. Структура памяти. Сборщик мусора. Строки. Интернирование строк. Методы. Числа. Библиотека коллекций в Java. Юнит тестирование JUnit. Альтернативная библиотека коллекций в Java: Apache Commons Collections & Google Guava. Примеры
3. Потоки в Java. Класс Thread, интерфейс Runnable. Ключевое слово synchronized. Сборщик Maven. Ввод и вывод в Java. Классы: InputStream, Outputstream, Scanner, BufferedReader. Исключения. Stream API

### Ситемы хранения и обработки данных, Scala
1. Работа с базами данных в Java. Введение в язык Scala – ООП и переменные, Scala Shell, коллекции в Scala
1. Примеры Pattern matching; ScalikeJDBC - библиотека для работы с реляционными БД в Scala; SCOPT - библиотека по разбору командной строки
1. Apache Spark, запуск и форматы данных: plain text storage, sequence files, parquet, orc, avro. 
1. Работы с вводом-выводом. Задачи на Stream API


## Задания
### 1. Основы Java. 
#### Задание 1
Выберите одну из двух задач или решите обе.
1. Решите задачу https://ivtipm.github.io/Programming/Glava10/index10.htm#z338
   - Решите с использованием массивов из чисел
   - Решите с использованием массивов из случайных строк
   - Решите с использованием множеств
   - напишите тесты, используйте assert
   - Пишите комментарии, документируйте код
   - Компилируйте и запускайте программу в консоли
   
2. Скачайте заголовки новостей как минимум со 100 страниц сайта zabgu.ru
   - Можно использовать любой другой сайт с новостями или постами. 
   - Компилируйте и запускайте программу в консоли.
      - Передавайте количество страниц для скачивания, файл для сохранения заголовков через аргументы командной строки
   - Дополнительные баллы за
     - скачивание метаданных (дата, теги, количество просмотров, лайков, реакций и т.п.)
     - скачивание текста статьи или новости
     - скачивание картинок
   - Сохраняйте данные в CSV файл
   
**Подсказки**
* Как скачать веб-страницу: https://github.com/VetrovSV/OOP/blob/master/examples/java/HTTP_request.md
   https://mkyong.com/java/java-how-to-download-web-page-from-internet/ 
* сервер может не выдавать страницы если не указан UserAgent.
* указывайте большое время ожидания (timeout) для чтобы иметь больше гарантий на получение страницы целиком
* если есть опасность блокировки множественных запросов, то делайте перерыв между запросами


### 2. Строки и текст
Что-то с регулярными выражениями, StringBuilder, Formatter

#### Задание 2
Дополните программу из предыдущего задания:
1. Скачивайте тексты новостей
2. Проанализируйте скаченные тексты:
    - найдите упоминания людей, названий факультетов или ищите другие сущности
    - подсчитайте частоты упоминаемых имён 

### 3. Коллекции
Списки, множества и словари

### 4. Многопоточность
Многопоточность с синхронизацией




### 5. Взаимодействие с СУБД
Использовать JDBC

### 6. Основы Scala
знакомство с базовым синтаксисом Scala: переменные, значения, типы; функции, анонимные функции; классы, объекты; интерфейсы и трейты; особенности и отличия от Java
- [Шпаргалка](scala/Readme.md)

### 7. Apach Spark - 1
Apache Spark. Настройка и работа c REPL.

### 8. Apach Spark - 2
Resilient Distributed Dataset


## Рубежный контроль
1. Программирование на Java. Потоки (stream) и потоки (thred) на Java. (35 баллов)
2. Программирование на Scala. (35 баллов)


## Экзамен
2 вопроса: теория + практика (20 + 10 баллов)
