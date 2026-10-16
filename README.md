<<<<<<< HEAD
# ИТхаб, осень 2026: бэкенд на Kotlin / Ktor

Дисциплина: кроссплатформенная разработка на Kotlin.  
Группа работает в **одной приватной репе**. У каждого своя ветка.

## Как работаем

1. Напиши преподавателю логин GitHub — тебя добавят в репу.
2. Склонируй репозиторий.
3. Своя ветка: `git checkout -b students/<логин>`
4. Выбери задание из списка ниже, которое ещё никто не занял, и напиши это в канал.
5. Пушь только в свою ветку. В `main` не пушим. Чужие ветки не трогаем.

## Софт

- JDK 21
- IntelliJ IDEA Community: https://www.jetbrains.com/idea/download
- Git: https://git-scm.com/downloads
- HTTPie Desktop: https://httpie.io/download
- аккаунт GitHub

Docker на первом занятии не нужен. curl — из терминала IDEA или Git Bash.

## Первое занятие

[Готовый проект с первым маршрутом](examples/01-hello-world/) — открыть в IDEA и запустить на JDK 17 или 22.

[Опорные материалы для первой пары](materials/01-first-pair/) — готовый `build.gradle.kts` и инструкция по подключению Ktor.

К концу двух пар сервер на `http://127.0.0.1:8080` отвечает:

| Запрос | Что делает |
|---|---|
| `GET /` | `{"ok": true}` |
| `GET /hello/{name}` | `{"message": "Привет, …"}` |
| `GET /products` | список из 4 товаров: id, name, price |
| `GET /products/{id}` | один товар, нет id — 404 |
| `GET /products?maxPrice=100` | фильтр по цене |

Список в памяти, без базы. В HTTPie — своя коллекция с этими пятью запросами.

Проверка:

```bash
curl -v http://127.0.0.1:8080/
```

Не успел на паре — доделать дома.

## Задание на курс

Цель к концу курса — мини-API сервиса, который без бэкенда не имеет смысла. Стек у всех один: Kotlin, Ktor, потом Postgres, JWT.

Одно задание — один человек. Занятое пометим в таблице.

| # | Как у | Что это | Кто |
|---|---|---|---|
| 1 | Google Drive | файлы и папки | |
| 2 | Кинопоиск | фильмы, оценки, рецензии | |
| 3 | Avito | объявления, поиск, избранное | |
| 4 | Книга рецептов | рецепты и состав | |
| 5 | Макдоналдс | меню и заказ | |
| 6 | Банк | счета и переводы | |

Минимум по каждому — чтобы объём был плюс-минус один:

**Google Drive.** Папки, файлы внутри папки, загрузить, скачать, список, ссылка «посмотреть без логина». Не куча байт без каталога.

**Кинопоиск.** Фильмы (название, год, жанр), поиск, оценка, короткая рецензия. Не справочник без действия пользователя.

**Avito.** Объявление (заголовок, цена, категория, город), лента с фильтром, избранное. Не одна карточка.

**Книга рецептов.** Рецепт, ингредиенты со количеством, поиск «что можно из яиц и муки», категория. Не текст без состава.

**Макдоналдс.** Меню по категориям, позиция с ценой, корзина, заказ, статус заказа. Без оплаты. Не PDF-меню.

**Банк.** Счёт, баланс, перевод между счетами, история операций. Без карт и KYC. Не одно число на экране.

## Второе занятие (КТ2)

Преза: [`slides/02-ktor.html`](slides/02-ktor.html) — открыть локально, как первую.

Инструкция: [`materials/02-kt2/`](materials/02-kt2/) — память, Postgres в Docker, JWT.

## Lesson 2

- Преза: [`slides/02-ktor.html`](slides/02-ktor.html)
- Стартер (открыть папку в IDEA): [`examples/02-kt2-starter`](examples/02-kt2-starter)
- Опорные куски по частям: [`lesson-2/`](lesson-2/)

## Нейросети

Нельзя. Код, запросы и коллекцию каждый пишет сам, руками. Сгенерированное сдать нельзя.

## Преза первого занятия

