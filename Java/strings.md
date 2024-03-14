# Обработка строк в Java


#### StringBuilder -- класс для эффективной конкатенации
```Java
StringBuilder str = new StringBuilder();
str.append("Hello");
str.append(",");
str.append(" ");
str.append("World");

String s = str.toString();
```

#### Форматирование
```Java
// форматирование такое же как и в println
String fs = String.format("Значение переменной float = " +
       "%6.2f, пока значение integer " +
       "переменная = %10d, и string " +
       "= %s", 333.0/106, 42, "abc");
// %6.2f -- 6 позиций всего, 2 десятичных знака
// %10d -- целое число, 10 позиций, выравнивание по правому краю

// fs = Значение переменной float =   3.14, пока значение integer переменная =         42, и string = abc
```

#### Строки и коллекции
```Java
// соединений коллекций в строку
List<String> people = Arrays.asList(
       "Philip J. Fry",
       "Turanga Leela",
       "Bender Bending Rodriguez",
       "Hubert Farnsworth",
       "Hermes Conrad",
       "John D. Zoidberg",
       "Amy Wong"
);

String peopleString = String.join("; ", people);
// разделение
String[] peopleArr = peopleString.split("; ");
```


# Регулярные выражения

![](https://imgs.xkcd.com/comics/regular_expressions.png)

Метасимволы
- `.`  — один произволььный символ
- `\d` — любая цифра, то же самое что и [0-9];
- `\D` — исключает все цифры и заменяет [^0-9];
- `\w` — заменяет любую цифру, букву, а также знак нижнего подчёркивания;
- `\W` — любой символ кроме латиницы, цифр или нижнего подчёркивания;
- `\s` — поиск символов пробела;
- `\S` — поиск любого непробельного символа.


Группы соответствия
- `[]` — любой один из переисленных символов; `[abc]`, `[А-Я]`
- `(cat|dog|bird)` — любое одно слово


Квантификаторы -- задание частоты повторения
- `?` — 0 или 1 повторение, то же самое, что и {0,1}.
- `*` — 0 или более, {0,}.
- `+` — 1 или более, {1,}.
- `{n}` — ровно n повторений
- `{n,m}` — не менее n и не более m раз.
- `*?` — символ ? после квантификатора делает его ленивым, чтобы найти наименьшее количество совпадений.


Другие обозначения
- `^` — начало строки
- `$` — начало строки
- `\` — экранирование; `\\` — искать в тексте `\`.


## Регулярные выражения в Java
Основные классы для работы с регулярными выражениями
- Matcher — выполняет операцию сопоставления в результате интерпретации шаблона [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/regex/Pattern.html#compile(java.lang.String,int)) ]. 
    - Проверка соответствия строки целиком: `.matches()`; 
    - Проверка соответствия любой части строки: `.lookingAt()`;
    - Искать следующее соответствие `.find()`. 
- Pattern — класс Регулярное Выражение [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/regex/Pattern.html) ]. Может создавать объекты типа Matcher:
    - `.compile("some regex");`
    - `.compile("some regex", flags);` Flags: Pattern.CASE_INSENSITIVE, Pattern.MULTILINE и др. [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/regex/Pattern.html#compile(java.lang.String,int)) ]
- PatternSyntaxException — предоставляет непроверенное исключение, что указывает на синтаксическую ошибку, допущенную в шаблоне RegEx.


Проверка всей строки
```Java
import java.util.regex.Pattern;

// проверка соответствия всего текста регулярному выражению
Pattern.matches("Java \\d{1,2}", "Java 20");       // -> true

// проверка соответствия всего текста регулярному выражению
Pattern.matches("Java \\d{1,2}", "new Java 20");   // -> false
```

Создание объекта Регулярное Выражение, объекта для поиска соответствий, последовательный поиск соответствий.
```Java
String text = """
                JDK 1.1     
                Вторая версия была выпущена 19 февраля 1997 года[12].       
                Библиотека Accessibility.
                Java 2D.
                Поддержка технологии drag-and-drop.
                Полная поддержка Unicode, включая поддержку ввода на японском, китайском и корейском языках.
                Поддержка воспроизведения аудиофайлов нескольких популярных форматов.
                Полная поддержка технологии CORBA.
                JIT-компилятор, улучшенная производительность.
                Усовершенствования инструментальных средств JDK, в том числе поддержка профилирования Java-программ.""";
                
import java.util.regex.Pattern;

Pattern digits = Pattern.compile("\\d{1,4}");     // от 1 до 4 цифр подряд

// matcher - объект для сравнения с текстом
Matcher matcher = words46.matcher(text);
while ( matcher.find() ) {      // поиск соответствий (true если найдено)
       // извлечение строки
       System.out.println( matcher.group() );

       // или
       System.out.println( text.substring(matcher.start(), matcher.end()));
       // matcher.start() - позиция начала текущего соответствия
       // matcher.end() - позиция конца текущего соответствия
}
```
Вывод:
```text
1997
Access
ibilit
Java
drag
drop
Unicod
CORBA
Java
```


**Извлчение **
```java
// регулярное выражение с группой захвата (capturing group) записанной в скобках - (\\d\\d\\d\\d)
// \d\d\d\d - последовательность из 4 цифр; 
// т.к. в Java \ (бэкслеш) используется для обозначения служебных символов, его нужно экранировать:  \\d\\d\\d\\d
Pattern num_regex = Pattern.compile("(\\d\\d\\d\\d) год");
// создание объекта для обработки строки регулярным выражением
Matcher matcher = num_regex.matcher("1995 год");
if (matcher.find()) {  // поиск по регулярному выражению, вернёт true если найдено соответствие
        System.out.println( matcher.group() );          // 1995 год
        System.out.println( matcher.group(0) );         // 1995 год
        // group() и .group(0) возвращает весь текст, который совпадает со всем регулярным выражением
        System.out.println( matcher.group(1) );         // 1995
        // matcher.group(1) выдаст подстроку, которая соответствует первой группе захвата в регулярном выражении
}
```


# Ссылки
- Слайды: https://docs.google.com/presentation/d/1pmOlWlulw2prFhPjn73f3SE6KCyYtW2jYux-aKugVcA/edit#slide=id.g1b63e56b075_0_77
- Шпаргалка: https://www.rexegg.com/regex-quickstart.html
- https://regex101.com/ -- для экспериментов с регулярными выражениями
***
- Регулярные выражения в Atom: поиск и замена с извлечением: http://2017.compciv.org/guide/topics/end-user-software/atom/how-to-use-regex-atom.html
