package com.timetablexpert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The visual half of {@link Guidance}: a per-screen instruction banner, a
 * Getting Started checklist, a pre-Generate preflight dialog, and field
 * tooltips. Everything is built in code and layered on top of the existing
 * FXML, so the original layout is left untouched.
 */
final class GuidanceUI {

    static final double BANNER_H = 40;

    // palette
    private static final String INK   = "#1f2430";
    private static final String INFO_BG = "#eef4ff";
    private static final String INFO_BAR = "#3b82f6";
    private static final String OK    = "#15803d";
    private static final String WARN  = "#b45309";
    private static final String WARN_BG = "#fff7ed";
    private static final String ERR   = "#b91c1c";
    private static final String ERR_BG = "#fef2f2";
    private static final String MUTED = "#6b7280";

    private final AnchorPane root;
    private final Supplier<Connection> db;

    private HBox banner;
    private Label chip;
    private Label ruleText;
    private Button rulesBtn;
    private Guidance.Step currentStep;

    // progress rail
    static final double RAIL_W = 186;
    private VBox rail;
    private Label railCount;
    private final List<RailRow> railRows = new ArrayList<>();

    private GuidanceUI(AnchorPane root, Supplier<Connection> db) {
        this.root = root;
        this.db = db;
    }

    // ------------------------------------------------------------------ install

    /**
     * Builds the banner + progress-rail nodes (safe to call during
     * {@code initialize()}, before a scene exists). Call {@link #attach()} once
     * the scene graph is live to wire up the field tooltips.
     */
    static GuidanceUI install(AnchorPane root, Supplier<Connection> db) {
        GuidanceUI g = new GuidanceUI(root, db);
        g.buildBanner();
        g.buildRail();
        return g;
    }

    /** Second phase: needs the node tree resolvable (run after the window shows). */
    void attach() {
        applyTooltips();
    }

    /**
     * The guidance toolbar. It is a fixed-height strip that {@link Stages} puts
     * <em>above</em> the scaled FXML view, so it never overlaps the design and
     * always spans the full window width at a readable size.
     */
    Node bannerNode() {
        return banner;
    }

    private void buildBanner() {
        chip = new Label();
        chip.setStyle("-fx-background-color:" + INFO_BAR + ";-fx-text-fill:white;-fx-font-weight:bold;"
            + "-fx-font-size:11px;-fx-padding:3 10 3 10;-fx-background-radius:11;");
        chip.setMinWidth(Region.USE_PREF_SIZE);

        ruleText = new Label();
        ruleText.setStyle("-fx-text-fill:" + INK + ";-fx-font-size:12.5px;");
        ruleText.setWrapText(false);
        HBox.setHgrow(ruleText, Priority.ALWAYS);
        ruleText.setMaxWidth(Double.MAX_VALUE);

        rulesBtn = new Button("Rules");
        rulesBtn.setStyle("-fx-background-color:white;-fx-border-color:" + INFO_BAR + ";"
            + "-fx-border-radius:4;-fx-background-radius:4;-fx-text-fill:" + INFO_BAR
            + ";-fx-font-size:11px;-fx-padding:3 10 3 10;-fx-cursor:hand;");
        rulesBtn.setOnAction(e -> showRulesForCurrent());

        Button guideBtn = new Button("Getting Started");
        guideBtn.setStyle("-fx-background-color:" + INFO_BAR + ";-fx-text-fill:white;"
            + "-fx-font-size:11px;-fx-padding:3 10 3 10;-fx-background-radius:4;-fx-cursor:hand;");
        guideBtn.setOnAction(e -> showGettingStarted());

        banner = new HBox(10, chip, ruleText, rulesBtn, guideBtn);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setPadding(new Insets(0, 16, 0, 16));
        banner.setPrefHeight(BANNER_H);
        banner.setMinHeight(BANNER_H);
        banner.setStyle("-fx-background-color:" + INFO_BG + ";"
            + "-fx-border-color: transparent transparent #cfe0fb transparent;-fx-border-width:0 0 1 0;");

        currentStep = Guidance.DASHBOARD;
        refreshBanner();
    }

