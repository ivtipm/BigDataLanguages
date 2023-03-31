# Повторение
1. Что такое абстрактный класс? Интерфейс(Java)?
1. Потоки и процессы. Общая и раздельная память.
1. Неопределённость параллелизма. Взаимная блокировка. Проблема ABA.
1. Критическая секция. Мьютекс. Атомарность. Потокобезопасность.
2. Закон Амдала.
3. Когда нужны параллельные алгоритмы? Паттерны параллельных процессов. Конкуренция и параллелизм.


# Thread
```java
// пакет java.lang поключается атоматически.
import java.lang.Thread;      // класс для управления потоком
// public class Thread
// extends Object
// implements Runnable: void run()

import java.lang.Runnable;    // интерфейс, метод которого может выполнятся в потоке
// @FunctionalInterface
// public interface Runnable: void run()

import java.util.concurrent.Callable;    // интерфейс, метод которого может выполнятся в потоке и вернуть значение
// @FunctionalInterface
// public interface Callable<V>: V call()

// класс для хранения результата выполнения метода из Callable
import java.util.concurrent.Future;
```

**Thread** — класс Поток. [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Thread.html)]

Каждый поток имеет уникальный индетификатор и имя. Имя задаётся при создании потока и не моежт быть ихменено после.

Методы:
- `getName()` - получить имя потока
- `getPriority()` - получить приоритет потока
- `isAlive()` - определить, выполняется ли поток
- `join()` - ожидать завершение потока
- `run()` - запуск потока. В нём пишите свой код
- `sleep(long millis)` - приостановить поток на заданное время
- `start()` - запустить поток

Некоторые конструкторы:
- `Thread()`
- `Thread(Runnable task)`
- `Thread(Runnable task, String name)`

Код, который должен выполнятся в потоке стоит создавать в отдельном классе, реализующим интерфейс **Runnable** с методом `run()`.
Наследоваться от Thread не стоит, т.к. это нарушит принцип единственной ответственности.

Интерфейс Callable [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/concurrent/Callable.html) ] похож на Runnable [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Runnable.html) ] тем, что оба они разработаны для классов, экземпляры которых будут выполняться в отдельном потоке. Runnable, однако, возвращает результат ( Future [[doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/concurrent/Future.html)]) и не может выбросить проверяемое исключение.




### Пример Thread + Runnable
Класс для параллельного выполнения кода

```java
public class Printer implements Runnable{

    private String s = "|";
    private int n = 1;

    public Printer(String s, int n){
        // TODO: check data
        this.s = s;
        this.n = n;}

    @Override
    public void run() {
        Thread currThread = Thread.currentThread();             // для примера

        for (int i = 0; i < this.n; i++) {
            System.out.print( s );
            try { currThread.sleep( 100 );                 // для примера
            } catch (InterruptedException e){ throw new RuntimeException(e); }     }
    }
}
```
Создание и запуск методов класса Printer в потоках
```Java
Thread th1 = new Thread(new Printer("\\",10) );
Thread th2 = new Thread(new Printer("/",10) );

th1.start();    // вызов метода run() экземпляра класса Printer
th2.start();    // вызов метода run() экземпляра класса Printer

// Ожидание завершения потока
th1.join();     // throws InterruptedException
th2.join();
```

Вывод программы
```
\/\/\/\/\/\/\/\//\/\
```

**Определение методы run при создании объекта потока**
```java
Thread th = new Thread(new Runnable() {
    @Override
    public void run() {
        // do something
    }
});
```

Другие примеры:
- https://github.com/ivtipm/ProcessCalculus/tree/master/examples/example_java_threads
- https://github.com/ivtipm/ProcessCalculus/tree/master/examples/example_java_thread-gui

### Пример Thread + лямбда
```java
// минимальное лямбда-выражение
() -> 42;

// с параметром
(n) -> 1.0 / n;
```

```java
Thread th3 = new Thread( () -> System.out.println("Hello, from || thread") );
th3.start();
th3.join();
```

Более явная запись:
```java
Thread th3 = new Thread(
        new Runnable() {
            @Override
            public void run() { System.out.println("Hello, from || thread"); }
        } );
th3.start();
th3.join();
```

Подробнее про анонимные функции в Java: https://github.com/VetrovSV/OOP/blob/master/examples/java/function-reference.md

Документация: https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/function/package-summary.html

***
Получить число ядер
```java
import java.lang.Runtime;     // класс для взаимодействия со средой выполнения

Runtime.getRuntime().availableProcessors()    // возвращает: количество _ядер_ * количество потоков процессора
```
[Документация](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Runtime.html)

См. также другие методы класса Runtime:
- `exec(String[] cmdarray)` — Executes the specified command and arguments in a separate process.
- `freeMemory()` — amount of free memory in the Java Virtual Machine.
- `gc()` — Runs the garbage collector.
- ....


