# Known issues

## Fixed

### Multi-program / evening-replica timetable generation (was: `Mixing of GROUP columns`)

Generating with more than one active program, or for an evening/"Replica"
section, used to abort with

```
Mixing of GROUP columns (MIN(),MAX(),COUNT(),...) with no GROUP columns
is illegal if there is no GROUP BY clause
```

**Cause:** the five stored procedures were dumped from MySQL with
`sql_mode = 'ONLY_FULL_GROUP_BY,...'` baked into their definition. A stored
routine permanently runs its body under the `sql_mode` captured when it was
`CREATE`d, so the bodies ran with `ONLY_FULL_GROUP_BY` even though MariaDB's
default (and the rest of the app) does not use it. One `... LIMIT 1` fetch on the
multi-program path, and one on the evening path, tripped its
"aggregate next to a bare column with no `GROUP BY`" rule.

**Fix:** `src/main/resources/db/schema.sql` now creates all five procedures with
`sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION'`
- `ONLY_FULL_GROUP_BY` dropped, everything else kept. Verified: single-program,
two-program and evening-replica generation all produce a valid timetable with no
teacher / room / lab / semester clashes.

## Open

### Guidance banner is cramped on very narrow windows

Below ~950 px wide the top banner's rule text truncates with "..." and the
Rules / Getting Started buttons shrink. Fine at any normal desktop size; the
progress rail already auto-hides at that width.

### Console logging noise

Running from a console prints a few lines about SLF4J / commons-logging / Log4j2
having no backing implementation, plus `CSS Error parsing style.css`. These come
from third-party libraries and the app's own stylesheet and are harmless - the
packaged `.exe` is windowed and shows none of them.

### `time_table_automation_backup.sql` in the repo root

The original MySQL dump committed at the repo root contains a real e-mail
address, a password hash and an absolute local path in its `admintable` insert.
The application no longer uses it - the sanitised, self-contained schema lives at
`src/main/resources/db/schema.sql`. Consider deleting the old dump (it is public
on GitHub).
