# PROMPT_REFERENCE — AgGridEnterprise Configuration & Selected Stacks

**Date:** 2025-12-02  
**Status:** Stage 1 Complete (Architecture & Foundations)

This document records the selected stacks, glossary composition (topic-first precedence), and provides a reference for all future prompts acting on this project.

---

## Selected Stacks & Inputs

### Organization & Project
- **Organization:** JWebMP
- **Project Name:** Ag Grid (Angular Data Grid) Enterprise
- **Repository URL:** https://github.com/JWebMP/AgGridEnterprise.git
- **License:** Apache 2.0
- **Stage Approvals:** Blanket approval (no STOP gates; auto-proceed through all stages)

### Languages & Build

| Category | Selection | Version/Details |
|----------|-----------|---|
| **Java LTS** | Java 25 LTS | See rules/generative/language/java/java-25.rules.md |
| **Build Tool** | Maven | See rules/generative/language/java/build-tooling.md |
| **Web Framework** | Angular (from JWebMP) | Core only; generated TS |

### Tech Topics (Selected)

- ✅ Fluent API Strategy: **CRTP** (not Builder pattern)
- ✅ Logging: **Log4j2**
- ✅ Structural: **JSpecify**
- ✅ Testing & Coverage: **Jacoco**, **Java Micro Harness**
- ✅ Frameworks (JWebMP):
  - **Core** — JWebMP base framework
  - **Client** — JWebMP client support
  - **TypeScript** — TS code generation
  - **Angular** — Angular integration (generated, not manual)
  - **AgGrid** — Community AgGrid plugin
- ✅ Backend Structure:
  - **GuicedEE Client** — DI and module discovery
  - **MapStruct** — DTO mapping
  - **Lombok** — Annotations (Provided scope)
- ✅ Architecture: **Specification-Driven Design (SDD)**, **Documentation-as-Code (mandatory)**, **TDD (docs-first)**

### NOT Selected
- [ ] Spring MVC / Quarkus / Vert.x (backend reactive — not needed; this is a UI plugin)
- [ ] Databases, CI/CD providers, infra (handled at application level, not plugin level)
- [ ] React, Vue, Next.js, Nuxt (Angular only via JWebMP generation)

---

## Architecture Summary

**AgGridEnterprise** is a type-safe JWebMP plugin extending the community AgGrid plugin. It exposes AG Grid Enterprise features (Charts, Range Selection, Row Grouping, Server-Side Row Models, Status Bar, Side Bar) via CRTP fluent API.

### Core Bounded Contexts

1. **Enterprise Options API** — Java fluent builders and strongly-typed POJOs for enterprise features
2. **Angular/TypeScript Integration** — TypeScript code generation and npm dependency management
3. **Page Configuration** — Boot-time wiring via GuicedEE `IPageConfigurator`
4. **Serialization/Mapping** — Jackson JSON marshaling and MapStruct transformations

### Design Principles

- **CRTP Fluent API** — Self-returning method chaining; no Lombok @Builder
- **Typed Enums & POJOs** — Replaces raw Object/Map for compile-time safety
- **Composition** — Feature options composed into parent, not via monolithic inheritance
- **Annotation-Driven Boot** — Service discovery and GuicedEE integration
- **Jackson + MapStruct** — Clean JSON serialization with enum/DTO transformations

---

## Glossary Composition (Topic-First Precedence)

This project's GLOSSARY.md is composed from the following topic-scoped glossaries and enterprise rules. Topic glossaries take precedence for their scope.

### Applied Topic Glossaries