    // --------------------------------------------------------------- progress rail

    /**
     * A slim panel {@link Stages} mounts on the right of the Home window: the
     * whole 0 - 9 workflow as a vertical stepper (done / current / pending),
     * click a step to jump to it. Auto-hidden by {@link Stages} on narrow windows.
     */
    Node railNode() {
        return rail;
    }

    private final class RailRow {
        final Guidance.Step step;
        final HBox node;
        final Label glyph = new Label();
        final Label label = new Label();

        RailRow(Guidance.Step step) {
            this.step = step;
            glyph.setMinWidth(16);
            glyph.setStyle("-fx-font-size:12px;-fx-font-weight:bold;");
            label.setStyle("-fx-font-size:11.5px;");
            label.setWrapText(false);
            node = new HBox(8, glyph, label);
            node.setAlignment(Pos.CENTER_LEFT);
            node.setPadding(new Insets(4, 6, 4, 6));
            node.setStyle("-fx-background-radius:6;-fx-cursor:hand;");
            node.setOnMouseClicked(e -> goToStep(step));
        }

        void render(boolean done, boolean current) {
            String g;
            String fg;
            String bg = "transparent";
            if (current) {
                g = "●";                 // filled dot
                fg = INFO_BAR;
                bg = "#eef4ff";
            } else if (done) {
                g = "✓";                 // check
                fg = OK;
            } else {
                g = "○";                 // hollow dot
                fg = "#9aa4b2";
            }
            glyph.setText(g);
            glyph.setStyle("-fx-font-size:12px;-fx-font-weight:bold;-fx-text-fill:" + fg + ";");
            label.setText(step.number + ".  " + step.title);
            label.setStyle("-fx-font-size:11.5px;-fx-text-fill:"
                + (current ? INK : done ? INK : MUTED) + ";"
                + (current ? "-fx-font-weight:bold;" : ""));
            node.setStyle("-fx-background-radius:6;-fx-cursor:hand;-fx-background-color:" + bg + ";");
        }
    }

    private void buildRail() {
        Label hdr = new Label("PROGRESS");
        hdr.setStyle("-fx-font-size:10px;-fx-font-weight:bold;-fx-text-fill:#64748b;");
        railCount = new Label();
        railCount.setStyle("-fx-font-size:10px;-fx-text-fill:#94a3b8;");
        Region grow = new Region();
        HBox.setHgrow(grow, Priority.ALWAYS);
        HBox head = new HBox(hdr, grow, railCount);
        head.setAlignment(Pos.CENTER_LEFT);
        head.setPadding(new Insets(0, 6, 8, 6));

        rail = new VBox(1);
        rail.setPrefWidth(RAIL_W);
        rail.setMinWidth(RAIL_W);
        rail.setMaxWidth(RAIL_W);
        rail.setPadding(new Insets(14, 12, 14, 12));
        rail.setStyle("-fx-background-color:white;"
            + "-fx-border-color: transparent transparent transparent #e2e8f0;-fx-border-width:0 0 0 1;");
        rail.getChildren().add(head);

        List<Guidance.Step> all = new ArrayList<>();
        all.add(Guidance.DASHBOARD);
        all.addAll(Guidance.STEPS);
        for (Guidance.Step s : all) {
            RailRow r = new RailRow(s);
            railRows.add(r);
            rail.getChildren().add(r.node);
        }
        refreshRail();
    }

    private void refreshRail() {
        if (rail == null) {
            return;
        }
        boolean[] done = Guidance.checklistStatus(dbOrNull()); // steps 1..9 -> index 0..8
        int completed = 0;
        for (int i = 0; i < railRows.size(); i++) {
            RailRow r = railRows.get(i);
            boolean isCurrent = currentStep != null && currentStep.number == r.step.number;
            boolean isDone = i >= 1 && i - 1 < done.length && done[i - 1];
            if (isDone) {
                completed++;
            }
            r.render(isDone, isCurrent);
        }
        railCount.setText(completed + " / " + Guidance.TOTAL_STEPS + " done");
    }

