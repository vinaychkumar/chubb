# Chubb APAC Claims Management System

A full-stack claims management application designed to support motor and property insurance claim processing for Chubb APAC.

The application provides separate workflows for claimants and claims officers, covering claim submission, claim tracking, claim assignment, assessment, and settlement.

## 1. Project Overview

The Chubb Claims Management System is designed to simplify and manage the insurance claim lifecycle.

The application supports two primary user roles:

**Claimant**
- Submit new insurance claims.
- View submitted claims.
- Track claim status.
- View claim details.
- Provide additional information when requested.

**Claims Officer**
- View incoming claims.
- Review assigned claims.
- Request additional information from claimants.
- Assess claims and make decisions.
- Progress claims towards settlement.

## 2. Technology Stack

| Layer | Technology |
|---|---|
| Frontend | Angular 21 |
| Backend | Java 17, Spring Boot |
| Database | MySQL |
| Backend build tool | Maven |
| Frontend package manager | npm |
| API communication | REST APIs |
| Version control | Git and GitHub |
| IDE | Eclipse |

## 3. Project Structure

The repository contains both frontend and backend applications.

```text
chubb/
│
├── claims-service/
│   │
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/chubb/claims/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   │
│   │   └── test/
│   │
│   └── pom.xml
│
├── chubb-claims-ui/
│   │
│   ├── src/app/
│   │   ├── claimant/
│   │   │   ├── dashboard/
│   │   │   ├── submit-claim/
│   │   │   ├── claim-list/
│   │   │   └── claim-details/
│   │   │
│   │   ├── officer/
│   │   │   ├── dashboard/
│   │   │   ├── incoming-claims/
│   │   │   └── claim-review/
│   │   │
│   │   ├── app.routes.ts
│   │   └── app.config.ts
│   │
│   ├── angular.json
│   └── package.json
│
├── documentation/
│
├── postman/
│
├── AI-WORKING-JOURNAL.md
├── README.md
└── .gitignore
```

*Note: The documentation and Postman folders are planned additions if they have not yet been created.*

## 4. Backend Architecture

The backend follows a layered architecture.

**Entity Layer**

Represents the application's database entities, including:

- AppUser
- Claim
- ClaimDocument
- ClaimHistory
- ClaimAssessment
- ClaimInformationRequest

**DTO Layer**

Handles API request and response data transfer.

Examples:
- ClaimRequest
- ClaimResponse
- AssignClaimRequest
- ClaimDecisionRequest
- AdditionalInfoRequest
- ClaimantInformationResponse

**Repository Layer**

Uses Spring Data JPA to communicate with the MySQL database.

**Service Layer**

Contains business logic for claim creation, retrieval, assignment, information requests, decisions, and settlement.

**Controller Layer**

Exposes REST endpoints for claimant and claims officer operations.

## 5. API Endpoints

The following endpoints have been implemented in the backend.

### Claimant APIs

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/claims` | Create a new claim |
| GET | `/api/claims/claimant/{claimantId}` | Retrieve claimant claims |
| GET | `/api/claims/{claimNumber}` | Retrieve claim details |

### Claims Officer APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/claims/incoming` | View incoming claims |
| GET | `/api/claims/officer/{officerId}` | View officer claims |
| PATCH | `/api/claims/{claimNumber}/assign` | Assign a claim |
| PATCH | `/api/claims/{claimNumber}/request-info` | Request additional information |
| PATCH | `/api/claims/{claimNumber}/decision` | Make a claim decision |
| PATCH | `/api/claims/{claimNumber}/settle` | Settle a claim |

The exact request parameters and JSON payloads should be documented alongside the API verification results.

## 6. Frontend Modules

The Angular application is organized into two feature areas.

### Claimant Module

1. Dashboard
2. Submit Claim
3. Claim List
4. Claim Details

### Claims Officer Module

1. Dashboard
2. Incoming Claims
3. Claim Review

Angular standalone components and routing are used to organize navigation between these screens.

Frontend implementation and backend integration are ongoing.

## 7. Database Configuration

The application uses MySQL.

Create the database:

```sql
CREATE DATABASE chubb_claims_db;
```

Configure the database connection using environment variables.

Example `application.properties` configuration:

```properties
spring.datasource.url=${db_url}
spring.datasource.username=${db_username}
spring.datasource.password=${db_password}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

Configure the following environment variables before starting the backend:

- `db_url`
- `db_username`
- `db_password`

Never commit actual database credentials or secrets to GitHub.

## 8. How to Run the Backend

**Prerequisites**
- Java 17 or compatible configured JDK.
- Maven or Maven Wrapper.
- MySQL.

**Steps**

1. Navigate to the backend directory:

```bash
cd claims-service
```

2. Configure your database environment variables.

3. Start the Spring Boot application:

Windows:

```cmd
mvnw.cmd spring-boot:run
```

Alternatively, using installed Maven:

```bash
mvn spring-boot:run
```

The backend runs on the configured server port.

## 9. How to Run the Frontend

**Prerequisites**
- Node.js
- npm
- Angular CLI

**Steps**

1. Navigate to the Angular project:

```bash
cd chubb-claims-ui
```

2. Install dependencies:

```bash
npm install
```

3. Start Angular:

```bash
ng serve --open
```

The frontend will be available at:

http://localhost:4200/

## 10. API Testing

Backend APIs are verified using Postman.

The verification process includes:

- Claim creation.
- Claim retrieval.
- Incoming claim retrieval.
- Claim assignment.
- Additional information requests.
- Claim decision.
- Claim settlement.
- Database record verification.

Postman collections and supporting test evidence will be added as part of the documentation milestone.

## 11. Security and Current Scope

The current implementation focuses on the claims management workflow.

JWT authentication, frontend authentication, and additional security integrations are not included in the current development scope.

These can be considered as future enhancements. The current application should be treated as a development prototype rather than a production-ready security implementation.

## 12. Future Enhancements

- Complete Angular claimant dashboard.
- Implement a multi-step claim submission process.
- Integrate Angular with Spring Boot REST APIs.
- Implement claimant claim tracking and details.
- Complete claims officer dashboard and review workflow.
- Add document upload and management.
- Implement authentication and role-based authorization.
- Add comprehensive automated tests.
- Add API documentation and Postman collections.
- Improve error handling and application validation.

## 13. AI Working Journal

An `AI-WORKING-JOURNAL.md` file is maintained in this repository.

It records:
- Questions asked to AI during development.
- Suggestions accepted.
- Suggestions challenged or overridden.
- Reasons for implementation decisions.
- Testing and verification activities.

This journal supports transparency and documents the developer's involvement in AI-assisted development.

## 14. Version Control

The project is maintained in GitHub.

Repository:

https://github.com/vinaychkumar/chubb

Meaningful commits are maintained as development progresses.

## 15. Author

**Developer:** Vinay Kumar

**Project:** Chubb APAC Claims Management System

**Purpose:** Full-stack developer technical assessment.
