// Добавить в plugins { } рядом с kotlin("jvm"):
kotlin("plugin.serialization") version "2.2.21"

// Добавить в dependencies { }, ktorVersion тот же, что в проекте (3.3.3):
implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
