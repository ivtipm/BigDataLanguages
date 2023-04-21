# Spark

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Apache_Spark_logo.svg/308px-Apache_Spark_logo.svg.png" width=200>

[Apache Spark](https://spark.apache.org/) – это распределенный фреймворк обработки данных, де-факто стандарт в обработке больших данных.
> Thousands of companies, including 80% of the Fortune 500, use Apache Spark™.
Over 2,000 contributors to the open source project from industry and academia.  [ [spark.apache.org](https://spark.apache.org/) ]

[https://stackshare.io/spark](https://stackshare.io/spark) — Spark в индустрии.

todo: общая характеристика

Варианты и аналоги:
- PySpark
- Docker Containers
- ...


**Установка**
Скачать: https://spark.apache.org/downloads.html
- Pre build for Apache Hadoop
- Pre build with user-provided Apache Hadoop (без Apache Hadoop в коплекте)

Linux:
1. Распаковать архив в `/opt/spark`
2. Задать значение переменной окружения `SPARK_HOME`, добавить `spark/bin` в `PATH`
  ```bash
  export SPARK_HOME=/opt/spark
  export PATH=$SPARK_HOME/bin:$PATH
  ```
  Проверить:
  ```bash
  printenv SPARK_HOME
  ```


**Основные инструменты**
- `spark-shell`
- `Appache Zeppelin` (устанавливается отдельно)



Структура\
<img src="https://habrastorage.org/r/w1560/getpro/habr/upload_files/e61/de0/97f/e61de097ff4f435c96268e7fff8bc02f.png" width=350>

# SparkML
- Аналог DataFrame
  - параллельный


# Ссылки
- https://habr.com/ru/companies/otus/articles/653033
- https://spark.apache.org  