# Spark
Слайды: https://ivtipm.github.io/BigDataLanguages/Spark/slides/Spark.html

Запуск на одной машине:
https://spark.apache.org/docs/latest/spark-standalone.html

# Настройка и установка тестовой среды

Виртуальная машина vs контейнер\
<img src="https://cdn-fiejl.nitrocdn.com/yNfvfiSxoeXhsQRaJFUuQCCZqugXTTRV/assets/images/optimized/rev-912609b/blog/wp-content/uploads/2020/12/container-vms.jpg" width=600>

Docker Engine — самая популярная платформа для запуска контейнеров.

Контейнер — изолированная программная среда, виртуальная машина — изолированная аппаратная среда.

Docker, containerd — самые популярные Container Engine.

Контейнер — отдельный UserSpace — изолированная среда для выполнения приложения.
Образ — дистрибутив контейнера. Существует большое количество готовых образов с популярными программами: nginx, PostgreSQL, NodeJS, Python, ...

### Установка Docker:
#### Linux

```bash
 sudo apt-get install docker-ce docker-ce-cli containerd.io
 ```
Подробности: https://docs.docker.com/engine/install/ubuntu/

Чтобы Докер не требовал права рута для каждой операции нужно добавить текущего пользователя в группу
```
sudo groupadd docker   # если группа не создана
sudo usermod -aG docker $USER
```
Проверка:
```bash
docker run hello-world
```
Команда скачает образ контейнера из репозитория (если он не скачен) и запустит его, перенаправив вывод контейнера в консоль

```
Unable to find image 'hello-world:latest' locally
latest: Pulling from library/hello-world
719385e32844: Pull complete
Digest: sha256:fc6cf906cbfa013e80938cdf0bb199fbdbb86d6e3e013783e5a766f50f5dbce0
Status: Downloaded newer image for hello-world:latest

Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/get-started/
```

#### Windows
https://www.docker.com/


<br>


## Настройка тестового стенда Apache Spark на основе контейнеров Docker
#### 1. Скачать образ
```bash
docker pull apache/spark
```

Посмотреть список скаченных образов:
```bash
docker images
```
```
REPOSITORY        TAG       IMAGE ID       CREATED         SIZE
hello-world       latest    9c7a54a9a43c   2 weeks ago     13.3kB
apache/spark      latest    31ca9f801ec3   4 weeks ago     963MB
apache/zeppelin   0.10.1    e8b5b40b7720   14 months ago   7.81GB
```
TAG как правило обозначает версию и её особенности.\
Удаление образа: `docker image rm <image id>`

*См. также создание собственных образов (с предварительно установленным и настроенными дополнительными программами) с помощью Dockerfile и команды build.*

#### 2. Запуск
  1. Запуск контейнера в интерактивном режиме (REPL)\
     `docker run -it <image name> <cmd-shell-name>` — запустить с подключением ввода (`i`) и терминала (`t`)\
  Выход — Ctrl + C\
  Вместо spark-shell можно запустить командную оболочку ОС — bash: `docker run -it apache/spark bash`\
  По умолчанию контейнер запускается с рабочим каталогом: `/opt/spark/work-dir`

```bash
docker run -it apache/spark /opt/spark/bin/spark-shell
```

  Можно подключится к уже запущенному контейнеру: `docker exec -it <my-container> bash`

  Контейнер работает пока в нём запушена программа или командная оболочка. После остановки контейнера, все изменения в нём (файлы, переменные среды ....) не сохраняются.

  Как правило контейнеры запускаются с дополнительными параметрами, описывающими способ взаимодействия контейнера и основной ОС. Среди них:
  - `-v <host_folder>:<container_folder>` — подключить внешний каталог [ [doc](https://docs.docker.com/storage/bind-mounts/) ]\
    `docker run -v ".:/opt/spark/work-dir" имя_образа` — подключение текущей папки (`.`) в папку контейнера `/opt/spark/work-dir`
  - `-p <external-port>:<container-port>`
    - `external-port` — порт на машине, где запускается контейнер
    - `container-port` — порт внутри контейнера, на котором что-то работает

  Запустить контейнер с доступом к текущей папке, в контейнере запустится программа `/opt/spark/bin/spark-submit` с аргументом `test.py` (файл находится в текущей папке):
  ```bash
  docker run -v .:/opt/spark/work-dir  apache/spark /opt/spark/bin/spark-submit test.py
  ```


<br>


## Настройка тестового стенда Apache Zeppelin — оболочки Spark для работы с интерактивными тетрадками (Scala, R, Python, Notebook)
Для Spark доступны образы Docker:
- https://hub.docker.com/r/apache/spark — Spark
- https://hub.docker.com/r/apache/zeppelin — Zeppilin (~ 3 Гб)


Далее все опарации будут выполняться в командной строке:

1. Скачать образ
```bash
docker pull apache/zeppelin:0.10.1
```

2. Запуск одного контейнера
`docker run [options] <image name>` — команда запуска [ [doc](https://docs.docker.com/engine/reference/commandline/run/) ]


```bash
docker run -p 8080:8080 --rm --name zeppelin apache/zeppelin:0.10.1
```
- `--rm` 		Automatically remove the container when it exits
- `--name` 		Assign a name to the container (по умолчанию для каждого контейнера выбирается случайное имя)

Показать список [запущенных] контейнеров
```bash
docker ps
```

```
CONTAINER ID   IMAGE                    COMMAND                  CREATED          STATUS          PORTS                                       NAMES
1e07a12e5336   apache/zeppelin:0.10.1   "/usr/bin/tini -- bi…"   39 seconds ago   Up 37 seconds   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   zeppelin
```

Теперь интерфейс ноутбуков доступен по адресу: `localhost:8080`

Остановка: `docker stop zeppelin`