    void showFor(String formFxId) {
        Guidance.Step s = Guidance.stepForForm(formFxId);
        if (s != null) {
            currentStep = s;
            refreshBanner();
        }
    }

    /** Point the banner at a specific step (used for Print, which has no in-Home form). */
    void showStepNumber(int n) {
        Guidance.Step s = Guidance.stepNumber(n);
        if (s != null) {
            currentStep = s;
            refreshBanner();
        }
    }

    /** Banner + open the "what to do" dialog for the Print step. */
    void showPrintGuide() {
        showStepNumber(9);
        showRulesForCurrent();
    }

    private void refreshBanner() {
        if (currentStep == null) {
            return;
        }
        chip.setText("STEP " + currentStep.number + " / " + Guidance.TOTAL_STEPS
            + "  ·  " + currentStep.title.toUpperCase());
        ruleText.setText(currentStep.rule);
        rulesBtn.setVisible(currentStep.details.length > 0);
        refreshRail();
    }

    // ------------------------------------------------------------- rules dialog

    private void showRulesForCurrent() {
        if (currentStep == null) {
            return;
        }
        VBox box = new VBox(8);
        box.setPadding(new Insets(4, 4, 4, 4));
        Label head = new Label("Step " + currentStep.number + " of " + Guidance.TOTAL_STEPS
            + ": " + currentStep.title);
        head.setStyle("-fx-font-weight:bold;-fx-font-size:14px;-fx-text-fill:" + INK + ";");
        box.getChildren().add(head);
        Label rule = new Label(currentStep.rule);
        rule.setWrapText(true);
        rule.setStyle("-fx-text-fill:" + INK + ";");
        box.getChildren().add(rule);
        for (String d : currentStep.details) {
            Label b = new Label("•  " + d);
            b.setWrapText(true);
            b.setStyle("-fx-text-fill:" + MUTED + ";-fx-font-size:12px;");
            box.getChildren().add(b);
        }
        Dialog<Void> dlg = baseDialog(currentStep.title + " — what to do");
        dlg.getDialogPane().setContent(wrapScroll(box, 460, 260));
        dlg.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dlg.show();
    }

    // ---------------------------------------------------------------- about

    void showAbout() {
        VBox box = new VBox(3);
        box.setPadding(new Insets(6, 4, 6, 4));
        box.setAlignment(Pos.CENTER);

        Label app = new Label("TimetableXpert");
        app.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:" + INK + ";");
        Label ver = new Label("Version 1.0");
        ver.setStyle("-fx-font-size:11px;-fx-text-fill:" + MUTED + ";");

        Label blurb = new Label("Academic timetable generator: programs, courses, teachers, "
            + "rooms and automatic scheduling, with Excel / PDF export.");
        blurb.setWrapText(true);
        blurb.setMaxWidth(380);
        blurb.setAlignment(Pos.CENTER);
        blurb.setStyle("-fx-font-size:12px;-fx-text-fill:" + MUTED + ";-fx-text-alignment:center;");
        blurb.setPadding(new Insets(6, 0, 10, 0));

        Label devHdr = new Label("DEVELOPED BY");
        devHdr.setStyle("-fx-font-size:10px;-fx-font-weight:bold;-fx-text-fill:" + INFO_BAR + ";");
        Label name = new Label("Abubakar Saeed");
        name.setStyle("-fx-font-size:14px;-fx-font-weight:bold;-fx-text-fill:" + INK + ";");
        Hyperlink email = new Hyperlink("abubakarsaeed915@gmail.com");
        email.setStyle("-fx-font-size:12px;");
        email.setOnAction(e -> openLink("mailto:abubakarsaeed915@gmail.com"));

        Label inst = new Label("Government College University Faisalabad\nChiniot Campus");
        inst.setStyle("-fx-font-size:11.5px;-fx-font-weight:bold;-fx-text-fill:" + INK
            + ";-fx-text-alignment:center;");
        inst.setWrapText(true);
        inst.setPadding(new Insets(12, 0, 2, 0));

        Label ctx = new Label("4th-Semester Project  ·  built over the summer break\nBSCS  2022 – 2026");
        ctx.setStyle("-fx-font-size:11.5px;-fx-text-fill:" + MUTED + ";-fx-text-alignment:center;");
        ctx.setPadding(new Insets(2, 0, 8, 0));
        ctx.setWrapText(true);

        Hyperlink repo = new Hyperlink("github.com/Abubakar-Saeed/Timetable-Xpert");
        repo.setStyle("-fx-font-size:11px;");
        repo.setOnAction(e -> openLink("https://github.com/Abubakar-Saeed/Timetable-Xpert"));

        box.getChildren().addAll(app, ver, blurb, devHdr, name, email, inst, ctx, repo);

        Dialog<Void> dlg = baseDialog("About");
        dlg.getDialogPane().setContent(box);
        dlg.getDialogPane().setPrefWidth(420);
        dlg.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dlg.show();
    }

