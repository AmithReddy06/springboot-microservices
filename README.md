
# 📘 Microservices Bookstore Example

This is a Spring Boot microservices application using Eureka for service discovery and MySQL for databases. The services include:

- `user-service`
- `book-service`
- `order-service`
- `discovery-server` (Eureka)

---

## 🧱 Microservices Architecture

Each service is containerized with Docker and connects to its own MySQL container via a common Docker network.

---

## 🔧 Step 1: Create Docker Network

```bash
docker network create bookstore-net
```

---

## 🐬 Step 2: Launch MySQL Containers

```bash
# User DB
docker run -d --name mysql-user --network bookstore-net \
  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=userdb -p 3307:3306 mysql:8

# Book DB
docker run -d --name mysql-book --network bookstore-net \
  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bookdb -p 3308:3306 mysql:8

# Order DB
docker run -d --name mysql-order --network bookstore-net \
  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=orderdb -p 3309:3306 mysql:8
```

---

## ⚙️ Step 3: application.properties

### 📍 `user-service`

```properties
spring.datasource.url=jdbc:mysql://mysql-user:3306/userdb
spring.datasource.username=root
spring.datasource.password=userpass
spring.jpa.hibernate.ddl-auto=update
spring.application.name=user-service
server.port=8081

eureka.client.service-url.defaultZone=http://discovery-server:8761/eureka
```

---

### 📍 `book-service`

```properties
spring.datasource.url=jdbc:mysql://mysql-book:3306/bookdb
spring.datasource.username=root
spring.datasource.password=bookpass
spring.jpa.hibernate.ddl-auto=update
spring.application.name=book-service
server.port=8082

eureka.client.service-url.defaultZone=http://discovery-server:8761/eureka
```

---

### 📍 `order-service`

```properties
spring.datasource.url=jdbc:mysql://mysql-order:3306/orderdb
spring.datasource.username=root
spring.datasource.password=orderpass
spring.jpa.hibernate.ddl-auto=update
spring.application.name=order-service
server.port=8083

eureka.client.service-url.defaultZone=http://discovery-server:8761/eureka
```

---

### 📍 `discovery-server`

```properties
spring.application.name=discovery-server
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

---

## 🛠️ Step 4: Build Docker Images

In each service directory (user-service, book-service, order-service, discovery-server), run:

```bash
./mvnw clean package -DskipTests
```

```bash
docker build -t user-service .
```

```bash
docker build -t book-service .
```

```bash
docker build -t order-service .
```

---

## 🚀 Step 5: Run Containers

```bash


# User service
docker run -d --name user-service --network bookstore-net -p 8081:8081 user-service

# Book service
docker run -d --name book-service --network bookstore-net -p 8082:8082 book-service

# Order service
docker run -d --name order-service --network bookstore-net -p 8083:8083 order-service
```

---

## 🔄 Step 6: Insert Sample Data

```bash
# Add users
curl -X POST http://localhost:8081/users -H "Content-Type: application/json" -d '{"name":"Surya", "email":"reddyamit8@gmail.com"}'
curl -X POST http://localhost:8081/users -H "Content-Type: application/json" -d '{"name":"Nataraj", "email":"nat18@gmail.com"}'
curl -X POST http://localhost:8081/users -H "Content-Type: application/json" -d '{"name":"Bharath GM", "email":"skanda9@gmail.com"}'
curl -X POST http://localhost:8081/users -H "Content-Type: application/json" -d '{"name":"Ranganath", "email":"colorsmell@gmail.com"}'
curl -X POST http://localhost:8081/users -H "Content-Type: application/json" -d '{"name":"Sunil", "email":"noonreddy@gmail.com"}'
curl -X POST http://localhost:8081/users -H "Content-Type: application/json" -d '{"name":"Vihaan", "email":"fafaanpasha@gmail.com"}'

# Add books
curl -X POST http://localhost:8082/books -H "Content-Type: application/json" -d '{"title":"God of Small Things","author":"Arundhati Roy"}'
curl -X POST http://localhost:8082/books -H "Content-Type: application/json" -d '{"title":"Midnight's Children","author":"Salman Rushdie"}'
curl -X POST http://localhost:8082/books -H "Content-Type: application/json" -d '{"title":"Malegalalli Madumagalu","author":"Kuvempu"}'
curl -X POST http://localhost:8082/books -H "Content-Type: application/json" -d '{"title":"Java: One Step Ahead","author":"Reema Thareja"}'
curl -X POST http://localhost:8082/books -H "Content-Type: application/json" -d '{"title":"Ministry of Utmost Happiness","author":"Arundhati Roy"}'
curl -X POST http://localhost:8082/books -H "Content-Type: application/json" -d '{"title":"Cuckold","author":"Vivek Shanbhag"}'

# Add orders
curl -X POST http://localhost:8083/orders -H "Content-Type: application/json" -d '{"userId":1, "bookId":1}'
curl -X POST http://localhost:8083/orders -H "Content-Type: application/json" -d '{"userId":2, "bookId":2}'
curl -X POST http://localhost:8083/orders -H "Content-Type: application/json" -d '{"userId":3, "bookId":2}'
curl -X POST http://localhost:8083/orders -H "Content-Type: application/json" -d '{"userId":4, "bookId":2}'
curl -X POST http://localhost:8083/orders -H "Content-Type: application/json" -d '{"userId":5, "bookId":2}'
```

---

## 🔗 Step 7: Test Inter-Service Communication

```bash
curl http://localhost:8083/orders/1/details
curl http://localhost:8083/orders/2/details
```

This will fetch an order from `order-service`, then internally query:
- `user-service` for user details
- `book-service` for book details

---

---

> ✍️ Developed by Surya Prakash Reddy J
