# Application exposing a rest endpoint allowing interaction with a bank account carried out in spring boot

# The application offers 4 APIs :

1. create an account :

   `POST /api/bank/create/account`

Payload is :
```
{
  "amount": 0,
  "balance": 0,
  "client": {
    "firstname": "jean",
    "lastname": "pierre"
  },
  "name": "banque-postale"
}
```

Response preview:
```
{
  "amount": 0,
  "balance": 0,
  "client": {
    "firstname": "jean",
    "lastname": "pierre"
  },
  "name": "banque-postale"
}
```
2. Withdrawing a given amount of money from an account:

    `PUT /api/bank/{accountName}/withdraw?amount=amount_value`
 
Response preview:
```
{
  "name": "CA",
  "client": {
    "firstname": "jean",
    "lastname": "pierre"
  },
  "balance": -400,
  "amount": 400
}
```
3. Deposit of a given amount of money on an account:

    `PUT /api/bank/{accountName}/withdraw?amount=amount_value`
    
 Response preview:
```  
{
  "name": "CA",
  "client": {
    "firstname": "jean",
    "lastname": "pierre"
  },
  "balance": 400,
  "amount": 800
}
```
4. show the history of all transaction with pagination:

    `GET /api/bank/{accountName}/history?page=1&size=5`
 
  Response preview:
  ```
  {
  "balance": 200,
  "operations": [
    {
      "date": "2021-10-19T12:33:51Z",
      "operationType": "withdrawal",
      "amount": -200
    },
    {
      "date": "2021-10-19T12:37:35Z",
      "operationType": "withdrawal",
      "amount": -200
    },
    {
      "date": "2021-10-19T12:41:03Z",
      "operationType": "deposit",
      "amount": 200
    },
    {
      "date": "2021-10-19T12:41:12Z",
      "operationType": "deposit",
      "amount": 400
    }
  ]
}
```

# Bank account kata
Think of your personal bank account experience When in doubt, go for the simplest solution

# Requirements
- Deposit and Withdrawal
- Account statement (date,amount,balance)
- Statement printing (history)
 
# User Stories
##### US 1:
**In order to** save money  
**As a** bank client  
**I want to** make a deposit in my account  
 
##### US 2: 
**In order to** retrieve some or all of my savings  
**As a** bank client  
**I want to** make a withdrawal from my account  
 
##### US 3: 
**In order to** check my operations  
**As a** bank client  
**I want to** see the history (operation, date, amount, balance)  of my operations  

# Building

## What do you need
- Java 8
- Wamp server-Mysql
- Create dataBase name see file application.properties
- to use swagger : http://localhost:8081/swagger-ui.html# 

```
