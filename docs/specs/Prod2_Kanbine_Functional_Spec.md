# Kanbine | Time Tracking Backend
# Prod2 Functional Spec (Current Implementation Snapshot)

---

## Summary

| Field | Value |
|---|---|
| **Start Date** | 2024-08-05 (earliest project log date observed) |
| **Submission Date** | 2026-05-06 (spec refresh draft) |
| **Business SME** | N/A |
| **Technical SME** | N/A |
| **Input Data (including Interviews)** | `README.md`, backend source code, ADRs 0001-0003 |
| **Related Specs** | `docs/adrs/adr_0001.md`, `docs/adrs/adr_0002.md`, `docs/adrs/adr_0003.md`, `docs/adrs/adr_0004.md`, `docs/specs/Prod3_Kanbine_DDD_Microservices_Migration_Spec.md` |
| **Scope Survey** | N/A (no separate survey teardown doc linked in repo) |
| **Pre Mortem** | Most likely rejection reason: missing product/business metrics and external stakeholder sign-off details. |
| **Previous Published Version** | None |
| **Changes Since the Previous Version** | 1) Replaced generic template placeholders with implementation-backed content. 2) Documented JWT-based security and API edge behavior. 3) Captured actual data model used by JPA entities. 4) Added concrete FR/NFR set tied to current code. 5) Added current risks and testing gaps. |

---

# Scope

### Product Summary
Kanbine is a backend API for tracking user work in 10-minute time blocks against assignments/projects. The current implementation is a Spring Boot 3.3 service with MySQL persistence, JWT authentication, and Swagger docs.

---

### Problem Summary
The existing spec file was still in template form and did not represent the current implemented behavior. Engineering and product need an up-to-date functional snapshot that reflects current endpoints, constraints, security posture, and known risks.

#### Background
- Backend stack is Java 21 + Spring Boot + Spring Security + Spring Data JPA.
- Core domain objects are **User**, **Assignment**, **TimeCard**, and **UserAssignment** (join entity).
- Authentication is stateless JWT; most API endpoints require a valid bearer token.
- Data seeding exists for local/demo environments under the `seed` profile.

#### 1-sentence Goal
Provide an accurate, implementation-backed Prod2 spec for the currently shipped Kanbine backend behavior.

---

### Input Constraints
- Must align with implemented code in `backend/src/main/java` and `backend/src/main/resources`.
- Authentication mechanism is fixed to JWT in current implementation (ADR-0003).
- Service layer architecture avoids `*Impl` split unless needed (ADR-0001).
- DTO inheritance pattern is already established (ADR-0002).

---

### Important Scope Items
- Auth endpoints for login and registration.
- Protected CRUD APIs for users, assignments, and time cards.
- User-assignment linking/unlinking endpoints.
- 10-minute time-card duration/block validation.
- MySQL-backed persistence with JPA/Hibernate.
- Swagger/OpenAPI documentation exposure.
- Local/demo bootstrap via Docker Compose and optional seed profile.

---

### Important Scope Exclusions
- Frontend/UI behavior is out of scope.
- Payroll/invoicing workflow is out of scope.
- Role-based authorization (fine-grained roles/permissions) is out of scope.
- Token refresh/revocation workflows are out of scope.
- Production SLO instrumentation and capacity benchmarking are out of scope.

---

### Functional Requirements

| Req # | Source | Type | Description |
|---|---|---|---|
| FR 1 | Customer/TPM | Changed | System supports user registration and login through `/api/auth/register` and `/api/auth/login`, returning JWT bearer credentials on successful login. |
| FR 2 | Customer/TPM | Changed | System supports authenticated CRUD-style read/create/delete operations for users at `/api/users` and `/api/users/{id}`. |
| FR 3 | Customer/TPM | Changed | System supports authenticated CRUD-style read/create/delete operations for assignments at `/api/assignments` and `/api/assignments/{id}`. |
| FR 4 | Customer/TPM | Changed | System supports authenticated CRUD-style read/create/delete operations for time cards at `/api/timecards` and `/api/timecards/{id}`. |
| FR 5 | Customer/TPM | New | System supports assigning and unassigning assignments to users via `/api/users/{userId}/assignments/{assignmentId}` (POST/DELETE). |
| FR 6 | Customer/TPM | New | System enforces time card validity rules: start and end are required, start is before end, duration does not exceed 10 minutes, and both timestamps must remain within a single 10-minute block. |