| Topic | Location | Applies To | Precedence |
|-------|----------|-----------|---|
| **Java 25 LTS** | rules/generative/language/java/ | Java language features | High |
| **Build Tooling (Maven)** | rules/generative/language/java/build-tooling.md | Maven, pom.xml, build phases | High |
| **Fluent API / CRTP** | rules/generative/backend/fluent-api/crtp.rules.md | Builder pattern, method chaining, generics | High |
| **Lombok** | rules/generative/backend/lombok/README.md | @Data, @Getter, @Setter, @Slf4j (not @Builder for CRTP) | High |
| **MapStruct** | rules/generative/backend/mapstruct/README.md | DTO mapping, enum transformation | High |
| **Logging** | rules/generative/backend/logging/README.md | Log4j2 configuration, @Log4j2 annotation | High |
| **JSpecify** | rules/generative/backend/jspecify/README.md | Null-safety annotations | High |
| **JWebMP Core** | rules/generative/frontend/jwebmp/README.md | Component model, fluent API | High |
| **JWebMP AgGrid** | rules/generative/frontend/jwebmp/aggrid/README.md | AgGrid community plugin, options, columns | High |
| **JWebMP Client** | rules/generative/frontend/jwebmp/client/README.md | Client integration | High |
| **JWebMP TypeScript** | rules/generative/frontend/jwebmp/typescript/README.md | TS code generation | High |
| **Architecture (SDD)** | rules/generative/architecture/README.md | Specification-Driven Design | Medium |
| **TDD** | rules/generative/architecture/tdd/README.md | Test-first docs, tests | Medium |

### Glossary Precedence Policy

- **Topic glossaries override root** — For terms defined in the above topics, use topic glossary definitions; do not duplicate in root GLOSSARY.md
- **Root GLOSSARY serves as index** — Links to topic glossaries; provides Prompt Language Alignment mappings unique to this project
- **Minimal duplication** — Only copy into root GLOSSARY.md terms that have no topic home (e.g., AgGridEnterprise-specific concepts)

### Prompt Language Alignment Mappings

None specific to AgGridEnterprise (no WebAwesome components); follow standard Java/Fluent API naming conventions.

---

## Documentation Artifacts

### Root-Level Docs (Host Repo, Outside rules/)

| Artifact | Purpose | Location |
|----------|---------|----------|
| **PACT.md** | Product architecture, contract, design decisions | Root |
| **RULES.md** | Project rules, tech stack, behavioral rules | Root |
| **GLOSSARY.md** | Terminology index (topic-first composition) | Root |
| **GUIDES.md** | How-to guides, usage patterns, examples | Root |
| **IMPLEMENTATION.md** | Module structure, code layout, build config | Root |
| **README.md** | Project overview, quick start | Root |
| **docs/AgGridEnterprise-Guide.md** | Usage guide with examples | docs/ |

### Architecture Diagrams (Docs-as-Code, Mermaid)

| Diagram | Purpose | File |
|---------|---------|------|
| C4 Context (L1) | System context, external dependencies | docs/architecture/c4-context.md |
| C4 Container (L2) | Containers/modules | docs/architecture/c4-container.md |
| C4 Component (L3) | Enterprise API components | docs/architecture/c4-component-enterprise-api.md |
| Sequence: Page Load | Grid initialization & boot flow | docs/architecture/sequence-page-load.md |
| Sequence: Charts | Chart rendering flow | docs/architecture/sequence-chart-render.md |
| Sequence: Server-Side Model | Server-side row model data exchange | docs/architecture/sequence-server-side-model.md |
| ERD: Enterprise Options | Options class hierarchy & relationships | docs/architecture/erd-enterprise-options.md |

### Submodule Content (rules/)

