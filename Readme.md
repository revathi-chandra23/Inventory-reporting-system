 Inventory Report Management System – Project Overview
🧩 Objective
To build a backend system that monitors and reports inventory movement (stock in/out) across multiple products and warehouses using data imported from CSV files. It provides insights such as current stock, low stock alerts, and inventory trends.

 Project Overview Summary
Your project is a Spring Boot-based Inventory Management System that:

Manages products, warehouses, and stock transactions

Allows CSV uploads

Sends low stock email alerts

Provides stock reports (current, low, trends)

Uses scheduled tasks for automatic checks

| Concept                                           | Description                                                        |
| ------------------------------------------------- | ------------------------------------------------------------------ |
| **OOP (Encapsulation, Inheritance, Abstraction)** | Used across `Product`, `Warehouse`, `StockTransaction`, DTOs, etc. |
| **Collections Framework**                         | `List`, `Map`, `Optional`, `Set` used in services and utilities    |
| **Exception Handling**                            | `try-catch`, custom exceptions (`ResourceNotFoundException`)       |
| **File Handling & Streams**                       | Parsing CSVs via `InputStream`                                     |
| **Java 8+ Features**                              | `Streams`, `Lambdas`, `Optional`, `LocalDate`                      |

Spring Boot & Spring Framework Concepts

| Concept                      | Description                                                             |
| ---------------------------- | ----------------------------------------------------------------------- |
| **Spring Boot**              | Main framework for building the app                                     |
| **Spring MVC**               | For REST API: `@RestController`, `@RequestMapping`, `@GetMapping`, etc. |
| **Spring Data JPA**          | ORM layer to interact with the database using repositories              |
| **Spring Scheduler**         | Used with `@Scheduled` for sending scheduled stock alerts               |
| **Spring Mail**              | Used to send emails using `JavaMailSender`                              |
| **Dependency Injection**     | Via `@Autowired`                                                        |
| **DTO Pattern**              | Used to expose data: `LowStockDTO`, `StockReportDTO`, etc.              |
| **Validation & Constraints** | Entity annotations like `@Column(unique = true)`                        |
| **Exception Handling**       | `@ExceptionHandler` or thrown in services                               |

utility concepts

| Concept                                       | Description                                                                 |
| --------------------------------------------- | --------------------------------------------------------------------------- |
| **CSV Parsing**                               | Custom logic in `CsvParserUtil` to parse product/warehouse/transaction data |
| **CSV Exporting**                             | In `CsvExportUtil` for downloading reports                                  |
| **StringBuilder, FileWriter, BufferedReader** | Used for constructing and handling file input/output                        |

Rest Api concepts
-------------------

| Concept                        | Description                                              |
| ------------------------------ | -------------------------------------------------------- |
| **Request Params & Path Vars** | `@RequestParam`, `@PathVariable` used in your controller |
| **HTTP Methods**               | `GET`, `POST`, `@PostMapping("/upload")`, etc.           |
| **Content Type Handling**      | Downloadable CSV via `HttpServletResponse`               |
| **Multipart File Upload**      | `@RequestParam("file") MultipartFile file`               |


🛠️ Tech Stack
Language: Java

Framework: Spring Boot

Database: H2 / MySQL / PostgreSQL (as configured)

Data Import: CSV (Apache Commons CSV)

API Testing: Postman

Build Tool: Maven or Gradle

Optional: Spring Security, Lombok, Apache POI (for future export)

 Core Features
1. CSV Upload
Supports uploading:

products.csv

warehouses.csv

stock_transactions.csv

Automatically parses and stores data into the database

2.  Inventory Reports
Current Stock Report:

Shows total available quantity of each product per warehouse

Low Stock Report:

Identifies products below their reorder level

Stock Trend Report:

Shows "IN" and "OUT" transactions over a date range

3.  CSV Export
Download reports in CSV format for:

Trend report (/download/trend)

Current stock

Low stock

4.  Error Handling
Graceful handling of:

Missing fields

Bad date formats

Invalid product/warehouse IDs

Shows appropriate error messages in responses
Main Packages / Modules
model – JPA Entities (Product, Warehouse, StockTransaction)

repository – Spring Data JPA Repositories

service – Business logic (inventory calculations)

controller – REST APIs for uploading, downloading, and viewing reports

dto – Data Transfer Objects for report responses

util – CSV parsing and exporting utilities

