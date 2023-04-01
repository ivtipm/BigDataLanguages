// ...

	
	/**  Загружает страницу номер i по адресу url_mask */
	public String get_page_content(int i);
	

	/** выдаёт список из новостей страницы по тексту страницы, содержащей карточки новостей */
	public static List<NewsItem> parse_news(String page);


	/** Парсит новости с count страниц сайта по шаблону адреса url_mask */
	public List<NewsItem> parse_pages(int count) throws IOException{
		if (count < 1) return null;
		// NewsItem - класс описывающий одну новость
		LinkedList<NewsItem> news = new LinkedList<>();
		for (int i = 1; i<=count; i++)
			news.addAll( parse_news( this.get_page_content(i) ));            // throws IOException
		return news; }




	/** Парсит новости с count страниц сайта по шаблону адреса url_mask; использует пул потоков для ускорения
	 * @param thread_timeout -- время ожидания перед запуском потока */
	public List<NewsItem> parse_pages_ll(int count, int threads_n, int thread_timeout) throws IOException, InterruptedException {
		if (count < 1) return null;

		ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads_n);

		LinkedList<Future<List<NewsItem>> > news_f = new LinkedList<>();			// список из будущих результатов
		// каждый вызов get_page_content выдаёт список новостей
		LinkedList<NewsItem> news = new LinkedList<>();								// список результатов

		for (int i = 1; i <= count; i++) {
			// внутри потока нельзя обращаться к переменным, которые можно менять
			// ограничение введено для потокобезопасности
			final int ii = i;
			Future<List<NewsItem>> r =
				pool.submit( () -> {
						// чтобы не выдавать слишком много запросов в единицу времени
						Thread.currentThread().sleep(thread_timeout);
						return parse_news(this.get_page_content( ii )); 	});
			news_f.add( r );
		}

		// завершение потоков, у которых нет задач
		pool.shutdown();
		// блокирующая функция ожидающая завершения потоков или 100 мс (и завершающая потоки)
		pool.awaitTermination(100, TimeUnit.MILLISECONDS);

		for (int i = 0; i < news_f.size(); i++) {
			try { news.addAll( news_f.get(i).get() ); }
			catch (ExecutionException e) { throw new RuntimeException(e); }
		}
		return news;
	}

	

	/** Парсит новости с count страниц сайта по шаблону адреса url_mask; Использует Stream API */
	public List<NewsItem> parse_pages_stream_ll(int count) throws IOException{
		if (count < 1) return null;
		List<NewsItem> news = IntStream.range(1, count+1)			// -> int from 1 to Count
								    .parallel()
									.mapToObj( (i) -> {
											try { return parse_news( this.get_page_content(i)); }
											catch (IOException e) { throw new RuntimeException(e); }
											} ) 						// -> List<NewsItem>
									// применение функции к каждому элементу потока (списку), чтобы преобразовать в поток
									// flatMap сделает поток из потоков просто потоком
									.flatMap( List::stream )
									.toList();
		return news;
	}