- **rules/RULES.md** — Enterprise-wide rules (referenced, not copied)
- **rules/GLOSSARY.md** — Enterprise glossary (topic-first index)
- **rules/generative/** — Topic guides (framework/pattern specific)
  - `language/java/java-25.rules.md`
  - `language/java/build-tooling.md`
  - `backend/fluent-api/crtp.rules.md`
  - `backend/lombok/README.md`
  - `backend/mapstruct/README.md`
  - `backend/logging/README.md`
  - `backend/jspecify/README.md`
  - `frontend/jwebmp/*/README.md`
  - `architecture/sdd/README.md`, `architecture/tdd/README.md`

---

## Key Artifacts & Links

### Traceability Matrix

| Stage | Artifact | Status | Links To |
|-------|----------|--------|----------|
| **1 (Architecture)** | PACT.md | ✅ Complete | Diagrams, GLOSSARY, RULES |
| **1 (Architecture)** | docs/architecture/*.md | ✅ Complete | PACT, GUIDES |
| **2 (Guides)** | GUIDES.md | ✅ Complete | PACT, RULES, GLOSSARY, IMPLEMENTATION |
| **2 (Guides)** | docs/AgGridEnterprise-Guide.md | ✅ Complete | GUIDES, examples |
| **3 (Implementation Plan)** | IMPLEMENTATION.md | ✅ Complete | Code structure, pom.xml, module-info.java |
| **4 (Code)** | Source code | ✅ Current | Follows IMPLEMENTATION, RULES |

---

## How to Use This Reference

### For Future Prompts

When a new prompt is run against this project:

1. **Load this document first** — Understand selected stacks, glossary composition, precedence
2. **Load PACT.md** — Understand product architecture and contract
3. **Load RULES.md** — Understand project rules and constraints
4. **Load GLOSSARY.md** — Use terminology consistently
5. **Load relevant GUIDES** — For feature-specific guidance
6. **Reference architecture diagrams** — Understand bounded contexts and data flows
7. **Reference IMPLEMENTATION.md** — Understand code organization

### For Maintaining Consistency

- **New features** — Add to appropriate bounded context (Enterprise API, TS Integration, Boot Wiring, Serialization)
- **Naming** — Use GLOSSARY.md and topic glossaries for term consistency
- **Code organization** — Follow IMPLEMENTATION.md structure
- **Documentation** — Update PACT/RULES/GUIDES/GLOSSARY when making changes
- **Forward-only policy** — No legacy stubs; remove/replace cleanly; update all references

### For AI Engines

**GitHub Copilot / Cursor / Claude / Roo / CodeX:**

- Pin PROMPT_REFERENCE.md as context at session start
- Refer to PACT.md for product decisions
- Refer to RULES.md for technical constraints
- Refer to GLOSSARY.md (+ topic glossaries) for terminology
- Refer to GUIDES.md for patterns and examples
- Use docs/architecture/ diagrams for bounded context understanding
- Apply forward-only policy for all changes

---

## Stage Approval Status

| Stage | Artifacts | Approval Status | User Action |
|-------|-----------|---|---|
| **Stage 1** | PACT, architecture diagrams, project structure | ✅ Auto-approved | Proceed to Stage 2 |
| **Stage 2** | RULES, GUIDES, GLOSSARY, doc composition | ✅ Auto-approved | Proceed to Stage 3 |
| **Stage 3** | IMPLEMENTATION, scaffolding plan | ✅ Auto-approved | Proceed to Stage 4 |
| **Stage 4** | Code implementation (as needed) | 🟡 Ready (on-demand) | User requests specific features |

**Blanket Approval Policy:** All stage gates auto-approved per input configuration; documentation completed per specifications; code implementation available on-demand.

---

## Next Steps

1. **Review Stage 1 deliverables** — PACT.md, architecture diagrams (docs/architecture/*)
2. **Confirm tech stack alignment** — Review RULES.md for Java 25, Maven, CRTP, etc.
3. **Validate glossary composition** — Check GLOSSARY.md cross-links to topic glossaries
4. **Request Stage 4 implementation** — If specific features need coding/testing
5. **Use GUIDES.md for new development** — Apply patterns for new enterprise features

---

## Document Versioning

| Document | Version | Last Updated | Status |
|----------|---------|---|---|
| PROMPT_REFERENCE.md | 1.0 | 2025-12-02 | Current |
| PACT.md | 1.0 | 2025-12-02 | Current |
| RULES.md | 1.0 | 2025-12-02 | Current |
| GLOSSARY.md | 1.0 | 2025-12-02 | Current |
| GUIDES.md | 1.0 | 2025-12-02 | Current |
| IMPLEMENTATION.md | 1.0 | 2025-12-02 | Current |
| Architecture Diagrams | 1.0 | 2025-12-02 | Current |

---

**End of PROMPT_REFERENCE.md**