    // -------------------------------------------------------- getting started

    void showGettingStarted() {
        boolean[] done = Guidance.checklistStatus(dbOrNull());
        int completed = 0;
        for (boolean b : done) {
            if (b) {
                completed++;
            }
        }

        VBox list = new VBox(6);
        list.setPadding(new Insets(6, 2, 6, 2));
        for (int i = 0; i < Guidance.STEPS.size(); i++) {
            Guidance.Step s = Guidance.STEPS.get(i);
            boolean ok = done[i];

            Label mark = new Label(ok ? "✓" : "○");
            mark.setMinWidth(18);
            mark.setStyle("-fx-font-size:14px;-fx-text-fill:" + (ok ? OK : MUTED) + ";-fx-font-weight:bold;");

            Label name = new Label(s.number + ".  " + s.title);
            name.setStyle("-fx-font-size:12.5px;-fx-text-fill:" + INK
                + (ok ? ";-fx-strikethrough:false" : "") + ";");
            HBox.setHgrow(name, Priority.ALWAYS);
            name.setMaxWidth(Double.MAX_VALUE);

            Label hint = new Label(s.rule);
            hint.setStyle("-fx-font-size:10.5px;-fx-text-fill:" + MUTED + ";");
            hint.setWrapText(true);

            Button open = new Button("Open");
            open.setStyle("-fx-font-size:10.5px;-fx-padding:1 8 1 8;");
            open.setOnAction(e -> {
                ((Stage) open.getScene().getWindow()).close();
                goToStep(s);
            });

            VBox textCol = new VBox(1, new HBox(8, mark, name, open), hint);
            HBox.setHgrow(textCol, Priority.ALWAYS);
            VBox rowBox = new VBox(textCol);
            rowBox.setPadding(new Insets(5, 6, 5, 6));
            rowBox.setStyle("-fx-background-color:" + (ok ? "#f0fdf4" : "#fafafa")
                + ";-fx-background-radius:6;-fx-border-color:#ececec;-fx-border-radius:6;");
            list.getChildren().add(rowBox);
        }

        Label intro = new Label("Fill these in order. Each one unlocks the next; Generate only works "
            + "once every class is fully allocated and has rooms.");
        intro.setWrapText(true);
        intro.setStyle("-fx-text-fill:" + MUTED + ";-fx-font-size:11.5px;");

        CheckBox hide = new CheckBox("Don't show this on startup");
        hide.setSelected(Guidance.isGettingStartedHidden());
        hide.setStyle("-fx-font-size:11px;-fx-text-fill:" + MUTED + ";");

        VBox content = new VBox(10, intro, wrapScroll(list, 520, 340), hide);

        Dialog<Void> dlg = baseDialog("Getting Started  —  " + completed + " of "
            + Guidance.TOTAL_STEPS + " done");
        dlg.getDialogPane().setContent(content);
        dlg.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dlg.setOnHidden(e -> Guidance.setGettingStartedHidden(hide.isSelected()));
        dlg.show(); // non-modal: never blocks the main window
    }

