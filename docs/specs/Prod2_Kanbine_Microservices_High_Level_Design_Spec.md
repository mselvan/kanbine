# Kanbine | DDD Microservices Migration (Prod2 HLD)

---

## Summary

| Field | Value |
|---|---|
| **Document Type** | Tactical high-level technical design |
| **Date** | 2026-05-06 |
| **Current State** | Spring Boot monolith (`backend`) |
| **Target State** | DDD-aligned microservices on shared platform standards |
| **Migration Style** | Strangler-fig incremental extraction |
| **Status** | Draft for implementation planning |

---

## 1) Objectives

### Business Objectives
- Improve delivery speed by enabling independent domain teams.
- Improve resilience by reducing blast radius per service.
- Prepare platform for enterprise-scale usage and compliance controls.

### Technical Objectives
- Move from package-level domain separation to bounded contexts with explicit ownership.
- Enforce per-service data ownership (no shared writes across services).
- Implement production-grade security, observability, and release controls.

### Non-Goals
- Big-bang rewrite.
- Immediate full event-sourcing adoption.
- One-shot decommission of the monolith.

---

## 2) Current State Baseline

The current system is a single Spring Boot backend with domain areas for auth/users, assignments, and timecards. API authentication is JWT-based, persistence is MySQL (runtime) with H2 for tests, and the code is organized in layered packages (`controllers`, `services`, `repositories`, `models`).

Key baseline constraints:
- Existing endpoint contracts must remain stable during migration.
- Existing data integrity rules (especially time-card validation) must be preserved.
- Existing auth behavior remains the minimum baseline until centralized IAM rollout.

---

## 3) Target DDD Bounded Contexts and Services

### Bounded Contexts
1. **Identity and Access**
   - Responsibilities: registration, authentication, token issuance/validation, authorization policies.
   - Service: `identity-access-service`.

2. **Assignment Management**
   - Responsibilities: assignment lifecycle, assignment metadata, assignment ownership relationships.
   - Service: `assignment-service`.

3. **Time Tracking**
   - Responsibilities: time-card lifecycle, temporal invariants, user-assignment validation for entries.
   - Service: `time-tracking-service`.

4. **Reporting and Analytics** (phase-later read model)
   - Responsibilities: query-optimized projections and reporting APIs.
   - Service: `reporting-service`.

### Aggregate and Ownership Rules
- Each service owns its database schema and migration history.
- Cross-context communication happens via API contracts and domain events.
- No direct table-level coupling across services.

---

## 4) Target Platform Architecture

### Runtime and Network
- API Gateway as single external ingress.
- Service-to-service communication over mTLS-enabled internal network.
- Kubernetes-based deployment model (or equivalent orchestration platform with parity controls).
- QA/integration target environment is a single-node Ubuntu MicroK8s cluster.

### Security
- OIDC-compatible identity flow, JWT access tokens, key rotation.
- Service authorization via scoped claims and policy checks.
- Secrets in managed secret store (no plaintext in repo).
- Mandatory dependency/image vulnerability scanning gates in CI.

### Observability
- Structured logs with correlation IDs.
- OpenTelemetry traces across gateway and services.
- Service dashboards for latency, error rate, saturation, and throughput.
- Alerting tied to SLO error budgets.

### Delivery and Operations
- Trunk-based development with protected main branch.
- CI stages: lint/test/build/SCA/SAST/container scan/contract tests.
- Progressive delivery (canary or blue/green) with rollback playbooks.
- Local developer environment uses Docker Desktop for containerized workflows.
- Integration and QA automation use Testcontainers where service-level ephemeral dependencies are required.

---

## 5) Data Strategy

- Start with service-per-schema while monolith remains active.
- Use change events and anti-corruption adapters during transition.
- Prefer idempotent consumers and outbox pattern for reliable event publishing.
- Establish canonical IDs and event versioning policy early.

Data migration principles:
- Migrate per bounded context, not per technical layer.
- Validate parity using dual-read checks before cutover.
- Keep rollback path for each migration step.

---

## 6) Migration Phases and Exit Criteria

### Phase 0 - Domain and Governance Foundation
- Confirm context map, ownership, and architecture decision records.
- Define SLOs, RTO/RPO, incident severity model, and compliance controls.

**Exit Criteria**
- Context boundaries approved.
- SLO baseline approved.
- ADR set approved.