---

### Non-Functional Requirements

| Req # | Source | Description |
|---|---|---|
| NFR 1 | TPM | API authentication is stateless JWT-based for all endpoints except `/api/auth/**` and Swagger endpoints. |
| NFR 2 | TPM | Passwords are stored as BCrypt hashes using Spring Security `PasswordEncoder`. |
| NFR 3 | TPM | API schema is discoverable via OpenAPI/Swagger UI (`/swagger-ui/**`, `/v3/api-docs/**`). |
| NFR 4 | TPM | Persistence uses MySQL in runtime (`mysql:8.1` in compose) and H2 in tests. |
| NFR 5 | TPM | Regression baseline requires Gradle/JUnit test suite to execute successfully in CI/local environments. |

---

# Context

### Survey Resolution

| Field | Value |
|---|---|
| **Technical Teardown** | N/A (no dedicated teardown artifact found in workspace) |
| **Fit to Build On?** | Yes, with caution |
| **Explanation** | Core architecture is clean enough for incremental work, but there are correctness/test-quality gaps (notably in service tests and auth lookup path) that should be addressed before major feature growth. |

---

### Customer-focused CIV Problems

*A CIV Problem is a real problem a customer faces. It describes a market opportunity, not the product. These statements are honest and correct problem statements, entirely problem-focused, not solution-focused.*

#### Challenging Problems
Thinking about the customer, what, if anything, would they find challenging to build themselves?
1. **Secure API auth flow**: Implementing robust JWT auth and request filtering consistently.
2. **Reliable time validation**: Enforcing strict 10-minute entry rules without user confusion.

#### Important Problems
Thinking about the customer, what, if anything, would they say is really Important for the product to solve?
1. **Track work accurately**: Capture auditable time entries against assignments.
2. **Organize assignment ownership**: Keep assignment-to-user relationships current.

#### Valuable Problems
Thinking about the customer, what problems does this product solve that have a direct financial consequence on the customer?
1. **Billable time capture**: Better time attribution improves revenue recovery.
2. **Assignment rate tracking**: Assignment hourly rates support downstream costing.

---

### Product-focused CIV Problems

*Sometimes a product needs to solve CIV problems that come from the goals of the company and not its customers.*

#### Challenging Problems
What will be challenging for the team building the product?
1. **Data integrity under growth**: Maintaining clean relationship data across users/assignments/timecards.
2. **Security hardening**: Expanding from auth-only security to role/permission controls.

#### Important Problems
What is really Important to solve to meet the company's goals?
1. **Correctness first**: Prevent invalid time records and broken auth behavior.
2. **Maintainability**: Keep service boundaries and DTO mapping predictable.

---

### Inputs, Outputs, Controls

#### Inputs
What are the important inputs to the system?
- Login credentials
- User details
- Assignment definitions (name/description/rate/currency)
- Time card timestamps linked to user/assignment IDs

#### Outputs
What are the important outputs from the system?
- Auth token payloads
- User, assignment, and timecard DTOs
- Assignment linkage views via ID sets

#### Controls
What are the important things the user controls to affect how the inputs are turned into outputs?
- API endpoint selection
- Payload content
- Assignment linkage operations
- JWT bearer token presentation

---

### Core Functions

- **Authentication**: Verifies user credentials and returns a signed JWT used to access protected endpoints.
- **Assignment Management**: Creates and retrieves assignment metadata, including rate and currency fields used for planning and costing context.
- **User Assignment Linking**: Associates users and assignments through a dedicated join entity so work ownership can be represented explicitly.
- **Time Block Capture**: Stores short-duration work entries and enforces 10-minute validity constraints before persistence.

---

### P2 Feedback
None.

---

# Insights

