# Gaming-Needs (Backend)

Gaming-Needs is a Spring Boot based backend application designed for managing gaming products, user authentication, cart operations, and order processing.  
This project helped me understand backend architecture including REST APIs, layered architecture, security, DTO mapping, and service design.

---

## 🚀 Tech Stack
- Java
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Maven
- MySQL / H2
- JPA / Hibernate

---

## 📌 Features

### ✔ Product Management  
- Get all products  
- Get product by ID  
- Search products by keyword *(my custom improvement)*  

### ✔ User Authentication  
- Login / Register  
- JWT-based secure endpoints (if applicable)

### ✔ Cart & Order Operations  
- Add items to cart  
- Remove items  
- Place order  
- View orders  

---

## 🛠 Build
```bash
mvn clean install
```

## ▶ Run
```bash
mvn spring-boot:run
```

## 📡 Access APIs
```bash
GET /products
GET /products/{id}
GET /products/search?name=xyz
```

## 🔧 Clone the Repository
```bash
git clone https://github.com/sajal-gupta-0202/Gaming-Needs
```

---

