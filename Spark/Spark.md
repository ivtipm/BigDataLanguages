# Spark
Слайды: https://ivtipm.github.io/BigDataLanguages/Spark/slides/Spark.html

### Запуск на одной машине:
https://spark.apache.org/docs/latest/spark-standalone.html

Далее предполагается, что путь к папке Spark указан в Path

1. Запуск основного (координирующего) сервера
```bash
./sbin/start-master.sh
# или
# запуск с интерфейсом на сетевом интерфейсе хоста -h 127.0.0.1 
# с портом -p 7034 для взаимодействия с Workers
# и web интерфейсом на порту 8080
./sbin/start-master.sh -h 127.0.0.1 -p 7034 --webui-port 8080
```

Далее, все остальные узлы-исполнители (даже запущенные на той же машине) будут взаимодействовать с сервером через сетевой интерфейс по его Master URL формата `spark://HOST:PORT`.

С запуском основного сервера становится доступна веб-страница его состояния:\
<img src=images/master-empty.png width=400>

2. Запуск исполнителей (workers)
```bash
./sbin/start-worker.sh spark://127.0.0.1:7034
```

`spark://127.0.0.1:7034` — URL основного сервера.
Теперь страница состояния выглядит так:\
<img src=images/master-with-worker.png width=600>


3. Запуск интерактивной оболочки или программы на кластере
```bash
./bin/spark-shell --master spark://127.0.0.1:7034 test.py
```

<img src=images/master-with-task.png width=600>


4. Остановка всех серверов:
```bash
./sbin/stop-all.sh
# остановить только главный сервер
./sbin/stop-master.sh
# остановить только исполнителей
./sbin/stop-workers.sh
```




<br>




# Настройка и установка тестовой среды на основе контейнеров Docker

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

### Сеть
По умолчанию контейнеры работают в одной виртуальной локальной сети с адресами вида 172.17.0.0 и маской
255.255.0.0.
Узнать IP адрес из контейнера можно стандартной программой `ifconfig`
```bash
ifconfig
```

```text
eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 172.17.0.2  netmask 255.255.0.0  broadcast 172.17.255.255
        ether 02:42:ac:11:00:02  txqueuelen 0  (Ethernet)
        RX packets 26  bytes 4715 (4.7 KB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 0  bytes 0 (0.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
```
<br>


## Настройка тестового стенда Apache Spark на основе контейнеров Docker
### Скачать образ
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

### Запуск одного узла
  1. Запуск контейнера в интерактивном режиме (REPL)\
     `docker run -it <image name> <cmd-shell-name>` — запустить с подключением ввода (`i`) и терминала (`t`)\
  Выход — Ctrl + C\
  Вместо spark-shell можно запустить командную оболочку ОС — bash: `docker run -it apache/spark bash`\
  По умолчанию контейнер запускается с рабочим каталогом: `/opt/spark/work-dir`

```bash
docker run -it apache/spark /opt/spark/bin/spark-shell
```

Показать список [запущенных] контейнеров
```bash
docker ps
```

```
CONTAINER ID   IMAGE                    COMMAND                  CREATED          STATUS          PORTS                                       NAMES
1e07a12e5336   apache/zeppelin:0.10.1   "/usr/bin/tini -- bi…"   39 seconds ago   Up 37 seconds   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   zeppelin
```

  Можно подключится к уже запущенному контейнеру: `docker exec -it <my-container> bash`

  Контейнер работает пока в нём запушена программа или командная оболочка. После остановки контейнера, все изменения в нём (файлы, переменные среды ....) не сохраняются.

  Как правило контейнеры запускаются с дополнительными параметрами, описывающими способ взаимодействия контейнера и основной ОС. Среди них:
  - `-v <host_folder>:<container_folder>` — подключить внешний каталог [ [doc](https://docs.docker.com/storage/bind-mounts/) ]\
    `docker run -v ".:/opt/spark/work-dir" имя_образа` — подключение текущей папки (`.`) в папку контейнера `/opt/spark/work-dir`
  - `-p <external-port>:<container-port>`
    - `external-port` — порт на машине, где запускается контейнер
    - `container-port` — порт внутри контейнера, на котором что-то работает

  Запустить контейнер с доступом к текущей папке, в контейнере запустится программа `/opt/spark/bin/spark-submit` с аргументом `test.jar` (файл находится в текущей папке):
  ```bash
  docker run -v .:/opt/spark/work-dir  apache/spark /opt/spark/bin/spark-submit test.jar
  ```
  Программа должна содержать параллельный алгоритм.



<br>



### Запуск кластера из контейнеров (интерактивный режим контейнера)
Кластер будет состоять из двух узлов: 
- главный, где запущен основной сервер (master) и исполнитель (worker)
- только исполнитель (worker)


Запуск главного контейнера с пробросом порта страницы состояния кластера (`-p 8080:8080`), монтированием текущей папки в контейнер (`-v .:/opt/spark/work-dir `) и запуском командной оболочки после старта контейнера (`it .... bash`)
```bash
docker run -p 8080:8080 -it -v .:/opt/spark/work-dir apache/spark  bash
```
Предположим, контейнер получил адрес 172.17.0.2. Для взаимодействия с другими узлами по умолчанию используется порт 7077, порт 8080 — для веб-страницы состояния.

Запуск основного сервера Spark и исполнителя:
```bash
/opt/spark/sbin/start-master.sh --webui-port 8080
# указать MASTER_URL 
/opt/spark/sbin/start-worker.sh spark://172.17.0.2:7077
```
Дальше, можно проверить состояние кластера по адресу 172.17.0.2

Запуск узла исполнителя, здесь страница состояния не представляет интереса, поэтому явно не указывается порт
```bash
docker run -it -v .:/opt/spark/work-dir apache/spark  bash
```
Запуск исполнителя на кластере:
```bash
/opt/spark/sbin/start-worker.sh spark://172.17.0.2:7077
```

Результат на странице состояния кластера:\
<img src="images/master-with-workers (docker).png" width=500>
<br>


Запустить на кластере spark-shell с любого узла
```bash
/opt/spark/bin/spark-shell --master spark://172.17.0.2:7077
```

Пример тестовой **параллельной** программы
```scala
val NUM_SAMPLES=10000
var count = sc.parallelize(1 to NUM_SAMPLES).filter { _ =>
  val x = math.random
  val y = math.random
  x*x + y*y < 1
}.count() * 4/(NUM_SAMPLES.toFloat)
```


## Настройка тестового стенда Apache Zeppelin — оболочки Spark для работы с интерактивными тетрадками (Scala, R, Python, Notebook)
Для Spark доступны образы Docker:
- https://hub.docker.com/r/apache/spark — Spark
- https://hub.docker.com/r/apache/zeppelin — Zeppilin (~ 3 Гб)


Далее все операции будут выполняться в командной строке:

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


Теперь интерфейс ноутбуков доступен по адресу: `localhost:8080`

Остановка: `docker stop zeppelin`