GitHub файл как слайды не покажет — его надо открыть у себя.

```bash
git clone git@github.com:dermanov-ru/ithub-ktor-2026.git
```

Потом в браузере: File → Open File → `slides/01-intro.html`, или из папки репы:

```bash
xdg-open slides/01-intro.html
```

На Mac: `open slides/01-intro.html`. Скрины софта можно класть рядом в `slides/`.
=======
# Первый маршрут Ktor

Готовый проект: `GET /` возвращает `Hello World!`. Нужны JDK **17 или 22**, Git и интернет для первой загрузки зависимостей. Gradle устанавливать отдельно не нужно.

## Склонировать

```bash
git clone https://github.com/dermanov-ru/ithub-ktor-2026.git
cd ithub-ktor-2026/examples/01-hello-world
```

Репозиторий приватный: нужен доступ от преподавателя и вход в GitHub.

## IDEA: JDK 17 или 22

1. **File → Open** → папка `examples/01-hello-world`. Открывай её как отдельный проект, а не весь репозиторий.
2. **File → Project Structure → Project SDK**: выбери установленный JDK 17 или 22. Если его нет в списке — **Add SDK → JDK** и папка установленного JDK.
3. **Settings → Build, Execution, Deployment → Build Tools → Gradle**: Distribution — **Wrapper**, Gradle JVM — **тот же JDK 17 или 22**.
4. Выполни **Sync All Gradle Projects** и дождись окончания загрузки.
5. Справа **Gradle → Tasks → application → run**, двойной клик. Либо открой `Application.kt` и нажми зелёный треугольник у `main`.
6. Открой http://localhost:8080/. Ответ: `Hello World!`. Красный квадрат Stop останавливает сервер.

## Терминал

Команды выполняй из папки этого проекта.

**macOS**, выбери одну версию:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
# Или вместо предыдущей строки:
export JAVA_HOME=$(/usr/libexec/java_home -v 22)
./gradlew --version
./gradlew run
```

**Linux**, укажи реальный путь к своему JDK 17 или 22:

```bash
export JAVA_HOME=/путь/к/jdk-17
./gradlew --version
./gradlew run
```

**Windows PowerShell**, укажи реальный путь к JDK 17 или 22:

```powershell
$env:JAVA_HOME = "C:\путь\к\jdk-17"
.\gradlew.bat --version
.\gradlew.bat run
```

В `--version` проверь Launcher/Daemon JVM. Терминал IDEA использует `JAVA_HOME`, а кнопка запуска Gradle — настройку **Gradle JVM**. Это разные настройки.

В другом терминале:

```bash
curl -i http://localhost:8080/
```

В PowerShell используй `curl.exe -i http://localhost:8080/`. Должны прийти `200 OK` и `Hello World!`. Остановка в терминале — Ctrl+C.

## JDK и JVM target

JDK 17 и 22 включают JVM для запуска. В проекте **JVM target = 17**: такой байткод работает на обеих версиях. Оставь `jvmTarget`, `sourceCompatibility` и `targetCompatibility` равными 17 даже при JDK 22. `jvmToolchain(17)` здесь не задан: отдельная установка JDK 17 при выбранном JDK 22 не требуется.

Gradle Wrapper 8.14 поддерживает обе версии: [таблица совместимости](https://docs.gradle.org/current/userguide/compatibility.html#java_runtime).

## Если не запускается

- **JAVA_HOME / JDK not found**: укажи установленный JDK, а не папку `bin` и не JRE.
- **Красные импорты Ktor**: проверь интернет, отключённый Offline Mode и дождись Gradle Sync.
- **Main class not found**: точка входа — `com.example.ApplicationKt`, файл `src/main/kotlin/com/example/Application.kt`.
- **Address already in use**: останови другой сервер или выбери порт 18080. В IDEA добавь переменную окружения `PORT=18080` в конфигурации запуска. В macOS/Linux: `PORT=18080 ./gradlew run`. В PowerShell: `$env:PORT="18080"`, затем `.\gradlew.bat run`. Проверяй http://localhost:18080/.
>>>>>>> master