# Thread Pools
Чаще всего количество подзадач, которые могут быть выполнены в алгоритме параллельно больше числа потоков, которые на устройстве могут быть выполнены *действительно* параллельно.

Тогда решать параллельные задачи приходится по очереди. Например, на процессоре с 4 ядрами можно сначала первые 4 подзадачи, потом следующие 4 и так далее.

В библиотеке Java для организации таких вычислений реализован подход — пулл потоков (thread pool).

![](https://cdn.javarush.com/images/article/decabcf3-8341-429b-9266-a262f8a4152b/800.webp)

На рис. задачи (объекты, методы которых нужно выполнить) обозначены кругами.
Пулл потоков — 6 зелёных прямоугольников.

Результаты вополнения (могут быть теми же самыми объектами, методы которых выполеялись) также могут юыть помещены в очередь.

Основные параметры и методы **ThreadPoolExecutor** [ [doc](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/concurrent/ThreadPoolExecutor.html) ]
- corePoolSize — количество потоков, которые могут постоянно(*) существовать, по умолчанию 0;
- maximumPoolSize
- keepAliveTime  — время по истечении которого, простаивающие (idle) потоки из corePoolSize не будуд завершены (по умолчанию 60).

- `submit` — добавление новой задачи;
  - `public Future<?> submit(Runnable task)`
  - `public <T> Future<T> submit(Runnable task, T result)`
  - `public <T> Future<T> submit(Callable<T> task)`
- `shutdown()` — завершение пула потоков.


Создание пула потоков, добавления новых задач в пул — **FixedThreadPool**.
```Java
import java.util.concurrent.ThreadPoolExecutor;

// Пул из двух потоков
ThreadPoolExecutor executor =
  (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
// Добаление задач -- анонимных функций
// (на самом деле объектов, анонимных классов с переопределённым методом )
// Задача выдаётся на выполнение свободному потоку
executor.submit(() -> {
    Thread.sleep(1000);
    return null;
});
executor.submit(() -> {
    Thread.sleep(1000);
    return null;
});
// Задачи, которым не хватило потоков, будут ожидать пока один из потоков не освободится
executor.submit(() -> {
    Thread.sleep(1000);
    return null;
});

assertEquals(2, executor.getPoolSize());      
assertEquals(1, executor.getQueue().size());

executor.shutdown();
```

Неограниченный пул потоков — **CachedThreadPool**. Максимальное число потоков — максимальное целочисленное значение.
```java
ThreadPoolExecutor executor =
  (ThreadPoolExecutor) Executors.newCachedThreadPool();
executor.submit(() -> {
    Thread.sleep(1000);
    return null;
});
executor.submit(() -> {
    Thread.sleep(1000);
    return null;
});
executor.submit(() -> {
    Thread.sleep(1000);
    return null;
});

assertEquals(3, executor.getPoolSize());
assertEquals(0, executor.getQueue().size());
```
when the threads are not needed anymore, they will be disposed of after 60 seconds of inactivity


**ThreadPoll + Callable = Future**
```
Future<String> response = thread_pool.submit( () -> {
    String page = "";
    try { page = get_page("https://zabgu.ru/php/news.php?category=1&page=" + 1); }
    catch (IOException e) { System.out.println(e.toString()); }
    return page;
  } );

//

// ожидание данных, получение данных 
String response_str = res.get();            // throws ExecutionException
```

**CachedThreadPool + реализация Runnable**
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyRunnable implements Runnable {
    private final String task;

    MyRunnable(String task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
           System.out.println("Executing "+ task +" with "+Thread.currentThread().getName());
        }
        System.out.println();
    }
}

public class Exec3 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 5; i++) {
            Runnable worker = new MyRunnable("Task" + i);
            executor.execute(worker);
        }
        executor.shutdown();  
    }
}
```

**Пример HTTP запросов в параллельных потоках**: [ThreadPool_UrlConnection.java](ThreadPool_UrlConnection.java)

См. также
- ScheduledThreadPoolExecutor
- Thread Pool's Implementation in Guava


# syncronized
Если блок кода помечен ключевым словом synchronized, это значит, что блок может выполняться только одним потоком одновременно.

Критическая секция — весь метод
```java
public synchronized void doSomething() {

   //...логика метода
}
```
Instance methods are synchronized over the instance of the class owning the method, which means only one thread per instance of the class can execute this method.



Критическая секция — часть метода, на освнове мьютекса представленного Object:
```java
private Object obj = new Object();

   public void doSomething() {

       //...какая-то логика, доступная для всех потоков

       synchronized (obj) {

           //логика, которая одновременно доступна только для одного потока
       }
   }
```
