# 🌐 hh.ru — API Automated Tests

Автоматизированные API-тесты для эндпоинтов hh.ru, реализованные на **REST-assured**, **JUnit 5**, **Gradle** и **Allure**. Модели на **Lombok**, спецификации на **RequestSpecBuilder**, кастомные Allure-шаблоны для request/response.

Часть дипломного проекта **QA.Guru** (Java Base): [UI](https://github.com/sadchill82/hh-ui-tests) / API / [Mobile](https://github.com/sadchill82/hh-mobile-tests) / [Manual](https://github.com/sadchill82/hh-manual-tests).

---

## 🔧 Стек технологий

| Инструмент | Назначение |
|---|---|
| [![Java 21](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/) | Язык программирования |
| [![Gradle](https://img.shields.io/badge/Gradle-Kotlin_DSL-02303A?logo=gradle&logoColor=white)](https://gradle.org/) | Система сборки |
| [![REST-assured](https://img.shields.io/badge/REST_assured-5.5-6DB33F?logo=spring&logoColor=white)](https://rest-assured.io/) | HTTP-клиент для тестов |
| [![JUnit 5](https://img.shields.io/badge/JUnit_5-5.11-25A162?logo=junit5&logoColor=white)](https://junit.org/junit5/) | Тестовый фреймворк |
| [![Allure](https://img.shields.io/badge/Allure-2.x-0097A7?logo=qameta&logoColor=white)](https://docs.qameta.io/allure/) | Отчёты о тестах |
| [![Lombok](https://img.shields.io/badge/Lombok-1.18-BC4521?logo=lombok&logoColor=white)](https://projectlombok.org/) | Аннотации для моделей |
| [![WireMock](https://img.shields.io/badge/WireMock-3.10-FF6C0C?logo=wiremock&logoColor=white)](https://wiremock.org/) | Mock-сервер для стабов |
| [![Jackson](https://img.shields.io/badge/Jackson-2.18-000000?logo=apachemaven&logoColor=white)](https://github.com/FasterXML/jackson) | Сериализация / snake_case |

---

## 📁 Структура проекта

- `hh-api-tests/`
  ├─ `build.gradle.kts`
  ├─ `gradlew / gradlew.bat`
  └─ `src/test/`
  &nbsp;&nbsp;&nbsp;&nbsp;├─ `java/guru/qa/hhapi/`
  &nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├─ `api/` — Step-классы (Vacancy/Area/Employer/DictionaryApi)
  &nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├─ `config/` — ApiConfig + Project
  &nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├─ `helpers/` — CustomAllureRestAssured
  &nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├─ `models/` — Vacancy, VacanciesResponse, Salary, Employer, Area
  &nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├─ `specs/` — HhSpecs
  &nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├─ `support/` — WireMockServerHolder
  &nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;└─ `tests/` — BaseApiTest + 5 классов тестов
  &nbsp;&nbsp;&nbsp;&nbsp;└─ `resources/`
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─ `config/prod.properties`
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─ `tpl/` — http-request.ftl, http-response.ftl
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─ `wiremock/` — mappings + __files

---

## 🔌 Про WireMock

Изначально целил на живой `api.hh.ru`. Что получилось:

- `/areas`, `/dictionaries` — работают без авторизации
- `/vacancies`, `/employers` — `403 Forbidden` без OAuth-токена
- User-Agent проверяется по чёрному списку, `example.com` отдаёт `400 blacklisted` ([BUG-002](https://github.com/sadchill82/hh-manual-tests/blob/main/bugs/BUG-002-api-blacklist-example-com.md))

Чтобы тесты не зависели от чужой капчи и личного OAuth-токена — WireMock с фикстурами один в один под схему hh.ru: `snake_case`, реальные id (Москва = 1, Россия = 113), реальные компании. Переключение на живой API — поменять `baseURI` в `BaseApiTest`.

---

## 🚀 Запуск проекта

### 📌 Предусловия

- Java 21
- Gradle (через `./gradlew`)

### ▶️ Запуск тестов

```bash
./gradlew test
```

WireMock поднимается в `@BeforeAll` на случайном порту, выключается shutdown-хуком. Внешний сервис не нужен.

### Параметры

| Ключ | По умолчанию | Описание |
|---|---|---|
| `env` | `prod` | какой `*.properties` грузить |
| `baseUri` | `https://api.hh.ru` (переопределяется WireMock) | целевой API |
| `hhToken` | пусто | OAuth-токен (для живого API) |

---

## 📊 Allure-отчёт

```bash
./gradlew allureServe
```

В `src/test/resources/tpl/` лежат кастомные Freemarker-шаблоны:

- **`http-request.ftl`** — карточка запроса: метод, URL, headers, body, cURL
- **`http-response.ftl`** — карточка ответа: цветной статус, headers, тело

Подключены через `CustomAllureRestAssured.withTemplates()` в `HhSpecs`.

---

## ✅ Покрытие тестами

**17 тестов:**

| Класс | Тесты |
|---|---|
| **VacancySearchTests** | базовый поиск, параметризованный (QA Automation, Java, Python, DevOps), фильтр `area=1`, `only_with_salary`, `per_page`, GET по id |
| **AreaTests** | дерево `/areas` с Россией (id=113), `/areas/1` → Москва |
| **DictionaryTests** | experience, employment, currency |
| **EmployerTests** | поиск по компании |
| **NegativeApiTests** | 404 для `GET /vacancies/0`, 400 для `per_page=999` |

---

## 📃 Лицензия

Проект создан в рамках курса **QA.Guru** (Java Base) и предназначен для обучения.
