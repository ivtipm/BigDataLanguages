# Scala
Это шпаргалка по языку, которой можно пользоваться как справочником или обзором возможностей языка. Но порядок изложения не ориентирован на последовательное изучение тем.

**Содержание**
- [Hello, World! и основы синтаксиса](#Hello,-World!-и-основы-синтаксиса)
- [Система типов](#Система-типов)
  - [Кортеж (Tuple)](#Кортеж-(Tuple))
- [Ввод и вывод](#Ввод-и-вывод)
- [Строки и регулярные выражения](#Строки-и-регулярные-выражения)
- [Управляющие операторы](#Управляющие-операторы)
- [Методы и функции](#Методы-и-функции)
- [Некоторые типы данных и коллекции](#Некоторые-типы-данных-коллекции)
- [Классы](#Классы)
- [Служебные задачи](#Служебные-задачи)
- [Некторые фреимворки и библиотеки](#Некторые-фреимворки-и-библиотеки)

## Hello, World! и основы синтаксиса
```scala
@main
def main() =
    println("Hello world!")
    println("Hello world!")
```
В стандарте Scala 2 нужно создать класс наследник от App, который будет включать основной код.

`@main` — аннотация, помечающая текущий метод как главную функцию программы.
Тело функции начинается после `=`. Фигурные скобки вокруг тела писать необязательно. 

Если фигурных скобок нет, то вложенность определяют отступы (как в Python).

Фигурные скобки могут включаться везде
```scala
println( "Hello, World!" )
println({"Hello, World"} )
println( {val s1 = "Hello"
          val s2 = ", World"
          s1+s2} )

```
Последнее выражение в фигурных скобках -- является результатом выполнения этого блока (как return в функции).

Точка с запятой в конце каждой строки не обязательна. Но можно указывать для отделения операторов в одной строке.





## Система типов
- Статическая типизация
- Местами строгая (нельзя без явного преобразования `val x: Int = 2.3`)
- Возможен автоматический вывод типа для многих ситуаций.\
 Например `val y: = 40+2; // Int`

Общий синтаксис объявления переменной или константы:
```
val|var name: type = value
```

 **Переменные и константы**
Scala поощряет использование констант, так как их использование даёт больше информации компилятору о поведении кода,
 в частности помогает делать оптимизации для параллельных алгоритмов; упрощает написание параллельных алгоритмов.   
```scala
val my_const = 333/106;   // константа
var my_variable:Int = 0;      // переменная
```
Тип у переменных указывать тоже не обязательно, но рекомендуется.  



#### Типы

Иерархия типов Scala
![](ScalaTypes.drawio.png)

Возможно приведение к более общим типам (надтипам). Нельзя приводить к подтипам (более частным).

Unit — единичный тип, обозначается как () или {}; используется для возвращаемого типа процедур
При записи всего в переменную типа Unit значение теряется, остаётся только ()
```scala
val x: Unit = 42;            // -> ()
val x: Unit = Math.sin(2);   // -> ()
```

Можно оборачивать действия в скобки и записывать в переменные
```scala
val x: Double = {
val z = 0
Math.cos(2)}
// тип перем. x = тип последнего выражения в скобках
```


#### Числовые литералы
```scala
// разряды можно отделять
val x = 1_000_000
```

#### Кортеж (Tuple)
```scala
val pair: (Int,Int) = (20, 30)
println(pair._1)        // 20
println(pair._2)        // 30

// экстракция (деконструкция)
val (first, second)  = pair

var (x,y,z) = (2,3, 0.7)
var (x:Int, y:String) = (2,"qwerty")
```





## Ввод и вывод

```scala
println("Hello, World!")
print("Hello, World!")
```

```scala
val p = 333.0 / 106            // 3.141509433962264
println( f"pi ≈ $p%7.3f" )

println( f"pi ≈ ${333.0/106}%7.3f" )
```

Вывод
```text
pi ≈   3.143
```


**Ввод**

```scala
// подключение трёх функций из пакета 
import scala.io.StdIn.{readLine, readInt, readLong}

val s: String = readLine()
```





## Строки и регулярные выражения

```scala
val r = "\\w+\\s\\d".r`
// метод r создает на основе строки объект типа scala.util.matching.Regex
r.matches( "some text ")    // -> Boolean
```








## Управляющие операторы
**if** 
- считается выражением
- если написано then то скобки в условии можно не писать
- 
Например:
```scala
import scala.util.Random
val rand = Random
val a = rand.nextInt()
val b = rand.nextInt()
val max = 
    if (a > b) a 
    else b
```

**while** используется редко, т.к. есть рекурсия. break и continue отсутствуют (как и return в середине функции).\
**do ... while** не используется.

**for** 
```scala
for ( i <- 1 to 10 )
    println(i)
    
// в обратном порядке
for ( i <- 10 to 1 by -1)
  println(i)
  
// цикл по коллекции  
for ( i <- Vector(1,2,3))
  println(i)
```
Можно использовать внутри конструкции, похожей как в list comprehension, в том числе с условием. 
```scala
println( for (i <- 1 to 10) yield i*i)    // 1, 4, 9, ...

// квадраты чисел кратных трём
println( for (i <- 1 to 100  if i%3 == 0) 
      yield i*i
)    // 9, 36, 81, 144 
```

Можно указывать произвольное число действий в заголовке цикла. Это создаст вложенный цикл:
```scala
for ( i <- 1 to 10;  j <- 1 to 10)
    print(f"$i,$j  ")
```
Код с фигурными скобками вместо круглых можно записать в несколько строк, не разделяя операторы точкой с запятой.
```scala
for { i <- 1 to 10
      j <- 1 to 10 }
    print(f"$i,$j;  ")
```
В тело цикла можно включить условие, контролирующее изменение счётчика
```scala
for { i <- 1 to 5
      j <- 1 to 5 if i >= j}
    print(f"$i,$j;  ")
```
Вывод
```text
1,1;  2,1;  2,2;  3,1;  3,2;  3,3;  4,1;  4,2;  4,3;  4,4;  5,1;  5,2;  5,3;  5,4;  5,5;

```


### Сопоставление с образцом (Pattern matching)
Проверка сверху вниз. Срабатывает только один case. Если не сработал ни один, то генерируется исключение. 
```scala
import scala.util.Random

val x: Int = Random.nextInt(10)

x match
  case 0 => "zero"
  case 1 => "one"
  case 2 => "two"
  case _ => "other"
```
`_` обозначает любое значение.


Вместо значения в `case` можно указать переменную, в которую это значение запишется. 
Так как переменная будет всегда равна проверяемому значению, такая ветка будет срабатывать всегда.
```scala
import scala.util.Random

val x: Int = Random.nextInt(10)

println(x)

x match
  case x => "always"
```

Но в ветку с переменной можно добавить условие:
```scala
import scala.util.Random

val x: Int = Random.nextInt(10)

println(x)

x match
  case x if (x%2==0) => "even"
  case 1 => "one"
  case 3 => "three"
  case _ => "other"
```

Можно сопоставлять объекты (экземпляры case classes) по отдельным полям. Поля, значение которых не важны заменены на `_`.
В поле можно встроить сравнение по регулярному выражению.
```scala
case class Email(sender: String, title: String, body: String)

//val x = new Email("Spammer", "Not a Spam", "Hello, dear friend")    // send to spam
val x = new Email("Manager", "important!!!!", "Hello, dear friend")          // delete
//val x = new Email("Manager2", "important!!!!", "Hello, dear friend")          // also delete
//val x = new Email("Vasya", "qwerty", "Hello, dear friend")  // do nothing

val title_reg   = "(.*!{2,}.*)".r   // 2 или более восклицательных знака среди всего остального, выражение должно быть в скобках для обозначения места для подстановки строки
val title_reg22 =  ".*!{2,}.*".r     // 2 или более восклицательных знака среди всего остального

x match
// проверка по полю sender, с извлечением значения из поля title в переменную title1
case Email("Spammer", title1, _)           => s"send <$title1> to spam"
// проверка по последнему полю
case Email(_, _, "money")                  => "mark important"
// проверка по точному совпадению с первым полем, извлечение значения второго поля в title22, проверка по регулярному выражению
case Email("Manager", title_reg(title22), _) => "delete"
// значение для проверки в регулярном выражении можно и не извлекать в переменную, ведь и так понятно что туда подставлять
case Email("Manager2", title_reg22(), _) => "also delete"
// если ничего не сработало
case _ => "do nothing"
```

Использование регулярных выражений с извлечением совпадающих частей регулярного выражения в переменные
```scala
import scala.util.matching.Regex
val contact = "JohnSmith@sample.domain.com"

val emailPattern: Regex = """^(\w+)@(\w+(.\w+)+)$""".r
val phonePattern: Regex = """^(\d{3}-\d{3}-\d{4})$""".r

contact match
  case emailPattern(localPart, domainName, _) =>
    println(s"Hi $localPart, we have saved your email address.")
  case phonePattern(phoneNumber) =>
    println(s"Hi, we have saved your phone number $phoneNumber.")
  case _ =>
    println("Invalid contact information, neither an email address nor phone number.")
```


## Некоторые модули

```scala
import Math;

import scala.util.Random.{nextInt, nextDouble};
```




## Методы и функции
- Объявление функции в scala начинается со слова `def`.\
Например: 
```scala
def foo(x:Int):Double = 42.0 + x    // Double -- тип возвращаемого значения
```
- Оператор `return` не используется, возвращаемое значение -- это значение последнего выражения внутри функции;
(это тоже упрощает анализ кода, поощряет написание функций где невозможен выход в неожиданном месте)
- Тип возвращаемого значения может быть выведен автоматически (кроме рекурсивных функций)
```scala
def foo(x:Int) = 42.0+x     
// или
def foo(x:Int) = {
    println("Hello, World!")
    42+x                      // возвращаемое значение -- значение последнего выражения в функции
}
```
- Функции объявлять можно где угодно, в том числе внутри других функций
- Функция, сама по себе, являясь значением некоторого функционального типа, может быть записана и в переменную, подобно анонимным функциям в языке C++. \
   Например: 
   ```scala
     val foo: Int => Double = (x:Int) => 42.0 + x
  ```
  где
    - `Int => Double` -- тип функции, принимающей Int и возвращаюший Double
    - ` = ` -- присваивание значения, в данном случае тела функции
    - `(x:Int) => 42.0 + x` -- тело функции

**Передача по имени (By-name parameters)** и ленивые вычисления
Параметры переданные по имени будут вычислены только при необходимости. Такие параметры обозначаются `=>` перед именем своего типа.
```scala
/** С вероятностью 0.5 возвращает квадрат числа или ноль */
def foo( x: => Int ) = {
if ( Random.nextInt() % 2 == 0) pow(x,2)
else 0
// оператор return в Scala не используется, метод вернёт значение последнего приведенного выражения
}

// сумма будет вычислена только если внутри функции будет выполнено условие
foo( 5 + 3 )

// код внутри фигурных скобок выдаёт результат последнего выражения
// будет выполнен если внутри функции будет выполнено условие
foo( {print("Evaluated"); 5 + 3} )
```

**Композиция функций**

```scala
f andThen g      // g( f(x) )
f compose g      // f( g(x) )
```

```scala
val f = (_:Double) + 2
val g = (_:Double) * 3

(f compose g)(1)   // 5
(f andThen g)(1)   // 9
```

### Каррирование (Currying)
Каррирование (карринг) или частичное применение функции — преобразование функции от многих аргументов в набор функций, 
каждая из которых является функцией от одного аргумента.

В результате частичного применения функции появляется новая функция, которая принимает оставшиеся аргументы.

Например: f(x,y) преобразуется в f'(x)(y), где f'(x) -- возвращает функцию.

```scala
/** Создаёт новый список на основе старого
 * @param f -- функция фильтрации списка
 * @param lst   -- список из целых чисел
 * @return список чисел, для которых функция f возвращает true
 */
def my_list_filter(f: Int => Boolean, lst: List[Int] ) =
  var filtered: List[Int] = List()        // новый пустой список
  for (element <- lst)                    // перебор элементов списка с записью каждого в element
    if ( f(element) )
      filtered = filtered.appended( element ) // добавление элемента в новый список
  filtered


val isOdd = (_:Int) % 2 == 0        // лямбда, проверяющая чётность числа
  // аналогично: def isOddMethod(x:Int)   =   x % 2 == 0


my_list_filter( isOdd,            List(1,2,3,4))          // -> List(2,4)
my_list_filter( (_:Int) % 2 == 0, List(1,2,3,4))          // -> List(2,4)
my_list_filter( _ % 2 == 0,       List(1,2,3,4))          // -> List(2,4)

// каррирование метода my_list_filter, 
// теперь он возвращает функцию, которая должна принять его второй параметр
// первый параметр уже задан
val oddFilter = my_list_filter.curried( isOdd )

// вызов каррированного метода
oddFilter( List(1,2,3,4) )
```


### Частичные функции (Partial Functions)
Частичная функция содержит в себе оператор match (неявно) и сравнивает значение первого и единственного аргумента 
со значениями в case, при совпадении возвращает значение. 
Первый и второй шаблонные типы должны совпадать с проверяемыми и возвращаемыми значениями.


```scala
val divide10: PartialFunction[Int, Int] = {
    case 1 => 10
    case 2 => 5
    case 5 => 2
    case 10 => 1
}
```
Если вариант case для текущего значения аргумента функции не предусмотрен, то бросается исключение. 
Однако в такую функцию встраивается метод `.isDefinedAt` которым можно проверить, 
определена ли функция для такого значения. 

### Обобщённые функции





## Некоторые типы данных, коллекции [ [Scala Book](https://docs.scala-lang.org/scala3/book/collections-classes.html) ]

### Опциональный тип Option[A]
Такой тип может содержать значение типа `A`, а может и не содержать.\
У `Option[A]` есть два подтипа
- `Some[A]` -- контейнер Some, гарантированно содержащий значение типа A
- `None`

Этот тип используется там, где, например, возвращаемого значения может и не быть.
Метод поиска элемента (find) в коллекции Vector может вернуть номер элемента если он найден, или вернуть None,
если такого элемента нет.

Как правило опциональные значения обрабатываются сопоставление с образцом
```scala
val v = Vector(23, 53, 8, 45, 0, 4)
val k = v.find( _ == 53)
// _ == 53 -- тело анонимной функции, сравнивающий элемент с числом 53
// _ -- имя по умолчанию параметра анонимной функции 
k match
  case None     => print("No element")
  case Some(i)  => print(f"Element number: $i")    // проверка на соответствие значения k подтипу Some с извлечением значения 
```

Основные методы
- `.getOrElse( default )` -- вернёт default, если объект типа `Option[A]` в себе не содержит значения типа A
- `.OrElse( x: Optional[A])` -- вернёт другое опциональное значение, если первое не содержит в себе значений типа A;
  будет сгенерировано исключение, если и второе значение является `None`
- `.map`, `flatMap` -- применяют функцию к опциональному значению, могут вернуть None; второй метод возвращает опциональное значение
- `.filter` -- применяет предикат, если false делает опциональное значение None
- `.collect` -- применяет частичную функцию

```scala
scala.collection.immutable
List[+A], Vector[+A], Set[+A], Map[K, +V]


scala.collection.mutable_
Buffer[A], Set[A], Map[K,V] Builder[-E, C]

scala.collection
Seq[+A], Set[+A], Map[K, +V], Iterator[+A]
```

immutable collections\
![](https://docs.scala-lang.org/resources/images/tour/collections-immutable-diagram-213.svg)

mutable collections\
![](https://docs.scala-lang.org/resources/images/tour/collections-mutable-diagram-213.svg)

Информация о том, что конкретные значения, в частности коллекции неизменны (immutable) позволяет компилятору 
более эффективно оптимизировать код. В том числе помогает программисту и компилятору организовывать параллельные вычисления 
не беспокоясь об отсутствии потокобезопансоти. 

Использование неизменяемых значений типично для функциональной парадигмы.

Создание коллекций
```scala
// односвязный список с O(1) вставкой в начало и O(n) вставкой в конец
val l = List(10,2,3,3,5)

// Vector реализован на основе префиксного дерева (trie), 
// быстрое добавление в начало и конец, произвольный доступ за константное время
// перебор медленнее чем в списке
val v = Vector(1,2,3,3,5)     // implemented as tree of blocks, provides fast random access
val s = Set(1,2,3,3,5)        // -> Set(1,2,3,5)
val m = Map("key1" -> 123, "key2" -> 456)
```

Доступ к элементам через оператор `()`, а не через `[]` (используется для аргументов обобщённых типов).

Некоторые примеры
```scala
l(0)        // -> 10
s(5)        // true
s(55)       // false
m("key1")   // -> 123
m("qwerty")   // Exception

m.get("key1")    // Some(123)
m.get("qwerty")  // None
s.contains(2)    // true

l.head      // -> 10
l.last      // -> 5

s.size      // -> 4
v.size      // -> 5


l - 10        // удаление элемента;   -> List(2,3,3,5)
m - "key1"    // удаление пары c ключом key1 
```


Операции для **последовательностей**
- Добавить слева `+:`
- Добавить справа `:+`
- Конкатенация `++`

Добавление в словарь: `val m2 = m + ("kry3" -> 789)`


Заполнение списка случайными числами
```scala
import scala.util.Random

// List.fill принимает два набора параметров: размер последовательности и выражение,
val rand_list = List.fill( 10 ) ( Random.nextInt(10) )
```



## Классы
- `class` ~ Java classes
- `trait` ~ Java interface, но может включать не только абстрактные методы
- `object` ~ Java singleton class; позволяет сразу создать объект, объявляя анонимный класс;
фактически используется для создания статических методов
- `case class` ~ особый вид классов с дополнительными возможностями (см. ниже)


Класс -- ссылочный тип.

```scala
class MyClass{
  var name = "World"
  def method() = println(f"hello $name")
}
```

Можно описывать класс в сокращённой форме, сразу указывая параметры конструктора и поля
```scala
// Объявление класса с 5 полями и одновременно конструктора с параметрами
class MyClass(field1: Int, val field2: Int, var field3:Int){
  // остальные члены класса (кроме field1) -- public
  // параметры конструктора станут полями класса,
  // параметры без модификатора val будут private и неизменны


  // объявление ещё четырёх полей:
  val const_field4 = 42       // public
  var var_field = 100;        // public

  protected val x = 200;
  private  val y = 300;

  // любой код в классе (кроме методов) -- это код, который вызывается при инициализации объекта
  println("class initialization ...")


  // методы
  def print() =
    println(this.field1)
  println(this.field2)
  println(this.field3)
  println(this.const_field4)
  println(this.var_field)

  def foo(x: Int) =
    this.field1 = x         // ошибка: присваивание нового значения val переменной
  this.field2 = x         // ошибка: присваивание нового значения val переменной
  this.const_field4 = x   // ошибка: присваивание нового значения val переменной
  this.field3 = x         // Ok
  this.var_field = x      // Ok

  private def private_method() =
    println("I am private")
}

// создание экземпляра класса
val x = MyClass(1,2,3)
x.field1 = 0        // ошибка, нет доступа
x.field2 = 0        // ошибка, нельзя присвоить новое значение val переменной
x.field3 = 0        // Ok
x.const_field4 = 0  // ошибка, нельзя присвоить новое значение val переменной
x.var_field = 0     // Ok

x.print()
x.foo(123)
x.private_method()    // ошибка, нет доступа
```

У всех объектов можно узнать имя класса:
```my_onject.getClass.getName```

### Case class
- очень лаконичное объявление, похоже на объявление конструктора с параметрами, без тела, только со словами `case class`
```scala
case class Person(name: String, id:Long);
```
- все параметры конструктора -- это неизменяемые (val) открытые (public) поля класса (состояние объекта менять нельзя);
- автоматически реализуются методы: `==` (`equals`, сравнение по содержимому) , `hashCode` (объекты можно использовать как ключи в Map),
`toString` (по умолчанию почти красиво), `copy`, `apply`;
- могут быть использованы при сопоставлении с образцом (pattern matching).

Благодаря лаконичности объявления могут заменять кортежи, с удобной **декомпозицией (экстракцией)**:
```scala
case class Email(addr: String, name: String)
// автоматически реализован метод сравнения (equals) на основе значений полей

// new не нужен
val eaddr1 = Email("Odersky@gmail.com", "Martin Odersky")
print(f"Email: ${eaddr1.addr} <${eaddr1.name}>")

// декомпозиция -- вытаскивание отдельных значений из полей объекта
val Email(martin_addr, _) = eaddr1
// создание переменной martin_addr типа String, в которую запишется соответствующее значение из объекта eaddr1
// поля, значения которых нужно игнорировать, обозначены пропуском _

// вместо менее осмысленного кортежа
val (email1, name1) = ("Odersky@gmail.com", "Martin Odersky")
```


Можно даже создавать кейс-классы с одним параметром, чтобы писать самодокументирующийся код, например заменяя абстрактный 
String на PhoneNumber и PersonName.


См. также trait Product и Serializable, которые реализует каждый кейс-класс.
### Наследование

`<class|trait|object|case class> NewClassName extends OldClass1 [with OldClass2]`


### Обобщённые типы
```scala
// А -- параметр типа
class NamedValue[A](name: String, value:A)

val a: NamedValue[Int] = NamedValue[Int]("The Answer", 42)
val pi = NamedValue[Double]("Pi", 333.0/106)
val pi2 = NamedValue("Pi", 333.0/106)
```

На параметры типов можно накладывать ограничения. Границы задаются не строго, тип параметр может включать сам пограничный тип. 
Задание верхней границы — надтипа (upper type bound)
```scala
class NamedValue[A <: AnyRef](name: String, value:A)
// AnyRef -- более общий (базовый) тип по отношению к A
```

Задание нижней границы — подтипа (lower type bound)
```scala
class NamedValue[A >: Nothing](name: String, value:A)
// тип А должен быть надтипом (базовым классом) для Nothing
```

Верхние и нижние ограничения могут быть любыми типами, в том числе определёнными программистом. Ограничения могут ссылать на другие параметры типа.
```scala
class MyClass[A,B <: A](field1:A, field2:B)

val o1 = MyClass[Int,Int](3,4)                    // ok
val o2 = MyClass[String, Int]("some string", 3)   // Error
val o3 = MyClass[Int, String](3,"some string")    // Error
```

## Служебные задачи

#### Структура проекта в IntelIJ IDEA
- build.sbt - описывает настройки сборки
- src -- исходный код
- target -- содержит результаты сборки (бинарные файлы)
- project -- служебные файлы, возникающие во время сборки


#### Добавление и скачивание зависимостей в Intelij IDEA
1. Указать имя и версию зависимости в .sbt файле;\
например: 
```
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.7.0"
)
```

2. Перейти на панель Build (внизу) > вкладка Sync > нажать 🔁 (Reload Sbt Project)

3. Проверить, что библиотеки скачались: Дерево проекта - External libraries


Некоторые библиотеки могут быть доступны для Scala 2, но не для Scala 3.

#### Измерение времени работы кода
```scala
val t0 = System.nanoTime()            // возвращает время в нс с некого фиксированного момента
// some code
val dt = (System.nanoTime()-t0)/1e9
```


#### Аргументы командной строки 
```scala
@main
def main(filename: String, n:Int) = 
  // программа с одним параметром (имя текущего файла не считается)
  // набор параметров функции = минимально необходимый набор аргументов командной строки
  println(f"filename = $filename")
  println(f"n = $n")
```

Примеры запуска:
```bash
scala my_progr some_filename.txt 1234

# Вывод
filename = some_filename.txt
n = 1234
```


```bash
scala procees_log.scala flask.log 234 1 2 another arg

# Вывод
filename = some_filename.txt
n = 1234
```

```bash
scala procees_log.scala flask.log

# Вывод
Illegal command line after first argument: more arguments expected
```
**См. также**
- Scopt — библиотека для разбора аргументов командной строки

[ [https://docs.scala-lang.org/scala3/book/methods-main-methods.html#command-line-arguments](https://docs.scala-lang.org/scala3/book/methods-main-methods.html#command-line-arguments) ]

#### Тестирование
```scala
assert( 4 == 2+2 )
```




## Некторые фреимворки и библиотеки
**Akka** is a source-available toolkit and runtime simplifying the construction of concurrent and distributed applications 
on the JVM. Akka supports multiple programming models for concurrency, but it emphasizes actor-based concurrency, 
with inspiration drawn from Erlang.


**Spark**






# Ссылки
- [Documentation - scala-lang.org/api/3.x](https://scala-lang.org/api/3.x/)
- [Scala cheatsheet - docs.scala-lang.org/cheatsheets/index.html](https://docs.scala-lang.org/cheatsheets/index.html)
