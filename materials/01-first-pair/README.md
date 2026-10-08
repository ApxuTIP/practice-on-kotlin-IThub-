# Опорные материалы: первая пара

## Готовые настройки Gradle

[build.gradle.kts](build.gradle.kts) — настройки для Kotlin, Ktor и JDK 21. Зависимости перепечатывать не нужно.

1. Создай в IDEA проект Kotlin → Gradle → Kotlin DSL с JDK 21.
2. Открой файл `build.gradle.kts` из этой папки и скопируй всё содержимое.
3. Замени им содержимое `build.gradle.kts` **в корне своего проекта**. Не создавай второй файл внутри `src`.
4. Нажми **Load Gradle Changes** (или **Sync Gradle Project**) и дождись загрузки зависимостей. Для первой загрузки нужен интернет.
5. Создай `src/main/kotlin/com/example/Application.kt` с пакетом `package com.example`. Код маршрута пишем на занятии.
6. Запусти `main` в IDEA или задачу Gradle `run`.

Что здесь настроено:

- Kotlin JVM и плагин `application` — сборка и запуск приложения.
- `ktor-server-core-jvm` — сервер и маршруты Ktor.
- `ktor-server-netty-jvm` — движок Netty.
- `logback-classic` — вывод логов.
- `jvmToolchain(21)` — JDK 21 для проекта.
- `mainClass` — `com.example.ApplicationKt`, точка входа из `Application.kt`.
- Зависимости тестов — для проверки маршрутов позднее.

Gradle Wrapper (`gradlew`, `gradlew.bat`, папку `gradle`) и `settings.gradle.kts`, созданные IDEA, оставь в своём проекте. Это опорный файл настроек, а не отдельный готовый проект.

В примерах урока используется порт **8080**. На скриншотах преподавателя — **18080**, потому что 8080 занят. В curl и HTTPie указывай тот порт, который задан в твоём коде сервера.
