# Copilot Instructions — AgGridEnterprise Plugin

**Workspace-scoped constraints and guidelines for GitHub Copilot Chat**

---

## Overview

This repository enforces a documentation-first, stage-gated development process for the AgGridEnterprise JWebMP plugin. All AI systems (GitHub Copilot, Cursor, Claude, Roo, Codex) must follow the same behavioral and technical rules.

---

## Behavioral Rules (Section 4 of RULES.md)

### Language & Continuity

- **Consistency** — Use terminology from GLOSSARY.md (topic-first)
- **Forward-Only** — No backwards-compatibility stubs; clean breaks only
- **Attribution** — Reference PACT.md, RULES.md, GUIDES.md, IMPLEMENTATION.md when explaining decisions

### Transparency & Iteration

- **Clear Statements** — Explain constraints upfront (e.g., "CRTP pattern enforced; no Lombok @Builder")
- **Staging Gates** — Follow documentation-first workflow; code only after docs approved
- **Revision** — Incorporate feedback immediately; update all references in one change

### Tool Handling

- When editing code:
  - Use forward-only approach (no legacy stubs)
  - Update all references (docs, tests, examples) in same changeset
  - Verify no broken links

---

## Technical Rules (Section 5 of RULES.md)

### Serialization (Jackson)

```java
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class MyOptions {
    // Fields only; no getters
}
```

### CRTP Fluent API (No @Builder)

```java
public T enableFeature() {
    getOptions().setFeatureOptions(new FeatureOptions());
    return this;  // Self-type chaining
}
```

### Enum Transformation (MapStruct)

Enums map to AG Grid string format:
```java
ChartTheme.AG_VIVID → "ag-vivid"
PanelShow.ALWAYS → "always"
```

### Module Discovery (GuicedEE)

```java
@Service
public class AgGridEnterprisePageConfigurator 
    implements IPageConfigurator {
    // Auto-discovered by GuicedEE
}
```

---

## Documentation-First Workflow

Do NOT write or modify source code until docs are completed:

1. **Stage 1 (Docs)** — PACT, architecture diagrams, C4, sequences
2. **Stage 2 (Docs)** — RULES, GUIDES, GLOSSARY, design specs
3. **Stage 3 (Plan)** — Implementation plan, file structure, build wiring
4. **Stage 4 (Code)** — Only AFTER explicit approval or blanket approval

For this project: **Blanket approval granted** (auto-proceed through all stages).

---

## Document Modularity Policy

- **Host Docs** — Root or `docs/` (not in rules/)
- **Submodule** — `rules/` contains only Rules Repository content (referenced, not copied)
- **Topic-First Glossary** — Link to topic glossaries; don't duplicate definitions in root GLOSSARY.md

Artifact locations:
- `PACT.md`, `RULES.md`, `GLOSSARY.md`, `GUIDES.md`, `IMPLEMENTATION.md` → Root
- `docs/architecture/`, `docs/AgGridEnterprise-Guide.md` → docs/
- `rules/generative/frontend/jwebmp/aggrid-enterprise/` → Submodule (enterprise rules)

---

## Forward-Only Change Policy (Section 6 of RULES.md)

### When Making Changes

1. **No Refactoring Without Deprecation** — Don't rename/restructure without documented path
2. **No Legacy Stubs** — Remove old classes completely; don't leave aliases
3. **Update All References** — Change one thing; update all docs/tests/links in same commit

### Checklist Before Removing Class/Method

- [ ] Documented in MIGRATION.md (if risky)
- [ ] All test usages updated
- [ ] All docs updated (PACT/RULES/GUIDES/IMPLEMENTATION)
- [ ] All links verified (grep to confirm no orphans)

---

## Terminology & Glossary

**Use GLOSSARY.md for all terms.** Topic-first composition:
- Topic glossaries (fluent-api, jwebmp, mapstruct, etc.) override root for their scope
- Maintain root GLOSSARY.md as index; link to topic files

### Enterprise-Specific Terminology

| Term | Definition | Reference |
|------|-----------|-----------|
| **AgGridEnterprise** | Main component class extending AgGrid | PACT.md |
| **ChartOptions** | Enterprise charts configuration | charts.rules.md |
| **CRTP** | Curiously Recurring Template Pattern | RULES.md § Behavioral |
| **@JsonUnwrapped** | Compose options without nesting | RULES.md § Serialization |
| **Feature Module** | 8 modular options classes (Charts, Range, etc.) | README.md § Module Structure |

---

## Code Organization

Follow IMPLEMENTATION.md structure:

```
src/main/java/com/jwebmp/plugins/aggridenterprise/
├── AgGridEnterprise<T>                 # Main component
├── options/
│   ├── AgGridEnterpriseOptions         # Root options
│   ├── ChartOptions
│   ├── RowGroupingOptions
│   ├── enums/
│   ├── mapping/  (DTOs)
│   └── *.Options (other features)
├── charts/
├── AgGridEnterprisePageConfigurator    # Boot wiring
└── implementations/
```

---

## Testing & Coverage

- **Unit Tests** — Options serialization (Jackson)
- **Integration Tests** — Fluent API chaining, feature composition
- **Target** — ≥ 80% coverage (Jacoco)

Test locations:
```
src/test/java/com/jwebmp/plugins/aggridenterprise/
├── options/
│   ├── ChartOptionsTest.java
│   ├── RowGroupingOptionsTest.java
│   └── ...
└── AgGridEnterpriseTest.java
```

---

