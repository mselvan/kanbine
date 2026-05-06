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
- Added `docs/specs/Prod3_Kanbine_DDD_Microservices_Migration_Spec.md` with phased production migration strategy, platform controls, data strategy, and phase exit criteria.
- Added `docs/adrs/adr_0004.md` to formalize DDD microservices adoption via strangler migration.
- Updated `docs/specs/Prod2_Kanbine_Functional_Spec.md` to reference migration direction artifacts.
- Updated `README.md` with a documentation index for specs and ADRs.
