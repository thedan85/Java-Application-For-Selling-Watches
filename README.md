# 🕐 Watch Store Management System (QLCH)

A comprehensive Java-based desktop application for managing a watch retail store, built with Swing GUI and SQL Server database integration created by me and my team, then created my own repository to push the source code.

## 📋 Overview

This Watch Store Management System (Quản Lý Cửa Hàng - QLCH) is a full-featured desktop application designed to streamline operations for watch retailers. The system provides separate interfaces for administrators, employees, and customers, with comprehensive inventory management, sales tracking, and reporting capabilities.

## ✨ Key Features

### 🔐 Authentication & Authorization
- **Multi-role login system** with Admin, Employee, and Customer accounts
- **Secure authentication** with username/password validation
- **Role-based access control** for different user types

### 📦 Inventory Management
- **Product catalog** with watch details (name, price, quantity, type)
- **Provider management** for supplier information
- **Stock tracking** with real-time quantity updates
- **Product categorization** by watch types

### 👥 User Management
- **Employee management** with roles and salary tracking
- **Customer registration** and profile management
- **Account management** for all user types

### 🧾 Sales & Transaction Management
- **Receipt generation** for sales transactions
- **Sales tracking** with detailed transaction history
- **Customer purchase history**
- **Import/Export functionality** for inventory updates

### 📊 Analytics & Reporting
- **Sales analytics** with graphical representations
- **Revenue tracking** by time periods
- **Inventory reports** and statistics
- **Year-over-year comparisons**

### 🎨 User Interface
- **Modern Swing GUI** with custom styling
- **Table-based data display** for easy management
- **Interactive forms** for data entry
- **Calendar integration** for date selection
- **Background image support** for branding

## 🛠️ Technology Stack

- **Language:** Java
- **GUI Framework:** Java Swing
- **Database:** Microsoft SQL Server
- **Build Tool:** Maven
- **Architecture:** 3-Layer Architecture (DAO, BUS, GUI)
- **Database Connectivity:** JDBC with SQL Server driver

## 🏗️ Project Structure

```
Project/qlch/
├── src/main/java/com/microsoft/sqlserver/
│   ├── DAO/          # Data Access Objects
│   │   ├── connectDatabase.java
│   │   ├── accountDAO.java
│   │   ├── productDAO.java
│   │   ├── customerDAO.java
│   │   ├── employeeDAO.java
│   │   ├── providerDAO.java
│   │   └── receiptDAO.java
│   ├── BUS/          # Business Logic Layer
│   │   ├── accountBUS.java
│   │   ├── productBUS.java
│   │   ├── customerBUS.java
│   │   ├── employeeBUS.java
│   │   └── receiptBUS.java
│   ├── DTO/          # Data Transfer Objects
│   │   ├── accountDTO.java
│   │   ├── productDTO.java
│   │   ├── customerDTO.java
│   │   ├── employeeDTO.java
│   │   └── receiptDTO.java
│   └── GUI/          # User Interface
│       ├── LoginForm.java
│       ├── mainInt.java
│       ├── adminInt.java
│       ├── productsTable.java
│       ├── customerTable.java
│       ├── employeeTable.java
│       └── GraphPanel.java
├── Sql/
│   └── QLCH.sql      # Database schema
└── pom.xml           # Maven configuration
```

## 🗄️ Database Schema

The system uses the following main entities:

- **Products** - Watch inventory with pricing and categorization
- **Providers** - Supplier information and contacts
- **Employees** - Staff management with roles and salaries
- **Customers** - Customer profiles and contact details
- **Accounts** - User authentication for employees and customers
- **Receipts** - Sales transaction records
- **ReceiptDetails** - Detailed line items for each sale

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Microsoft SQL Server (or SQL Server Express)
- Maven 3.6+ for build management
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/thedan85/Java-Application-For-Selling-Watches.git
   cd Java-Application-For-Selling-Watches
   ```

2. **Database Setup:**
   - Install Microsoft SQL Server
   - Execute `Project/qlch/Sql/QLCH.sql` to create the database schema
   - Update database connection in `config.properties`:
     ```properties
     dbUrl=jdbc:sqlserver://localhost:1433;databaseName=QLCH;encrypt=true;trustServerCertificate=true
     username=sa
     password=your_password
     ```

3. **Build the project:**
   ```bash
   cd Project/qlch
   mvn clean compile
   ```

4. **Run the application:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.microsoft.sqlserver.GUI.LoginForm"
   ```

## 💻 Usage

1. **Login:** Start with the login form using your credentials
2. **Admin Panel:** Full access to all management features
3. **Employee Panel:** Access to sales and basic inventory functions
4. **Customer Panel:** View products and purchase history

### Default Login Credentials
- **Admin:** Check database for initial admin account
- **Employee:** Contact administrator for account creation
- **Customer:** Register through the customer registration form

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is developed as an educational project. Please check with the repository owner for specific licensing terms.

## 👨‍💻 Development Team

This project was developed as part of a software engineering course, demonstrating enterprise-level application development with Java and database integration.

## 📞 Support

For support, please create an issue in the GitHub repository or contact the development team.

---

**Note:** This is a comprehensive business management system designed for educational purposes and real-world application in watch retail businesses.