# Отчет Spring Boot Starter Example

## Описание проекта

Этот Spring Boot Starter предназначен для централизованного логирования HTTP-запросов и ответов в вашем приложении. Он поддерживает гибкую настройку логирования, включая возможность логировать только запросы, только ответы или оба варианта, а также выбор HTTP-методов, которые необходимо логировать. Основные компоненты Starter включают интерсепторы для логирования запросов и ответов, конфигурацию логирования и проверку валидности настроек на этапе запуска.

## Инструкции по запуску

### Клонирование репозитория

```bash
git clone https://github.com/username/repository.git
```

### База данных
Проект использует базу данных для хранения данных о пользователях и заказах. По умолчанию используется база данных PostgreSQL.
Для настройки подключения к базе данных, переименуйте файл `application.properties.origin` из директории `src/main/resources/` в `application.properties` и укажите в нем следующие параметры:

```
spring.datasource.url=jdbc:ваша_база_данных_url
spring.datasource.username=ваш_пользователь
spring.datasource.password=ваш_пароль
```

### Конфигурация логирования 

Starter поддерживает гибкую конфигурацию через файл application.properties. Вот пример конфигурации:

```
my-logging.enabled=true               # Включить или отключить логирование
my-logging.log-requests=true          # Логировать запросы
my-logging.log-responses=true         # Логировать ответы

# Методы, которые должны логироваться (значения перечисляются через запятую)
my-logging.logging-methods=GET,POST

# Уровень логирования
my-logging.log-level=INFO

# Формат логирования запросов
my-logging.log-request-format=Request: Method = #{#method}, URL = #{#url}, Locale = #{#locale}, Headers = #{#headers}, Query Params: #{#queryString}

# Формат логирования ответов
my-logging.log-response-format=Response: Code = #{#responseCode}, Headers = #{#headers}, Locale = #{#locale}, Processing time = #{#processingTime} ms
```
### Сборка и запуск приложения
Перейдите в директорию проекта и выполните следующие команды:

```bash
mvn install
mvn spring-boot:run
```

### Тестирование
Для запуска тестов выполните команду:

```bash
mvn test
```

## Документация Logging Starter

### Конфигурационные свойства, определяемые в application.properties:

- `my-logging.enabled`: Включает или отключает весь механизм логирования.
- `my-logging.log-requests`: Включает или отключает логирование запросов.
- `my-logging.log-responses`: Включает или отключает логирование ответов.
- `my-logging.logging-methods`: Список методов, которые должны логироваться (GET, POST и т.д.).
- `my-logging.log-level`: Уровень логирования для запросов и ответов(TRACE, DEBUG, INFO, WARN, ERROR).
- `my-logging.log-request-format`: Формат логирования запросов.
- `my-logging.log-response-format`: Формат логирования ответов.

### Теги формата логирования:
Текст логов формируется с помощью тегов. Какие они бывают:
- `method`: HTTP-метод запроса (например, GET, POST).
- `url`: Полный URL запроса.
- `locale`: Локаль запроса (например, en_US).
- `headers`: Заголовки HTTP-запроса или ответа.
- `queryString`: Параметры запроса из строки URL.
- `responseCode`: HTTP-статус ответа (например, 200, 404).
- `processingTime`: Время обработки запроса в миллисекундах.

Чтобы их использовать, нужно в параметре `log-response-format` или `log-request-format` прописать место для их вставки - `#{#<тег>}`. Например: `#{#method}`

### Пример логов:
```
2024-08-19 01:39:18,518 [WARN] o.d.s.a.i.RequestLoggingInterceptor [http-nio-8080-exec-1] - Request: Method = GET, URL = http://localhost:8080/orders/getAll, Locale = ru_RU, Headers = [user-agent=PostmanRuntime/7.41.1; accept=*/*; cache-control=no-cache; postman-token=67524305-ee69-456f-872d-9153602f0116; host=localhost:8080; accept-encoding=gzip, deflate, br; connection=keep-alive; ], Query Params:
2024-08-19 01:39:18,774 [WARN] o.d.s.a.i.RequestLoggingInterceptor [http-nio-8080-exec-1] - Response: Code = 200, Headers = [Content-Type=application/json; Transfer-Encoding=chunked; Date=Sun, 18 Aug 2024 22:39:18 GMT; Keep-Alive=timeout=60; Connection=keep-alive; ], Locale = ru_RU, Processing time = 256 ms
```

## Документация API

### OrderController

Контроллер для управления заказами.

### 1. Создание заказа

