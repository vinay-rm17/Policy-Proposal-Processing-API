<<<<<<< HEAD
# Policy Proposal Processing API

## Overview

The **Policy Proposal Processing API** is a Spring Boot REST application that simulates a simplified insurance policy proposal system. The application allows users to create customers, create policy proposals, submit proposals, and maintain an audit trail.

This project is implemented using **Spring Boot 3.x** and stores all data in-memory using Java Collections. No database is used.

---

## Technology Stack

* Java 21
* Spring Boot 3.x
* Maven
* Java Collections (ConcurrentHashMap, synchronizedList)
* JUnit 5
* Postman

---

## Project Structure

```
src
 ├── controller
 │     ├── CustomerController
 │     ├── ProposalController
 │     ├── ReferenceMasterController
 │     └── AuditController
 │
 ├── service
 │     ├── CustomerService
 │     ├── ProposalService
 │     ├── ReferenceMasterService
 │     └── AuditService
 │
 ├── repository
 │     ├── CustomerRepository
 │     ├── ProposalRepository
 │     └── AuditRepository
 │
 ├── model
 │     ├── Customer
 │     ├── Proposal
 │     ├── Audit
 │     ├── ProposalStatus
 │     ├── PolicyTerm
 │     └── PaymentFrequency
 │
 └── exception
```

---

## Setup Instructions

### Clone the project

```bash
git clone <repository-url>
```

### Navigate to the project

```bash
cd PolicyProposalProcessingAPI
```

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

The application starts on

```
http://localhost:8080
```

---

# Business Rules

* Customer age must be between **18 and 65**.
* Policy term must be **10, 15, 20, 25, or 30 years**.
* Sum assured must be between **₹1,00,000 and ₹5,00,00,000**.
* Minimum annual premium is **₹5,000**.
* PAN is mandatory when annual premium exceeds **₹50,000**.
* Nominee cannot be the same as the customer.
* Payment frequency must be one of the supported reference values.

---

# API Endpoints

## 1. Reference Master

### Get Reference Data

```
GET /reference-master/{category}
```

Example

```
GET /reference-master/POLICY_TERM
```

Response

```json
[
  "10",
  "15",
  "20",
  "25",
  "30"
]
```

---

## 2. Customer

### Create Customer

```
POST /customers
```

Request

```json
{
  "firstName": "Vinay",
  "lastName": "RM",
  "age": 23,
  "gender": "Male",
  "email": "vinay@gmail.com",
  "mobileNumber": "9876543210",
  "panNumber": "ABCDE1234F"
}
```

Response

```json
{
  "customerId": 1001,
  "firstName": "Vinay",
  "lastName": "RM",
  "age": 23,
  "gender": "Male",
  "email": "vinay@gmail.com",
  "mobileNumber": "9876543210",
  "panNumber": "ABCDE1234F"
}
```

---

### Get All Customers

```
GET /customers
```

---

### Get Customer by ID

```
GET /customers/{id}
```

Example

```
GET /customers/1001
```

---

### Update Customer

```
PUT /customers/{id}
```

---

## 3. Proposal

### Create Proposal

```
POST /proposals
```

Request

```json
{
  "customerId": 1001,
  "policyTerm": 20,
  "sumAssured": 500000,
  "annualPremium": 25000,
  "nomineeName": "Ramesh",
  "paymentFrequency": "YEARLY"
}
```

Response

```json
{
  "proposalId": 1001,
  "customerId": 1001,
  "policyTerm": 20,
  "sumAssured": 500000,
  "annualPremium": 25000,
  "nomineeName": "Ramesh",
  "paymentFrequency": "YEARLY",
  "status": "CREATED",
  "policyNumber": null
}
```

---

### Get Proposal by ID

```
GET /proposals/{id}
```

---

### Submit Proposal

```
POST /proposals/{id}/submit
```

Example

```
POST /proposals/1001/submit
```

Response

```json
{
  "proposalId": 1001,
  "customerId": 1001,
  "policyTerm": 20,
  "sumAssured": 500000,
  "annualPremium": 25000,
  "nomineeName": "Ramesh",
  "paymentFrequency": "YEARLY",
  "status": "SUBMITTED",
  "policyNumber": "POL1752744685123"
}
```

---

## 4. Audit

### Get All Audit Records

```
GET /audits
```

Response

```json
[
  {
    "auditId": 1,
    "proposalId": 1001,
    "action": "SUBMIT_PROPOSAL",
    "description": "Proposal submitted successfully",
    "timestamp": "2026-07-16T09:30:25"
  }
]
```

---

# Thread-Safe Storage

The application uses Java Collections for in-memory storage.

* ConcurrentHashMap for Customers
* ConcurrentHashMap for Proposals
* synchronizedList for Audit Records

---

# Running Tests

Run all unit tests using Maven:

```bash
mvn test
```

Or run the test classes directly from your IDE.

---

# Expected Workflow

1. Retrieve reference master data.
2. Create a customer.
3. Retrieve customer details.
4. Create a policy proposal.
5. Submit the proposal.
6. Generate policy number.
7. Update proposal status.
8. Create an audit record.
9. Retrieve audit history.

---



