# Maven
Maven - сборщик Java.

Аналоги: gradle, ant;


## Установка
```bash
apt install maven
```
Последняя версия (май 2023) - 3.9.2

Проверка:
```bash
mvn --version
```

Для корректной работы нужно задать значение переменной окружения `JAVA_HOME` - путь к JDK, к папке, в которой лежит папка bin.
Например: `/usr/lib/jvm/java-20-openjdk-amd64`


## Создание проекта
Создание проекта по шаблону `maven-archetype-quickstart`
```bash
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my_project_name -DarchetypeArtifactId=maven-archetype-quickstart
```
`-DgroupId=com.mycompany.app` - имя главного пространства имён программы
`-DartifactId=my_project_name` - название проекта, название JAR файла проекта
Если не указать параметры -DgroupId=com.mycompany.app -DartifactId=my_project_name то они будут запрошены при создании катологов и проекта.

```
|-pom.xml -- Project Object Model -- файл описывающий настройки сборки
|-src     -- папка для исходного кода
  |-main
    |-java
      |-com
        |-mycompany
          |-app
            |-App.java
  |-test
    |-java
      |-com
        |-mycompany
          |-app
            |-AppTest.java
  |-
```
Подробнее про файловую структуру проекта: https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
Другие шаблоны проектов: https://maven.apache.org/guides/introduction/introduction-to-archetypes.html

Для явного указания версии JDK можно в файле `pom.xml` задать версию:
```xml
<properties>
    <maven.compiler.source>20</maven.compiler.source>
    <maven.compiler.target>20</maven.compiler.target>
  </properties>
```

Далее все операции должны происходит из папки в pom файлом.

Компиляция
```bash
mvn compile
```

Запуск тестов (включает compile)
```bash
mvn test
```

Создание JAR файла (включает test)
```bash
mvn package
```

Запуск jar файла
```bash
java -cp jarfilename.jar com.mycompany.MainClass
```


### Зависимости
Зависимости описываются в pom.xml.
Например, junit:
```bash
<dependencies>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>

  </dependencies>
```

Во время первой сборкой maven скачает их в локальный репозиторий.

Если зависимость представлена JAR файлом, то его тоже можно установить в локальный репозиторий (`${user.home}/.m2/repository`)
https://stackoverflow.com/questions/4955635/how-to-add-local-jar-files-to-a-maven-project


Допустимо (но depricated) указывать путь к отдельным файлам напрямую:
```xml
<dependency>
    <groupId>com.sample</groupId>
    <artifactId>sample</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/resources/Name_Your_JAR.jar</systemPath>
</dependency>
```


**Сайт репозитория Maven**
https://mvnrepository.com/

На сайте есть поиск по библиотекам. Для каждой библиотеки приведены примеры описания зависимости в файлах сборки (в частности для Maven и Gradle). Можно отдельно скачать jar файл.

## Ссылки
1. Начальное руководство Maven: https://maven.apache.org/guides/getting-started/index.html
1. Соглашение о файловой структуре проекта: https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html