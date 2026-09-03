package com.timetablexpert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * All the "what do I do on this screen / why won't it generate" copy and checks
 * in one place. The app enforces a strict data-entry order that used to be
 * implied only by the stored procedure's error messages; this makes it explicit.
 *
 * <p>Order: Programs -> Sessions -> Semesters -> Courses -> Teachers ->
 * Rooms &amp; Labs -> Allocate Courses -> Generate -> Print.
 */
public final class Guidance {

    private Guidance() {
    }

    /** One workflow step, shown in the banner and the Getting Started checklist. */
    public static final class Step {
        public final int number;
        public final String title;
        public final String navButtonFxId;   // sidebar button that opens it
        public final String formFxId;        // AnchorPane shown for it
        public final String rule;            // one-line banner text
        public final String[] details;       // bullet points for the "?" dialog
        final String doneSql;                // COUNT(*) query; > 0 means "done"

        Step(int number, String title, String navButtonFxId, String formFxId,
             String rule, String doneSql, String... details) {
            this.number = number;
            this.title = title;
            this.navButtonFxId = navButtonFxId;
            this.formFxId = formFxId;
            this.rule = rule;
            this.doneSql = doneSql;
            this.details = details;
        }
    }

    public static final int TOTAL_STEPS = 9;

    /** The Dashboard - "step 0": an overview, not part of the data-entry flow. */
    public static final Step DASHBOARD = new Step(0, "Dashboard", "home_btn", "home_form",
        "Overview of what's in the system. Work through steps 1 - 9 in the left menu, "
            + "top to bottom, to build a timetable.",
        "SELECT 0",
        "The tiles show current totals; the charts break them down per program.",
        "Each menu item below is one step, in order: Programs -> Sessions -> Semesters -> "
            + "Courses -> Teachers -> Rooms & Labs -> Allocate Courses -> Generate -> Print.",
        "A step's screen carries its own rule in this bar, with full details on \"Rules\".");

    /** Steps in workflow order. */
    public static final List<Step> STEPS = List.of(
        new Step(1, "Programs", "program_btn", "program_form",
            "Add every degree program (e.g. BSCS, BSSE). Everything else is attached to a program.",
            "SELECT COUNT(*) FROM programtable",
            "A program is a degree offered by the department.",
            "You must add at least one program before anything else works."),
        new Step(2, "Sessions", "session_btn", "session_form",
            "Add the intake sessions (e.g. 2024-2028). A session groups the classes admitted in one year.",
            "SELECT COUNT(*) FROM sessiontable",
            "Example: \"2024-2028\", \"2025-2029\".",
            "Each semester you create later belongs to one session."),
        new Step(3, "Semesters", "semester_btn", "semester_form",
            "Create each class as Program + Session + Semester + Section, and set its total credit hours.",
            "SELECT COUNT(*) FROM programsemestertable",
            "Section \"Morning\" = regular; \"Replica\" = the evening/second copy.",
            "Total credit hours here must match the sum of the courses you allocate in step 7.",
            "Nothing can be generated for a class whose courses don't add up to this number."),
        new Step(4, "Courses", "course_btn", "course_form",
            "Add the course catalogue for each program and semester (code, title, credit hours, room vs lab).",
            "SELECT COUNT(*) FROM coursetable",
            "Credit hours drive how many weekly slots the course needs.",
            "Mark lab courses as needing a Lab so they are placed in a lab, not a room."),
        new Step(5, "Teachers", "teacher_btn", "teacher_form",
            "Add the teachers for each program and mark them Regular or Visitor.",
            "SELECT COUNT(*) FROM lecturetable",
            "Every course you allocate in step 7 needs a teacher.",
            "A teacher is capped at 3 classes per day by the generator."),
        new Step(6, "Rooms & Labs", "room_lab_btn", "room_lab_form",
            "Add enough rooms and labs per program: at least one room per class and one lab per lab-course.",
            "SELECT COUNT(*) FROM roomtable",
            "The generator refuses to run if a program has fewer rooms than it has classes,",
            "or fewer labs than it has distinct lab requirements."),
        new Step(7, "Allocate Courses", "allocate_course_btn", "allocate_course_form",
            "Attach each course to a class and a teacher until the class's credit hours are fully allocated.",
            "SELECT COUNT(*) FROM programsemestersubjecttable",
            "This is the step people most often leave unfinished.",
            "If a class is short even one credit hour, Generate will stop and name that class."),
        new Step(8, "Generate", "generate_btn", "generate_form",
            "Once every class is fully allocated and has rooms, click Generate to build the timetable.",
            "SELECT COUNT(*) FROM timetbltable",
            "Use Regenerate to reshuffle, Reset to clear the current timetable.",
            "Then use Print to export to Excel or PDF."),
        new Step(9, "Print Timetable", "print_btn", null,
            "Export the generated timetable to Excel and/or PDF, by semester, by teacher or by room.",
            "SELECT COUNT(*) FROM timetbltable",
            "Tick what to produce (Semester-wise / Teacher-wise / Room-wise) and the format(s) "
                + "(Excel .xls and/or PDF), then click Print.",
            "Files are written to a \"Time Table\" folder next to the application, with an "
                + "\"Excel\" and a \"PDF\" sub-folder and one file per view.",
            "Generate (step 8) must have produced a timetable first - there is nothing to print otherwise.")
    );

