-- Tạo database
create database QLCH;
go
use QLCH;
go

-- Bảng nhà cung cấp
create table Providers(
	ProviderIDNum INT IDENTITY(1,1),
	ProviderID AS 'NCC' + RIGHT('000' + CAST(ProviderIDNum AS VARCHAR(3)),3) PERSISTED UNIQUE,
	ProviderName nvarchar(100) not null,
	ProviderPhone nvarchar(100) not null,
	constraint PK_Providers primary key (ProviderID)
);

-- Bảng sản phẩm
create table Products(
	ProductIDNum INT IDENTITY(1,1),
	ProductID AS 'SP' + RIGHT('000'+CAST(ProductIDNum AS VARCHAR(3)),3) PERSISTED UNIQUE,
	ProductName nvarchar(100) not null,
	ProductPrice int not null,
	Quantity int,
	ProviderID varchar(6) not null,
	ProductType char(10) not null,
	constraint FK_Products foreign key (ProviderID) references Providers(ProviderID)
);

-- Bảng nhân viên
create table Employees(
	EmployeeIDNum INT IDENTITY(1,1),
	EmployeeID AS 'NV' +RIGHT('000' + CAST(EmployeeIDNum AS VARCHAR(3)),3) PERSISTED UNIQUE,
	EmployeeName nvarchar(100) not null,
	EmployeeRole char(10),
	EmployeeSalary int not null,
	EmployeeNumber nvarchar(100) not null,
	constraint PK_Employees primary key (EmployeeID)
);

-- Bảng tài khoản nhân viên
create table Accounts(
	AccountIDNum INT IDENTITY(1,1),
	AccountID AS 'AC' +RIGHT('000' + CAST(AccountIDNum AS VARCHAR(3)),3) PERSISTED UNIQUE,
	AccountType varchar(10),
	Username varchar(5) not null,
	Password nvarchar(10) not null,
	constraint PK_Accounts primary key (AccountID),
	constraint FK_Accounts foreign key (Username) references Employees(EmployeeID)
);

-- Bảng khách hàng
create table Customers(
	CustomersIDNum INT IDENTITY(1,1),
	CustomersID AS 'KH' +RIGHT('000' + CAST(CustomersIDNum AS VARCHAR(3)),3) PERSISTED UNIQUE,
	CustomersName nvarchar(100) not null,
	CustomersNumber nvarchar(100) not null,
	constraint PK_Customers primary key (CustomersID)
);

-- Bảng tài khoản khách hàng
create table CustomerAccounts(
	AccountIDNum INT IDENTITY(1,1),
	AccountID AS 'C.AC' +RIGHT('000' + CAST(AccountIDNum AS VARCHAR(3)),3) PERSISTED UNIQUE,
	AccountType varchar(10),
	Username varchar(5) not null,
	Password nvarchar(10) not null,
	constraint PK_CusAccounts primary key (AccountID),
	constraint FK_CusAccounts foreign key (Username) references Customers(CustomersID)
);

-- Bảng hóa đơn
create table Receipts(
	ReceiptIDNum INT IDENTITY(1,1),
	ReceiptID AS 'HD' + RIGHT('000' + CAST(ReceiptIDNum AS VARCHAR(3)), 3) PERSISTED UNIQUE,
	EmployeeID varchar(5),
	ReceiptDate datetime not null,
	ReceiptType BIT not null,
	ProviderID varchar(6),
	CustomersID varchar(5),
	constraint PK_Receipts primary key (ReceiptID),
	constraint FK_Receipts_Employees foreign key (EmployeeID) references Employees(EmployeeID),
	constraint FK_ProviderID foreign key (ProviderID) references Providers(ProviderID),
	constraint FK_CustomersID foreign key (CustomersID) references Customers(CustomersID)
);

-- Bảng chi tiết hóa đơn
create table ReceiptsDetails(
	ReceiptID varchar(5) not null,
	ProductID varchar(5) not null,
	Quantity int not null,
	Price int not null,
	constraint PK_ReceiptsDetails primary key (ReceiptID, ProductID),
	constraint FK_ReceiptsDetails foreign key (ReceiptID) references Receipts(ReceiptID),
	constraint FK_ReceiptsDetails_ProductID foreign key (ProductID) references Products(ProductID)
);

-- Thêm nhân viên
insert into Employees(EmployeeName, EmployeeRole, EmployeeSalary, EmployeeNumber) values
(N'Nguyễn Văn A', 'Admin', 5000000, '0123456789'),
(N'Nguyễn Văn B', 'Staff', 6000000, '0987654321'),
(N'Nguyễn Văn C', 'Manager', 8000000, '1234567890'),
(N'Nguyễn Văn D', 'Manager', 7000000, '2345678901');

-- Thêm nhà cung cấp
insert into Providers(ProviderName, ProviderPhone) values 
(N'Casio', '0123456789'),
(N'Citizen', '0123456788'),
(N'Orient', '0123456787'),
(N'Rolex', '0123456786');

-- Thêm sản phẩm đồng hồ
insert into Products(ProductName, ProductPrice, Quantity, ProviderID, ProductType) values
(N'Casio MTP-V002L-1B3UDF', 1200000, 20, 'NCC001', 'Analog'),
(N'Citizen BM8475-26E', 4200000, 15, 'NCC002', 'EcoDrive'),
(N'Orient RA-AA0004E19B', 3800000, 10, 'NCC003', 'Automatic'),
(N'G-Shock GA-100-1A1DR', 2600000, 25, 'NCC001', 'Digital'),
(N'Rolex Submariner 116610LN', 250000000, 2, 'NCC004', 'Luxury');

-- Thêm khách hàng
insert into Customers(CustomersName, CustomersNumber) values 
(N'Nguyễn Thị A','0123456789');

-- Tạo tài khoản nhân viên
insert into Accounts(AccountType, Username, Password) values 
('Admin','NV001','123');

-- Tạo tài khoản khách hàng
insert into CustomerAccounts(AccountType, Username, Password) values 
('Customer', 'KH001', '12345');

-- Thêm hóa đơn
insert into Receipts(EmployeeID, ReceiptDate, ReceiptType, ProviderID, CustomersID) values
('NV001', '2025-05-01', 1, NULL, 'KH001'), 
('NV002', '2025-04-30', 0, 'NCC001', NULL); 

-- Chi tiết hóa đơn
insert into ReceiptsDetails(ReceiptID, ProductID, Quantity, Price) values
('HD001', 'SP001', 1, 1200000),
('HD001', 'SP004', 2, 2600000),
('HD002', 'SP001', 10, 1200000),
('HD002', 'SP004', 15, 2600000);