## Logging & Observability

- **Framework** — Log4j2
- **Annotation** — Use `@Log4j2` (Lombok; not other Lombok logging)
- **Levels** — DEBUG (dev), INFO (config), WARN (deprecations), ERROR (failures)

```java
@Log4j2
public class MyClass {
    public void doSomething() {
        log.debug("Starting operation");
        log.info("Configuration loaded");
        log.warn("Feature X deprecated");
        log.error("Failed to load data", ex);
    }
}
```

---

## Build & CI/CD

### Maven Build

```bash
mvn clean install
```

- **Compiler** — Java 25 LTS
- **Annotation Processors** — Lombok → MapStruct → CompilerPlugin order
- **Output** — JAR artifact at `target/aggrid-enterprise-2.0.0.jar`

### Test Execution

```bash
mvn test                  # Run all tests
mvn test -Dtest=MyTest    # Single test
mvn jacoco:report         # Coverage report
```

---

## Architecture Diagrams

All diagrams stored in `docs/architecture/` as Mermaid/PlantUML (not images):

- `c4-context.md` — System context (L1)
- `c4-container.md` — Container/modules (L2)
- `c4-component-enterprise-api.md` — Enterprise API components (L3)
- `sequence-*.md` — Core flows
- `erd-enterprise-options.md` — Options hierarchy

Reference in docs via links: [C4 Context](../docs/architecture/c4-context.md)

---

## Enterprise Rules (Submodule)

Enterprise feature rules maintained at:
```
rules/generative/frontend/jwebmp/aggrid-enterprise/
├── README.md (parent index)
├── GLOSSARY.md (enterprise terminology)
├── QUICK_REFERENCE.md
├── charts.rules.md
├── range-selection.rules.md
├── row-grouping.rules.md
├── server-side-row-model.rules.md
├── side-bar-and-status-bar.rules.md
├── pivot-tables-and-aggregation.rules.md
├── advanced-filtering.rules.md
├── dynamic-series-coloring.rules.md
└── examples/
    ├── chart-integration-example.md
    └── server-side-row-model-example.md
```

When discussing enterprise features:
- Link to feature .rules.md file
- Reference GLOSSARY.md for terminology
- Point to QUICK_REFERENCE.md for code examples

---

## Related Topic Rules

This project depends on and extends the following Rules Repository topics:

- **JWebMP AgGrid (Community)** — rules/generative/frontend/jwebmp/aggrid/
- **JWebMP Core** — rules/generative/frontend/jwebmp/core/
- **Fluent API (CRTP)** — rules/generative/backend/fluent-api/crtp.rules.md
- **MapStruct** — rules/generative/backend/mapstruct/README.md
- **Logging (Log4j2)** — rules/generative/backend/logging/README.md
- **JSpecify** — rules/generative/backend/jspecify/README.md

Reference these when explaining patterns.

---

## Constraints & Limitations

- **No Backwards Compatibility** — Forward-only policy; breaking changes allowed
- **No Separate TS/HTML** — All UI rendered via JWebMP components (Java)
- **No Inline HTML** — Use JWebMP components (Div, Span, etc.); no `<div>` strings
- **CRTP Required** — Fluent API must use CRTP; no Lombok @Builder
- **JPMS Compliance** — All public APIs exported via module-info.java

---

## When to Request AI Assistance

### Good Cases

- Implement new enterprise feature following documented patterns
- Add unit test for existing feature
- Generate code snippet using QUICK_REFERENCE.md template
- Refactor within forward-only guidelines
- Document new feature (rules.md, examples, glossary updates)

### Escalate to User

- Stage gates requiring user approval (if user hasn't waived)
- Design decisions affecting multiple modules
- Breaking changes or policy violations
- Uncertain feature scope

---

## Quick Reference

### Load These First

1. **PACT.md** — Product architecture and contract
2. **RULES.md** — Technical and behavioral rules
3. **GLOSSARY.md** — Terminology (topic-first)
4. **PROMPT_REFERENCE.md** — Configuration and glossary composition
5. **enterprise-features.rules.md** — AG Grid v34.2.0 feature list

### Common Tasks

| Task | Reference | File |
|------|-----------|------|
| Add new feature | IMPLEMENTATION.md, GUIDES.md | Add in src/main/java; create .rules.md |
| Fix bug | RULES.md § Forward-Only | Update code + all docs + tests |
| Write test | QUICK_REFERENCE.md | Add in src/test/java |
| Update docs | GLOSSARY.md § Topic-First | Add/update .rules.md in submodule |
| Deploy | README.md | `mvn clean install` |

---

## Document Control

| Document | Version | Purpose |
|----------|---------|---------|
| copilot-instructions.md | 1.0 | This file; Copilot constraints |
| RULES.md | 1.0 | Project rules (primary source) |
| PACT.md | 1.0 | Architecture contract |
| GLOSSARY.md | 1.0 | Terminology index |
| GUIDES.md | 1.0 | How-to guides |
| IMPLEMENTATION.md | 1.0 | Code structure |
| PROMPT_REFERENCE.md | 1.0 | Configuration reference |

---

## Support

For questions about:
- **Architecture** → See PACT.md
- **Rules** → See RULES.md
- **Terminology** → See GLOSSARY.md
- **Patterns** → See GUIDES.md
- **Code Layout** → See IMPLEMENTATION.md
- **Feature Config** → See rules/generative/frontend/jwebmp/aggrid-enterprise/

---

**End of copilot-instructions.md**
