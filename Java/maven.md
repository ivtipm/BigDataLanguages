# Сборщик Maven
## Установка и настрока
1. JDK
2. [Maven](https://maven.apache.org/download.cgi)
  - последняя версия на май 2023 — 3.9.1
  Проверить версию:
  ```bash
  mvn --version
  ```
3. Задать путь к JDK через переменную окружения JAVA_HOME
  ```bash
  # linux
  export JAVA_HOME=<path=to-jdk-folder>
  # например: /usr/lib/jvm/java-20-openjdk-amd64/
  ```
  
## https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

## Создание шаблона проекта
`pom.xml` — файл конфигурации сборки.

Файл сборки и структуру каталогов можно создавать вручную или с помощью шаблонов из Maven.

Шаблон простого приложения:
```bash
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4
```


