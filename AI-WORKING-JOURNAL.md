# AI Working Journal — Chubb Claims Management System

**Project:** Chubb APAC Claims Management System  
**Developer:** Vinay Kumar  
**Technology:** Java, Spring Boot, MySQL, Angular 21  
**Purpose:** Record AI assistance, accepted suggestions, challenged recommendations, and independent development decisions.

---

## Session 1 — Backend project setup

**Topic:** Spring Boot project structure and database setup.

**What I asked AI:**
- How to structure a Spring Boot claims management application.
- How to create entities, enums, DTOs, repositories, services, and controllers.
- How to configure MySQL connectivity.

**What I accepted:**
- Layered backend architecture.
- Separate entity, DTO, repository, service, and controller packages.
- Separate entities for claims, documents, assessment, and claim history.

**What I reviewed:**
- Entity relationships and database mappings.
- API request and response structures.
- Whether the proposed structure matched the claims management requirements.

**Outcome:** Backend project created and successfully connected to MySQL.

---

## Session 2 — Claim management APIs

**Topic:** Claim submission and claimant operations.

**What I asked AI:**
- How to implement claim creation.
- How to retrieve claims by claimant ID.
- How to retrieve an individual claim using its claim number.

**What I accepted:**
- REST API structure.
- Request and response DTO approach.
- Service-layer business logic.
- Saving claim history when a claim is created.

**Verification performed:**
- Tested POST and GET endpoints using Postman.
- Checked HTTP responses.
- Verified saved claim and history records in MySQL.

**Outcome:** Claim creation and retrieval APIs worked successfully.

---

## Session 3 — Claims officer workflow

**Topic:** Claims officer operations.

**What I asked AI:**
- How to implement incoming claims.
- How to assign claims to officers.
- How to request additional information.
- How to make claim decisions and settle claims.

**What I accepted:**
- Separate endpoints for officer operations.
- Claim assignment and decision workflow.
- Claim history tracking.

**Verification performed:**
- Tested incoming claims.
- Tested officer assignment.
- Tested additional information requests.
- Tested claim decisions and settlement.
- Verified database updates.

**Outcome:** Claims officer workflow APIs were implemented and tested.

---

## Session 4 — Authentication scope decision

**Topic:** JWT authentication and security.

**What AI suggested:**
- Implement JWT authentication.
- Add login and registration functionality.
- Configure role-based authorization.

**What I challenged / overrode:**
- I decided not to implement JWT at the current development stage.
- I also decided not to add frontend authentication or external data-source integrations.

**Reason:**
- My immediate priority was to deliver a working claims management model.
- I wanted to focus on the claimant and claims officer workflows and frontend-to-backend integration.
- Authentication can be considered as a future enhancement.

**Outcome:** JWT implementation deferred based on project scope.

---

## Session 5 — Angular frontend structure

**Topic:** Angular 21 frontend setup.

**What I asked AI:**
- How to create the Angular frontend for the existing claims project.
- How to organize claimant and officer components.
- How to configure application routing.

**What I accepted:**
- Standalone Angular component structure.
- Separate claimant and officer feature folders.
- Angular routing for the seven required screens.

**Components planned:**

Claimant:
- Dashboard
- Submit Claim
- Claim List
- Claim Details

Claims Officer:
- Dashboard
- Incoming Claims
- Claim Review

**Outcome:** Angular project created and initial component structure and routing configured. Component implementation remains in progress.

---

## Session 6 — Git and GitHub repository setup

**Topic:** Version control and project submission.

**What I asked AI:**
- How to upload the Angular frontend and Spring Boot backend into one GitHub repository.
- How to configure `.gitignore`.
- How to resolve the nested Git repository issue.

**What I accepted:**
- One common repository containing both applications.
- Exclusion of generated folders such as `node_modules` and `target`.
- Protection of database credentials and environment secrets.
- Creation of a backup of the existing backend Git history.

**What I reviewed:**
- Existing backend Git history.
- Staged frontend and backend files.
- Repository structure before publishing.

**Issue encountered:**
- Git initially treated the backend as an embedded repository.
- The backend Git history was backed up before resolving the nested repository relationship.

**Outcome:** Frontend and backend were prepared for a combined repository.

---

## Session 7 — GitHub publishing

**Topic:** Connecting the local project to GitHub.

**What I asked AI:**
- How to configure the remote repository.
- How to push the project to GitHub.

**Issue encountered:**
- Initial push returned HTTP 403 because the remote repository belonged to a different GitHub account.

**Decision:**
- Updated the remote URL to my own GitHub repository.

**Outcome:** GitHub publishing to be verified after the push.

---

## Future AI-assisted development

The following activities are planned and will be updated as development progresses:

- Complete claimant dashboard.
- Implement the multi-step claim submission form.
- Integrate Angular with Spring Boot REST APIs.
- Implement claimant claim tracking and details.
- Implement claims officer dashboard and review screens.
- Add API verification documentation and Postman collection.
- Complete project README and setup instructions.
- Perform final application testing.

---

## Developer responsibility

AI was used as a development assistant for architecture guidance, implementation suggestions, troubleshooting, and documentation.

I reviewed the proposed solutions, made project-scope decisions, tested backend APIs, verified database changes, and decided which suggestions to accept or defer.

All final implementation decisions and the ability to explain and demonstrate the application remain my responsibility.
