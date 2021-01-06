DB Scripts:

 CREATE TABLE price (
 id int(11) NOT NULL AUTO_INCREMENT,
 firm_name varchar(255) DEFAULT NULL,
 market_price double NOT NULL,
 on_date varchar(10) DEFAULT NULL,
  PRIMARY KEY (id)
);



 CREATE TABLE holding (
 id int(11) NOT NULL AUTO_INCREMENT,
 firm_name varchar(255) DEFAULT NULL,
 on_date varchar(10) DEFAULT NULL,
 portfolio varchar(255) DEFAULT NULL,
 quantity int(11) NOT NULL,
  PRIMARY KEY (id)
);


Payload sample for creation: 	

Price
{
    "firmName" : "IBM",
    "marketPrice": 12.5
    "onDate" : "2021-01-06"
}



Holding
{
    "firmName" : "IBM",
    "portfolio": "Balances",
    "quantity": 10, 
    "onDate" : "2021-01-06"
}

