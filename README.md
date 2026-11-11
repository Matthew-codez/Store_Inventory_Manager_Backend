# Store Inventory Manager

A full-stack store inventory management system built with a **Spring Boot** REST API backend and a **Java Swing** desktop client frontend. The system supports two types of users — **Employees**, who manage inventory and view order history, and **Customers**, who browse products by category and make purchases.

## Project Structure

This is a Maven multi-module project with two modules:

```
Store_Inventory_Manager_Backend/
├── backend/    → Spring Boot REST API (Java, JPA/Hibernate, H2 database)
├── frontend/   → Java Swing desktop client (consumes the backend via HTTP)
└── data/       → Local H2 database files (generated at runtime, see [Viewing the database](#viewing-the-database-h2-console))
```

The `backend` and `frontend` modules are independent — the frontend communicates with the backend exclusively through HTTP calls to `http://localhost:8080`, the same way a web or mobile client would. Both must be running for the application to function.

## Features

### Employee (Staff)
- Login with username/password
- Register new employee accounts (only accessible once logged in)
- Add, update and delete inventory items (product, category, supplier, quantity, unit price, location)
- View a full history of customer orders — who bought what, how much, and when

### Customer
- Sign up and log in with username/password
- Browse available products, filterable by category
- Select a quantity and purchase an item
- Purchases automatically reduce stock and are recorded as an order

## Tech Stack

**Backend**
- Java 21
- Spring Boot 4.1.0
- Spring Data JPA / Hibernate
- H2 Database (file-based, embedded)
- Maven

**Frontend**
- Java 21
- Java Swing
- Jackson (JSON serialization)
- `java.net.http.HttpClient` for REST calls

## Domain Model

| Entity | Description |
|---|---|
| `Employee` | Staff accounts with login credentials, used for inventory management |
| `Customer` | Shopper accounts with login credentials and contact details |
| `Product` | A sellable item, identified by product ID, with its name and category |
| `Supplier` | The supplier of a product |
| `Inventory` | A stock record linking a Product and a Supplier, with quantity, price, and location |
| `Order` | A record of a completed purchase — customer, item, quantity, total, and status |

## Getting Started

### Prerequisites
- JDK 21
- Maven
- IntelliJ IDEA (recommended) or another Java IDE

### Running the backend
1. Open the `backend` module in your IDE
2. Run `RedesignedStoreInventoryManagerApplication.java`
3. The API starts on `http://localhost:8080`
4. The H2 database console is available in your browser at `http://localhost:8080/h2-console` (see [Viewing the database](#viewing-the-database-h2-console) for the login details)

### Running the frontend
1. Open the `frontend` module in your IDE
2. Run `ClientApp.java`
3. The Login window opens, with a toggle to log in as either an **Employee** or a **Customer**

> The backend must be running before the frontend, since the Swing client depends on the API being reachable.

### First-time setup
On first start the backend creates a default employee automatically (see `DataInitializer`):

- Username: `admin`
- Password: `admin123`

Log in as an **Employee** with those details. **Change this default password in `DataInitializer` before sharing or deploying the project.** Passwords are stored BCrypt-hashed, so inserting a plaintext row through the H2 console will not work.

Further employees are added from the Inventory screen via **Employees → Add** (username, password, name, position, salary). Customers sign up from the login screen. Every employee screen (Order History, Suppliers, Customers, Employees) is opened with the buttons across the top of the Inventory screen, and **Log out** returns to the login screen.

## Viewing the database (H2 console)

The backend stores all its data in an H2 database. H2 comes with a web page, the **H2 console**, that lets you browse the tables and run SQL straight from your browser. No extra software is needed.

### Opening it

1. Start the backend (`RedesignedStoreInventoryManagerApplication`). The console only exists while the backend is running.
2. Open **http://localhost:8080/h2-console** in any browser.
3. Fill in the login form exactly as shown below, then click **Connect**:

| Field | Value |
|---|---|
| Saved Settings | Generic H2 (Embedded) |
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:file:./data/storeDB` |
| User Name | `sa` |
| Password | *(leave empty)* |

These values come from the `spring.datasource.*` lines in `backend/src/main/resources/application.properties`. If you ever change them there, change them here too.

4. The tables are listed in the panel on the left. Click a table name to put a `SELECT` for it in the editor, then press **Run** (or `Ctrl + Enter`).

### Tables

| Table | What it holds |
|---|---|
| `PRODUCT` | Product ID, product name and category |
| `INVENTORY` | Stock records: quantity, unit price and location, linked to a product and a supplier |
| `SUPPLIER` | Suppliers |
| `CUSTOMER` | Customer accounts (passwords are stored as BCrypt hashes) |
| `EMPLOYEE` | Employee accounts (passwords are stored as BCrypt hashes) |
| `ORDERS` | Completed purchases (called `ORDERS` because `ORDER` is a reserved SQL word) |

Table and column names are shown in capitals with underscores, so the field `quantityInStock` is the column `QUANTITY_IN_STOCK`.

### Handy queries

```sql
SELECT * FROM PRODUCT;
SELECT * FROM INVENTORY;
SELECT * FROM ORDERS;
SHOW COLUMNS FROM INVENTORY;   -- lists every column in a table
```

### Good to know

- **Changes are saved immediately.** An `UPDATE` or `DELETE` run in the console changes the real data straight away, so double-check it before you run it.
- **Add accounts through the app, not SQL.** Passwords must be BCrypt-hashed, so a customer or employee row inserted by hand cannot log in.
- **Where the data lives.** H2 writes the database to a `data/` folder (file `storeDB.mv.db`), created in the folder the backend is started from. It is listed in `.gitignore`. To start again with a clean database, stop the backend and delete that folder. The default `admin` account is recreated on the next start.

### Troubleshooting

| Problem | Likely cause and fix |
|---|---|
| The page does not load | The backend is not running, or it is not on port 8080. |
| You connect, but the tables are missing | The JDBC URL is not exactly `jdbc:h2:file:./data/storeDB`, so you opened a different, empty database. Re-enter it and connect again. |
| `Database may be already in use` / `The file is locked` | Another tool (such as an IDE database tab or a second backend) has the file open. Use the browser console while the backend is running, or stop the backend first. |
| `Wrong user name or password` | The user name must be `sa` and the password must be empty. |
| `Table "..." not found` | Check the spelling. Use `ORDERS`, not `ORDER`. |

### Security note

The console has no login of its own beyond the database user, and `SecurityConfig` deliberately leaves `/h2-console/**` open. This is fine for development on your own machine only. Before deploying the project anywhere shared, remove `spring.h2.console.enabled=true` from `application.properties`, the `/h2-console/**` rule from `SecurityConfig`, and the `frameOptions` setting.

## API Overview

| Endpoint | Purpose |
|---|---|
| `POST /api/auth/login` | Employee login (returns the account's role) |
| `POST /api/customer-auth/login`, `/register` | Customer authentication |
| `GET / POST / PUT / DELETE /api/inventory` | Inventory CRUD |
| `GET /api/orders` | Order history |
| `POST /api/purchase` | Customer purchase (decrements stock, records an order) |
| `GET / POST / DELETE /api/customers` | Customer records |
| `GET /api/v1/employee/getAll`, `POST /save`, `DELETE /delete?id=` | Employee records and creation |