### Engineering Insights
- Keep service classes concrete (per ADR-0001) unless multi-implementation need appears.
- Preserve DTO inheritance pattern to avoid request/response duplication (ADR-0002).
- Maintain stateless JWT flow and keep auth exceptions limited to `/api/auth/**` + Swagger endpoints.
- Treat time-card validation as a domain invariant; enforce in service and entity lifecycle hooks.
- Prefer repository-backed lookup paths that avoid full-collection scans in auth-related code paths.

---

### Follow-up Recommendations
1. Add repository method usage in auth login path (avoid loading all users then filtering).
2. Repair service tests that currently mock mapper beans not injected by service instances.
3. Add integration tests for protected endpoints (401 vs 200 behavior and JWT parsing).
4. Add explicit uniqueness constraint for `users.email` if not already present at DB migration level.
5. Define production-ready observability (error rates, auth failure metrics, p95 latency).
6. Execute phased migration plan from `docs/specs/Prod3_Kanbine_DDD_Microservices_Migration_Spec.md`.

---

# Solution Architecture

### Before Diagram
Before this implementation snapshot, no concrete pre-change architecture is documented in-repo. N/A.

Link to LucidChart: N/A

---

### After Diagram
```text
Client -> AuthController (/api/auth/login, /register)
       -> JWT token
Client + Bearer token -> JwtAuthenticationFilter -> SecurityContext
                      -> User/Assignment/TimeCard Controllers
                      -> Services -> Repositories -> MySQL
```

---

### Solution Summary
- A single Spring Boot backend exposes REST endpoints for auth, users, assignments, and timecards.
- Spring Security enforces stateless JWT authentication on all non-auth/non-Swagger routes.
- Domain data is persisted through JPA repositories in MySQL (runtime) with H2 for tests.
- A `seed` profile can populate local/demo data for quick startup and testing.

---

### Big Bang ITDs

#### ITD AUTH.1 - Use JWT bearer tokens for stateless API authentication.

| Field | Details |
|---|---|
| **The Problem** | How should the API authenticate requests without relying on server-side session state? |
| **Options Considered** *(Decision in bold)* | 1. Stateful session authentication. 2. **Stateless JWT bearer authentication.** |
| **Reasoning** | JWT keeps backend state minimal and works naturally with REST APIs and distributed deployment patterns; this is implemented in current `SecurityConfig`, `JwtAuthenticationFilter`, and `JwtUtils`. |
| **P2 Feedback** | Refresh/revocation strategy remains future work. |

#### ITD DATA.1 - Model user-assignment relation as an explicit join entity.

| Field | Details |
|---|---|
| **The Problem** | How should many-to-many user/assignment linkage be represented while allowing future extension? |
| **Options Considered** *(Decision in bold)* | 1. Implicit JPA many-to-many table. 2. **Explicit `UserAssignment` entity with composite key.** |
| **Reasoning** | Explicit join entity improves extensibility and supports constraints/metadata on relationship records later. |
| **P2 Feedback** | Unique constraint on (`user_id`, `assignment_id`) is present in entity mapping. |

---

### Risks

| Name | Details |
|---|---|
| Auth lookup inefficiency | Login flow currently resolves user ID by loading all users and filtering by email in memory; this is inefficient and can regress with scale. |
| Test reliability gap | Several unit tests mock mappers that are not dependency-injected in services, reducing trust in test assertions. |
| Validation duplication drift | Time validation exists in service and entity hooks; if rules diverge, behavior can become inconsistent. |
| Missing role-based authorization | All authenticated users currently share the same access scope; sensitive operations are not role-gated. |

---

# Experiment Details

### Experiment Index

#### GitHub repo for experiments
Link to GitHub repo: N/A

| ID | Risks Addressed | Hypothesis | Description |
|---|---|---|---|
| 1 | Auth lookup inefficiency | A direct email lookup in auth path reduces query and memory overhead compared to full list filtering. | Implement login path refactor to resolve user identity by repository lookup and compare behavior/perf in integration tests. |
| 2 | Validation duplication drift | Consolidating time-card rules into one authoritative validator reduces divergence risk while preserving behavior. | Prototype shared validator usage from both service and entity paths, then run regression tests. |

---

