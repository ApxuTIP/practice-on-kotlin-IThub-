# Стартер КТ2

Готовый проект: JSON уже подключён, список в памяти **пустой**, работают два GET.

```
GET /            → {"ok":true}
GET /items       → []
GET /items/{id}  → 404, пока ничего не создали
```

POST, PUT, DELETE дописываешь из `lesson-2/01-memory/`. Класс `Item` потом переименуй под свой домен.

Нужны JDK **17 или 22**, Git, интернет на первую загрузку Gradle.

## Склонировать и открыть

```bash
git clone https://github.com/dermanov-ru/ithub-ktor-2026.git
cd ithub-ktor-2026/examples/02-kt2-starter
```

В IDEA: **File → Open** → папка `examples/02-kt2-starter` как отдельный проект. Gradle JVM = JDK 17 или 22. **Sync**, потом `run` у `main`.

Проверка:

```bash
curl -v http://127.0.0.1:8080/
curl -v http://127.0.0.1:8080/items
```

Порт занят — `PORT=18080 ./gradlew run`.
