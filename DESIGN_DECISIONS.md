# DESIGN_DECISIONS.md

# Policy Proposal Processing API – Design Decisions

## 1. Architecture

The application follows a layered architecture to ensure separation of concerns and maintainability.

```text
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
In-Memory Storage (Java Collections)
```

### Responsibilities

* **Controller**

    * Exposes REST endpoints.
    * Receives HTTP requests.
    * Delegates business processing to the service layer.

* **Service**

    * Implements business rules.
    * Performs validations.
    * Coordinates repository operations.

* **Repository**

    * Stores and retrieves application data.
    * Uses Java Collections instead of a database.

---

# 2. In-Memory Storage

The assignment explicitly prohibits database usage.

The following thread-safe collections were selected:

| Data          | Collection                                      |
| ------------- | ----------------------------------------------- |
| Customers     | ConcurrentHashMap<Long, Customer>               |
| Proposals     | ConcurrentHashMap<Long, Proposal>               |
| Audit Records | Collections.synchronizedList(new ArrayList<>()) |

### Reason

* Fast lookup by ID for customers and proposals.
* Efficient updates using unique IDs.
* Thread-safe access for concurrent requests.
* Audit records are append-only, making a synchronized list suitable.

---

# 3. ID Generation

Unique IDs are generated using `AtomicLong`.

Example:

```java
customerIdGenerator.incrementAndGet();
proposalIdGenerator.incrementAndGet();
auditIdGenerator.incrementAndGet();
```

### Reason

* Thread-safe.
* Simple implementation.
* No database sequence required.

---

# 4. Policy Number Generation

Policy numbers are generated during proposal submission.

Example:

```text
POL100001
```

### Reason

* Ensures every submitted proposal receives a unique policy number.
* Generation occurs only after successful validation.

---

# 5. Business Validation

Business rules are implemented in the service layer instead of the controller.

Implemented validations include:

* Customer age must be between 18 and 65 years.
* Policy term must be 10, 15, 20, 25, or 30 years.
* Sum assured must be between ₹1,00,000 and ₹5,00,00,000.
* Minimum annual premium is ₹5,000.
* PAN is mandatory when annual premium exceeds ₹50,000.
* Nominee cannot be the customer.
* Payment frequency must be a valid reference value.

### Reason

Keeping business logic inside the service layer improves maintainability, promotes code reuse, and keeps controllers focused on handling HTTP requests.

---

# 6. Reference Master

Reference values such as Policy Term and Payment Frequency are maintained centrally.

### Reason

* Eliminates hard-coded values throughout the application.
* Makes future updates easier.
* Improves consistency.

---

# 7. Exception Handling

Business validation failures result in exceptions with meaningful messages.

Examples:

* Customer not found
* Proposal not found
* Invalid policy term
* PAN is mandatory

### Reason

Provides clear feedback to API consumers and simplifies debugging.

---

# 8. Proposal Submission Flow

The proposal submission process follows these steps:

1. Retrieve proposal.
2. Verify proposal exists.
3. Retrieve customer.
4. Revalidate all business rules.
5. Generate policy number.
6. Update proposal status to SUBMITTED.
7. Create audit record.
8. Return updated proposal.

This ensures that only valid proposals are submitted and every submission is recorded.

---

# 9. Testing Approach

JUnit 5 is used to verify the core business logic of the application by testing the service layer directly.

The implemented test cases include:

- Successful customer creation.
- Customer age validation (age less than 18).
- Customer age validation (age greater than 65).
- Mandatory first name validation.
- Successful proposal creation.
- Successful proposal submission.
- Policy status update from `CREATED` to `SUBMITTED`.
- Policy number generation during proposal submission.

---

# 10. Assumptions

The following assumptions were made during implementation:

* Data is retained only while the application is running.
* Customer IDs and Proposal IDs are unique.
* A proposal belongs to exactly one customer.
* Audit records are immutable after creation.
* Reference master values remain fixed during execution.

---

# 11. Trade-offs

Because the assignment does not allow a database:

* Data is lost when the application stops.
* No persistent storage is available.
* Relationships are maintained manually using IDs.