### Phase 1 - Platform Foundation
- Stand up gateway, telemetry, secrets, CI/CD templates, and policy gates.
- Provide golden service template for new microservices.
- Establish deploy/test pipeline path to the MicroK8s QA cluster.

**Exit Criteria**
- One service scaffold deployable through full pipeline.
- Logging/metrics/tracing visible end-to-end.
- Security checks blocking non-compliant builds.
- MicroK8s QA deployment smoke tests and integration tests pass.

### Phase 2 - First Service Extraction (Assignment)
- Extract assignment APIs behind gateway routing.
- Keep compatibility layer in monolith during coexistence period.

**Exit Criteria**
- Assignment traffic fully handled by `assignment-service`.
- No Sev1/Sev2 regressions in soak window.
- Rollback rehearsal completed.

### Phase 3 - Core Domain Extraction (Time Tracking)
- Extract time-card domain and invariants.
- Integrate with assignment identity through contracts/events.

**Exit Criteria**
- Time tracking traffic fully cut over.
- Invariant parity tests pass against baseline behavior.
- SLOs meet target under expected peak load.

### Phase 4 - Identity and Access Service Hardening
- Centralize token issuance and authorization policy enforcement.
- Introduce role/permission model and audit logging.

**Exit Criteria**
- All services validate tokens against centralized trust model.
- Authorization coverage complete for privileged actions.
- Security review and threat model closure complete.

### Phase 5 - Monolith Decommission
- Retire migrated endpoints in monolith.
- Remove cross-context coupling and legacy adapters.

**Exit Criteria**
- No production traffic to retired monolith endpoints.
- Operational KPIs improved versus baseline.
- Legacy support plan documented.

---

## 7) Production Readiness Gates (Applies to Every Phase)

- **Reliability**: SLOs defined and monitored with error budget policy.
- **Security**: Passing SAST/SCA/container scans, secret rotation policy, least-privilege IAM.
- **Quality**: Unit, integration, contract, and resilience test coverage thresholds met.
- **Operability**: Runbooks, alerts, dashboards, and on-call ownership in place.
- **Recoverability**: Backup/restore tested and rollback steps validated.

---

## 8) Team Topology and Ownership

- **Platform Team**: gateway, CI/CD, observability, security controls, service templates.
- **Identity Squad**: `identity-access-service` and policy model.
- **Assignment Squad**: assignment context and contracts.
- **Time Tracking Squad**: time-card domain, invariants, and high-throughput API behavior.
- **Data/Reporting Squad**: read models, analytics pipelines, and BI interfaces.

Ownership model:
- One primary owning team per service.
- Shared interface governance through contract review.
- Weekly architecture governance for cross-context changes.

---

## 9) Risks and Mitigations

- **Service boundary mistakes**: mitigate with event storming and ADR review before extraction.
- **Data inconsistency during migration**: mitigate with outbox, idempotency, dual-read verification, phased cutovers.
- **Operational overhead increase**: mitigate via platform automation and golden paths.
- **Security drift across services**: mitigate with centralized policy checks and standardized auth libraries.

---

## 10) Deliverables Checklist

- [ ] ADRs approved for boundaries, communication, and security.
- [ ] Context map and API/event contracts published.
- [ ] Platform baseline running in non-prod.
- [ ] Assignment extraction complete.
- [ ] Time Tracking extraction complete.
- [ ] Identity hardening complete.
- [ ] Monolith endpoint retirement complete.

---

## 11) Related Documents

- `docs/specs/Prod3_Kanbine_Strategic_Vision_Spec.md`
- `docs/specs/Prod1_Kanbine_Implementation_Design_Spec.md`
- `docs/adrs/adr_0001.md`
- `docs/adrs/adr_0002.md`
- `docs/adrs/adr_0003.md`
- `docs/adrs/adr_0004.md`

## 12) Environment and Test Strategy

### Local Development
- Runtime: Docker Desktop.
- Service testing: unit tests + focused integration tests with Testcontainers.
- Goal: fast feedback and deterministic dependency startup during development.

### Integration and QA
- Runtime: single-node Ubuntu MicroK8s cluster.
- Test scope: integration, API contract, smoke, and QA regression suites.
- Goal: Kubernetes deployment validation before promotion to higher environments.

### CI Guidance
- Keep Testcontainers-enabled tests in CI for dependency-realistic checks.
- Use environment-parity manifests/charts so MicroK8s and higher environments differ only by configuration.

