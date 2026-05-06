# Spec Refresh Plan

## Checklist
- [x] Inventory current architecture and project metadata from README, ADRs, and backend configuration.
- [x] Catalog current APIs, security posture, and service behavior from controllers/services.
- [x] Capture current data structures, persistence details, and validation rules from models/repositories.
- [x] Rewrite `docs/specs/Prod2_Kanbine_Functional_Spec.md` with implementation-backed, up-to-date content.
- [x] Verify spec consistency against source and run backend test suite as a baseline verification step.
- [x] Add review notes and completion status.

## Review
- Refreshed `docs/specs/Prod2_Kanbine_Functional_Spec.md` from template format to a current implementation snapshot.
- Validation step executed: `backend\\gradlew.bat test --no-daemon`.
- Result: 14 tests run, 3 failed (`TimeCardServiceTest.testSaveTimeCard`, `UserServiceTest.testSaveUser`, `UserServiceTest.testAssignAssignmentToUser`).
- Spec now explicitly calls out test reliability and auth lookup inefficiency as known risks/follow-up items.
- Applied readability cleanup: replaced list-heavy table sections in Context (`Customer-focused CIV Problems`, `Product-focused CIV Problems`, `Inputs, Outputs, Controls`, `Core Functions`) with heading + bullet format while keeping requirements tables unchanged.
- Added `docs/specs/Prod3_Kanbine_Strategic_Vision_Spec.md` for strategic direction.
- Added `docs/specs/Prod2_Kanbine_Microservices_High_Level_Design_Spec.md` for tactical architecture migration.
- Reclassified implementation snapshot as `docs/specs/Prod1_Kanbine_Implementation_Design_Spec.md`.
- Updated `docs/specs/Prod1_Kanbine_Implementation_Design_Spec.md` to reference migration direction artifacts.
- Updated `README.md` with a documentation index for specs and ADRs.
- Documented deployment/testing environment strategy: Docker Desktop for local development, Testcontainers for local dependency-realistic checks, and Ubuntu single-node MicroK8s for integration/QA.
