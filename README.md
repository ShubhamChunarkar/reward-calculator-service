
# Reward Calculator

This service offers a rewards program to customers, granting points for each transaction made within a specified period.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction. (e.g. a $120 purchase = 2*$20 + 1*$50 = 90 Points).





## Technology Used

Java 17

Springboot 3.3.2

Maven

MySQL workbench

Swagger Ui



## Setup:
1.Open the project in any IDE

2.Run MySQL workbench db locally and run it.Change the database setting in application.properties file and Transaction repository class query.

3.Run the project on apache maven server

4.Customer and Transaction table will be generated in db

5.Click the below link to check Swagger ui. http://localhost:8091/swagger-ui/index.html (to check api endpoints goto swagger ui)

## Flow Diagram

![SubmitCustomerDetails](https://github.com/user-attachments/assets/fe6aac8b-64ab-4aec-bed5-8d1494d7ac65)

![All_Customer](https://github.com/user-attachments/assets/a0006239-0d66-4367-a503-93ca0cb54e59)

![Rewards](https://github.com/user-attachments/assets/4a5a4e13-ad16-4e50-af1a-60ed5de8ae0e)

![CustomerData](https://github.com/user-attachments/assets/6692af59-a844-4091-a44f-bf3525d1ad78)








## API Reference


1. Post Customer details and Transaction details

```http
  POST http://localhost:8091/customers/save
```
Reqeset body

 #### {
    "customerId":4,
    "customerName":"Chris",
    "listtransaction":[
        {
            "transactionId":512,
            "customerId":4,
            "transactionDate":"2024-05-30",
            "transactionAmount":55
        },  {
            "transactionId":513,
            "customerId":4,
            "transactionDate":"2024-06-30",
            "transactionAmount":95
        },
          {
            "transactionId":514,
            "customerId":4,
            "transactionDate":"2024-07-30",
            "transactionAmount":50
        }
    ]
}

Response body

#### {
    "customerId": 4,
    "customerName": "Chris",
    "listtransaction": [
        {
            "transactionId": 512,
            "customerId": 4,
            "transactionDate": "2024-05-30T00:00:00.000+00:00",
            "transactionAmount": 55.0
        },
        {
            "transactionId": 513,
            "customerId": 4,
            "transactionDate": "2024-06-30T00:00:00.000+00:00",
            "transactionAmount": 95.0
        },
        {
            "transactionId": 514,
            "customerId": 4,
            "transactionDate": "2024-07-30T00:00:00.000+00:00",
            "transactionAmount": 50.0
        }
    ]
}

#### 2.Get All customer
It is used to fetch all customer details
```http
  GET /customers/getAllCustomer
```
Response body
#### [
    {
        "customerId": 1,
        "customerName": "Shubham",
        "listtransaction": [
            {
                "transactionId": 501,
                "customerId": 1,
                "transactionDate": "2024-05-20T00:00:00.000+00:00",
                "transactionAmount": 120.0
            },
            {
                "transactionId": 502,
                "customerId": 1,
                "transactionDate": "2024-07-20T00:00:00.000+00:00",
                "transactionAmount": 55.0
            },
            {
                "transactionId": 503,
                "customerId": 1,
                "transactionDate": "2024-08-07T00:00:00.000+00:00",
                "transactionAmount": 120.0
            }
        ]
    },
    {
        "customerId": 2,
        "customerName": "John",
        "listtransaction": [
            {
                "transactionId": 504,
                "customerId": 2,
                "transactionDate": "2024-05-10T00:00:00.000+00:00",
                "transactionAmount": 120.0
            },
            {
                "transactionId": 505,
                "customerId": 2,
                "transactionDate": "2024-06-20T00:00:00.000+00:00",
                "transactionAmount": 55.0
            },
            {
                "transactionId": 506,
                "customerId": 2,
                "transactionDate": "2024-07-07T00:00:00.000+00:00",
                "transactionAmount": 120.0
            },
            {
                "transactionId": 507,
                "customerId": 2,
                "transactionDate": "2024-07-25T00:00:00.000+00:00",
                "transactionAmount": 350.0
            }
        ]
    },
    {
        "customerId": 3,
        "customerName": "John",
        "listtransaction": [
            {
                "transactionId": 508,
                "customerId": 3,
                "transactionDate": "2024-05-30T00:00:00.000+00:00",
                "transactionAmount": 55.0
            },
            {
                "transactionId": 509,
                "customerId": 3,
                "transactionDate": "2024-06-30T00:00:00.000+00:00",
                "transactionAmount": 95.0
            },
            {
                "transactionId": 510,
                "customerId": 3,
                "transactionDate": "2024-07-30T00:00:00.000+00:00",
                "transactionAmount": 120.0
            },
            {
                "transactionId": 511,
                "customerId": 3,
                "transactionDate": "2024-07-29T00:00:00.000+00:00",
                "transactionAmount": 350.0
            }
        ]
    },
    {
        "customerId": 4,
        "customerName": "Chris",
        "listtransaction": [
            {
                "transactionId": 512,
                "customerId": 4,
                "transactionDate": "2024-05-30T00:00:00.000+00:00",
                "transactionAmount": 55.0
            },
            {
                "transactionId": 513,
                "customerId": 4,
                "transactionDate": "2024-06-30T00:00:00.000+00:00",
                "transactionAmount": 95.0
            },
            {
                "transactionId": 514,
                "customerId": 4,
                "transactionDate": "2024-07-30T00:00:00.000+00:00",
                "transactionAmount": 50.0
            }
        ]
    }
]

#### 3.Get Customer Data based on id
It is used to fetch customer details
```http
  GET /customers/{customerId}
```
Response body 
### {
    "customerId": 2,
    "customerName": "John",
    "listtransaction": [
        {
            "transactionId": 504,
            "customerId": 2,
            "customer": null,
            "transactionDate": "2024-05-10T00:00:00.000+00:00",
            "transactionAmount": 120.0
        },
        {
            "transactionId": 505,
            "customerId": 2,
            "customer": null,
            "transactionDate": "2024-06-20T00:00:00.000+00:00",
            "transactionAmount": 55.0
        },
        {
            "transactionId": 506,
            "customerId": 2,
            "customer": null,
            "transactionDate": "2024-07-07T00:00:00.000+00:00",
            "transactionAmount": 120.0
        },
        {
            "transactionId": 507,
            "customerId": 2,
            "customer": null,
            "transactionDate": "2024-07-25T00:00:00.000+00:00",
            "transactionAmount": 350.0
        }
    ]
}
#### 4.Get total reward based on customer id

