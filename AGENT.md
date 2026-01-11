# AGENT.md — Project Guidelines

This project is an Android application written in Kotlin, using Jetpack Compose and Clean Architecture.
All changes must strictly follow the existing project structure, conventions, and practices.

---

## 1. Architecture & Structure

- The project follows **Clean Architecture** with a **feature-based structure**.
- Each feature is isolated and self-contained.
- UI code must live inside a feature module/package, never in `core` or `app`.
- `MainActivity` must only act as a host (navigation, theme, setup). No business logic.
- Cross-cutting concerns live in `core`.
- Dependency injection is handled via Hilt and must be respected.
- Navigation is centralized and must not tightly couple features together.

Before adding new code:
- Inspect the existing project structure.
- Match package naming, folder hierarchy, and file responsibilities.
- Do not introduce new architectural patterns unless explicitly requested.

---

## 2. UI Guidelines (Jetpack Compose)

- UI must be implemented using **Jetpack Compose**.
- Follow existing UI patterns, styles, colors, typography, spacing, and layout decisions.
- Reuse existing theme definitions and components.
- Screens must be responsive and respect system insets (status bar, navigation bar).
- Prefer `Scaffold` when needed to avoid UI overlapping system UI.
- Layouts should be centered when required and avoid full-width components unless explicitly intended.

---

## 3. Features & Navigation

- Any screen with UI and user interaction must be implemented as a **feature**.
- Menus, entry points, or dashboards are also features.
- Navigation logic must not leak UI details.
- Navigation identifiers must be stable and language-agnostic (do not rely on UI text).
- Assume the app will support multiple languages in the future.

---

## 4. ViewModels & Use Cases

- ViewModels must not be empty.
- If a ViewModel exists, it must encapsulate meaningful presentation logic.
- Business logic must live in **UseCases**, not in ViewModels or UI.
- UseCases must follow the same structure and conventions as existing ones.
- When introducing a new UseCase, compare it with existing UseCases and match:
    - naming
    - constructor structure
    - invocation style
    - return types

Do not refactor existing code unless explicitly requested.

---

## 5. Testing (Mandatory)

- Code must be written using **TDD when possible**:
    1. Write unit tests first
    2. Then implement functional code
- Tests must be **unit tests**, not UI tests, unless explicitly requested.
- Test structure, naming, and style must match existing tests in the project.
- Do not introduce a large number of tests unnecessarily.
- Prefer:
    - one test per meaningful behavior
    - one test per UseCase branch when applicable
- If a test style already exists in the project, replicate it exactly.

---

## 6. Naming & Code Style

- All code must be written in **English**.
- Names must be:
    - clear
    - explicit
    - self-explanatory
- Avoid unnecessary abstractions.
- Do not add comments unless absolutely required.
- Do not add documentation blocks.
- Code should be readable without explanation.

---

## 7. Scope Control

- Only implement what is explicitly requested.
- Do not refactor unrelated code.
- Do not move files or packages unless explicitly requested.
- If something is missing or unclear:
    - Assume the simplest solution that fits existing patterns.
    - Prefer consistency over novelty.

---

## 8. Dependencies & Gradle

- Before using a library or feature, verify it already exists in Gradle.
- Do not add new dependencies unless explicitly requested.
- If something requires a dependency that is not present, stop and ask.

---

## 9. Commits

- When asked for a commit message:
    - Do not modify code.
    - Do not run commands.
    - Only provide the commit message.
- Commit messages must follow the existing style:
    - concise
    - imperative
    - consistent with previous commits

---

## 10. Default Assumptions

- Do not assume the functional completeness of any feature.
- The current state of a feature must be inferred exclusively from existing code, navigation setup, and tests.
- If a screen, route, or behavior is not clearly implemented:
    - do not invent functionality
    - do not force navigation
    - do not create stubs unless explicitly requested
- Only integrate or connect features that are already wired in the codebase.
- When in doubt, prefer leaving behavior unimplemented rather than making assumptions.

---

## Golden Rule

Always prioritize:
- consistency with existing code
- architectural correctness
- minimal and focused changes
