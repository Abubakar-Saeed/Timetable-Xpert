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

### Report header ran labels into values (was: `Semester:BSCS 1st Semester`)

In the exported PDFs the page-header labels collided with their values
(`DepartmentBSCS`, `Name:Dr Ahmed Ali`, ...) and the `Dated:` value lost its
year.

**Cause:** the four `.jrxml` templates (`Semester`, `Teacher`, `Room`, `Lab`)
size the label `staticText` boxes for **Calibri**. When the embedded font was
switched to the (wider) standard PDF font **Helvetica**, the trailing `": "`
overflowed the box and was clipped, and the value fields, positioned for the
narrower font, overlapped the label. The date box was also too narrow for
`MMMMM dd, yyyy`.

**Fix:** widened the label boxes, shifted the value fields right to leave a
clear gap, and switched the date pattern to `dd-MMM-yyyy`. The Excel exports
were unaffected (each value is written to its own cell).

## Open

### Department-wise PDF merge can fail if the target file is open

`Print` builds `Time Table/PDF/Department Wise/<program>.pdf` by merging the
per-timetable PDFs with `PDFMergerUtility`. If a previously generated
`<program>.pdf` is still held open (e.g. in a PDF viewer, or by a crashed prior
run), the merge aborts with *"The process cannot access the file because it is
being used by another process"*. The individual `<program><n>.pdf` files are
still produced. Close the old PDF and print again.

### Guidance banner is cramped on very narrow windows

Below ~950 px wide the top banner's rule text truncates with "..." and the
Rules / Getting Started buttons shrink. Fine at any normal desktop size; the
progress rail already auto-hides at that width.

### Console logging noise

Running from a console prints a few lines about SLF4J / commons-logging / Log4j2
having no backing implementation, plus `CSS Error parsing style.css`. These come
from third-party libraries and the app's own stylesheet and are harmless - the
packaged `.exe` is windowed and shows none of them.