### Experiment @Experiment::ID
N/A.

---

# Solution Details

### Solution Index

| Type | Name | Description |
|---|---|---|
| Important Topic | API and Security Edge | Documents externally visible API behavior and JWT security model. |
| Data Structures | Time Tracking Core Model | Documents entities, relationships, and persistence behavior for user/assignment/timecard data. |
| Edge Interactions | REST API Surface | Documents endpoint interactions between clients and backend service. |

---

## Important Topic: API and Security Edge

### Background
The backend exposes REST endpoints under `/api/**`. Auth endpoints (`/api/auth/**`) are public; all other API routes require a valid bearer token. Swagger/OpenAPI endpoints are public for developer discovery.

#### Topic ITDs & IFs

##### IF AUTH.1 - Non-auth endpoints require JWT authentication.

| Field | Details |
|---|---|
| **Description** | `SecurityConfig` permits `/api/auth/**`, `/v3/api-docs/**`, `/swagger-ui/**`, and `/swagger-ui.html`; all other requests require authentication. `JwtAuthenticationFilter` parses `Authorization: Bearer <token>`. |
| **P2 Feedback** | No role-based route segmentation is currently configured. |

##### IF AUTH.2 - JWT tokens are signed and time-bound.

| Field | Details |
|---|---|
| **Description** | `JwtUtils` signs tokens using HS256 and `kanbine.app.jwtSecret`; expiration uses `kanbine.app.jwtExpirationMs` (default 24h). |
| **P2 Feedback** | Token refresh/revocation behavior is not implemented in current scope. |

---

## Data Structures: Time Tracking Core Model

| Field | Details |
|---|---|
| **Overview** | The system persists users, assignments, time cards, and explicit user-assignment relationships via JPA entities. |
| **Diagram** | ER summary: `users (1) -- (many) time_cards (many) -- (1) assignments`; `users (1) -- (many) user_assignments (many) -- (1) assignments`. |
| **Description** | - **User**: *id*, *email*, *password* and collection links to **TimeCard** and **UserAssignment**. - **Assignment**: *id*, *name*, *description*, *hourlyRate*, *currency*. - **TimeCard**: *id*, *startTime*, *endTime*, *user_id*, *assignment_id*. - **UserAssignment**: composite key over *user_id* + *assignment_id*. |
| **CUD Operations** | Auth/register and user endpoints create **User** records; assignment endpoints create **Assignment** records; timecard endpoint creates **TimeCard** records; user assignment endpoints create/delete **UserAssignment** rows. |
| **Query Patterns** | Controllers primarily perform list-all and by-id lookups through service/repository methods; auth loads user details by email through `UserRepository.findByEmail`. |
| **Persistence** | Runtime DB is MySQL (`jdbc:mysql://.../kanbine`), with Hibernate DDL auto configured by property (`update` default). Tests use in-memory H2 with `create-drop`. |
| **Metrics** | Explicit throughput/latency SLOs are not defined in-repo. |
| **Comparison with Teardown** | N/A (no teardown comparison artifact found). |

#### Data Structure Design Decisions

##### ITD DATA.2 - Enforce short-duration time entry constraints at domain level.

| Field | Details |
|---|---|
| **The Problem** | Where should the system enforce the 10-minute time entry rules to prevent invalid persisted records? |
| **Options Considered** *(Decision in bold)* | 1. Controller-only validation. 2. **Service + entity lifecycle validation.** |
| **Reasoning** | Current implementation validates in both service (`TimeCardService`) and entity hooks (`@PrePersist/@PreUpdate`) to reduce risk of bypass paths persisting invalid data. |
| **P2 Feedback** | Consolidation to a single shared validator can reduce maintenance drift risk. |

---

## Edge Interactions: REST API Surface

| Field | Details |
|---|---|
| **Purpose** | Provide programmatic API access for authentication and managing users, assignments, and time cards. |
| **Interaction Type** | REST/JSON over HTTP with bearer-token auth. |
| **Personas** | API client developers, internal integrators, and authenticated end users via frontend/service clients. |
| **Diagram** | N/A |
| **Interactions** | `POST /api/auth/login`, `POST /api/auth/register`; authenticated `GET/POST/DELETE` for `/api/users`, `/api/assignments`, `/api/timecards`; assignment linking endpoints under `/api/users/{userId}/assignments/{assignmentId}`. |
| **Comparison with Teardown** | Rebuild/iteration with concrete JWT auth and documented endpoint surface. |

