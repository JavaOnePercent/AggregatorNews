# AggregatorNews

## Описание проекта

Web-приложение аггрегатор новостей, позволяющее загружать, просматривать, искать новости

## Настройка и запуск проекта

### Параметры базы
* Откройте файл [/main/resources/application.properties](https://github.com/JavaOnePercent/AggregatorNews/blob/master/aggregator-backend/src/main/resources/application.properties)
* Установите значения параметрам ```spring.datasource.username``` и ```spring.datasource.password```

### Сборка клиентской части
* Перейдите в директорию aggregator-frontend
* В командной строке с помощью команды ```npm install``` установите необходимые пакеты
* Выполните команду ```npm run build```, чтобы собрать webpack
* Запустите файл move-bundle.py
* Запустите web-приложение с помощью [/main/java/com/news/aggregator/AggregatorApplication.java](https://github.com/JavaOnePercent/AggregatorNews/blob/master/aggregator-backend/src/main/java/com/news/aggregator/AggregatorApplication.java)
* Приложение запустится на [localhost:8080](http://localhost:8080)

### Без сборки клиентской части
* Перейдите в директорию aggregator-frontend
* В командной строке с помощью команды ```npm install``` установите необходимые пакеты
* Выполните команду ```npm run start```
* Запустите приложение с помощью [/main/java/com/news/aggregator/AggregatorApplication.java](https://github.com/JavaOnePercent/AggregatorNews/blob/master/aggregator-backend/src/main/java/com/news/aggregator/AggregatorApplication.java)
* Перейдите по адресу [localhost:3000](http://localhost:3000)
