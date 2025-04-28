# Интерфейсы SAM (Single Abstract Method)

**Что такое SAM?**
SAM (Single Abstract Method) — это интерфейс с **одним абстрактным методом**. Именно такие интерфейсы можно реализовать через лямбды и ссылки на методы. 
Чаще их помечают аннотацией `@FunctionalInterface`, но это не обязательно.

### Зачем нужны функциональные интерфейсы
- **Параметризация поведения** - передача алгоритма (функции, метода) в другой метод без создания большого анонимного класса.
- **Лямбды и ссылки на методы** - код становится короче и понятнее.
- **Совместимость со Stream API и другими библиотеками** - методы `map`, `filter`, `forEach` и т. д. принимают стандартизированные интерфейсы.

## Основные интерфейсы в пакете `java.util.function`
1. **Supplier<T>**
   - Метод: `T get()`
   - Возвращает значение без входных аргументов.

2. **Consumer<T>**
   - Метод: `void accept(T t)`
   - Принимает аргумент, ничего не возвращает.

3. **Function<T,R>**
   - Метод: `R apply(T t)`
   - Преобразует `T` в `R`.
   - Дополнительно: 
        - `andThen(Function<? super R,? extends V> after)` - сначала применяет текущую функцию, затем другую.
        - `compose(Function<? super V,? extends T> before)` сначала применяет другую функцию, затем текущую.
        - `identity()` возвращает функцию, которая возвращает свой аргумент.

4. **Predicate<T>**
   - Метод: `boolean test(T t)`
   - Проверяет условие, возвращает `true` или `false`.
   - Дополнительно: `and()`, `or()`, `negate()`, `isEqual()`.

5. **Runnable**
    Метод: `void run()`  - исполнение без возвращаемого значения

6. **Callable**
    Метод: `V call()` - возвращает значение или бросает исключение

7. **Comparator<T>** 
    Метод: `int compare(T,T)`

5. **UnaryOperator<T>** / **BinaryOperator<T>**
   - Унаследованы от `Function` и `BiFunction`, но возвращают тот же тип, что и принимают.

6. **BiFunction<T,U,R>**, **BiConsumer<T,U>**, **BiPredicate<T,U>**
   Версия для двух аргументов: `apply`, `accept`, `test`.

7. **Специализированные интерфейсы для примитивов**
   Например, `IntFunction<R>`, `DoublePredicate`, `IntUnaryOperator` и др.
10. **ActionListener** (`void actionPerformed(ActionEvent)`), и др.   


В пакете java.util.function 43 интерфейса: базовые (Function, Predicate и др.), версии для двух аргументов (BiFunction и др.) и специализированные для примитивов (IntFunction, DoublePredicate и т. д.).


**Когда какой интерфейс использовать?**

- Runnable/Consumer — для операций без возврата (потоки, колбэки без результата).

- Supplier/Callable — для отложенного получения значения (ленивая инициализация, задачи с результатом).

- Function/UnaryOperator/BiFunction — для преобразований и маппинга данных.

- Predicate/BiPredicate — для проверки условий и фильтрации.

- Comparator — для сравнения и сортировки.

- BinaryOperator — для аккумуляции/сокращения (reduce).

## Примеры использования
```java
// 1. Supplier: текущее время
Supplier<LocalDateTime> now = LocalDateTime::now;
System.out.println(now.get());

// 2. Consumer: печать строк
Consumer<String> print = System.out::println;
Stream.of("A","B","C").forEach(print);

// 3. Function: длина строки
Function<String,Integer> len = String::length;
List<Integer> lens = Stream.of("foo","bar").map(len).collect(toList());

// 4. Predicate: непустая строка
Predicate<String> nonEmpty = s -> !s.isEmpty();
List<String> nonEmptyList = Stream.of("", "x").filter(nonEmpty).collect(toList());

// 5. BinaryOperator: сумма чисел
BinaryOperator<Integer> sum = Integer::sum;
int total = Stream.of(1,2,3).reduce(sum).orElse(0);
```

## Паттерн объектно-ориентированного проектирования Strategy
Паттерн Strategy инкапсулирует алгоритм за интерфейсом, чтобы менять его в процессе работы программы. Функциональный интерфейс в Java — тот же интерфейс стратегии, но без создания отдельного класса. Достаточно передать лямбду или ссылку на метод.


