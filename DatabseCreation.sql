use  rinl_yard;

create table Bin_Master (Bin_No varchar(6), Bin_Desc varchar(40) not null, Bin_Location varchar(40), primary key(Bin_No));
select * from Bin_Master;

create table Product_Master (Product_Id varchar(6), Product_Desc varchar(40) not null, Stock_In_Tons DECIMAL(10,3), Bin_No varchar(20), Product_Grade varchar(10),  primary key(Product_Id));
select * from Product_Master;

select Product_Id from Product_Master where Bin_No= 8910;

create table Yard_Receipts( Receipt_Id varchar(6),  Transport_Type VARCHAR(10) CHECK (Transport_Type IN ('TRUCK', 'RAIL')),Product_Id varchar(6), Received_Date date, Received_Qty_in_Tons decimal(10,3), Remarks varchar(60), primary key(Receipt_Id),foreign key(Product_Id) references Product_Master(Product_Id));
select * from Yard_Receipts;

create table Customer_Master ( Customer_No varchar(6), Customer_Name varchar(40) not null, Customer_Address varchar(40), City varchar(20), Pin varchar(6), Phone_No varchar(10), Email_id varchar(30), Product_Grade varchar(10), primary key(Customer_No));
select * from Customer_Master;

create table Sales_Order(Sales_Order_Id varchar(6), Sales_Order_Date date, Customer_id varchar(6), Product_Id varchar(6), Sales_Order_Qty_in_Tons decimal(10,3), Remarks varchar(60) , primary key (Sales_Order_Id), foreign key (Customer_id) references Customer_Master(Customer_No), foreign key (Product_Id) references Product_Master(Product_Id));
select * from Sales_Order;

create table Yard_Despatches( Despatch_Id varchar(6), Transport_Type VARCHAR(10) CHECK (Transport_Type IN ('TRUCK', 'RAIL')) , Despatch_Date date, Sales_Order_Id varchar(6), Despatched_Qty_in_Tons decimal(10,3), Remarks varchar(60), primary key( Despatch_Id), foreign key(Sales_Order_Id) references Sales_Order(Sales_Order_Id));
select * from Yard_Despatches;

create table User_Master( User_id varchar(6), User_Type VARCHAR(10) CHECK (User_Type IN ('ADMIN', 'MKTG')), User_name varchar(40) not null, Email_id varchar(60), Phone_no varchar(10), Password varchar(20), primary key(User_id));
select * from User_Master;