    public static Step stepForForm(String formFxId) {
        if (formFxId == null) {
            return null;
        }
        if (formFxId.equals(DASHBOARD.formFxId)) {
            return DASHBOARD;
        }
        for (Step s : STEPS) {
            if (formFxId.equals(s.formFxId)) {
                return s;
            }
        }
        return null;
    }

    public static Step stepNumber(int n) {
        return (n >= 1 && n <= STEPS.size()) ? STEPS.get(n - 1) : null;
    }

    // ---------------------------------------------------------------- checklist

    /** Which steps already have at least one row, in step order (index 0 = step 1). */
    public static boolean[] checklistStatus(Connection c) {
        boolean[] done = new boolean[STEPS.size()];
        if (c == null) {
            return done;
        }
        for (int i = 0; i < STEPS.size(); i++) {
            try (Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery(STEPS.get(i).doneSql)) {
                done[i] = rs.next() && rs.getInt(1) > 0;
            } catch (SQLException ignored) {
                done[i] = false;
            }
        }
        return done;
    }

    // ---------------------------------------------------------------- preflight

    /** A blocking problem found before Generate, plus the step that fixes it. */
    public static final class Problem {
        public final String message;
        public final Step fixStep;

        Problem(String message, Step fixStep) {
            this.message = message;
            this.fixStep = fixStep;
        }
    }

    private static Step step(int number) {
        return STEPS.get(number - 1);
    }

