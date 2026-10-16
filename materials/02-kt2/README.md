# День 2: КТ2

Три части: CRUD в памяти → тот же CRUD в Postgres → вход по JWT.

Новый проект не создаём. Открываем вчерашний или `examples/01-hello-world`.

## Часть 1. Память

В `build.gradle.kts` (версия Ktor как в проекте, у нас `3.3.3`):

```kotlin
plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.2.21"
    application
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.5.21")
}
```

Sync Gradle. В `Application.kt`: `install(ContentNegotiation) { json() }`.

Свой data class, `mutableList`, маршруты:

| Метод | Путь | Код |
|---|---|---|
| GET | `/items` | 200 список |
| GET | `/items?category=` | 200 фильтр |
| GET | `/items/{id}` | 200 или 404 |
| POST | `/items` | 201 |
| PUT | `/items/{id}` | 200 или 404 |
| DELETE | `/items/{id}` | 204 или 404 |

Кривой JSON — 400. Коллекция в HTTPie (Postman можно).

## Часть 2. Postgres в Docker

```bash
docker run --name ithub-pg -d \
  -e POSTGRES_PASSWORD=pass \
  -e POSTGRES_DB=shop \
  -p 5432:5432 \
  postgres:16
```

Зависимости:

```kotlin
implementation("org.jetbrains.exposed:exposed-core:0.57.0")
implementation("org.jetbrains.exposed:exposed-jdbc:0.57.0")
implementation("org.postgresql:postgresql:42.7.4")
```

Подключение: `jdbc:postgresql://127.0.0.1:5432/shop`, user `postgres`, password `pass`.

Таблица — `object ... : Table()`. Все insert/select/update/delete только внутри `transaction { }`. В `main` один раз `SchemaUtils.create(...)`.

Проверка: POST, убить сервер, поднять, GET тот же id. Контейнер не `rm`.

## Часть 3. BCrypt и JWT

```kotlin
implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
implementation("at.favre.lib:bcrypt:0.10.2")
```

Пользователь: email + passwordHash. Пароль текстом не храним.

- `POST /register` — хеш BCrypt, сохранить пользователя. Без токена.
- `POST /login` — сверить хеш, отдать `{ "token": "..." }`. Без токена.
- Остальные `/items` — заголовок `Authorization: Bearer <token>`. Нет или битый — 401.

Секрет подписи — строка в коде на занятии, например `ithub-secret`. В HTTPie: Login → копируем token → заголовок на GET/POST/PUT/DELETE. Без заголовка тот же GET должен дать 401.

## Exposed, BCrypt, JWT — коротко преподавателю

**Exposed** — SQL-обёртка JetBrains. Не JPA. Таблица описывается объектом, запросы пишутся Kotlin-кодом, выполняются в `transaction`. Драйвер Postgres отдельно.

**BCrypt** — односторонняя функция. `hashToString` при регистрации, `verify` при логине. Из хеша пароль не достать.

**JWT** — подписанная строка. Сервер после логина кладёт в неё email и ставит подпись секретом. Клиент шлёт строку обратно. Плагин Ktor проверяет подпись, в handler уже «этот запрос от email X». Это не сессия в Redis и не cookie: билет лежит у клиента.