```http
  GET /api/v1/{customerId}/rewards
```
Response body 
### {
    "customer": {
        "id": 2,
        "name": "Shubham"
    },
    "totalPoints": 300,
    "rewards": [
        {
            "transactionId": "504",
            "transactionAmount": 200,
            "points": 250,
            "transactionDate": "2024-08-05T17:47:00.722000"
        },
        {
            "transactionId": "505",
            "transactionAmount": 100,
            "points": 50,
            "transactionDate": "2024-07-01T17:47:00.722000"
        }
    ]
}

#### 5.Get total reward of All Customers

```http
  GET /api/v1/getAllCustomer
```
Response body 
### [
    {
        "customer": {
            "id": 1,
            "name": "John"
        },
        "totalPoints": 300,
        "rewards": [
            {
                "transactionId": "501",
                "transactionAmount": 200,
                "points": 250,
                "transactionDate": "2024-08-11T17:47:00.722000"
            },
            {
                "transactionId": "502",
                "transactionAmount": 100,
                "points": 50,
                "transactionDate": "2024-07-11T17:47:00.722000"
            },
            {
                "transactionId": "503",
                "transactionAmount": 45,
                "points": 0,
                "transactionDate": "2024-07-26T17:47:00.722000"
            }
        ]
    },
    {
        "customer": {
            "id": 2,
            "name": "Shubham"
        },
        "totalPoints": 300,
        "rewards": [
            {
                "transactionId": "504",
                "transactionAmount": 200,
                "points": 250,
                "transactionDate": "2024-08-05T17:47:00.722000"
            },
            {
                "transactionId": "505",
                "transactionAmount": 100,
                "points": 50,
                "transactionDate": "2024-07-01T17:47:00.722000"
            }
        ]
    },
    {
        "customer": {
            "id": 3,
            "name": "Joseph"
        },
        "totalPoints": 196,
        "rewards": [
            {
                "transactionId": "511",
                "transactionAmount": 89,
                "points": 39,
                "transactionDate": "2024-08-11T18:11:04.063000"
            },
            {
                "transactionId": "512",
                "transactionAmount": 129,
                "points": 108,
                "transactionDate": "2024-07-11T18:11:04.063000"
            },
            {
                "transactionId": "513",
                "transactionAmount": 99,
                "points": 49,
                "transactionDate": "2024-08-01T18:11:04.063000"
            }
        ]
    }
]