#### Edge Design Decisions

##### ITD EDGE.1 - Keep Swagger endpoints unauthenticated for developer usability.

| Field | Details |
|---|---|
| **The Problem** | Should API documentation endpoints require JWT auth? |
| **Options Considered** *(Decision in bold)* | 1. Require auth for docs. 2. **Allow unauthenticated Swagger/OpenAPI access.** |
| **Reasoning** | Improves developer onboarding/testing; risk is limited to schema exposure and can be revisited in production hardening. |
| **P2 Feedback** | Production deployment may later gate docs by environment/network policy. |

---

## Process Design: Build/Test and Local Run

| Field | Details |
|---|---|
| **Purpose** | Provide reliable local and CI execution path for building, testing, and running the backend stack. |
| **Diagram** | Source -> Gradle build/test -> container image (optional) -> Docker Compose runtime (app + MySQL). |
| **Solution** | Mostly linear process using Gradle tasks and optional compose orchestration. |
| **Expected Median Duration** | N/A (not benchmarked in spec). |
| **Trigger** | Developer command invocation or CI pipeline trigger. |
| **Blank Data Structure** | Build artifacts, test reports, and runtime DB tables in MySQL/H2. |
| **Comparison with Teardown** | N/A. |

### Process Component Index: Build/Test and Local Run

| Type | Name | Implementation Status |
|---|---|---|
| Work Unit | Build and Unit Test | Implemented |
| Work Unit | Local Runtime via Docker Compose | Implemented |
| Build Assembly Line | Build Assembly Line: Build/Test and Local Run | |

### Process Components

#### WORK UNIT: Build and Unit Test

| Field | Details |
|---|---|
| **Purpose** | Compile code and run JUnit tests for regression detection. |
| **Inputs** | Backend source code, Gradle build config, test resources. |
| **Outputs** | Compiled classes and test results. |
| **Quality Bar** | All tests pass; no critical compilation or startup-context failures. |

#### WORK UNIT: Local Runtime via Docker Compose

| Field | Details |
|---|---|
| **Purpose** | Run app + MySQL locally with predictable configuration for development/demo use. |
| **Inputs** | `docker-compose.yaml`, Dockerfile, application properties, environment variables. |
| **Outputs** | Running backend on port 8080 and MySQL on 3306. |
| **Quality Bar** | Service startup succeeds and endpoints are reachable with expected auth behavior. |

---

# Appendices

## A. Current Endpoint Inventory
- Auth: `POST /api/auth/login`, `POST /api/auth/register`
- Users: `GET /api/users`, `POST /api/users`, `GET /api/users/{id}`, `DELETE /api/users/{id}`
- User-Assignment: `POST /api/users/{userId}/assignments/{assignmentId}`, `DELETE /api/users/{userId}/assignments/{assignmentId}`
- Assignments: `GET /api/assignments`, `POST /api/assignments`, `GET /api/assignments/{id}`, `DELETE /api/assignments/{id}`
- TimeCards: `GET /api/timecards`, `POST /api/timecards`, `GET /api/timecards/{id}`, `DELETE /api/timecards/{id}`

## B. Verification Notes
- Source alignment validated against current backend controllers, services, entities, repositories, config, and ADR files.
- Functional and non-functional statements in this doc are intentionally limited to behavior observed in repository code.
- Baseline verification run executed: `backend\\gradlew.bat test --no-daemon` on 2026-05-06.
- Current baseline status: 14 tests run, 3 failing (`TimeCardServiceTest.testSaveTimeCard`, `UserServiceTest.testSaveUser`, `UserServiceTest.testAssignAssignmentToUser`).
- Strategic migration direction is documented in `docs/specs/Prod3_Kanbine_DDD_Microservices_Migration_Spec.md` and `docs/adrs/adr_0004.md`.
