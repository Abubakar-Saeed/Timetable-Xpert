package com.timetablexpert;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

/**
 * Makes the fixed-size FXML views behave on any screen size and any Windows
 * display scaling (100 % / 125 % / 150 % / 4K / ...).
 *
 * <p>The views are laid out with absolute coordinates at a fixed "design" size,
 * so rather than reflow them we wrap the view in a {@link Group}, scale it
 * uniformly, and centre it in a plain {@link Pane}. An optional fixed-height bar
 * (the guidance banner) sits above the scaled area as a real toolbar, so it
 * never overlaps the FXML content and stays readable at any window size. The
 * window opens at a comfortable fraction of the monitor it appears on, keeping
 * the design aspect ratio, and re-fits live on resize / maximise / monitor move.
 */
public final class Stages {

    private Stages() {
    }

    private static final double SCREEN_FRACTION = 0.92;
    private static final double MAX_UPSCALE = 1.9;

    /** Plain scaled window, no toolbar (Login, Register). */
    public static void show(Stage stage, Parent content, String title,
                            double designW, double designH) {
        show(stage, content, title, designW, designH, null, null);
    }

    public static void show(Stage stage, Parent content, String title,
                            double designW, double designH, Node topBar) {
        show(stage, content, title, designW, designH, topBar, null);
    }

    /**
     * Scaled window with an optional fixed-height {@code topBar} above the content
     * and an optional fixed-width {@code rightBar} beside it. The right bar is
     * hidden automatically when the window is too narrow to spare the room.
     */
    public static void show(Stage stage, Parent content, String title,
                            double designW, double designH, Node topBar, Node rightBar) {

        Scale scale = new Scale(1, 1, 0, 0);
        content.getTransforms().add(scale);

        Group group = new Group(content);
        Pane holder = new Pane(group);
        holder.setStyle("-fx-background-color: #e9edf3;");

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(holder.widthProperty());
        clip.heightProperty().bind(holder.heightProperty());
        holder.setClip(clip);

        Runnable fit = () -> {
            double w = holder.getWidth();
            double h = holder.getHeight();
            if (w <= 0 || h <= 0) {
                return;
            }
            // Uniform scale that fits both dimensions (no distortion, nothing
            // clipped). The view is pinned to the left so the sidebar always
            // touches the window edge - any spare space is a right-hand margin
            // that matches the app's grey background.
            double s = Math.min(w / designW, h / designH);
            if (s <= 0 || Double.isNaN(s) || Double.isInfinite(s)) {
                s = 1;
            }
            s = Math.min(s, MAX_UPSCALE);
            scale.setX(s);
            scale.setY(s);
            group.setLayoutX(0);
            group.setLayoutY(Math.max(0, (h - designH * s) / 2));
        };
        holder.widthProperty().addListener((o, a, b) -> fit.run());
        holder.heightProperty().addListener((o, a, b) -> fit.run());

        double extraH = 0;
        Parent sceneRoot;
        if (topBar != null || rightBar != null) {
            if (topBar instanceof Region) {
                ((Region) topBar).setMinHeight(Region.USE_PREF_SIZE);
                extraH = ((Region) topBar).getPrefHeight();
            }
            BorderPane bp = new BorderPane();
            bp.setTop(topBar);
            bp.setCenter(holder);
            bp.setRight(rightBar);
            bp.setStyle("-fx-background-color: #e9edf3;");
            sceneRoot = bp;

            if (rightBar != null) {
                double railW = rightBar instanceof Region ? ((Region) rightBar).getPrefWidth() : 0;
                // hide the rail when the window can't spare the width for it
                Runnable railFit = () -> {
                    boolean room = bp.getWidth() >= designW * 0.80 + railW;
                    rightBar.setVisible(room);
                    rightBar.setManaged(room);
                };
                bp.widthProperty().addListener((o, a, b) -> railFit.run());
                Platform.runLater(railFit);
            }
        } else {
            sceneRoot = holder;
        }

        Scene scene = new Scene(sceneRoot);
        try {
            scene.getStylesheets().add(
                Objects.requireNonNull(Stages.class.getResource("style.css")).toExternalForm());
        } catch (RuntimeException ignored) {
            // scroll-pane background is a nicety, not essential
        }
        scene.widthProperty().addListener((o, a, b) -> fit.run());
        scene.heightProperty().addListener((o, a, b) -> fit.run());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(true);
        stage.setMinWidth(520);
        stage.setMinHeight(400);
        try {
            stage.getIcons().add(new Image(Objects.requireNonNull(
                    Stages.class.getResourceAsStream("icon.png"))));
        } catch (RuntimeException ignored) {
            // icon is cosmetic
        }

        sizeToScreen(stage, designW, designH, extraH);
        stage.maximizedProperty().addListener((o, a, b) -> Platform.runLater(fit));
        stage.show();
        // Run once the final window/holder size is settled (a couple of pulses).
        Platform.runLater(fit);
        Platform.runLater(() -> Platform.runLater(fit));
    }

    private static void sizeToScreen(Stage stage, double designW, double designH, double extraH) {
        Rectangle2D vb = currentScreen(stage).getVisualBounds();
        double aspect = designW / designH;

        double h = vb.getHeight() * SCREEN_FRACTION;
        double w = h * aspect;
        if (w > vb.getWidth() * 0.96) {
            w = vb.getWidth() * 0.96;
            h = w / aspect;
        }
        if (w > designW * MAX_UPSCALE) {
            w = designW * MAX_UPSCALE;
            h = designH * MAX_UPSCALE;
        }
        h += extraH;

        // If the design is about as big as the screen, just maximise.
        if (designW + 24 >= vb.getWidth() || designH + extraH + 24 >= vb.getHeight()) {
            stage.setX(vb.getMinX());
            stage.setY(vb.getMinY());
            stage.setWidth(Math.min(designW, vb.getWidth()));
            stage.setHeight(Math.min(designH + extraH, vb.getHeight()));
            stage.setMaximized(true);
            return;
        }

        stage.setWidth(w);
        stage.setHeight(h);
        stage.setX(vb.getMinX() + (vb.getWidth() - w) / 2);
        stage.setY(vb.getMinY() + Math.max(0, (vb.getHeight() - h) / 2));
    }

    private static Screen currentScreen(Stage stage) {
        double x = stage.getX();
        double y = stage.getY();
        if (!Double.isNaN(x) && !Double.isNaN(y)) {
            List<Screen> on = Screen.getScreensForRectangle(x, y, 1, 1);
            if (!on.isEmpty()) {
                return on.get(0);
            }
        }
        return Screen.getPrimary();
    }
}