    void maybeShowGettingStartedOnStartup() {
        if (!Guidance.isGettingStartedHidden()) {
            showGettingStarted();
        }
    }

    // -------------------------------------------------------------- preflight

    /**
     * @return true if the caller should proceed with Generate, false to abort.
     */
    boolean confirmGenerate() {
        List<Guidance.Problem> problems = Guidance.preflight(dbOrNull());
        if (problems.isEmpty()) {
            return true;
        }

        VBox box = new VBox(7);
        box.setPadding(new Insets(4));
        Label head = new Label("Fix these before generating:");
        head.setStyle("-fx-font-weight:bold;-fx-text-fill:" + ERR + ";-fx-font-size:13px;");
        box.getChildren().add(head);

        for (Guidance.Problem p : problems) {
            Label l = new Label("•  " + p.message
                + (p.fixStep != null ? "   (Step " + p.fixStep.number + ": " + p.fixStep.title + ")" : ""));
            l.setWrapText(true);
            l.setStyle("-fx-text-fill:" + INK + ";-fx-font-size:12px;-fx-background-color:" + ERR_BG
                + ";-fx-padding:5 8 5 8;-fx-background-radius:5;");
            box.getChildren().add(l);
        }
        Label note = new Label("Generating with problems left unfixed will stop with an error "
            + "(the generator no longer hangs), so it is safe to try — but it will not finish.");
        note.setWrapText(true);
        note.setStyle("-fx-text-fill:" + MUTED + ";-fx-font-size:11px;");
        box.getChildren().add(note);

        Dialog<ButtonType> dlg = baseDialog("Timetable can't be generated yet");
        dlg.initModality(Modality.APPLICATION_MODAL); // must block for the answer
        dlg.getDialogPane().setContent(wrapScroll(box, 480, 300));
        ButtonType fix = new ButtonType("Take me there", ButtonBar.ButtonData.OK_DONE);
        ButtonType anyway = new ButtonType("Generate anyway", ButtonBar.ButtonData.OTHER);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dlg.getDialogPane().getButtonTypes().setAll(fix, anyway, cancel);

        ButtonType choice = dlg.showAndWait().orElse(cancel);
        if (choice == fix) {
            Guidance.Problem first = problems.get(0);
            if (first.fixStep != null) {
                goToStep(first.fixStep);
            }
            return false;
        }
        return choice == anyway;
    }

    // -------------------------------------------------------------- tooltips

    private void applyTooltips() {
        Guidance.tooltips().forEach((id, text) -> {
            Node n = root.lookup("#" + id);
            if (n instanceof Control) {
                Tooltip tp = new Tooltip(text);
                tp.setShowDelay(Duration.millis(250));
                tp.setWrapText(true);
                tp.setMaxWidth(320);
                ((Control) n).setTooltip(tp);
            }
        });
    }

    // -------------------------------------------------------------- helpers

    private void goToStep(Guidance.Step s) {
        Node n = root.lookup("#" + s.navButtonFxId);
        if (n instanceof Button) {
            ((Button) n).fire();
        }
    }

    /** Open a URL / mailto in the user's default browser or mail client. */
    private static void openLink(String uri) {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(uri));
        } catch (Exception ex) {
            try {
                new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", uri).start();
            } catch (Exception ignored) {
                // give up silently - not critical
            }
        }
    }

    private Connection dbOrNull() {
        try {
            return db.get();
        } catch (RuntimeException e) {
            return null;
        }
    }

    private static ScrollPane wrapScroll(Region content, double w, double h) {
        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setPrefViewportWidth(w);
        sp.setPrefViewportHeight(h);
        sp.setStyle("-fx-background-color:transparent;-fx-background:transparent;");
        return sp;
    }

    private static <T> Dialog<T> baseDialog(String title) {
        Dialog<T> d = new Dialog<>();
        d.setTitle("TimetableXpert");
        d.setHeaderText(title);
        d.setResizable(true);
        // Info dialogs float alongside the window; only confirmGenerate() makes
        // its dialog modal, because it has to wait for a yes/no answer.
        d.initModality(Modality.NONE);
        return d;
    }
}
