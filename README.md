# 📝 Full-Stack To-Do List Application
A responsive, full-stack To-Do List application that allows users to manage their daily tasks seamlessly. Scalable architecture powered by a **Java backend** and a persistent **PostgreSQL database**.

## 🚀 Features
*   **Create, Read, Update, Delete (CRUD):** Add new tasks, edit existing text, toggle completion status, and remove tasks permanently.
*   **Persistent Database Storage:** Tasks are securely stored in a cloud or local PostgreSQL database instead of volatile browser memory.
*   **RESTful API:** Clean, structured backend endpoints handling JSON data exchange.
*   **Responsive UI:** A clean HTML, CSS, and modern JavaScript frontend that communicates asynchronously with the API using `fetch()`.

## 🛠️ Tech Stack
*   **Frontend:** HTML5, CSS3, JavaScript (ES6+, Async/Await)
*   **Backend:** Java (Spring Boot)
*   **Database:** PostgreSQL
*   **ORM / Data Access:** Spring Data JPA / Hibernate
*   **Build Tool:** Maven / Gradle *(Keep only the one you use)*
  
## 🔌 API Endpoints
The frontend communicates with the Java backend via the following REST API endpoints:
| Method | Endpoint | Description | Request Body (JSON) |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/todos` | Fetch all tasks from the database | None |
| **POST** | `/api/todos` | Save a new task | `{ "title": "String", "completed": false }` |
| **PUT** | `/api/todos/{id}` | Edit task text or toggle status | `{ "title": "String", "completed": true }` |
| **DELETE** | `/api/todos/{id}` | Delete a task by its unique ID | None |

