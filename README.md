# Языки программирования для работы с большими данными
## Содержание
### Основы Java
1. О курсе. Основы языка программирования Java. Примеры использования технологий BigData. Типы данных в Java. Работа со строками и числами.  Структура JVM
   - https://docs.google.com/presentation/d/1pmOlWlulw2prFhPjn73f3SE6KCyYtW2jYux-aKugVcA/edit?usp=sharing
2. Структура памяти. Сборщик мусора. Строки. Интернирование строк. Методы. Числа. Библиотека коллекций в Java. Юнит тестирование JUnit. Альтернативная библиотека коллекций в Java: Apache Commons Collections & Google Guava. Примеры
3. Потоки в Java. Класс Thread, интерфейс Runnable. Ключевое слово synchronized. Пулы потоков.
   - https://github.com/ivtipm/BigDataLanguages/tree/main/Java/Threads
   - Сборщик Maven. Ввод и вывод в Java. Классы: InputStream, Outputstream, Scanner, BufferedReader. Исключения. Stream API


### Системы хранения и обработки данных, Scala
1. Работа с базами данных в Java. Введение в язык Scala – ООП и переменные, Scala Shell, коллекции в Scala
   - https://ivtipm.github.io/BigDataLanguages/Scala/slides/Scala_about.html
   - [Шпаргалка](Scala/Readme.md)
1. Примеры Pattern matching; ScalikeJDBC - библиотека для работы с реляционными БД в Scala; SCOPT - библиотека по разбору командной строки
1. Apache Spark, запуск и форматы данных: plain text storage, sequence files, parquet, orc, avro. 
1. Работы с вводом-выводом. Задачи на Stream API


## Задания
### 1. Основы Java. 
#### Задание 1. Скрапер
Скачайте заголовки новостей как минимум со 100 страниц сайта zabgu.ru
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
- Слайды: https://docs.google.com/presentation/d/1pmOlWlulw2prFhPjn73f3SE6KCyYtW2jYux-aKugVcA/edit#slide=id.g1b63e56b075_0_77
- Примеры работы с регулярными выражениями: https://github.com/ivtipm/BigDataLanguages/blob/main/Java/strings.md

#### Задание 2. Анализ текста
Дополните программу из предыдущего задания:
1. Скачивайте тексты новостей
2. Проанализируйте скаченные тексты:
    - найдите упоминания людей, названий факультетов или ищите другие сущности
    - подсчитайте частоты упоминаемых имён 
    
    
**Подсказки**
- https://regex101.com -- для экспериментов с регулярными выражениями

### 3. Коллекции
Списки, множества и словари

### 4. Многопоточность
Многопоточность с синхронизацией.

#### Задание 3. Многопоточность.
Создайте многопоточную версию программы из задания 1.
Используйте пул потоков. 
Используйте потоки, в первую очередь, для оптимизации узких мест. 
Действуйте так, чтобы избежать блокировки на ресурсе, откуда скачиваете новости.


### 5. Взаимодействие с СУБД
Использовать JDBC.
#### Задание 4. Взаимодействие с БД
Модифицируйте программу из прошлого задания. Записывайте данные в БД на выбор: SQLite, Postgres, MySql, свой вариант, в том числе noSql БД.
#### Задание 5. Stream API
Создайте вариант программы из предыдущих заданий, который использует Stream API для скачивания и обработки данных.
- Конспект: https://github.com/ivtipm/BigDataLanguages/tree/main/Java/Streams


### 6. Основы Scala
Знакомство с базовым синтаксисом Scala: переменные, значения, типы; функции, анонимные функции; классы, объекты; интерфейсы и трейты; особенности и отличия от Java
- https://ivtipm.github.io/BigDataLanguages/Scala/slides/Scala_about.html
- [Шпаргалка](Scala/Readme.md)
#### Задание 6. Scala
Установите компилятор Scala. Попробуйте запустить примеры кода из лекции.



### 7. Apache Spark - 1
Apache Spark. Настройка и работа c REPL.

### 8. Apache Spark - 2
Resilient Distributed Dataset


## Рубежный контроль
1. Программирование на Java. Потоки (stream) и потоки (thread) на Java. (35 баллов)
2. Программирование на Scala. (35 баллов)


## Экзамен
2 вопроса: теория + практика (20 + 10 баллов)
