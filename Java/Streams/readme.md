# Потоки данных (streams) в Java

### Потоки ввода и вывода
Бинарные потоки ввода и вывода

![](https://proglang.su/images/categories/java/ierarkhiya-klassov-v-java.webp)


Версии Java имеют хорошую (почти всегда) обратную совместимость друг с другом. Однако со временем добавляются новые классы, использование которых удобнее и лаконичнее.

Всегда стоит смотреть *последние* лучшие практики.


### Stream API. Потоки [данных] и коллекции
```java
import java.util.stream.*;
```
Потоки (streams) и коллекции частично взаимозаменяемы.

Коллекции обеспечивают эффективный доступ к одиночным объектам, а стримы, наоборот, для прямого доступа и обработки отдельных элементов не используются. Стримы предназначены для параллельных и последовательных агрегаций, выполняемых через цепочку методов.

![](https://annimon.com/ablogs/file816/stream.png)

Большиснтво коллекций имеют в своём составе методы, которые создают поток на освнове данных коллекции (`Collection.stream()`).

Stream API — это новый способ работать со структурами данных в функциональном стиле.

Пакет `java.util.stream` содержит классы для поддержки операций с потоками элементов в функциональном стиле. Ключевой абстракцией, введенной в этом пакете, является Поток [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/stream/Stream.html) ].

```java
public interface Stream<T> extends BaseStream<T,Stream<T>>
```








Методы потоков (операторы) можно разделить на две группы:

- Промежуточные (intermediate, lazy) — обрабатывают поступающие элементы и возвращают стрим. Промежуточных операторов в цепочке обработки элементов может быть много.
- Терминальные (terminal, eager) — обрабатывают элементы и завершают работу стрима, так что терминальный оператор в цепочке может быть только один.

Обработка не начнётся до тех пор, пока не будет вызван терминальный оператор.

Промежуточных операторов вызванных на одном стриме может быть множество, в то время терминальный оператор только один


Промежуточные методы
- `.filter(Predicate predicate)` выдаёт те элементы потока, для которых predicate -> true;
- `.map(Function mapper)` преобразует каждый элемент в новый с помощью фукции mapper;
- `.flatMap(Function<T, Stream<R>> mapper)` аналогично map но умеет обрабатывать вложенные потоки.

Функциональный интерфейс Function<T,R> представляет функцию перехода от объекта типа T к объекту типа R

Остальные методы:
https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/stream/Stream.html

```java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


// список на основе динамического массива
List<String> list = new ArrayList<String>();
// добавление объектов
list.add("One");  list.add("Two");   list.add("Three"); list.add("Four"); list.add("Five");
list.add("Six");  list.add("Seven"); list.add("Eight"); list.add("Nine"); list.add("Ten");

// создание потока на основе коллекции
Stream stream = list.stream();

// создание нового потока в функциональном стиле
// отберём все элеенты, которые содержат строки длиной в три символа
Stream short_strs = stream.filter(
        s -> s.toString().length() == 3     // предикат в виде анонимной функции;
        // параметр анонимной функции всегда Object, но можно преобразовать
        );
// предикат - логическая функция

// применение функции к каждому элементу
short_strs.forEach( System.out::println );
```


```java
String[] array = {"Java", "Stream", "API"};
        Stream<String> streamOfArray = Arrays.stream(array);        // коллекция -> поток
        streamOfArray.map( s -> s.split("") )                  // Преобразование слова в массив букв
                .flatMap(Arrays::stream).distinct()                 // создаёт из трёх потоков из потоков один поток
                .collect(Collectors.toList()).forEach(System.out::println);
```


#  Ссылки