| Method | Endpoint                                          | Description                               |
| ------ | ------------------------------------------------- | ----------------------------------------- |
| POST   | `/api/report/upload`                              | Upload CSV (product/warehouse/stock data) |
| GET    | `/api/report/current-stock`                       | Get current stock for all products        |
| GET    | `/api/report/low-stock`                           | Get products below reorder level          |
| GET    | `/api/report/trend?from=YYYY-MM-DD&to=YYYY-MM-DD` | View stock movement in/out                |
| GET    | `/api/report/download/trend?from=...`             | Download stock trend report as CSV        |


Use Case
Ideal for businesses or warehouse teams who need a simple backend to:

Import inventory transaction data

Monitor current stock levels

Prevent stockouts via low stock alerts

Generate daily/weekly inventory insights





git readme file

# 📦 Inventory Report Management System

A Spring Boot-based backend application for uploading, tracking, and reporting inventory across multiple warehouses and products. It supports CSV data imports and generates reports for current stock, low stock alerts, and inventory trends.

---

##  Features

-  Upload CSV files (Products, Warehouses, Transactions)
-  View stock reports (current stock, low stock, trends)
-  Export reports as downloadable CSV files
- Persistent database integration (H2/MySQL/PostgreSQL)
-  Auto-calculates product stock per warehouse
-  daily stock alerts via emails by scheduling emails

---

##  Tech Stack

| Layer         | Tech                       |
|---------------|----------------------------|
| Language      | Java 17+                   |
| Framework     | Spring Boot                |
| Database      | H2 / MySQL / PostgreSQL    |
| CSV Parsing   | Apache Commons CSV         |
| API Testing   | Postman                    |
| Build Tool    | Maven / Gradle             |
| Utilities     | Lombok, Spring Web         |

---

## Project Structure

src/
├── controller/ # REST endpoints
├── dto/ # Report DTOs
├── model/ # JPA entities
├── repository/ # Spring Data Repositories
├── service/ # Business logic
├── util/ # CSV parser and exporter
└── InventoryApplication # Main entry point
---



##  API Endpoints

### Upload CSV
```http
POST /api/report/upload
Form-Data:
- file: (CSV File)
- type: product | warehouse | transaction

| Method | Endpoint                                          | Description                       |
| ------ | ------------------------------------------------- | --------------------------------- |
| GET    | `/api/report/current-stock`                       | View product-wise current stock   |
| GET    | `/api/report/low-stock`                           | View products below reorder level |
| GET    | `/api/report/trend?from=YYYY-MM-DD&to=YYYY-MM-DD` | View stock movement trend         |
| GET    | `/api/report/download/trend?from=...&to=...`      | Download trend as CSV             |


 Future Enhancements
 Excel export support (using Apache POI)

User authentication (Spring Security)

 Admin dashboard with charts (React/Angular)



 | Feature                 | Used? |
 | ----------------------- | ----- |
 | Collections             | ✅     |
 | OOP Principles          | ✅     |
 | Exception Handling      | ✅     |
 | File I/O                | ✅     |
 | Java 8 Date/Time API    | ✅     |
 | Generics                | ✅     |
 | Reflection (via Spring) | ✅     |
 | Logging                 | ✅     |
 | JavaBeans               | ✅     |


✅ 1. Java Collections Framework
Used extensively for handling data:

List, Map, Set, ArrayList, HashMap, etc.

✅ 2. OOP Principles
Your code uses Object-Oriented Programming concepts:

Encapsulation: via classes like Product, Warehouse, StockTransaction, DTOs

Abstraction: Interfaces like ProductRepository, WarehouseRepository

Inheritance & Polymorphism: through annotations and service abstraction layers (like EmailService, ReportService)


✅ 3. Exception Handling
Custom and runtime exceptions

 6. Annotations (from core Java and Spring)
Some annotations are Spring-specific, but conceptually tied to core Java:

@Autowired, @Service, @RestController

@RequestMapping, @Scheduled

@Id, @GeneratedValue (JPA annotations built on Java Reflection)

@Column(unique = true) (JPA metadata configuration)


✅ 7. Java Bean Standards
All your model and DTO classes follow the JavaBeans convention:

Private fields with public getters/setters

No-arg constructors

8. Java Generics
Generic types are used in Repositories and Collections:

public interface ProductRepository extends JpaRepository<Product, Long>
Optional<Product> findBySku(String sku);

 9. Java Reflection (Indirect via Spring/JPA)
You're using frameworks (Spring, Hibernate) that rely heavily on Java Reflection to:

Inject dependencies (@Autowired)
Map models to tables
Schedule tasks

 10. Logging (SLF4J / Java Logging)
You have this logging line (Spring Boot uses Slf4j under the hood):

