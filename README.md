# Order Service

Учебное веб-приложение для учёта заказов небольшого сервиса (кафе).
Написано на Spring Boot.

## Стек

- Java 21, Spring Boot 4.1
- PostgreSQL + Spring Data JPA
- Thymeleaf (веб-интерфейс)
- Spring Security (роли ADMIN/EMPLOYEE)
- Flyway (миграции БД)
- JUnit 5 + Mockito (тесты)
- Docker / Docker Compose

## Возможности

- CRUD для товаров, покупателей, заказов и позиций заказа — через REST API и веб-интерфейс
- Подсчёт суммы заказа на основе позиций
- Авторизация с разграничением прав по ролям
- Обработка ошибок (например, попытка удалить покупателя со связанными заказами)

## Как запустить

Через Docker Compose (приложение + база данных):

\`\`\`bash
docker compose up --build
\`\`\`

Приложение будет доступно на `http://localhost:8080`

Тестовые пользователи:
- `admin` / `admin123` — полный доступ
- `employee` / `employee123` — без права на удаление

## Структура

- `/api/products`, `/api/customers`, `/api/orders`, `/api/order-items` — REST API
- `/products/page`, `/customers/page`, `/orders/page`, `/order-items/page` — веб-интерфейс
