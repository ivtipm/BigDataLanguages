package org.lab;

/*
Пример использования пула потоков для параллельного скачивания страниц.

Задачи для потоков (лямбда-функции на основе Runnable) создаются прямо при передаче в метод submit.
Альтернативное решение - реализация Runnable со всей логикой выполнения запроса.

Данные сохраняются в общий для потоков список с синхронизацией на основе критической секции.
Альтернативное решение - возврат значений из потока (с задачей на основе Callable) и сохранение результатов в список из Future.

*/

import java.lang.Runnable;      // не обязательный импорт
import java.io.InputStream;     // низкоуровневый поток для чтения ответа веб-сервера
import java.io.InputStreamReader;
import java.io.BufferedReader;  // для чтения ответа веб-сервера. умеет выдавать строки
import java.io.IOException;     // исключение чтения потока
import java.net.URL;
import java.net.URLConnection;

import java.util.LinkedList;    // связный список
import java.util.List;          // интерфейс для списка

import java.util.concurrent.*;  // ThreadPoolExecutor, Executors

public class Main {

    /** Выдаёт исходный код HTML страницы по адресу url; Соединение */
    public static String get_page(String url) throws IOException{

        URLConnection con = new URL( url ).openConnection();
        // есть ещё HTTPURLConnection
        con.setReadTimeout( 5000 );        // миллисекунды, чтобы дождаться всех данных
        con.setConnectTimeout( 5000 );  // миллисекунды, чтобы дождаться подключения
        con.addRequestProperty("User-Agent", "Mozilla" );        // иначе сервер может выдавать неполную страницу

        StringBuilder page_builder = new StringBuilder();        // для эффективной склейки строк
        // пробуем создать объекты для чтения
        // это try с ресурсами, который при неудаче вызовет метод close у closable объекта в скобках
        try(InputStream is = con.getInputStream();          // throw IOEXception
            BufferedReader br = new BufferedReader(new InputStreamReader(is)) ){
            String buffer;
            // Чтение из потока. Страница может приходить не целиком
            while( (buffer = br.readLine()) != null ){
                page_builder.append( buffer );
            }
        }

        return page_builder.toString();
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> pages = new LinkedList<>();        // список (на основе связного списка) содержимого страниц

        int pages_count = 70;
        int threads_count = 50;
        // Много потоков, т.к. их основная задача отправка запроса - ожидание - приём, что не требует много ресурсов процессора

        // ============================================= последовательный алгоритм
        long start = System.currentTimeMillis();
        for (int i = 1; i < pages_count; i++) {
            String page = get_page( "https://zabgu.ru/php/news.php?category=1&page=" + i);
            pages.add( page );      // не потокоSбезопасная операция
        }
        long stop = System.currentTimeMillis();
        System.out.println("Скачено страниц:" + pages.size());
        System.out.println( String.format("serial algorithm dt:   %7d ms", stop-start) );


        // ============================================= параллельный алгоритм
        List<String> pages2 = new LinkedList<>();        // список (на основе связного списка) содержимого страниц
        ThreadPoolExecutor thread_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool( threads_count );

        start = System.currentTimeMillis();
        for (int i = 1; i < pages_count; i++) {
            // внутри лямбд можно использовать только final локальные переменные
            final String url = "https://zabgu.ru/php/news.php?category=1&page=" + i;               
            
            // добавление потоку новой работы -- лямбда функции (на основе экземпляра наследника Runnable())
            thread_pool.submit( () -> {
                String page = "";
                try {
                    page = get_page(url);
                    // запишем непотокобезоспасную операцию в критической секции
                    // критическая секция на основе мьютекса pages2; этот фрагмент кода может выполняться только одним потоком в один момент времени;
                    // добавление в список - быстрая операция ( O(1) ) поэтому данная синхронизация на основе критической секции не будет существенно замедлять программу
                    // мьютексом может быть любой общий для потоков объект, необязательно тот, который они используют
                    synchronized (pages2) {  pages2.add(page);  }
                } catch (IOException e) { System.out.println(e.toString()); }
            } );
        }



        // Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
        thread_pool.shutdown();
        try {
            // пул не завершает все потоки сразу, как только кончились задачи ( Runnable или Callable )
            // ждём завершения работы всех потоков ИЛИ Long.MAX_VALUE секунд и удаляем потоки
            thread_pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println( e.getStackTrace() );
        }
        stop = System.currentTimeMillis();

        System.out.println( String.format("parallel algorithm dt: %7d ms", stop-start) );

        System.out.println("Скачено страниц:" + pages2.size());
        // проверка соответствия результатов работы алгоритмов
        assert pages.size() == pages2.size();
        for (int i = 0; i < pages.size(); i++) {
            assert pages.get(i).equals( pages2.get(i) );
        }


//        pages2.forEach(System.out::println);
    }
}

/*

serial algorithm dt:   38009 ms
parallel algorithm dt:  5540 ms

 */