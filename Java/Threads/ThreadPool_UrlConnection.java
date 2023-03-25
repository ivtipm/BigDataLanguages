package org.example;

import java.lang.Runnable;      // не обязательный импорт
import java.io.InputStream;     // низкоуровневый поток для чтения ответа веб-сервера
import java.io.InputStreamReader;
import java.io.BufferedReader;  // для чтения ответа веб-сервера. умеет выдавать строки
import java.io.IOException;     // исключение чтения потока
import java.net.URL;
import java.net.URLConnection;

import java.util.LinkedList;    // свзяный список
import java.util.List;          // интерфйес для списка

import java.util.concurrent.*;  // ThreadPoolExecutor, Executors

public class Main {

    /** Выдаёт исходный код HTML страницы по адресу url; Соединение */
    public static String get_page(String url) throws IOException{

        URLConnection con = new URL( url ).openConnection();
        // есть ещё HTTPURLConnection
        con.setReadTimeout( 5000 );		// миллисекунды, чтобы дождаться всех данных
        con.setConnectTimeout( 5000 );  // миллисекунды, чтобы дождаться подключения
        con.addRequestProperty("User-Agent", "Mozilla" );		// иначе сервер может выдавать неполную страницу

        StringBuilder page_builder = new StringBuilder();		// для эффективной склейки строк
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
            pages.add( page );      // не потокобезопасная операция
        }
        long stop = System.currentTimeMillis();
        System.out.println( String.format("serial algorithm dt:   %7d ms", stop-start) );


        // ============================================= параллельный алгоритм
        List<String> pages2 = new LinkedList<>();        // список (на основе связного списка) содержимого страниц
        ThreadPoolExecutor thread_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool( threads_count );

        start = System.currentTimeMillis();
        for (int i = 1; i < 70; i++) {
            final int ii = i;               // внутри лямбд можно использовать только final локальные переменные
            // добавление потоку новой работы -- лямбда функции (на основе экземпляра наследника Runnable())
            thread_pool.submit( () -> {
                        String page = "";
                        try {  page = get_page("https://zabgu.ru/php/news.php?category=1&page=" + ii);
                        } catch (Exception e) { }

                        // запишем непотокобезоспасную операцию в критической секции
                        // критическая секция на основе мьютекса pages2; этот фрагмент кода может выполняться только одним потоком в один момент времени;
                        // добавление в писок - быстрая операция ( O(1) ) поэтому данная синхронизация на основе критической секции не будет существенно замедлять программу
                        // мьютексом может быть любой общий для потоков объект, необязательно тот, котоый они используют
                        synchronized (pages2) {  pages2.add(page);  }
                    }
            );
        }

        // пул не завершает все потоки сразу, как только кончились задачи ( Runnable или Callable )
        // потоки пула можно завершить только после таймаута
        // ждём завершения работы всех потоков, потом ещё 100 ms и удаляем потоки
        thread_pool.awaitTermination( 100, TimeUnit.MILLISECONDS);     // InterruptedException
        thread_pool.shutdown();
        stop = System.currentTimeMillis();

        System.out.println( String.format("parallel algorithm dt: %7d ms", stop-start) );

        // проверка соответствия результатов работы алгоритмов
        assert pages.size() == pages2.size();
        for (int i = 0; i < pages.size(); i++) {
            assert pages.get(i).equals( pages2.get(i) );
        }

    }
}

/*

serial algorithm dt:   358404 ms
parallel algorithm dt:    126 ms

 */