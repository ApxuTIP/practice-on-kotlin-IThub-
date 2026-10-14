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