    /**
     * Mirrors the validation the stored procedure does up-front, but collects
     * every problem and phrases it for a person instead of aborting on the first
     * one with a raw SQL SIGNAL.
     */
    public static List<Problem> preflight(Connection c) {
        List<Problem> problems = new ArrayList<>();
        if (c == null) {
            problems.add(new Problem("Not connected to the database.", null));
            return problems;
        }

        if (count(c, "SELECT COUNT(*) FROM all_subjects_view") == 0) {
            problems.add(new Problem(
                "No courses are allocated to any class yet.", step(7)));
            return problems; // nothing else is meaningful without allocations
        }

        // Per active class: allocated credit hours must reach the class total.
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT programSemesterID, title, totalCreditHours FROM programsemestertable WHERE isActive = 1");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int psId = rs.getInt(1);
                String title = rs.getString(2);
                int total = rs.getInt(3);
                int allocated = count(c,
                    "SELECT COALESCE(SUM(creditHours),0) FROM all_subjects_view WHERE programSemesterID = " + psId);
                if (allocated < total) {
                    problems.add(new Problem(
                        "\"" + title + "\": only " + allocated + " of " + total
                            + " credit hours allocated.", step(7)));
                }
            }
        } catch (SQLException ignored) {
            // fall through - the procedure will still guard
        }

        // Per program: enough rooms and labs.
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT programID, name FROM programtable WHERE isActive = 1");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int pid = rs.getInt(1);
                String name = rs.getString(2);
                int requiredRooms = count(c,
                    "SELECT COUNT(DISTINCT programSemesterID) FROM programsemestersubjecttable "
                    + "WHERE programID = " + pid + " AND timetableTypeID = 1");
                int availRooms = count(c, "SELECT COUNT(*) FROM roomtable WHERE programID = " + pid);
                if (requiredRooms > 0 && availRooms < requiredRooms) {
                    problems.add(new Problem(
                        "\"" + name + "\": needs at least " + requiredRooms
                            + " room(s), has " + availRooms + ".", step(6)));
                }
                int requiredLabs = count(c,
                    "SELECT COUNT(DISTINCT labID) FROM programsemestersubjecttable "
                    + "WHERE programID = " + pid + " AND timetableTypeID = 1 AND labID > 0");
                int availLabs = count(c, "SELECT COUNT(*) FROM labtable WHERE programID = " + pid);
                if (requiredLabs > 0 && availLabs < requiredLabs) {
                    problems.add(new Problem(
                        "\"" + name + "\": needs at least " + requiredLabs
                            + " lab(s), has " + availLabs + ".", step(6)));
                }
            }
        } catch (SQLException ignored) {
            // fall through
        }
        return problems;
    }

    private static int count(Connection c, String sql) {
        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    // ---------------------------------------------------------------- tooltips

    /** fx:id -> tooltip text, applied to controls that exist on the screen. */
    public static Map<String, String> tooltips() {
        Map<String, String> t = new LinkedHashMap<>();
        // Programs
        t.put("add_program_text_field", "Program name, e.g. BSCS. Add this first - everything hangs off a program.");
        // Sessions
        t.put("add_session_name", "Intake session, e.g. 2024-2028.");
        // Semesters
        t.put("add_semester_program_combo", "Which program this class belongs to.");
        t.put("add_semester_session_combo", "Which intake session this class belongs to.");
        t.put("add_semester_semester_combo", "1st..8th semester.");
        t.put("add_semester_type_combo", "Morning = regular. Replica = the evening / second section.");
        t.put("add_semester_crHrs_field", "Total weekly credit hours for this class. Your allocated courses must add up to this.");
        // Courses
        t.put("add_course_code_field", "Course code, e.g. CS-101.");
        t.put("add_course_title_field", "Course title.");
        t.put("credit_hour_combo", "Contact hours, e.g. 3-0 (theory) or 3-1 (theory+lab).");
        t.put("course_program_combo", "Program this course belongs to.");
        t.put("course_semester_combo", "Semester this course belongs to.");
        // Teachers
        t.put("add_teacher_name_field", "Teacher's full name - shown on the timetable.");
        t.put("add_teacher_type_combo", "Regular or Visitor.");
        t.put("add_teacher_program_combo", "Program this teacher belongs to.");
        // Rooms & Labs
        t.put("add_room_lab_type_combo", "Room = normal classroom. Lab = for lab courses.");
        t.put("add_room_No_field", "Room / lab number as it should appear on the timetable.");
        t.put("add_room_capacity_field", "Seating capacity.");
        // Allocate
        t.put("allocate_program_combo", "Program of the class you are allocating to.");
        t.put("allocate_semester_combo", "The class (semester) you are allocating courses to.");
        t.put("allocate_course_combo", "Course to attach to this class.");
        t.put("allocate_course_teacher_combo", "Teacher who will take this course.");
        t.put("allocate_lab_combo", "For a lab course, which lab it needs.");
        return t;
    }

    // ---------------------------------------------------------------- prefs

    private static Path prefsFile() {
        String local = System.getenv("LOCALAPPDATA");
        Path dir = (local != null && !local.isBlank())
            ? Paths.get(local, "TimetableXpert")
            : Paths.get(System.getProperty("user.home"), ".timetablexpert");
        return dir.resolve("guidance.properties");
    }

    public static boolean isGettingStartedHidden() {
        Properties p = new Properties();
        Path f = prefsFile();
        if (Files.exists(f)) {
            try (InputStream in = Files.newInputStream(f)) {
                p.load(in);
            } catch (IOException ignored) {
                // treat as not hidden
            }
        }
        return Boolean.parseBoolean(p.getProperty("hideGettingStarted", "false"));
    }

    public static void setGettingStartedHidden(boolean hidden) {
        Properties p = new Properties();
        Path f = prefsFile();
        if (Files.exists(f)) {
            try (InputStream in = Files.newInputStream(f)) {
                p.load(in);
            } catch (IOException ignored) {
                // start fresh
            }
        }
        p.setProperty("hideGettingStarted", Boolean.toString(hidden));
        try {
            Files.createDirectories(f.getParent());
            try (OutputStream out = Files.newOutputStream(f)) {
                p.store(out, "TimetableXpert guidance preferences");
            }
        } catch (IOException ignored) {
            // non-fatal - just won't remember the choice
        }
    }
}