- **Метод**: `POST`
- **URL**: `/orders/create/{userId}`
- **Параметры**:
    - `userId` (PathVariable, `Long`): Идентификатор пользователя, для которого создается заказ.
- **Тело запроса**: JSON с данными заказа (`OrderDTO`).
- **Ответы**:
    - `201 CREATED`: Возвращает созданный заказ (`OrderDTO`).
    - `404 NOT FOUND`: Возвращает пустое тело в случае ошибки.

**Пример запроса**:

```json
{
    "description": "description",
    "status": "status"
}
```

### 2. Получение всех заказов

- **Метод**: `GET`
- **URL**: `/orders/getAll`
- **Ответы**:
    - 200 OK: Возвращает список всех заказов.

**Пример ответа**:

```json
[
  {
    "id": 5,
    "description": "Заказ 1",
    "status": "В пути",
    "userId": 1
  },
  {
    "id": 5,
    "description": "Заказ 2",
    "status": "Доставлен",
    "userId": 1
  }
]

```

### 3. Получение заказа по ID

- **Метод**: `GET`
- **URL**: `/orders/{id}`
- **Параметры**:
  - 'id' (PathVariable, Long): Идентификатор заказа.
- **Ответы**:
    - 200 OK: Возвращает данные заказа.
    - 404 NOT FOUND: Если заказ не найден.

**Пример ответа**:

```json
{
  "id": 3,
  "description": "description",
  "status": "status",
  "userId": 1
}

```

### 4. Обновление заказа

- **Метод**: `PUT`
- **URL**: `/orders/{id}`
- **Параметры**:
    - 'id' (PathVariable, Long): Идентификатор заказа.
- **Тело запроса**: JSON с обновленными данными заказа (OrderDTO).
- **Ответы**:
  - 200 OK: Возвращает обновленный заказ.
  - 404 NOT FOUND: Если заказ не найден.

### 5. Удаление заказа
- **Метод**: `DELETE`
- **URL**: `/orders/{id}`
- **Параметры**:
    - 'id' (PathVariable, Long): Идентификатор заказа.
- **Ответы**:
  - 200 OK: Если заказ успешно удален.

### UserController

Контроллер для управления пользователями.

### 1. Создание пользователя
- **Метод**: `POST`
- **URL**: `/users/create`
- **Тело запроса**: JSON с данными пользователя (UserDTO).
- **Ответы**:
    - 201 CREATED: Возвращает созданного пользователя (UserDTO).
** Пример запроса **

```json
  {
  "name": "john_doe",
  "email": "john.doe@example.com"
  }
```

### 2. Получение всех пользователей
- **Метод**: `GET`
- **URL**: `/users/getAll`
- **Ответы**:
    - 200 OK: Возвращает список всех пользователей (List<UserDTO>).

** Пример ответа**
```json
[
    {
      "id": 1,
      "name": "test",
      "email": "2222@mail.ru",
      "orders": [
        {
          "id": 5,
          "description": null,
          "status": null,
          "userId": 1
        }
      ]
    },
    {
    "id": 2,
    "name": "hello",
    "email": "33333@mail.ru",
    "orders": [
        {
            "id": 3,
            "description": "desc",
            "status": "status",
            "userId": 2
        }
      ]
    }
  ]

```

### 3. Получение пользователя по ID
- **Метод**: `GET`
- **URL**: `/users/{id}`
- **Параметры**:
  - id (PathVariable, Long): Идентификатор пользователя.
- **Ответы**:
  - 200 OK: Возвращает данные пользователя (UserDTO).
  - 404 NOT FOUND: Если пользователь не найден.

** Пример ответа**
```json
{
  "id": 2,
  "name": "hello",
  "email": "33333@mail.ru",
  "orders": [
    {
      "id": 3,
      "description": "desc",
      "status": "status",
      "userId": 2
    }
  ]
}
```

### 4. Обновление пользователя
- **Метод**: `PUT`
- **URL**: `/users/{id}`
- **Параметры**:
  - id (PathVariable, Long): Идентификатор пользователя.
- **Тело запроса**: JSON с обновленными данными пользователя (UserDTO).
- **Ответы**:
  - 200 OK: Возвращает обновленного пользователя (UserDTO).
  - 404 NOT FOUND: Если пользователь не найден.

** Пример запроса **

```json
  {
  "name": "john_doe",
  "email": "john.doe@example.com"
  }
```

### 5. Удаление пользователя
- **Метод**: `DELETE`
- **URL**: `/users/{id}`
- **Параметры**:
    - id (PathVariable, Long): Идентификатор пользователя.
- **Ответы**:
    - 200 OK: Если пользователь успешно удален.


