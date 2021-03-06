**Back end organisation management app (REST-API)**

*ПРЕДУСТАНОВКА*

1) Импортируйте все зависимости из "pom.xml"
2) Настройте свойства приложения и базы данных в конфигурационном файле "application.properties" 
3) Запустите приложение для генерации таблиц в БД
4) Исполните sql-запросы в БД из файла "triggers.sql"

*РЕАЛИЗОВАННЫЕ МЕТОДЫ*

**GET /api/employees**

Возвращает список всех сотрудников всех организаций.

**GET /api/employees/{id}**

Возвращает информацию о сотруднике с конкретным идентификатором (id).

**POST /api/employees/add**

Создает нового сотрудника, в тело запроса передается EmployeeDTO.

**PUT /api/employees/update/{id}**

Обновляет информацию о сотруднике с конкретным идентификатором, в тело запроса передается 
EmployeeDTO с новыми данными о сотруднике.

**DELETE /api/employees/update/{id}**

Удаляет сотрудника с конкретным идентификатором.

**GET /api/employees/tree/{id}**

Возвращает древовидное представление сотрудников организации с идентификатором id.

Для организаций **(organisations)** методы те же самые, кроме последнего:

**GET /api/organisations/tree**

Возвращает древовидное представление всех организаций.
