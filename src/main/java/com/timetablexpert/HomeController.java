package com.timetablexpert;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class HomeController  extends DataBaseLayer implements Initializable {

    /** Per-screen instructions, Getting Started checklist, tooltips, Generate preflight. */
    private GuidanceUI guidance;

    private static final String[] GUIDANCE_FORM_IDS = {
        "home_form", "program_form", "session_form", "semester_form", "course_form",
        "teacher_form", "room_lab_form", "allocate_course_form", "generate_form"
    };

    @FXML
    private AnchorPane rootWindow;
    @FXML
    private LineChart<String, Number> visitor_chart;
    @FXML
    private BarChart<String, Number> teacher_chart;
    @FXML
    private AreaChart <String, Number> class_chart;
    @FXML
    private TextField search_allocate;
    @FXML
    private TextField add_semester_crHrs_field;
    @FXML
    private TableColumn<?,?> add_semester_creditHours_col;

    @FXML
    private TableColumn<?, ?> time_table_ID_col;
    @FXML
    private Label allocated_course_allocated_hours_header;

    @FXML
    private Label allocated_course_semester_title_header;

    @FXML
    private Label allocated_course_total_credit_hours_header;
    @FXML
    private TableColumn<?, ?> time_table_day_col;

    @FXML
    private TableColumn<?, ?> time_table_slot_col;

    @FXML
    private TableColumn<?, ?> time_table_subject_col;

    String semesterTitle;
    @FXML
    private TextField search_time_table;
    @FXML
    private AnchorPane allocate_course_form;
    @FXML
    private  Label total_programs;
    @FXML
    private Label total_labs;
    @FXML
    private Label total_regular;
    @FXML
    private Label total_rooms;
    @FXML
    private Label total_visiting;
    @FXML
    private Label total_classes;
    @FXML
    private TableColumn<Program, String> add_program_ID_col;
    @FXML
    private TextField add_program_text_fieldID;
    @FXML
    private Button generate_btn;

    @FXML
    private AnchorPane generate_form;

    @FXML
    private Button add_program_button;
    @FXML
    private TextField add_session_ID;

    @FXML
    private TextField add_session_name;
    @FXML
    private TableColumn<Session, String> session_ID_col;

    @FXML
    private TextField add_room_ID_field;
    @FXML
    private  TableColumn<?,?> add_teacher_type_col;
    @FXML
    private Label add_room_ID_label;

    @FXML
    private TextField add_room_No_field;

    @FXML
    private Label add_room_No_label;

    @FXML
    private TextField add_room_capacity_field;

    @FXML
    private ComboBox<String> add_room_lab_combo;
    @FXML
    private ComboBox<String> allocate_course_section_combo;
    @FXML
    private ComboBox<String> add_room_lab_type_combo;
    @FXML
    private TableColumn<?, ?> labID_col;

    @FXML
    private TableColumn<?, ?> labNo_col;
    @FXML

    private ComboBox<String> allocate_course_combo;

    @FXML
    private TableColumn<?, ?> lab_capacity_col;

    @FXML
    private TableColumn<?, ?> lab_program_col;

    @FXML
    private TableView<Lab> lab_table_view;
    @FXML
    private TableView<GenerateTimeTable> time_table_view_table;
    @FXML
    private TableColumn<?, ?> roomID_col;

    @FXML
    private TableColumn<?, ?> roomNo_col;

    @FXML
    private TableColumn<?, ?> room_capacity_col;
    @FXML
    private TextField room_lab_search;

    @FXML
    private TableColumn<?, ?> room_program_col;

    @FXML
    private TableView<Room> room_table_view;

    @FXML
    private Button session_add_btn;

    @FXML
    private TextField search_teacher;

    @FXML
    private TableColumn<Session, String> session_name_col;

    @FXML
    private TextField session_search;
    @FXML
    private Label add_course_semester_header;
    @FXML
    private Label add_course_total_credit_hours_header;
    @FXML
    private Label add_course_credit_hours_header;
    @FXML
    private TableView<Session> session_table_view;

    @FXML
    private Button session_update_btn;

    @FXML
    private Button session_clear_btn;
    @FXML
    private Button allocate_course_btn;
    @FXML
    private Button session_delete_btn;

    @FXML
    private TableColumn<Program, String> add_program_name_col;

    @FXML
    private AnchorPane bp;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane teacher_form;

    @FXML
    private Button program_clear_button;

    @FXML
    private Button program_delete_button;

    @FXML
    private Button teacher_btn;

    @FXML
    private AnchorPane program_form;
    @FXML
    private AnchorPane session_form;
    @FXML
    private AnchorPane  room_lab_form;
    @FXML
    private Button program_update_button;
    @FXML

    private Button home_btn;
    @FXML
    private Button program_btn;
    @FXML
    Button session_btn;

    @FXML
    private TextField search;
    @FXML
    TableView<Program> program_table_view;
    @FXML

   TextField add_program_text_field;
    @FXML
    Button semester_btn;
    @FXML
    AnchorPane semester_form;
    @FXML
    private TextField add_semester_ID_field;

    @FXML
    private Button add_semester_add_btn;

    @FXML
    private TextField add_semester_capacity_field;
    @FXML

    private Button add_semester_clear_btn;

    @FXML
    private Button add_semester_delete_btn;
    @FXML
    private Button room_lab_btn;
    @FXML
    private ComboBox<String> allocate_semester_combo;
    @FXML
    private ComboBox<String> add_semester_program_combo;
    @FXML
    private ComboBox<String> add_teacher_type_combo;
    @FXML
    private ComboBox<String> allocate_program_combo;
    @FXML
    private ComboBox<String> add_semester_semester_combo;
    @FXML
    private ComboBox<String> add_semester_type_combo;
    @FXML
    private ComboBox<String> allocate_course_teacher_combo;

    @FXML
    private ComboBox<String> add_semester_session_combo;

    @FXML
    private Button add_semester_update_btn;
    @FXML
    private TableColumn<?, ?> add_semester_capacity_col;
    @FXML
    private TableColumn<?,?> add_teacher_program_col;
    @FXML
    private TableColumn<?, ?> add_semester_semesterID_col;
    @FXML
    private Button course_btn;

    @FXML
    private TableColumn<?, ?> add_semester_title_col;
    @FXML
    private TableColumn<?,?> add_semester_section_col;
    @FXML
    private TextField semester_search;
    @FXML
    private AnchorPane course_form;
    @FXML
    private TableView<Semester> semester_table_view;
    @FXML
    private TableView<Teacher> teacher_view_table;
    @FXML
    private TextField add_course_ID_field;

    @FXML
    private TextField add_course_code_field;

    @FXML
    private TableColumn<?, ?> add_course_course_ID_col;

    @FXML
    private TableColumn<?, ?> add_course_course_code_col;

    @FXML
    private TableColumn<?, ?> add_course_course_hours_col;

    @FXML
    private TableColumn<?, ?> add_course_course_title_col;

    @FXML
    private TextField add_course_title_field;
    @FXML
    private Button course_add_btn;

    int flag = -1;
    int timeTableType = 0;
    @FXML
    private Button course_clear_btn;

    @FXML
    private Button course_delete_btn;
    @FXML
    private ComboBox<String> course_program_combo;

    @FXML
    private TextField course_search;
    @FXML
    private ComboBox<String> course_semester_combo;
    @FXML
    private TableView<Course> course_table_view;
    @FXML
    private Button course_update_btn;
    @FXML
    private TableColumn<?,?> add_course_semester_col;
    @FXML
    private TableColumn<?,?> add_course_program_col;
    @FXML
    private ComboBox<String> credit_hour_combo;
    @FXML
    private TableColumn<?, ?> add_teacher_ID_col;
    @FXML
    private TextField add_teacher_ID_field;
    @FXML
    private Button add_teacher_add_btn;
    @FXML
    private Button add_teacher_clear_btn;
    @FXML
    private Button add_teacher_delete_btn;
    @FXML
    private TableColumn<?, ?> add_teacher_email_col;
    @FXML
    private TextField add_teacher_email_field;
    @FXML
    private TableColumn<?, ?> add_teacher_gender_col;
    @FXML
    private ComboBox<String> add_teacher_gender_combo;
    @FXML
    private TableColumn<Teacher, String> add_teacher_name_col;
    @FXML
    private TextField add_teacher_name_field;
    @FXML
    private TableColumn<?, ?> add_teacher_phone_col;
    @FXML
    private TableColumn <?,?> allocate_course_ID_col;
    @FXML
    private TableColumn <?,?> allocate_course_title_col;
    @FXML
    private TableColumn<?,?> allocate_lab_col;
    @FXML
    private TableColumn<?,?> allocate_section_col;
    @FXML
    private TableView<AllocateCourse> allocate_course_table_view;
    @FXML
    private TextField allocate_subject_ID_field;
    @FXML
    private TextField add_teacher_phone_field;
    @FXML
    private ComboBox<String> add_teacher_program_combo;
    @FXML
    private ComboBox<String> allocate_lab_combo;
    @FXML
    private ComboBox<String> allocate_teacher_type_combo;
    @FXML
    private Button print_btn;
    @FXML
    private Button add_teacher_update_btn;
    @FXML
    private Button log_out_btn;
    @FXML
    private Label profile_name_label;
    @FXML
    private ImageView profile_image;
    @FXML
    private Image image;
    @FXML
    private ProgressIndicator progressIndicator;
    

    private String[] semesters = {"1st Semester","2nd Semester","3rd Semester",
            "4th Semester","5th Semester","6th Semester","7th Semester","8th Semester"} ;

    private  String[] sections = {"Morning","Replica"};

    private String[] creditHours = {"1-0","2-0","3-0","2-1","3-1","4-0","0-2"};

    private String[] genderList = {"Male","Female"};

    private String[] type = {"Room","Lab"};
    @FXML
    private Circle circle;

    private String[] teacherType = {"Regular","Visitor"};
    Alert alert;

    String currentSelected;
    String currentSection;
    String currentSemester;
    int currentType = -1;
    int currentGender = -1;
    int totalCreditHours = 0;
    int semesterCreditHours = 0;

    private ObservableList<Program>  programList;
    private ObservableList<Session>  sessionList;
    public ObservableList<Semester> semesterListData;
    public ObservableList<Teacher> teacherListData;
    public ObservableList<Room> roomListData;
    public ObservableList<GenerateTimeTable> timeTableListData;

    public ObservableList<Lab> labListData;
    public ObservableList<AllocateCourse> allocateCourseListData;
    private Button selectedButton;
    public ObservableList<Course> courseList;

    int i = 0;


    /**
     * Loads the logged-in admin's profile picture, falling back to the bundled
     * {@code default.jpeg} when the stored path is missing or unreadable (e.g. the
     * seeded {@code admin} account, or a picture that has since been moved). The
     * old code passed the raw value straight to {@code new Image(...)}, which threw
     * a {@link NullPointerException} and left the window blank.
     */
    private Image loadProfileImage(String path) {
        if (path != null && !path.isBlank()) {
            try {
                Image img = new Image(path, false);
                if (!img.isError()) {
                    return img;
                }
            } catch (RuntimeException ignored) {
                // fall through to the bundled default
            }
        }
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("default.jpeg")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        image = loadProfileImage(AppState.path);

        progressIndicator.setVisible(false);
       circle.setFill(new ImagePattern(image));
        profile_name_label.setText(AppState.username);


        if (i == 0) {

            selectedButton = home_btn;

            selectedButton.getStyleClass().add("active-btn");
        }
        i++;


        add_semester_semester_combo.setItems(FXCollections.observableArrayList(semesters));
        add_semester_type_combo.setItems(FXCollections.observableArrayList(sections));
        credit_hour_combo.setItems(FXCollections.observableArrayList(creditHours));
        add_teacher_gender_combo.setItems(FXCollections.observableArrayList(genderList));
        add_room_lab_type_combo.setItems(FXCollections.observableArrayList(type));
        add_teacher_type_combo.setItems(FXCollections.observableArrayList(teacherType));
        allocate_teacher_type_combo.setItems(FXCollections.observableArrayList(teacherType));
        allocate_course_section_combo.setItems(FXCollections.observableArrayList(sections));


        sessionComboList();
        programComboList();
        displayHome();
        displayProgramListData();
        displaySessionListData();
        displayLabListData();
        displayRoomListData();
        displayAllocateCourseListData();
        displayGenerateTimeTableListData();
        displayTeacherListData();
        displaySemesterListData();
        displayCourseListData();

        // Build the banner now so the window can mount it above the view;
        // wire tooltips + show the first-run guide once the window is live.
        guidance = GuidanceUI.install(rootWindow, DataBaseLayer::getConnection);
        guidance.showFor("home_form");
        javafx.application.Platform.runLater(() -> {
            guidance.attach();
            guidance.maybeShowGettingStartedOnStartup();
        });
    }

    /** The guidance toolbar node, mounted above the view by {@link Stages}. */
    public javafx.scene.Node guidanceBanner() {
        return guidance != null ? guidance.bannerNode() : null;
    }

    /** The progress rail, mounted to the right of the view by {@link Stages}. */
    public javafx.scene.Node guidanceRail() {
        return guidance != null ? guidance.railNode() : null;
    }

    /** Sidebar footer -> About dialog (developer + project info). */
    @FXML
    void showAbout() {
        if (guidance != null) {
            guidance.showAbout();
        }
    }

    /** Tell the guidance banner which screen is now visible. */
    private void updateGuidanceBanner() {
        if (guidance == null) {
            return;
        }
        for (String fid : GUIDANCE_FORM_IDS) {
            javafx.scene.Node f = rootWindow.lookup("#" + fid);
            if (f != null && f.isVisible()) {
                guidance.showFor(fid);
                return;
            }
        }
    }

    public void switchForm(ActionEvent event) {

        // Hide all forms
        home_form.setVisible(false);
        session_form.setVisible(false);
        program_form.setVisible(false);
        semester_form.setVisible(false);
        course_form.setVisible(false);
        teacher_form.setVisible(false);
        room_lab_form.setVisible(false);
        allocate_course_form.setVisible(false);
        generate_form.setVisible(false);

        // Determine which button was clicked and update the form visibility
        if (event.getSource() == home_btn) {

            home_form.setVisible(true);
        } else if (event.getSource() == program_btn) {

            add_program_text_fieldID.setEditable(false);

            program_form.setVisible(true);
            searchProgram();
        } else if (event.getSource() == session_btn) {

            session_form.setVisible(true);
            add_session_ID.setEditable(false);
            searchSession();
        } else if (event.getSource() == semester_btn) {

            semester_form.setVisible(true);
            add_semester_ID_field.setEditable(false);
            sessionComboList();
            searchSemester();
            programComboList();

        } else if (event.getSource() == course_btn) {

            course_form.setVisible(true);
            searchCourse();
            course_program_combo.getSelectionModel().clearSelection();
            allocate_course_combo.getSelectionModel().clearSelection();
            course_semester_combo.getSelectionModel().clearSelection();
            allocate_program_combo.getSelectionModel().clearSelection();
            programComboList();
            credit_hour_combo.setItems(FXCollections.observableArrayList(creditHours));
            add_course_ID_field.setEditable(false);
        } else if (event.getSource() == teacher_btn) {

            teacher_form.setVisible(true);
            add_teacher_ID_field.setEditable(false);
            add_teacher_type_combo.setItems(FXCollections.observableArrayList(teacherType));
            add_teacher_gender_combo.setItems(FXCollections.observableArrayList(genderList));
            programComboList();
            searchTeacher();
        } else if (event.getSource() == room_lab_btn) {
            room_lab_form.setVisible(true);
            add_room_lab_type_combo.setItems(FXCollections.observableArrayList(type));
            add_room_lab_combo.setDisable(true);
            add_room_ID_field.setEditable(false);
            add_room_capacity_field.setEditable(false);
            add_room_No_field.setEditable(false);
            programComboList();
            searchRoom();
        } else if (event.getSource() == allocate_course_btn) {
            allocate_subject_ID_field.setText("");
            allocate_subject_ID_field.setEditable(false);
            course_program_combo.getSelectionModel().clearSelection();
            searchAllocateCourse();
            programComboList();
            allocate_course_form.setVisible(true);
            course_program_combo.getSelectionModel().clearSelection();
            allocate_course_combo.getSelectionModel().clearSelection();
            course_semester_combo.getSelectionModel().clearSelection();
            allocate_program_combo.getSelectionModel().clearSelection();
            allocate_teacher_type_combo.setItems(FXCollections.observableArrayList(teacherType));
        } else if (event.getSource() == generate_btn) {

            searchTimeTable();
            generate_form.setVisible(true);
        }
        else if(event.getSource() == print_btn){

            displayHome();
            if (guidance != null) {
                // Point the banner at step 9; details are one click away on "Rules"
                // (no extra pop-up before the Print window opens).
                guidance.showStepNumber(9);
            }
            Parent root = null;

            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Print.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle("TimetableXpert");
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
            stage.getIcons().add(image);
            stage.centerOnScreen();
            stage.setResizable(false);
            Scene scene = new Scene(root);


            stage.setScene(scene);
            stage.show();
            home_form.setVisible(true);



        }else if (event.getSource() == log_out_btn) {

            home_form.setVisible(true);

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to log out: " + add_program_text_field.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)){
                log_out_btn.getScene().getWindow().hide();
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setResizable(false);
            Scene scene = new Scene(root);
                stage.setTitle("TimetableXpert");
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
                stage.getIcons().add(image);


            stage.setScene(scene);
            stage.show();
        }
        }



        // Reset the previously selected button's style to its CSS class
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("active-btn");
        }

        // Set the currently clicked button as the selected button and update its style
        Button clickedButton = (Button) event.getSource();
        clickedButton.getStyleClass().add("active-btn");
        selectedButton = clickedButton;

        updateGuidanceBanner();
    }


    public void displayHome(){

        try {

            statement = DataBaseLayer.connection.prepareStatement("Select count(*) from ProgramTable");
            resultSet = retrieve(statement);

            int total = 0;
            while (resultSet.next()){


                total = resultSet.getInt(1);

            }
            total_programs.setText(String.valueOf(total));

            statement = DataBaseLayer.connection.prepareStatement("Select count(*) from programSemesterTable");
            resultSet = retrieve(statement);

            total = 0;
            while (resultSet.next()){


                total = resultSet.getInt(1);
            }

            total_classes.setText(String.valueOf(total));


            statement = DataBaseLayer.connection.prepareStatement("Select count(*) from lectureTable where type = 0");
            resultSet = retrieve(statement);

            total = 0;
            while (resultSet.next()){


                total = resultSet.getInt(1);
            }

            total_regular.setText(String.valueOf(total));

            statement = DataBaseLayer.connection.prepareStatement("Select count(*) from roomTable");
            resultSet = retrieve(statement);

            total = 0;
            while (resultSet.next()){


                total = resultSet.getInt(1);
            }

            total_rooms.setText(String.valueOf(total));

            statement = DataBaseLayer.connection.prepareStatement("Select count(*) from lectureTable where type = 1");
            resultSet = retrieve(statement);

            total = 0;
            while (resultSet.next()){


                total = resultSet.getInt(1);
            }

            total_visiting.setText(String.valueOf(total));
            statement = DataBaseLayer.connection.prepareStatement("Select count(*) from labtable");
            resultSet = retrieve(statement);

            total = 0;
            while (resultSet.next()){


                total = resultSet.getInt(1);
            }

            total_labs.setText(String.valueOf(total));


            XYChart.Series<String,Number> chart = new XYChart.Series<>();


            class_chart.getData().clear();

            statement = DataBaseLayer.connection.prepareStatement("SELECT programName, COUNT(*) FROM ProgramSemesterTable GROUP BY programName;");
            resultSet = retrieve(statement);

            while (resultSet.next()){

                chart.getData().add(new XYChart.Data<>(resultSet.getString(1),resultSet.getInt(2)));
            }
            class_chart.getData().add(chart);

            chart = new XYChart.Series<>();


            teacher_chart.getData().clear();

            resultSet = retrieve(statement);

            statement = DataBaseLayer.connection.prepareStatement(
                    "SELECT programName, COUNT(*) FROM LectureTable WHERE type = 0 GROUP BY programName;"
            );
            while (resultSet.next()){

                chart.getData().add(new XYChart.Data<>(resultSet.getString(1),resultSet.getInt(2)));
            }
            teacher_chart.getData().add(chart);

            chart = new XYChart.Series<>();


            visitor_chart.getData().clear();

            resultSet = retrieve(statement);

            statement = DataBaseLayer.connection.prepareStatement(
                    "SELECT programName, COUNT(*) FROM LectureTable WHERE type = 1 GROUP BY programName;"
            );
            while (resultSet.next()){

                chart.getData().add(new XYChart.Data<>(resultSet.getString(1),resultSet.getInt(2)));
            }
            visitor_chart.getData().add(chart);
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }





    }


    public ObservableList<Program> programListData()  {

        ObservableList<Program>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        Program program;
        try {
            statement = DataBaseLayer.connection.prepareStatement("Select programID,name from ProgramTable");
            resultSet = retrieve(statement);

            while (resultSet.next()){

                program = new Program(resultSet.getInt(1),resultSet.getString(2));

                listData.add(program);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;


    }


    public void displayProgramListData(){

        programList = programListData();
        add_program_ID_col.setCellValueFactory(new PropertyValueFactory<>("programID"));
        add_program_name_col.setCellValueFactory(new PropertyValueFactory<>("programName"));
        program_table_view.setItems(programList);

    }
    public void programSelect(){



        Program program = program_table_view.getSelectionModel().getSelectedItem();
        int num = program_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};



        add_program_text_field.setText(String.valueOf(program.getProgramName()));
        add_program_text_fieldID.setText(String.valueOf(program.getProgramID()));
    }

    public void programReset(){

        add_program_text_field.setText("");
        add_program_text_fieldID.setText("");
    }
    public void addProgram()
    {


        try {
            if (add_program_text_field.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;
            }
            else {

                statement = DataBaseLayer.connection.prepareStatement("SELECT name FROM ProgramTable WHERE name = ?");
                statement.setString(1, add_program_text_field.getText());
                resultSet = retrieve(statement);

                assert resultSet != null;
                if (resultSet.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Program Name: " + add_program_text_field.getText() + " was already exist" );
                    alert.showAndWait();

                }else {


                    statement = DataBaseLayer.connection.prepareStatement("INSERT INTO ProgramTable (name) VALUES (?)");
                    statement.setString(1, add_program_text_field.getText());
                    insert(statement);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!" );
                    alert.showAndWait();
                    displayProgramListData();
                }
            }

            programReset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void updateProgram() {

        Program program = program_table_view.getSelectionModel().getSelectedItem();
        int num = program_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Select the Record to update");
            alert.showAndWait();
            return;
            }else if (add_program_text_field.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;


        } else {



            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update program: " + add_program_text_field.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {
                    statement = DataBaseLayer.connection.prepareStatement("Update  ProgramTable set name = ? where programID = ?");
                    statement.setString(1, add_program_text_field.getText());
                    statement.setString(2, add_program_text_fieldID.getText());

                    update(statement);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                displayProgramListData();
                programReset();
            }

        }

    }
    public void deleteProgram(){

        Program program = program_table_view.getSelectionModel().getSelectedItem();
        int num = program_table_view.getSelectionModel().getSelectedIndex();
        if ((num - 1 ) < -1) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Select the Record to delete");
            alert.showAndWait();
            return;
        }
        else if (add_program_text_field.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        }else {


            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete program: " + add_program_text_field.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {

                    statement = DataBaseLayer.connection.prepareStatement("delete from ProgramTable WHERE name = ? and programID = ?");
                    statement.setString(1, add_program_text_field.getText());
                    statement.setString(2, add_program_text_fieldID.getText());

                    delete(statement);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    alert.showAndWait();
                    displayProgramListData();
                    programReset();


                } catch (SQLException e) {


                    throw new RuntimeException(e);
                }


            }



        }
    }
    public void searchProgram(){


        FilteredList<Program> filter = new FilteredList<>(programList, e -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(program -> {
                // If the search field is empty, show all programs

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(program.getProgramID()).contains(searchKey)) {
                    return true;
                }else if(program.getProgramName().toLowerCase().contains(searchKey)){
                    return true;
                }else{

                    return false;
                }


            });
        });
        SortedList<Program> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(program_table_view.comparatorProperty());
        program_table_view.setItems(sortedList);
    }
    public ObservableList<Session> sessionListData()  {

        ObservableList<Session>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        Session session;
        try {
            statement = DataBaseLayer.connection.prepareStatement("Select sessionID,title from SessionTable");
            resultSet = retrieve(statement);


            while (resultSet.next()){

                session = new Session(resultSet.getInt(1),resultSet.getString(2));

                listData.add(session);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;

    }
    public void deleteSession(){

        Session session = session_table_view.getSelectionModel().getSelectedItem();
        int num = session_table_view.getSelectionModel().getSelectedIndex();
        if ((num - 1 ) < -1) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Select the Record to delete");
            alert.showAndWait();
            return;
        }
        else if (add_session_name.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        }else {


            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete session: " + add_session_name.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {

                    statement = DataBaseLayer.connection.prepareStatement("delete from SessionTable WHERE title = ? and sessionID = ?");
                    statement.setString(1, add_session_name.getText());
                    statement.setString(2, add_session_ID.getText());
                    delete(statement);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                displaySessionListData();
                sessionReset();
            }

        }
    }
    public void displaySessionListData(){

        sessionList = sessionListData();
        session_ID_col.setCellValueFactory(new PropertyValueFactory<>("sessionID"));
        session_name_col.setCellValueFactory(new PropertyValueFactory<>("sessionName"));
        session_table_view.setItems(sessionList);

    }
    public void sessionSelect(){



        Session session = session_table_view.getSelectionModel().getSelectedItem();
        int num = session_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};



        add_session_ID.setText(String.valueOf(session.getSessionID()));
        add_session_name.setText(String.valueOf(session.getSessionName()));
    }
    public void sessionReset(){

        add_session_ID.setText("");
        add_session_name.setText("");
    }
    public void addSession()
    {

        try {
            if (add_session_name.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;
            }
            else {

                statement = DataBaseLayer.connection.prepareStatement("SELECT title FROM SessionTable WHERE title = ?");
                statement.setString(1, add_session_name.getText());
                // Execute the query to check if the program already exists
                resultSet = retrieve(statement);

                assert resultSet != null;
                if (resultSet.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Session Name: " + add_session_name.getText() + " was already exist" );
                    alert.showAndWait();
                }else {


                    statement = DataBaseLayer.connection.prepareStatement("INSERT INTO SessionTable (title) VALUES (?)");
                    statement.setString(1, add_session_name.getText());
                    insert(statement);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!" );
                    alert.showAndWait();
                    displaySessionListData();
                }
            }

            sessionReset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void updateSession() {


        Session session = session_table_view.getSelectionModel().getSelectedItem();
        int num = session_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Select the Record to update");
            alert.showAndWait();
            return;
        }else if (add_session_name.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        } else {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update session: " + add_session_name.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {
                    statement = DataBaseLayer.connection.prepareStatement("Update  SessionTable set title = ? where sessionID = ?");
                    statement.setString(1, add_session_name.getText());
                    statement.setString(2, add_session_ID.getText());

                    update(statement);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                displaySessionListData();
                sessionReset();
            }

        }

    }

    public void searchSession(){


        FilteredList<Session> filter = new FilteredList<>(sessionList, e -> true);

        session_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(session -> {
                // If the search field is empty, show all programs

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(session.getSessionID()).contains(searchKey)) {
                    return true;
                }else if(session.getSessionName().toLowerCase().contains(searchKey)){
                    return true;
                }else{

                    return false;
                }


            });
        });
        SortedList<Session> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(session_table_view.comparatorProperty());
        session_table_view.setItems(sortedList);
    }

    public void sessionComboList(){

        List<String> list = new ArrayList<>();
        try {
            statement = DataBaseLayer.connection.prepareStatement("Select title from SessionTable order by title");
            resultSet = retrieve(statement);

            while (resultSet.next()){

               list.add(resultSet.getString(1));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> listData =  FXCollections.observableArrayList(list);
        add_semester_session_combo.setItems(listData);

    }
    public void programComboList(){


        List<String> list = new ArrayList<>();
        DataBaseLayer.connect();
        try {
            statement = DataBaseLayer.connection.prepareStatement("SELECT name FROM ProgramTable ORDER BY SUBSTRING(name, 4, 1);");
            resultSet = retrieve(statement);

            while (resultSet.next()){

                list.add(resultSet.getString(1));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> listData =  FXCollections.observableArrayList(list);
        add_semester_program_combo.setItems(listData);
        course_program_combo.setItems(listData);
        add_teacher_program_combo.setItems(listData);
        add_room_lab_combo.setItems(listData);
        allocate_program_combo.setItems(listData);

    }

    public void labComboList(){


        List<String> list = new ArrayList<>();
        DataBaseLayer.connect();
        try {

            statement = DataBaseLayer.connection.prepareStatement("SELECT labNo FROM LabTable WHERE programName = ? ORDER BY labNo;");
            statement.setString(1,allocate_program_combo.getSelectionModel().getSelectedItem());

            resultSet = retrieve(statement);

            while (resultSet.next()){

                list.add(resultSet.getString(1));

            }

            statement = DataBaseLayer.connection.prepareStatement("SELECT Count(*) FROM courseTable WHERE title = ? and roomTypeID = ?;");
            statement.setString(1,allocate_course_combo.getSelectionModel().getSelectedItem());
            statement.setInt(2,4);

            resultSet = retrieve(statement);

            int count = -1;
            while (resultSet.next()){

                count = resultSet.getInt(1);

            }

            if (count == 0){


                allocate_lab_combo.setDisable(true);
            }else{

                allocate_lab_combo.setDisable(false);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> listData =  FXCollections.observableArrayList(list);

        allocate_lab_combo.setItems(listData);



    }

    public void teacherComboList(){


        List<String> list = new ArrayList<>();
        DataBaseLayer.connect();
        try {

            statement = DataBaseLayer.connection.prepareStatement("SELECT fullName FROM lectureTable WHERE type = ? ORDER BY SUBSTRING(fullName, 5, 1);");
            statement.setInt(1,allocate_teacher_type_combo.getSelectionModel().getSelectedIndex());

            resultSet = retrieve(statement);

            while (resultSet.next()){

                list.add(resultSet.getString(1));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> listData =  FXCollections.observableArrayList(list);
        allocate_course_teacher_combo.setItems(listData);

    }

    public void programSemesterComboList(){

        List<String> list = new ArrayList<>();
        try {


                statement = DataBaseLayer.connection.prepareStatement("SELECT  Distinct title FROM ProgramSemesterTable WHERE programName = ? ORDER BY SUBSTRING(title, 4, 1);");

                statement.setString(1,course_program_combo.getSelectionModel().getSelectedItem());
                resultSet = retrieve(statement);

                while (resultSet.next()){

                    list.add(resultSet.getString(1));

                }
                ObservableList<String> listData =  FXCollections.observableArrayList(list);
                course_semester_combo.setItems(listData);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    public void courseComboList(){

        List<String> list = new ArrayList<>();



        if (allocate_semester_combo.getSelectionModel().getSelectedItem() != null) {

        try {



                String selectedItem = allocate_semester_combo.getSelectionModel().getSelectedItem();

                int endIndex = selectedItem.indexOf("Semester") + "Semester".length();
                // Extract the substring up to the end of "Semester"
                String substring = selectedItem.substring(0, endIndex);

                statement = DataBaseLayer.connection.prepareStatement("select programSemesterID from programSemesterTable where title = ? limit 1");
                statement.setString(1, substring);

                resultSet = retrieve(statement);

                int programSemesterID = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    programSemesterID = resultSet.getInt(1);
                }

                DataBaseLayer.connect();

                statement = DataBaseLayer.connection.prepareStatement("SELECT Distinct title FROM courseTable WHERE  semesterID = ?  ORDER By title;");

                statement.setInt(1, programSemesterID);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    list.add(resultSet.getString(1));


                }
            } catch(SQLException e){
                throw new RuntimeException(e);
            }

            ObservableList<String> listData = FXCollections.observableArrayList(list);
            allocate_course_combo.setItems(listData);
        }

    }


    public ObservableList<Semester> semesterListData()  {


        ObservableList<Semester>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();



        Semester semester;
        try {

            statement = DataBaseLayer.connection.prepareStatement("Select programSemesterID,title,capacity,timetabletypeID,totalCreditHours from ProgramSemesterTable  order by semesterID");
            resultSet = retrieve(statement);

            while (resultSet.next()){

                semester = new Semester(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        (resultSet.getInt(4) == 1 ? "Morning" : "Replica")
               ,resultSet.getInt(5) );
                listData.add(semester);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;

    }

    public void displaySemesterListData(){

        currentSection = add_semester_type_combo.getSelectionModel().getSelectedItem();
        currentSelected = add_semester_program_combo.getSelectionModel().getSelectedItem();
        ObservableList<Semester> listData = FXCollections.observableArrayList();

        Semester semester;
        try {
           if (currentSelected != null && currentSection!=null){

               connection = DataBaseLayer.connect();

               statement = DataBaseLayer.connection.prepareStatement("Select programSemesterID,title,capacity,timetabletypeID,totalCreditHours from ProgramSemesterTable where programName = ? and timeTableTypeID = ? order by semesterID");
               statement.setString(1, add_semester_program_combo.getSelectionModel().getSelectedItem());
               statement.setInt(2, (add_semester_type_combo.getSelectionModel().getSelectedIndex() +1 ));


               resultSet = retrieve(statement);

               while (resultSet.next()) {

                   semester = new Semester(
                           resultSet.getInt(1),
                           resultSet.getString(2),
                           resultSet.getInt(3),
                           (resultSet.getInt(4) == 1 ? "Morning" : "Replica")
                           , resultSet.getInt(5));
                   listData.add(semester);
               }
               semester_table_view.setItems(listData);

           }

        else if (currentSelected !=null) {

            connection = DataBaseLayer.connect();

            statement = DataBaseLayer.connection.prepareStatement("Select programSemesterID,title,capacity,timetabletypeID,totalCreditHours from ProgramSemesterTable where programName = ? order by semesterID");
                statement.setString(1, add_semester_program_combo.getSelectionModel().getSelectedItem());

                resultSet = retrieve(statement);

                while (resultSet.next()) {

                    semester = new Semester(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            (resultSet.getInt(4) == 1 ? "Morning" : "Replica")
                            , resultSet.getInt(5));
                    listData.add(semester);
                }
                semester_table_view.setItems(listData);


        }else if(currentSection != null){

            statement = DataBaseLayer.connection.prepareStatement("Select programSemesterID,title,capacity,timetabletypeID,totalCreditHours from ProgramSemesterTable where timeTableTypeID = ? order by semesterID");
            statement.setInt(1, (add_semester_type_combo.getSelectionModel().getSelectedIndex() +1 ));

            resultSet = retrieve(statement);

            while (resultSet.next()) {

                semester = new Semester(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        (resultSet.getInt(4) == 1 ? "Morning" : "Replica")
                        , resultSet.getInt(5));
                listData.add(semester);
            }
            semester_table_view.setItems(listData);
        }
        else {

            semesterListData = semesterListData();
            add_semester_semesterID_col.setCellValueFactory(new PropertyValueFactory<>("semesterID"));
            add_semester_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
            add_semester_section_col.setCellValueFactory(new PropertyValueFactory<>("Section"));
            add_semester_capacity_col.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
            add_semester_creditHours_col.setCellValueFactory(new PropertyValueFactory<>("CreditHours"));
            semester_table_view.setItems(semesterListData);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void addSemester() {
        try {
            if (add_semester_session_combo.getSelectionModel().getSelectedItem() == null ||
                    add_semester_semester_combo.getSelectionModel().getSelectedItem() == null ||
                    add_semester_program_combo.getSelectionModel().getSelectedItem() == null ||
                    add_semester_crHrs_field.getText().isEmpty() ||
                    add_semester_capacity_field.getText().isEmpty() ||
                    add_semester_type_combo.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;
            } else {
                String title = add_semester_program_combo.getSelectionModel().getSelectedItem() + " " +  add_semester_semester_combo.getSelectionModel().getSelectedItem();
                statement = DataBaseLayer.connection.prepareStatement("SELECT title,timetabletypeID FROM ProgramSemesterTable WHERE title = ? and timetabletypeID = ?");
                statement.setString(1, title);
                statement.setInt(2, (add_semester_type_combo.getSelectionModel().getSelectedIndex() + 1));
                resultSet = retrieve(statement);

                assert resultSet != null;
                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Semester Name: " + title + " " + add_semester_type_combo.getSelectionModel().getSelectedItem() + " was already exist");
                    alert.showAndWait();
                } else {
                    try {
                        int capacity;
                        int creditHours;

                        try {
                            capacity = Integer.parseInt(add_semester_capacity_field.getText());
                            // Check for negative or zero capacity
                            if (capacity <= 0) {
                                throw new NumberFormatException("Capacity must be a positive integer.");
                            }

                        } catch (NumberFormatException e) {

                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter a valid integer for capacity.");
                            alert.showAndWait();
                            return;
                        }

                        try {

                            creditHours = Integer.parseInt(add_semester_crHrs_field.getText());
                            // Check for negative or zero capacity
                            if (creditHours <= 0) {
                                throw new NumberFormatException("Credit Hours must be a positive integer.");
                            }

                        } catch (NumberFormatException e) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter a valid integer for credit hours.");
                            alert.showAndWait();
                            return;
                        }

                        // Retrieve sessionID from SessionTable
                        statement = DataBaseLayer.connection.prepareStatement("select sessionID from SessionTable where title = ?");
                        statement.setString(1, add_semester_session_combo.getSelectionModel().getSelectedItem());
                        resultSet = retrieve(statement);

                        int sessionID = 0;
                        assert resultSet != null;
                        if (resultSet.next()) {
                            sessionID = resultSet.getInt(1);
                        }

                        // Retrieve programID from ProgramTable
                        statement = DataBaseLayer.connection.prepareStatement("select programID from ProgramTable where name = ? limit 1");
                        statement.setString(1, add_semester_program_combo.getSelectionModel().getSelectedItem());
                        resultSet = retrieve(statement);

                        int programID = 0;
                        assert resultSet != null;
                        if (resultSet.next()) {
                            programID = resultSet.getInt(1);
                        }

                        // Insert into ProgramSemesterTable
                        statement = DataBaseLayer.connection.prepareStatement("INSERT INTO ProgramSemesterTable (title, SemesterID, capacity,programID,timetabletypeID,programName,totalCreditHours,sessionID) VALUES (?,?,?,?,?,?,?,?)");
                        statement.setString(1, title);
                        statement.setInt(2, (add_semester_semester_combo.getSelectionModel().getSelectedIndex() + 1));
                        statement.setInt(3, capacity);
                        statement.setInt(4, programID);
                        statement.setInt(5, (add_semester_type_combo.getSelectionModel().getSelectedIndex() + 1));
                        statement.setString(6, add_semester_program_combo.getSelectionModel().getSelectedItem());
                        statement.setInt(7, creditHours);
                        statement.setInt(8, sessionID);
                        insert(statement);

                        // Show success message
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Added!");
                        alert.showAndWait();

                        add_semester_session_combo.getSelectionModel().clearSelection();
                        add_semester_semester_combo.getSelectionModel().clearSelection();
                        add_semester_crHrs_field.setText("");
                        add_semester_ID_field.setText("");
                        add_semester_capacity_field.setText("");
                        displaySemesterListData();

                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Handle the error and show an error message
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Failed to Add Data!");
                        alert.showAndWait();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void semesterReset(){

        add_semester_type_combo.getSelectionModel().clearSelection();
        add_semester_session_combo.getSelectionModel().clearSelection();
        add_semester_semester_combo.getSelectionModel().clearSelection();
        add_semester_crHrs_field.setText("");
        add_semester_ID_field.setText("");
        add_semester_capacity_field.setText("");
        add_semester_program_combo.getSelectionModel().clearSelection();
        currentSelected = null;


    }
    public void semesterSelect(){

        Semester semester = semester_table_view.getSelectionModel().getSelectedItem();
        int num = semester_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};


        add_semester_ID_field.setText(String.valueOf(semester.getSemesterID()));
        add_semester_capacity_field.setText(String.valueOf(semester.getCapacity()));
        add_semester_type_combo.getSelectionModel().clearSelection();
        add_semester_semester_combo.getSelectionModel().clearSelection();
        add_semester_session_combo.getSelectionModel().clearSelection();

    }

    public void updateSemester() {


        String title = add_semester_program_combo.getSelectionModel().getSelectedItem() + " " +  add_semester_semester_combo.getSelectionModel().getSelectedItem();


        if ( add_semester_session_combo.getSelectionModel().getSelectedItem() == null ||
                add_semester_semester_combo.getSelectionModel().getSelectedItem() == null ||
                add_semester_program_combo.getSelectionModel().getSelectedItem() == null ||
               add_semester_crHrs_field.getText().isEmpty() || add_semester_capacity_field.getText().isEmpty() || add_semester_type_combo.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        } else {

            try {

                title = add_semester_program_combo.getSelectionModel().getSelectedItem() + " " + add_semester_semester_combo.getSelectionModel().getSelectedItem();
                statement = DataBaseLayer.connection.prepareStatement("SELECT title,timetabletypeID FROM ProgramSemesterTable WHERE title = ? and timetabletypeID = ?");
                statement.setString(1, title);
                statement.setInt(2, (add_semester_type_combo.getSelectionModel().getSelectedIndex() + 1));
                resultSet = retrieve(statement);

                assert resultSet != null;
                if (resultSet.next()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Semester Name: " + title + " " + add_semester_type_combo.getSelectionModel().getSelectedItem() + " was already exist");
                    alert.showAndWait();
                    return;

                }
            }catch (SQLException e) {

                System.out.println(e.getMessage());
            }

            int capacity;
            int creditHours;

            try {

                capacity = Integer.parseInt(add_semester_capacity_field.getText());
                // Check for negative or zero capacity
                if (capacity <= 0) {
                    throw new NumberFormatException("Capacity must be a positive integer.");
                }

            } catch (NumberFormatException e) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid integer for capacity.");
                alert.showAndWait();
                return;
            }

            try {

                creditHours = Integer.parseInt(add_semester_crHrs_field.getText());
                // Check for negative or zero capacity
                if (creditHours <= 0) {
                    throw new NumberFormatException("Credit Hour must be a positive integer.");
                }

            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid integer for credit hours.");
                alert.showAndWait();
                return;
            }
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update semester: ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {
                    statement = DataBaseLayer.connection.prepareStatement("select sessionID from SessionTable where title = ?");
                    statement.setString(1, add_semester_session_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int sessionID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        sessionID = resultSet.getInt(1);
                    }




                    // Retrieve programID from ProgramTable
                    statement = DataBaseLayer.connection.prepareStatement("select programID from ProgramTable where name = ? limit 1");
                    statement.setString(1, add_semester_program_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int programID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programID = resultSet.getInt(1);
                    }



                    statement = DataBaseLayer.connection.prepareStatement("UPDATE ProgramSemesterTable SET title = ?,  SemesterID = ? ,capacity = ? ,  programID = ? , timetabletypeID = ?, programName = ?, totalCreditHours = ?,sessionID = ?  WHERE programSemesterID = ?");
                    statement.setString(1, title);
                    statement.setInt(2, (add_semester_semester_combo.getSelectionModel().getSelectedIndex()) + 1);
                    statement.setInt(3, Integer.parseInt(add_semester_capacity_field.getText()));
                    statement.setInt(4, programID);
                    statement.setInt(5,(add_semester_type_combo.getSelectionModel().getSelectedIndex() + 1));
                    statement.setString(6,add_semester_program_combo.getSelectionModel().getSelectedItem());
                    statement.setInt(7,Integer.parseInt(add_semester_crHrs_field.getText()));
                    statement.setInt(8,sessionID);
                    statement.setInt(9,Integer.parseInt(add_semester_ID_field.getText()));



                    update(statement);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                add_semester_session_combo.getSelectionModel().clearSelection();
                add_semester_semester_combo.getSelectionModel().clearSelection();
                add_semester_crHrs_field.setText("");
                add_semester_ID_field.setText("");
                add_semester_capacity_field.setText("");
                displaySemesterListData();

            }

        }

    }
    public void deleteSemester(){

        String title = add_semester_program_combo.getSelectionModel().getSelectedItem() + " " +  add_semester_semester_combo.getSelectionModel().getSelectedItem();


         if (add_semester_capacity_field.getText().isEmpty() ||add_semester_ID_field.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        }else {


            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete semester ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {
                    statement = DataBaseLayer.connection.prepareStatement("select sessionProgramID from programSemesterTable where programSemesterID = ?");
                    statement.setInt(1, Integer.parseInt(add_semester_ID_field.getText()));
                    resultSet = retrieve(statement);

                    int programSessionID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programSessionID = resultSet.getInt(1);
                    }

                    statement = DataBaseLayer.connection.prepareStatement("delete from programSemesterTable WHERE  programSemesterID = ?");
                    statement.setInt(1, Integer.parseInt(add_semester_ID_field.getText()));
                    delete(statement);

                    statement = DataBaseLayer.connection.prepareStatement("delete from sessionProgramTable WHERE  sessionProgramID = ?");
                    statement.setInt(1, programSessionID);
                    delete(statement);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    alert.showAndWait();

                } catch (SQLException e) {

                    throw new RuntimeException(e);
                }


                add_semester_session_combo.getSelectionModel().clearSelection();
                add_semester_semester_combo.getSelectionModel().clearSelection();
                add_semester_crHrs_field.setText("");
                add_semester_ID_field.setText("");
                add_semester_capacity_field.setText("");
                displaySemesterListData();
            }

        }
    }


    public void searchSemester(){


        FilteredList<Semester> filter = new FilteredList<>(semesterListData, e -> true);

        semester_search.textProperty().addListener((observable, oldValue,  newValue) -> {
            filter.setPredicate(semester -> {
                // If the search field is empty, show all programs

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(semester.getSemesterID()).contains(searchKey)) {
                    return true;
                }else if(semester.getTitle().toLowerCase().contains(searchKey)){
                    return true;

                }else if(String.valueOf(semester.getCapacity()).contains(searchKey)){
                    return true;
                }
                else if(semester.getSection().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (String.valueOf(semester.getCreditHours()).contains(searchKey)) {
                        return true;

                }else{

                    return false;
                }


            });
        });

        SortedList<Semester> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(semester_table_view.comparatorProperty());
        semester_table_view.setItems(sortedList);
    }

    public ObservableList<Course> courseListData()  {

        ObservableList<Course>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        Course course;
        try {
            assert DataBaseLayer.connection != null;
            statement = DataBaseLayer.connection.prepareStatement("Select courseID,courseTitle,courseCode, creditHours,semester,program from CourseView");
            resultSet = retrieve(statement);

            while (resultSet.next()){

                course = new Course(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
                        resultSet.getString(5),resultSet.getString(6));

                listData.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;


    }


    public void displayCourseListData(){


        currentSelected = course_program_combo.getSelectionModel().getSelectedItem();
        currentSemester = course_semester_combo.getSelectionModel().getSelectedItem();

        String selectedItem = null;
        int endIndex = 0;
        String substring = null;

        ObservableList<Course>  listData = FXCollections.observableArrayList();
        try {
        connection = DataBaseLayer.connect();

        Course course;


        if (currentSelected != null && currentSemester!= null) {

             selectedItem = course_semester_combo.getSelectionModel().getSelectedItem();
             endIndex = selectedItem.indexOf("Semester") + "Semester".length();
             substring = selectedItem.substring(0, endIndex);

            statement = DataBaseLayer.connection.prepareStatement("select semesterID from programSemesterTable where title = ? limit 1");
            statement.setString(1, substring);
            resultSet = retrieve(statement);

            int semesterID = 0;
            assert resultSet != null;
            if (resultSet.next()) {
                semesterID = resultSet.getInt(1);
            }
            String semesterName = null;
            for (int i = 0 ; i<semesters.length ; i++){

                if ((i + 1  == semesterID)){

                    semesterName = semesters[i];
                }

            }


            assert DataBaseLayer.connection != null;
                statement = DataBaseLayer.connection.prepareStatement("Select courseID,courseTitle,courseCode, creditHours,semester,program from CourseView where program = ? and semester = ?");
                statement.setString(1,currentSelected);
                statement.setString(2,semesterName);
                resultSet = retrieve(statement);

                while (resultSet.next()) {

                    course = new Course(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6));

                    listData.add(course);
                }
            course_table_view.setItems(listData);

            statement = DataBaseLayer.connection.prepareStatement("select programSemesterID from programSemesterTable where title = ? limit 1");
            statement.setString(1, substring);
            resultSet = retrieve(statement);

            int programSemesterID = 0;
            assert resultSet != null;
            if (resultSet.next()) {

                programSemesterID = resultSet.getInt(1);

            }

            statement = DataBaseLayer.connection.prepareStatement("select totalCreditHours from programSemesterTable where programSemesterID = ? limit 1");
            statement.setInt(1, programSemesterID);
            resultSet = retrieve(statement);

            totalCreditHours = 0;
            assert resultSet != null;
            if (resultSet.next()) {

                totalCreditHours = resultSet.getInt(1);

            }
            statement = DataBaseLayer.connection.prepareStatement("select sum(crHrs) from courseTable where semesterID = ? limit 1");
            statement.setInt(1, programSemesterID);
            resultSet = retrieve(statement);

            semesterCreditHours = 0;
            assert resultSet != null;
            if (resultSet.next()) {

                semesterCreditHours = resultSet.getInt(1);

            }

            add_course_semester_header.setText(currentSemester);
            add_course_total_credit_hours_header.setText(String.valueOf(totalCreditHours));
            add_course_credit_hours_header.setText(String.valueOf(semesterCreditHours));


        }else if(currentSelected != null ){


            assert DataBaseLayer.connection != null;
            statement = DataBaseLayer.connection.prepareStatement("Select courseID,courseTitle,courseCode, creditHours,semester,program from CourseView where program = ? ");
            statement.setString(1,currentSelected);
            resultSet = retrieve(statement);

            while (resultSet.next()) {

                course = new Course(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6));

                listData.add(course);
            }
            course_table_view.setItems(listData);


        }else if(currentSemester!=null){

            selectedItem = course_semester_combo.getSelectionModel().getSelectedItem();
            endIndex = selectedItem.indexOf("Semester") + "Semester".length();
            substring = selectedItem.substring(0, endIndex);

            statement = DataBaseLayer.connection.prepareStatement("select semesterID from programSemesterTable where title = ? limit 1");
            statement.setString(1, substring);
            resultSet = retrieve(statement);

            int semesterID = 0;
            assert resultSet != null;
            if (resultSet.next()) {
                semesterID = resultSet.getInt(1);
            }
            String semesterName = null;
            for (int i = 0 ; i<semesters.length ; i++){

                if ((i + 1  == semesterID)){

                    semesterName = semesters[i];
                }

            }

            System.out.println(semesterName);

            assert DataBaseLayer.connection != null;
            statement = DataBaseLayer.connection.prepareStatement("Select courseID,courseTitle,courseCode, creditHours,semester,program from CourseView where semester = ?");
            statement.setString(1,semesterName);
            resultSet = retrieve(statement);

            while (resultSet.next()) {

                course = new Course(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6));

                listData.add(course);
            }
            course_table_view.setItems(listData);



        }else{

            courseList = courseListData();
            add_course_course_ID_col.setCellValueFactory(new PropertyValueFactory<>("CourseID"));
            add_course_course_code_col.setCellValueFactory(new PropertyValueFactory<>("CourseCode"));
            add_course_course_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
            add_course_course_hours_col.setCellValueFactory(new PropertyValueFactory<>("CreditHours"));
            add_course_semester_col.setCellValueFactory(new PropertyValueFactory<>("semester"));
            add_course_program_col.setCellValueFactory(new PropertyValueFactory<>("program"));
            course_table_view.setItems(courseList);
            add_course_semester_header.setText("");
            add_course_total_credit_hours_header.setText(String.valueOf(0));
            add_course_credit_hours_header.setText(String.valueOf(0));


        }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void courseSelect(){



        Course course = course_table_view.getSelectionModel().getSelectedItem();
        int num = course_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};



        add_course_code_field.setText(String.valueOf(course.getCourseCode()));
        add_course_ID_field.setText(String.valueOf(course.getCourseID()));
        add_course_title_field.setText(String.valueOf(course.getTitle()));
    }

    public void courseReset(){

        add_course_title_field.setText("");
        add_course_ID_field.setText("");
        add_course_code_field.setText("");

        credit_hour_combo.getSelectionModel().clearSelection();
        course_program_combo.getSelectionModel().clearSelection();
        course_semester_combo.getSelectionModel().clearSelection();
        currentSelected = null;
        currentSemester = null;
        currentSection = null;

    }


    public void addCourse(){

        int lab = 0;
        int nonLab = 0;

        try {
            if ( add_course_code_field.getText().isEmpty()
            || course_semester_combo.getSelectionModel().getSelectedItem() == null||
                course_program_combo.getSelectionModel().getSelectedItem() == null || credit_hour_combo.getSelectionModel().getSelectedItem() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;

            }
            else {



                if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 0){

                    lab = 0;
                    nonLab = 1;
                }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 1){

                    lab = 0;
                    nonLab = 2;
                }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 2){

                    lab = 0;
                    nonLab = 3;
                }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 3){

                    lab = 1;
                    nonLab = 2;

                }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 4){
                    lab = 1;
                    nonLab = 3;
                }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 5){

                    lab = 0;
                    nonLab = 4;

                }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 6){

                    lab = 2;
                    nonLab = 0;
                }




                String selectedItem = course_semester_combo.getSelectionModel().getSelectedItem();
                int endIndex = selectedItem.indexOf("Semester") + "Semester".length();
                String substring = selectedItem.substring(0, endIndex);

                if ((semesterCreditHours + (lab + nonLab)) > totalCreditHours){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(substring + " total Credit Hours is already allocated");
                    alert.showAndWait();
                    return;

                }
                statement = DataBaseLayer.connection.prepareStatement("select semesterID from programSemesterTable where title = ? limit 1");
                statement.setString(1, substring);
                resultSet = retrieve(statement);

                int semesterID = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    semesterID = resultSet.getInt(1);
                }


                String semesterName = null;
                for (int i = 0 ; i<semesters.length ; i++){

                    if ((i + 1  == semesterID)){

                        semesterName = semesters[i];
                    }

                }

                statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM courseView WHERE courseTitle = ? AND program = ? AND semester = ? ");
                statement.setString(1, add_course_title_field.getText());
                statement.setString(2,course_program_combo.getSelectionModel().getSelectedItem());
                statement.setString(3,semesterName);
                resultSet = retrieve(statement);

                assert resultSet != null;


                if (resultSet.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Course : "+ add_course_code_field.getText() + ": " + add_course_title_field.getText() + " was already exist" );
                    alert.showAndWait();
                    return;

                }else {

                    statement = DataBaseLayer.connection.prepareStatement("select programID from programTable where name = ?");
                    statement.setString(1, course_program_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int programID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programID = resultSet.getInt(1);
                    }



                    statement = DataBaseLayer.connection.prepareStatement("select programSemesterID from programSemesterTable where title = ?  limit 1");
                    statement.setString(1,substring);
                    resultSet = retrieve(statement);

                    int programSemesterID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programSemesterID = resultSet.getInt(1);
                    }



                    statement = DataBaseLayer.connection.prepareStatement("INSERT INTO CourseView (courseCode, CourseTitle, creditHours,program,semester,programID) VALUES ( ?, ?, ?,?,?,?)");
                    statement.setString(1,add_course_code_field.getText());
                    statement.setString(2,add_course_title_field.getText());
                    statement.setInt(3,nonLab + lab);
                    statement.setString(4,course_program_combo.getSelectionModel().getSelectedItem());
                    statement.setString(5,semesterName);
                    statement.setInt(6,programID);

                    insert(statement);


                    statement = DataBaseLayer.connection.prepareStatement("select courseID from CourseView where courseTitle = ?");
                    statement.setString(1, add_course_title_field.getText());
                    resultSet = retrieve(statement);
                    int courseID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        courseID = resultSet.getInt(1);
                    }

                    if (nonLab != 0) {

                        statement = DataBaseLayer.connection.prepareStatement("INSERT INTO CourseTable (courseCode, title, crHrs, roomTypeID,programID,semesterID,courseViewID) VALUES (?, ?, ?, ?,?,?,?)");
                        statement.setString(1, add_course_code_field.getText());
                        statement.setString(2, add_course_title_field.getText());
                        statement.setInt(3, nonLab);
                        statement.setInt(4,3);
                        statement.setInt(5,programID);
                        statement.setInt(6, programSemesterID);
                        statement.setInt(7,courseID);
                        insert(statement);

                    }

                    if (lab != 0) {


                        statement = DataBaseLayer.connection.prepareStatement("INSERT INTO CourseTable (courseCode, title, crHrs, roomTypeID,programID,semesterID,courseViewID) VALUES (?, ?, ?, ?,?,?,?)");
                        statement.setString(1, add_course_code_field.getText());
                        statement.setString(2, add_course_title_field.getText());
                        statement.setInt(3, lab);
                        statement.setInt(4,4);
                        statement.setInt(5,programID);
                        statement.setInt(6,programSemesterID);
                        statement.setInt(7,courseID);
                        insert(statement);


                    }
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!" );
                    alert.showAndWait();


                    add_course_title_field.setText("");
                    add_course_ID_field.setText("");
                    add_course_code_field.setText("");
                    credit_hour_combo.getSelectionModel().clearSelection();
                    displayCourseListData();


                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void updateCourse(){


        int lab = 0;
        int nonLab = 0;
         if ( add_course_code_field.getText().isEmpty()
                || add_course_ID_field.getText().isEmpty()|| course_semester_combo.getSelectionModel().getSelectedItem() == null||
                course_program_combo.getSelectionModel().getSelectedItem() == null || credit_hour_combo.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        } else {



             if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 0){

                 lab = 0;
                 nonLab = 1;
             }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 1){

                 lab = 0;
                 nonLab = 2;
             }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 2){

                 lab = 0;
                 nonLab = 3;
             }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 3){

                 lab = 1;
                 nonLab = 2;

             }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 4){
                 lab = 1;
                 nonLab = 3;
             }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 5){

                 lab = 0;
                 nonLab = 4;

             }else if (credit_hour_combo.getSelectionModel().getSelectedIndex() == 6){

                 lab = 2;
                 nonLab = 0;
             }

             String semesterName = null;
             int programSemesterID = 0;

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update Course: " + add_course_title_field.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            try {


                statement = DataBaseLayer.connection.prepareStatement("select semesterID from programSemesterTable where title = ?  limit 1");
                statement.setString(1, course_semester_combo.getSelectionModel().getSelectedItem());
                resultSet = retrieve(statement);

                int semesterID = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    semesterID = resultSet.getInt(1);
                }


                statement = DataBaseLayer.connection.prepareStatement("select programSemesterID from programSemesterTable where title = ?  limit 1");
                statement.setString(1, course_semester_combo.getSelectionModel().getSelectedItem());
                resultSet = retrieve(statement);


                assert resultSet != null;
                if (resultSet.next()) {
                    programSemesterID = resultSet.getInt(1);
                }


                for (int i = 0; i < semesters.length; i++) {

                    if ((i + 1 == semesterID)) {

                        semesterName = semesters[i];

                    }

                }
            }catch (SQLException e){

                System.out.println(e.getMessage());
            }



            if (option.get().equals(ButtonType.OK)) {

                try {

                    statement = DataBaseLayer.connection.prepareStatement("select count(*) from courseTable where courseViewID = ?");
                    statement.setInt(1, Integer.parseInt(add_course_ID_field.getText()));
                    resultSet = retrieve(statement);

                    int courseCount = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        courseCount = resultSet.getInt(1);
                    }


                    statement = DataBaseLayer.connection.prepareStatement("select programID from programTable where name = ?");
                    statement.setString(1, course_program_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int programID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programID = resultSet.getInt(1);
                    }



                    statement = DataBaseLayer.connection.prepareStatement("select crHrs from courseTable where courseViewID = ? and crHrs = ? and roomTypeID = ? limit 1");
                    statement.setInt(1, Integer.parseInt(add_course_ID_field.getText()));
                    statement.setInt(2,2);
                    statement.setInt(3,4);
                    resultSet = retrieve(statement);
                    int ID = 0;

                    assert resultSet != null;
                    if (resultSet.next()) {

                        ID = resultSet.getInt(1);

                    }



                    statement = DataBaseLayer.connection.prepareStatement("Update  CourseView set courseCode = ?,  courseTitle = ? ,  creditHours = ? ,  program = ? , semester = ? where courseID = ? " );
                    statement.setString(1,add_course_code_field.getText());
                    statement.setString(2,add_course_title_field.getText());
                    statement.setInt(3,nonLab + lab);
                    statement.setString(4,course_program_combo.getSelectionModel().getSelectedItem());
                    statement.setString(5,semesterName);
                    statement.setInt(6,Integer.parseInt(add_course_ID_field.getText()));
                    update(statement);


                    if (nonLab != 0 && ID == 2 && courseCount ==1) {

                        statement = DataBaseLayer.connection.prepareStatement("Update CourseTable Set courseCode = ?, title = ? , crHrs = ?, roomTypeID = ? , programID = ? , semesterID  = ? where courseViewID = ? and roomTypeID = ?");
                        statement.setString(1, add_course_code_field.getText());
                        statement.setString(2, add_course_title_field.getText());
                        statement.setInt(3, nonLab);
                        statement.setInt(4,3);
                        statement.setInt(5,programID);
                        statement.setInt(6,programSemesterID);
                        statement.setInt(7,Integer.parseInt(add_course_ID_field.getText()));
                        statement.setInt(8,4);

                        update(statement);

                    }
                  else if (nonLab !=0 ){

                        statement = DataBaseLayer.connection.prepareStatement("Update CourseTable Set courseCode = ?, title = ? , crHrs = ?, roomTypeID = ? , programID = ? , semesterID  = ? where courseViewID = ? and roomTypeID = ?");
                        statement.setString(1, add_course_code_field.getText());
                        statement.setString(2, add_course_title_field.getText());
                        statement.setInt(3, nonLab);
                        statement.setInt(4,3);
                        statement.setInt(5,programID);
                        statement.setInt(6,programSemesterID);
                        statement.setInt(7,Integer.parseInt(add_course_ID_field.getText()));
                        statement.setInt(8,3);

                        update(statement);

                    }

                    statement = DataBaseLayer.connection.prepareStatement("set foreign_key_checks = 0");
                    insert(statement);

                    if (lab != 0) {



                        if (courseCount == 1 && ID > 0){

                            statement = DataBaseLayer.connection.prepareStatement("INSERT INTO CourseTable (courseCode, title, crHrs, roomTypeID,programID,semesterID,courseViewID) VALUES (?, ?, ?, ?,?,?,?)");
                            statement.setString(1, add_course_code_field.getText());
                            statement.setString(2, add_course_title_field.getText());
                            statement.setInt(3, lab);
                            statement.setInt(4,4);
                            statement.setInt(5,programID);
                            statement.setInt(6,programSemesterID);
                            statement.setInt(7,Integer.parseInt(add_course_ID_field.getText()));
                            insert(statement);


                        }

                       else if (courseCount == 1 ){



                            statement = DataBaseLayer.connection.prepareStatement("INSERT INTO CourseTable (courseCode, title, crHrs, roomTypeID,programID,semesterID,courseViewID) VALUES (?, ?, ?, ?,?,?,?)");
                            statement.setString(1, add_course_code_field.getText());
                            statement.setString(2, add_course_title_field.getText());
                            statement.setInt(3, lab);
                            statement.setInt(4,4);
                            statement.setInt(5,programID);
                            statement.setInt(6,programSemesterID);
                            statement.setInt(7,Integer.parseInt(add_course_ID_field.getText()));
                            insert(statement);



                        }else {


                            statement = DataBaseLayer.connection.prepareStatement("Update CourseTable Set courseCode = ?, title = ? , crHrs = ?, roomTypeID = ? , programID = ? , semesterID  = ? where courseViewID = ? and roomTypeID = ?  ");
                            statement.setString(1, add_course_code_field.getText());
                            statement.setString(2, add_course_title_field.getText());
                            statement.setInt(3, lab);
                            statement.setInt(4, 4);
                            statement.setInt(5, programID);
                            statement.setInt(6, programSemesterID);
                            statement.setInt(7, Integer.parseInt(add_course_ID_field.getText()));
                            statement.setInt(8, 4);
                            update(statement);

                        }

                    }


                    if (courseCount == 2){


                        if (lab == 0) {



                            statement = DataBaseLayer.connection.prepareStatement("delete from  courseTable where courseViewID = ? and roomTypeID = ?");
                            statement.setInt(1, Integer.parseInt(add_course_ID_field.getText()));
                            statement.setInt(2,4);
                            delete(statement);

                        }
                        if (nonLab == 0){


                            statement = DataBaseLayer.connection.prepareStatement("delete from  courseTable where courseViewID = ? and roomTypeID = ?");
                            statement.setInt(1, Integer.parseInt(add_course_ID_field.getText()));
                            statement.setInt(2,3);
                            delete(statement);

                        }
                    }
                    if (courseCount == 1 && lab !=0 && nonLab == 0){


                        statement = DataBaseLayer.connection.prepareStatement("delete from  courseTable where courseViewID = ? and roomTypeID = ?");
                        statement.setInt(1, Integer.parseInt(add_course_ID_field.getText()));
                        statement.setInt(2,3);
                        delete(statement);


                    }
                    statement = DataBaseLayer.connection.prepareStatement("set foreign_key_checks = 1");
                    insert(statement);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                add_course_title_field.setText("");
                add_course_ID_field.setText("");
                add_course_code_field.setText("");
                credit_hour_combo.getSelectionModel().clearSelection();

                displayCourseListData();
            }

        }


    }

    public void deleteCourse(){

        Course course = course_table_view.getSelectionModel().getSelectedItem();
        int num = course_table_view.getSelectionModel().getSelectedIndex();

        int lab = 0;
        int nonLab = 0;
        if ((num - 1 ) < -1) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Select the Record to update");
            alert.showAndWait();
            return;
        }else if ( add_course_code_field.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        } else {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Course: " + add_course_title_field.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {


                    statement = DataBaseLayer.connection.prepareStatement("select courseID from CourseView where courseTitle = ?");
                    statement.setString(1, add_course_title_field.getText());
                    resultSet = retrieve(statement);
                    int courseID = 0;

                    assert resultSet != null;
                    if (resultSet.next()) {
                        courseID = resultSet.getInt(1);
                    }

                    // Insert into sessionProgramTable
                    statement = DataBaseLayer.connection.prepareStatement("delete from courseView where courseID = ?" );
                    statement.setInt(1,Integer.parseInt(add_course_ID_field.getText()));

                    delete(statement);

                    statement = DataBaseLayer.connection.prepareStatement("delete from courseTable where courseViewID = ?" );
                    statement.setInt(1,Integer.parseInt(add_course_ID_field.getText()));

                    delete(statement);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                add_course_title_field.setText("");
                add_course_ID_field.setText("");
                add_course_code_field.setText("");
                credit_hour_combo.getSelectionModel().clearSelection();

                displayCourseListData();

            }

        }


    }

    public void searchCourse(){


        FilteredList<Course> filter = new FilteredList<>(courseList, e -> true);

        course_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(course -> {
                // If the search field is empty, show all programs

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(course.getCourseID()).toLowerCase().contains(searchKey)){
                    return true;
                }else if(course.getCourseCode().toLowerCase().contains(searchKey)){
                    return true;
                }else if(course.getTitle().toLowerCase().contains(searchKey)){
                    return true;
                }else if(course.getSemester().toLowerCase().contains(searchKey)){
                    return true;
                }else if(course.getProgram().toLowerCase().contains(searchKey)){
                    return true;
                }
                else{

                    return false;
                }


            });
        });
        SortedList<Course> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(course_table_view.comparatorProperty());
        course_table_view.setItems(sortedList);
    }

    public ObservableList<Teacher> teacherListData()  {

        ObservableList<Teacher>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        Teacher teacher;
        try {

            statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable order by substr(fullName,4);");
            resultSet = retrieve(statement);

            while (resultSet.next()){


                teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                        resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                listData.add(teacher);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listData;


    }
    public void displayTeacherListData(){


        currentSelected = add_teacher_program_combo.getSelectionModel().getSelectedItem();
        currentType = add_teacher_type_combo.getSelectionModel().getSelectedIndex();
        currentGender = add_teacher_gender_combo.getSelectionModel().getSelectedIndex();



        ObservableList<Teacher>  listData = FXCollections.observableArrayList();

        Teacher teacher;
        try {

            if (currentSelected != null && currentGender!= -1 && currentType !=-1){

                statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable where programName = ? AND gender = ? AND type= ? order by substr(fullName,4);");
                statement.setString(1,currentSelected);
                statement.setInt(2,currentGender);
                statement.setInt(3,currentType);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                            resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                    listData.add(teacher);
                }
                teacher_view_table.setItems(listData);


            }else if (currentSelected != null && currentGender!= -1) {

                statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable where programName = ? AND gender = ? order by substr(fullName,4);");
                statement.setString(1, currentSelected);
                statement.setInt(2, currentGender);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                            resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                    listData.add(teacher);
                }
                teacher_view_table.setItems(listData);

            }
               else if (currentSelected != null  && currentType !=-1) {

                    statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable where programName = ?AND type= ? order by substr(fullName,4);");
                    statement.setString(1, currentSelected);
                    statement.setInt(2, currentType);
                    resultSet = retrieve(statement);

                    while (resultSet.next()) {


                        teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                                resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                        listData.add(teacher);
                    }
                teacher_view_table.setItems(listData);

            }
                  else if ( currentGender!= -1 && currentType !=-1) {

                        statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable where gender = ? AND type= ? order by substr(fullName,4);");
                        statement.setInt(1, currentGender);
                        statement.setInt(2, currentType);
                        resultSet = retrieve(statement);

                        while (resultSet.next()) {


                            teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                                    resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                                    resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                            listData.add(teacher);
                        }
                teacher_view_table.setItems(listData);

            }


      else if (currentSelected != null) {

            statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable where programName = ? order by substr(fullName,4);");
            statement.setString(1,add_teacher_program_combo.getSelectionModel().getSelectedItem());
            resultSet = retrieve(statement);

            while (resultSet.next()) {


                teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                        resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                listData.add(teacher);
            }
                teacher_view_table.setItems(listData);


            }else if (currentType != -1){

            statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable where type = ? order by substr(fullName,4);");
            statement.setInt(1,currentType);
            resultSet = retrieve(statement);

            while (resultSet.next()) {


                teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                        resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                listData.add(teacher);
            }
                teacher_view_table.setItems(listData);


            }else if (currentGender != -1){

            statement = DataBaseLayer.connection.prepareStatement("select lectureID,fullName,contactNo,gender,programName,email,type from lecturetable where gender = ? order by substr(fullName,4);");
            statement.setInt(1,currentGender);
            resultSet = retrieve(statement);

            while (resultSet.next()) {


                teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4) == 1 ? "Female" : "Male",
                        resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7) == 1 ? "Visitor" : "Regular");
                listData.add(teacher);
            }
                teacher_view_table.setItems(listData);


            }else {


            teacherListData = teacherListData();


            add_teacher_ID_col.setCellValueFactory(new PropertyValueFactory<>("TeacherID"));
            add_teacher_name_col.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));
            add_teacher_phone_col.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            add_teacher_gender_col.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            add_teacher_program_col.setCellValueFactory(new PropertyValueFactory<>("Department"));
            add_teacher_email_col.setCellValueFactory(new PropertyValueFactory<>("Email"));
            add_teacher_type_col.setCellValueFactory(new PropertyValueFactory<>("Type"));


            teacher_view_table.setItems(teacherListData);
        }
        }catch (SQLException e){

            System.out.println(e.getMessage());
        }


    }
    public void teacherSelect(){



        Teacher teacher = teacher_view_table.getSelectionModel().getSelectedItem();
        int num = teacher_view_table.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};



        add_teacher_name_field.setText(teacher.getTeacherName());
        add_teacher_email_field.setText(teacher.getEmail());
        add_teacher_phone_field.setText(teacher.getPhone());
        add_teacher_ID_field.setText(String.valueOf(teacher.getTeacherID()));
    }
    public void teacherReset(){

        add_teacher_program_combo.getSelectionModel().clearSelection();
        add_teacher_name_field.setText("");
        add_teacher_email_field.setText("");
        add_teacher_phone_field.setText("");
        add_teacher_ID_field.setText("");


        add_teacher_type_combo.getSelectionModel().clearSelection();
       add_teacher_gender_combo.getSelectionModel().clearSelection();

        currentType = -1;
        currentGender = -1;
       currentSelected = null;

    }
    public void addTeacher()
    {


        try {
            if ( add_teacher_phone_field.getText().isEmpty()
            || add_teacher_email_field.getText().isEmpty() || add_teacher_name_field.getText().isEmpty()||
            add_teacher_gender_combo.getSelectionModel().getSelectedItem() == null || add_teacher_gender_combo.getSelectionModel().getSelectedItem() == null
            || add_teacher_type_combo.getSelectionModel().getSelectedItem() == null)  {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;
            }
            else {

                statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM LectureTable WHERE fullName = ?");
                statement.setString(1, add_teacher_name_field.getText());

                // Execute the query to check if the professor already exists
                resultSet = retrieve(statement);

                assert resultSet != null;
                if (resultSet.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Teacher Name: " + add_teacher_name_field.getText() + " was already exist" );
                    alert.showAndWait();

                }
                else {

                    statement = DataBaseLayer.connection.prepareStatement("select programID from programTable where name = ?");
                    statement.setString(1, add_teacher_program_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int programID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programID = resultSet.getInt(1);
                    }
                    statement = DataBaseLayer.connection.prepareStatement("INSERT INTO lecturetable (fullName,contactNo,programID,gender,email,programName,type) VALUES (?,?,?,?,?,?,?)");
                    statement.setString(1, add_teacher_name_field.getText());
                    statement.setString(2,add_teacher_phone_field.getText());
                    statement.setInt(3,programID);
                    statement.setInt(4,add_teacher_gender_combo.getSelectionModel().getSelectedIndex());
                    statement.setString(5,add_teacher_email_field.getText() );
                    statement.setString(6, add_teacher_program_combo.getSelectionModel().getSelectedItem());
                    statement.setInt(7,add_teacher_type_combo.getSelectionModel().getSelectedIndex());
                    insert(statement);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!" );
                    alert.showAndWait();
                    displayTeacherListData();
                }
            }

            add_teacher_name_field.setText("");
            add_teacher_email_field.setText("");
            add_teacher_phone_field.setText("");
            add_teacher_ID_field.setText("");


            add_teacher_type_combo.getSelectionModel().clearSelection();
            add_teacher_gender_combo.getSelectionModel().clearSelection();

            currentType = -1;
            currentGender = -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateTeacher() {

     if ( add_teacher_phone_field.getText().isEmpty()
                || add_teacher_email_field.getText().isEmpty() || add_teacher_name_field.getText().isEmpty()||
                add_teacher_gender_combo.getSelectionModel().getSelectedItem() == null || add_teacher_gender_combo.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        } else {



            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update teacher: " + add_teacher_name_field.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {


                    // Retrieve programID from ProgramTable
                    statement = DataBaseLayer.connection.prepareStatement("select programID from ProgramTable where name = ? limit 1");
                    statement.setString(1, add_teacher_program_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int programID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programID = resultSet.getInt(1);
                    }


                    statement = DataBaseLayer.connection.prepareStatement("UPDATE lectureTable set lectureID = ?, fullName = ? , contactNo = ?, gender = ?,email = ? ,programName = ?, programID = ?, type = ? where lectureID = ?");
                    statement.setString(1, add_teacher_ID_field.getText());
                    statement.setString(2, add_teacher_name_field.getText());
                    statement.setString(3, add_teacher_phone_field.getText());
                    statement.setInt(4, add_teacher_gender_combo.getSelectionModel().getSelectedIndex());
                    statement.setString(5,add_teacher_email_field.getText());
                    statement.setString(6,add_teacher_program_combo.getSelectionModel().getSelectedItem());
                    statement.setInt(7,programID);
                    statement.setInt(8,add_teacher_type_combo.getSelectionModel().getSelectedIndex());

                    statement.setString(9, add_teacher_ID_field.getText());

                    update(statement);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                displayTeacherListData();
                add_teacher_name_field.setText("");
                add_teacher_email_field.setText("");
                add_teacher_phone_field.setText("");
                add_teacher_ID_field.setText("");


                add_teacher_type_combo.getSelectionModel().clearSelection();
                add_teacher_gender_combo.getSelectionModel().clearSelection();


                currentType = -1;
                currentGender = -1;
            }

        }

    }
    public void deleteTeacher(){


         if ( add_teacher_phone_field.getText().isEmpty()
                || add_teacher_email_field.getText().isEmpty() || add_teacher_name_field.getText().isEmpty())
            {


            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        }else {


            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Teacher: " + add_teacher_name_field.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {

                    statement = DataBaseLayer.connection.prepareStatement("delete from lectureTable WHERE  lectureID = ?");
                    statement.setInt(1, Integer.parseInt(add_teacher_ID_field.getText()));
                    delete(statement);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    alert.showAndWait();

                } catch (SQLException e) {

                    throw new RuntimeException(e);
                }

                displayTeacherListData();
                add_teacher_name_field.setText("");
                add_teacher_email_field.setText("");
                add_teacher_phone_field.setText("");
                add_teacher_ID_field.setText("");


                add_teacher_type_combo.getSelectionModel().clearSelection();
                add_teacher_gender_combo.getSelectionModel().clearSelection();


                currentType = -1;
                currentGender = -1;
            }

        }
    }
    public void searchTeacher(){


        FilteredList<Teacher> filter = new FilteredList<>(teacherListData, e -> true);

            search_teacher.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(teacher -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(teacher.getTeacherID()).toLowerCase().contains(searchKey)){
                    return true;
                }else if(teacher.getTeacherName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(teacher.getPhone().toLowerCase().contains(searchKey) ) {
                    return true;
                } else if (teacher.getEmail().toLowerCase().contains(searchKey)){

                    return true;

                }else if(teacher.getDepartment().toLowerCase().contains(searchKey)){
                    return true;
                }else if(teacher.getGender().toLowerCase().contains(searchKey)){
                    return true;
                } else if(teacher.getType().toLowerCase().contains(searchKey)){
                    return true;
                }
                else{

                    return false;
                }


            });
        });

        SortedList<Teacher> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(teacher_view_table.comparatorProperty());
        teacher_view_table.setItems(sortedList);

    }
    public void type(ActionEvent eventType){

        ComboBox co = (ComboBox) eventType.getSource();
        lab_table_view.setDisable(false);
        room_table_view.setDisable(false);



            if (co.getSelectionModel().getSelectedIndex() == 0) {
                lab_table_view.setDisable(true);
                room_table_view.setDisable(false); // Enable the room table view if needed
                add_room_ID_label.setText("Room ID: ");
                add_room_No_label.setText("Room No: ");

                // Remove any previous event handler to avoid stacking them
                add_room_lab_combo.setOnAction(null);
                add_room_lab_combo.setOnAction(event -> displayRoomListData());
            } else if (co.getSelectionModel().getSelectedIndex() == 1) {
                room_table_view.setDisable(true);
                lab_table_view.setDisable(false); // Enable the lab table view if needed
                add_room_ID_label.setText("Lab ID: ");
                add_room_No_label.setText("Lab No: ");

                // Remove any previous event handler to avoid stacking them
                add_room_lab_combo.setOnAction(null);
                add_room_lab_combo.setOnAction(event -> displayLabListData());
            }



        add_room_lab_combo.setDisable(false);
        add_room_capacity_field.setEditable(true);
        add_room_No_field.setEditable(true);
        flag =  co.getSelectionModel().getSelectedIndex();

    }
    public ObservableList<Room> roomListData()  {

        ObservableList<Room>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        Room room;
        try {
            statement = DataBaseLayer.connection.prepareStatement("select roomNo,roomID,programName,capacity from roomTable;");
            resultSet = retrieve(statement);

            while (resultSet.next()){


                room = new Room(resultSet.getString(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getInt(4));

                listData.add(room);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listData;


    }
    public void displayRoomListData(){

        currentSelected = add_room_lab_combo.getSelectionModel().getSelectedItem();

        System.out.println(currentSelected);

        ObservableList<Room> listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        if (currentSelected != null) {
            Room room;
            try {
                statement = DataBaseLayer.connection.prepareStatement("select roomNo,roomID,programName,capacity from roomTable where programName = ?;");
                statement.setString(1,currentSelected);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    room = new Room(resultSet.getString(1), resultSet.getInt(2),
                            resultSet.getString(3), resultSet.getInt(4));

                    listData.add(room);
                }
                room_table_view.setItems(listData);



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else {

            roomListData = roomListData();
            roomID_col.setCellValueFactory(new PropertyValueFactory<>("roomID"));
            roomNo_col.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
            room_capacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            room_program_col.setCellValueFactory(new PropertyValueFactory<>("Department"));
            room_table_view.setItems(roomListData);

        }


    }
    public void roomSelect(){



        Room room = room_table_view.getSelectionModel().getSelectedItem();
        int num = room_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};



        add_room_ID_field.setText(String.valueOf(room.getRoomID()));
        add_room_capacity_field.setText(String.valueOf(room.getCapacity()));
        add_room_No_field.setText(room.getRoomNo());

    }
    public void roomReset(){


        add_room_No_field.setText("");
        add_room_capacity_field.setText("");
        add_room_ID_field.setText("");
        add_room_lab_combo.getSelectionModel().clearSelection();
        currentSelected = null;
    }
    public void labSelect(){



        Lab lab = lab_table_view.getSelectionModel().getSelectedItem();
        int num = lab_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};



        add_room_ID_field.setText(String.valueOf(lab.getLabID()));
        add_room_capacity_field.setText(String.valueOf(lab.getCapacity()));
        add_room_No_field.setText(lab.getLabNo());
    }
    public ObservableList<Lab> labListData()  {

        ObservableList<Lab>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        Lab lab;
        try {
            statement = DataBaseLayer.connection.prepareStatement("select labNo,labID,programName,capacity from labTable;");
            resultSet = retrieve(statement);

            while (resultSet.next()){


                lab = new Lab(resultSet.getString(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getInt(4));
                listData.add(lab);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listData;


    }
    public void displayLabListData(){


        currentSelected = add_room_lab_combo.getSelectionModel().getSelectedItem();
        ObservableList<Lab>  listData = FXCollections.observableArrayList();

        if (currentSelected != null) {


            Lab lab;
            try {
                statement = DataBaseLayer.connection.prepareStatement("select labNo,labID,programName,capacity from labTable where programName = ?;");
                statement.setString(1,currentSelected);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    lab = new Lab(resultSet.getString(1), resultSet.getInt(2),
                            resultSet.getString(3), resultSet.getInt(4));
                    listData.add(lab);
                }
                lab_table_view.setItems(listData);



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {


            labListData = labListData();
            labID_col.setCellValueFactory(new PropertyValueFactory<>("labID"));
            labNo_col.setCellValueFactory(new PropertyValueFactory<>("labNo"));
            lab_capacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            lab_program_col.setCellValueFactory(new PropertyValueFactory<>("Department"));


            lab_table_view.setItems(labListData);
        }

    }


    public void addRoom()
    {


        try {
            if (add_room_No_field.getText().isEmpty() || add_room_lab_combo.getSelectionModel().getSelectedItem() ==null ||
            add_room_lab_type_combo.getSelectionModel().getSelectedItem() == null || add_room_capacity_field.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;
            }
            else {

                if (flag == 0){
                    statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM RoomTable WHERE roomNo = ? and programName = ?");


                }else{

                    statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM LabTable WHERE labNo = ? and programName = ?");


                }

                statement.setString(1, add_room_No_field.getText());
                statement.setString(2,add_room_lab_combo.getSelectionModel().getSelectedItem());

                // Execute the query to check if the professor already exists
                resultSet = retrieve(statement);

                assert resultSet != null;
                if (resultSet.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room/Lab: " + add_room_No_field.getText()+ " was already exist" );
                    alert.showAndWait();

                }
                else {

                    int capacity = 0;

                    try {
                        capacity = Integer.parseInt(add_room_capacity_field.getText());

                        // Check for negative or zero capacity
                        if (capacity <= 0) {
                            throw new NumberFormatException("Capacity must be a positive integer.");
                        }

                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter a valid positive integer for capacity.");
                        alert.showAndWait();
                        return;
                    }


                    statement = DataBaseLayer.connection.prepareStatement("select programID from programTable where name = ?");
                    statement.setString(1, add_room_lab_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int programID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {

                        programID = resultSet.getInt(1);

                    }

                    if (flag == 0){

                        statement = DataBaseLayer.connection.prepareStatement("INSERT INTO roomTable (roomNo,capacity,programID,programName) VALUES (?,?,?,?)");


                    }else{
                        statement = DataBaseLayer.connection.prepareStatement("INSERT INTO labTable (labNo,capacity,programID,programName) VALUES (?,?,?,?)");

                    }

                    statement.setString(1, add_room_No_field.getText());
                    statement.setInt(2, Integer.parseInt(add_room_capacity_field.getText()));
                    statement.setInt(3,programID);
                    statement.setString(4,add_room_lab_combo.getSelectionModel().getSelectedItem());
                    insert(statement);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!" );
                    alert.showAndWait();
                    displayRoomListData();
                    displayLabListData();
                }
            }

            add_room_No_field.setText("");
            add_room_capacity_field.setText("");
            add_room_ID_field.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void updateRoom() {




         if (add_room_ID_field.getText().isEmpty() || add_room_No_field.getText().isEmpty() || add_room_lab_combo.getSelectionModel().getSelectedItem() ==null ||
                add_room_lab_type_combo.getSelectionModel().getSelectedItem() == null || add_room_capacity_field.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        } else {

            try {


                if (flag == 0) {
                    statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM RoomTable WHERE roomNo = ? and programName = ?");


                } else {

                    statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM LabTable WHERE labNo = ? and programName = ?");


                }

                statement.setString(1, add_room_No_field.getText());
                statement.setString(2, add_room_lab_combo.getSelectionModel().getSelectedItem());

                // Execute the query to check if the professor already exists
                resultSet = retrieve(statement);

                assert resultSet != null;
                if (resultSet.next()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room/Lab: " + add_room_No_field.getText() + " was already exist");
                    alert.showAndWait();

                }
            }catch (SQLException e){

                System.out.println(e.getMessage());
            }
             int capacity = 0;

             try {
                 capacity = Integer.parseInt(add_semester_capacity_field.getText());

             } catch (NumberFormatException e) {

                 alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Error Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Please enter a valid integer for capacity.");
                 alert.showAndWait();
                 return;
             }

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            if (flag == 0){
                alert.setContentText("Are you sure you want to update room: " + add_room_No_field.getText() + "?");

            }else{

                alert.setContentText("Are you sure you want to update lab: " + add_room_No_field.getText() + "?");

            }
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {


                    // Retrieve programID from ProgramTable
                    statement = DataBaseLayer.connection.prepareStatement("select programID from ProgramTable where name = ? limit 1");
                    statement.setString(1, add_room_lab_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int programID = 0;
                    assert resultSet != null;
                    if (resultSet.next()) {
                        programID = resultSet.getInt(1);
                    }

                    if (flag == 0){

                        statement = DataBaseLayer.connection.prepareStatement("UPDATE RoomTable set roomNo = ?, capacity = ? , programID = ?, programName = ? where roomID = ?");

                    }else{

                        statement = DataBaseLayer.connection.prepareStatement("UPDATE LabTable set labNo = ?, capacity = ? , programID = ?, programName = ? where labID = ?");

                    }


                    statement.setString(1, add_room_No_field.getText());
                    statement.setInt(2, Integer.parseInt(add_room_capacity_field.getText()));
                    statement.setInt(3, programID);
                    statement.setString(4,add_room_lab_combo.getSelectionModel().getSelectedItem());
                    statement.setInt(5, Integer.parseInt(add_room_ID_field.getText()));

                    update(statement);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                displayLabListData();
                displayRoomListData();
                add_room_No_field.setText("");
                add_room_capacity_field.setText("");
                add_room_ID_field.setText("");
            }

        }

    }
    public void deleteRoom(){



         if (add_room_ID_field.getText().isEmpty() || add_room_No_field.getText().isEmpty() || add_room_capacity_field.getText().isEmpty() || add_room_lab_type_combo.getSelectionModel().getSelectedItem() == null)
        {


            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blanks fields");
            alert.showAndWait();
            return;

        }else {


            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            System.out.println(flag);
            if (add_room_lab_type_combo.getSelectionModel().getSelectedIndex() == 0){
                alert.setContentText("Are you sure you want to delete room: " + add_room_No_field.getText() + "?");

            }else{

                alert.setContentText("Are you sure you want to delete lab: " + add_room_No_field.getText() + "?");

            }
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                try {


                    System.out.println(add_room_lab_type_combo.getSelectionModel().getSelectedItem());

                    if (add_room_lab_type_combo.getSelectionModel().getSelectedIndex() == 0){

                        statement = DataBaseLayer.connection.prepareStatement("delete from roomTable WHERE  roomID = ?");
                        statement.setInt(1, Integer.parseInt(add_room_ID_field.getText()));
                       if (delete(statement)){

                           System.out.println("Successfully Delteted");
                       }else{

                           System.out.println("Not deleted");
                       }


                    }else{

                        statement = DataBaseLayer.connection.prepareStatement("delete from labTable WHERE  labID = ?");

                        statement.setInt(1, Integer.parseInt(add_room_ID_field.getText()));
                        delete(statement);


                    }




                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    alert.showAndWait();

                } catch (SQLException e) {

                    throw new RuntimeException(e);
                }

               displayRoomListData();
                displayLabListData();
                add_room_No_field.setText("");
                add_room_capacity_field.setText("");
                add_room_ID_field.setText("");
            }

        }
    }
    public void searchRoom(){


        FilteredList<Room> filter = new FilteredList<>(roomListData, e -> true);

        room_lab_search.textProperty().addListener((observable, oldValue, newValue) -> {

            filter.setPredicate(room -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(room.getRoomID()).toLowerCase().contains(searchKey)  ){
                    return true;
                }else if(room.getRoomNo().toLowerCase().contains(searchKey)){
                    return true;
                }else if(String.valueOf(room.getCapacity()).toLowerCase().contains(searchKey) ) {
                    return true;
                } else if (room.getDepartment().toLowerCase().contains(searchKey)){

                    return true;
                }
                else{

                    return false;
                }


            });

        });

        SortedList<Room> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(room_table_view.comparatorProperty());
        room_table_view.setItems(sortedList);

        FilteredList<Lab> labFilter = new FilteredList<>(labListData, e -> true);


        room_lab_search.textProperty().addListener((observable, oldValue, newValue) -> {

            labFilter.setPredicate(lab -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(lab.getLabID()).toLowerCase().contains(searchKey)  ){
                    return true;
                }else if(lab.getLabNo().toLowerCase().contains(searchKey)){
                    return true;
                }else if(String.valueOf(lab.getCapacity()).toLowerCase().contains(searchKey) ) {
                    return true;
                } else if (lab.getDepartment().toLowerCase().contains(searchKey)){

                    return true;
                }
                else{

                    return false;
                }


            });

        });

        SortedList<Lab> sortedListLab = new SortedList<>(labFilter);
        sortedListLab.comparatorProperty().bind(lab_table_view.comparatorProperty());
        lab_table_view.setItems(sortedListLab);

    }


    public ObservableList<AllocateCourse> allocateCourseListData()  {

        ObservableList<AllocateCourse>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        AllocateCourse allocateCourse;
        try {

            statement = DataBaseLayer.connection.prepareStatement("select programsemestersubjectviewID,title,lab,section from programsemestersubjecttableview order by SUBSTRING(title, 4, 1) ");

            resultSet = retrieve(statement);

            while (resultSet.next()){


                allocateCourse = new AllocateCourse(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4));
                listData.add(allocateCourse);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listData;


    }
    public void displayAllocateCourseListData(){

        ObservableList<AllocateCourse>  listData = FXCollections.observableArrayList();

        AllocateCourse allocateCourse;
        currentSelected = allocate_program_combo.getSelectionModel().getSelectedItem();
        currentSemester = allocate_semester_combo.getSelectionModel().getSelectedItem();
        System.out.println(currentSemester);
        currentSection = allocate_course_section_combo.getSelectionModel().getSelectedItem();
        String lab = allocate_lab_combo.getSelectionModel().getSelectedItem();


        try{

            if (currentSection != null && currentSemester != null && currentSelected != null) {


                statement = DataBaseLayer.connection.prepareStatement("select programsemestersubjectviewID,title,lab,section from programsemestersubjecttableview where programName = ? and section = ? and semester = ? order by SUBSTRING(title, 4, 1) ");
                statement.setString(1,currentSelected);
                statement.setString(2,currentSection);
                statement.setString(3,currentSemester);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    allocateCourse = new AllocateCourse(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4));
                    listData.add(allocateCourse);
                }
                allocate_course_table_view.setItems(listData);
                statement = DataBaseLayer.connection.prepareStatement("select programSemesterID from programSemesterTable where title = ? and timeTableTypeID = ? limit 1");
                statement.setString(1, allocate_semester_combo.getSelectionModel().getSelectedItem());
                statement.setInt(2,(allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1));
                resultSet = retrieve(statement);

                int programSemesterID = 0;
                assert resultSet != null;
                if (resultSet.next()) {

                    programSemesterID = resultSet.getInt(1);

                }

                statement = DataBaseLayer.connection.prepareStatement("select totalCreditHours from programSemesterTable where programSemesterID = ? limit 1");
                statement.setInt(1, programSemesterID);
                resultSet = retrieve(statement);

                semesterCreditHours = 0;
                assert resultSet != null;
                if (resultSet.next()) {

                    semesterCreditHours = resultSet.getInt(1);

                }
                statement = DataBaseLayer.connection.prepareStatement("select sum(creditHours) from all_subjects_view where programSemesterID = ? limit 1");
                statement.setInt(1, programSemesterID);
                resultSet = retrieve(statement);


                totalCreditHours = 0 ;
                assert resultSet != null;
                if (resultSet.next()) {

                    totalCreditHours = resultSet.getInt(1);

                }

                String semesterName = allocate_semester_combo.getSelectionModel().getSelectedItem();
                if ((allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1) == 2){

                    semesterName += " (Replica)";

                }

                allocated_course_semester_title_header.setText(semesterName);
                allocated_course_allocated_hours_header.setText(String.valueOf(totalCreditHours));
                allocated_course_total_credit_hours_header.setText(String.valueOf(semesterCreditHours));


            }


           else if (currentSelected != null && currentSemester != null ){


                statement = DataBaseLayer.connection.prepareStatement("select programsemestersubjectviewID,title,lab,section from programsemestersubjecttableview where programName = ?  and semester = ? order by SUBSTRING(title, 4, 1) ");
                statement.setString(1,currentSelected);
                statement.setString(2,currentSemester);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    allocateCourse = new AllocateCourse(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4));
                    listData.add(allocateCourse);
                }
                allocate_course_table_view.setItems(listData);

            }
           else if (currentSelected != null && currentSection != null ) {


                statement = DataBaseLayer.connection.prepareStatement("select programsemestersubjectviewID,title,lab,section from programsemestersubjecttableview where programName = ?  and section = ? order by SUBSTRING(title, 4, 1) ");
                statement.setString(1, currentSelected);
                statement.setString(2, currentSection);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    allocateCourse = new AllocateCourse(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4));
                    listData.add(allocateCourse);
                }
                allocate_course_table_view.setItems(listData);

            }
            else if (currentSemester != null) {

                statement = DataBaseLayer.connection.prepareStatement("select programsemestersubjectviewID,title,lab,section from programsemestersubjecttableview where semester = ? order by SUBSTRING(title, 4, 1) ");
                statement.setString(1,currentSemester);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    allocateCourse = new AllocateCourse(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4));
                    listData.add(allocateCourse);
                }
                allocate_course_table_view.setItems(listData);

            }else if (currentSection != null){

                statement = DataBaseLayer.connection.prepareStatement("select programsemestersubjectviewID,title,lab,section from programsemestersubjecttableview where Section = ? order by SUBSTRING(title, 4, 1) ");
                statement.setString(1,currentSection);
                resultSet = retrieve(statement);

                while (resultSet.next()) {


                    allocateCourse = new AllocateCourse(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4));
                    listData.add(allocateCourse);
                }
                allocate_course_table_view.setItems(listData);
            }

            else if (currentSelected != null) {

            statement = DataBaseLayer.connection.prepareStatement("select programsemestersubjectviewID,title,lab,section from programsemestersubjecttableview where programName = ? order by SUBSTRING(title, 4, 1) ");
            statement.setString(1, currentSelected);
            resultSet = retrieve(statement);

            while (resultSet.next()) {


                allocateCourse = new AllocateCourse(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4));
                listData.add(allocateCourse);
            }
            allocate_course_table_view.setItems(listData);




        }

         else

         {


            allocateCourseListData = allocateCourseListData();
            allocate_course_ID_col.setCellValueFactory(new PropertyValueFactory<>("SubjectID"));
            allocate_course_title_col.setCellValueFactory(new PropertyValueFactory<>("SubjectTitle"));
            allocate_lab_col.setCellValueFactory(new PropertyValueFactory<>("Lab"));
            allocate_section_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            allocate_course_table_view.setItems(allocateCourseListData);

             allocated_course_semester_title_header.setText("");
             allocated_course_allocated_hours_header.setText(String.valueOf(""));
             allocated_course_total_credit_hours_header.setText(String.valueOf(""));
            
        }





    }catch (SQLException e){

                System.out.println(e.getMessage());
            }


    }

    public void allocateSelect(){



        AllocateCourse allocateCourse = allocate_course_table_view.getSelectionModel().getSelectedItem();
        int num = allocate_course_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {return ;};



        allocate_subject_ID_field.setText(String.valueOf(allocateCourse.getSubjectID()));

    }
    public void allocateReset(){


        allocate_subject_ID_field.setText("");
        allocate_program_combo.getSelectionModel().clearSelection();
        allocate_course_combo.getSelectionModel().clearSelection();
        allocate_semester_combo.getSelectionModel().clearSelection();
        allocate_teacher_type_combo.getSelectionModel().clearSelection();
        allocate_course_teacher_combo.getSelectionModel().clearSelection();
        allocate_course_section_combo.getSelectionModel().clearSelection();
        currentSection = null;
        currentSemester = null;
        currentSelected = null;

    }


    public void addAllocateCourse(){



        try {
            if (allocate_course_section_combo.getSelectionModel().getSelectedItem() == null || allocate_program_combo.getSelectionModel().getSelectedItem() == null ||
                    allocate_course_combo.getSelectionModel().getSelectedItem() == null ||
                    allocate_semester_combo.getSelectionModel().getSelectedItem() == null ||
                    allocate_course_teacher_combo.getSelectionModel().getSelectedItem() == null ||
                    (allocate_lab_combo.getSelectionModel().getSelectedItem() == null && !allocate_lab_combo.isDisable()) ||
                    allocate_teacher_type_combo.getSelectionModel().getSelectedItem() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;

            }
            else {


                String selectedItem = allocate_semester_combo.getSelectionModel().getSelectedItem();
                int endIndex = selectedItem.indexOf("Semester") + "Semester".length();
                String substring = selectedItem.substring(0, endIndex);

                statement = DataBaseLayer.connection.prepareStatement("select lectureID from lectureTable where fullName = ?");
                statement.setString(1, allocate_course_teacher_combo.getSelectionModel().getSelectedItem());
                resultSet = retrieve(statement);

                int lectureID = 0;

                if (resultSet.next()){

                    lectureID = resultSet.getInt(1);
                }
                statement = DataBaseLayer.connection.prepareStatement("select programID from ProgramTable where name = ? limit 1");
                statement.setString(1, allocate_program_combo.getSelectionModel().getSelectedItem());
                resultSet = retrieve(statement);

                int programID = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    programID = resultSet.getInt(1);
                }



                statement = DataBaseLayer.connection.prepareStatement("select sum(creditHours) as Sum from lecture_view where lectureID = ?");
                statement.setInt(1, lectureID);
                resultSet = retrieve(statement);


                int totalCreditHours = 0;
                if (resultSet.next()){

                    totalCreditHours = resultSet.getInt(1);

                }
                statement = DataBaseLayer.connection.prepareStatement("select courseID from CourseView where courseTitle = ?");
                statement.setString(1, allocate_course_combo.getSelectionModel().getSelectedItem());
                resultSet = retrieve(statement);

                int courseViewID = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    courseViewID = resultSet.getInt(1);
                }


                statement = DataBaseLayer.connection.prepareStatement("select sum(crHrs)  from courseTable where courseViewID = ?");
                statement.setInt(1, courseViewID);
                resultSet = retrieve(statement);

                int a = 0;
                if (resultSet.next()){

                    a = resultSet.getInt(1);

                }

                if ((totalCreditHours + a ) > 12){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("A teacher cannot be allocated more than 12 credit hours");
                    alert.showAndWait();
                    return;

                }

                statement = DataBaseLayer.connection.prepareStatement("select courseID from courseTable where courseViewID = ? top 1");
                statement.setInt(1, courseViewID);

                resultSet = retrieve(statement);

                int courseID = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    courseID = resultSet.getInt(1);
                }
                statement = DataBaseLayer.connection.prepareStatement("select count(*) from courseTable where title = ?");
                statement.setString(1, allocate_course_combo.getSelectionModel().getSelectedItem());
                resultSet = retrieve(statement);

                int courseCount = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    courseCount = resultSet.getInt(1);
                }



                statement = DataBaseLayer.connection.prepareStatement("select programSemesterID from programSemesterTable where title = ? and timeTableTypeID = ? limit 1");
                statement.setString(1,substring);
                statement.setInt(2,(allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1));
                resultSet = retrieve(statement);

                int programSemesterID = 0;
                assert resultSet != null;
                if (resultSet.next()) {
                    programSemesterID = resultSet.getInt(1);
                }



                // Check if the course already exists

                statement = DataBaseLayer.connection.prepareStatement("SELECT lectureSubjectID FROM lectureSubjectTable  where lectureID = ? AND courseID = ?");
                statement.setInt(1,lectureID );
                statement.setInt(2, courseID);
                resultSet = retrieve(statement);

                int b = 0 ;

                if (resultSet.next()){

                    b = resultSet.getInt(1);

                }

                statement = DataBaseLayer.connection.prepareStatement("SELECT courseID,programSemesterID FROM lectureSubjectTable  where  courseID = ? AND programSemesterID = ?");
                statement.setInt(1, courseID);
                statement.setInt(2,programSemesterID);
                resultSet = retrieve(statement);


                if (resultSet.next()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Subject : " + allocate_semester_combo.getSelectionModel().getSelectedItem() + " " + allocate_course_combo.getSelectionModel().getSelectedItem()  + " was already allocated");
                    alert.showAndWait();
                    return;
                }


                statement = DataBaseLayer.connection.prepareStatement("select * from ProgramSemesterSubjectTable where programSemesterID = ? AND title = ? and lectureSubjectID = ? and timetableTypeID = ? " );

                statement.setInt(1, programSemesterID);
                statement.setString(2,substring +" "+ allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                statement.setInt(3,b);
                statement.setInt(4,(allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1));
                resultSet = retrieve(statement);



                if (resultSet.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Subject : "+ allocate_program_combo.getSelectionModel().getSelectedItem() +" "+ allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")" + " was already exist" );
                    alert.showAndWait();

                }else {

                    assert resultSet != null;
                    statement = DataBaseLayer.connection.prepareStatement("SELECT labID FROM labTable  where labNo = ? AND programName = ?");
                    statement.setString(1,allocate_lab_combo.getSelectionModel().getSelectedItem());
                    statement.setString(2, allocate_program_combo.getSelectionModel().getSelectedItem());
                    resultSet = retrieve(statement);

                    int labID = 0 ;

                    if (resultSet.next()){

                        labID = resultSet.getInt(1);

                    }



                    statement = DataBaseLayer.connection.prepareStatement("insert into ProgramSemesterSubjectTableView(title,lab,section,timeTableTypeID,programName,semester) values(?,?,?,?,?,?)");
                    statement.setString(1,substring +" "+ allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                    statement.setString(2,allocate_lab_combo.getSelectionModel().getSelectedItem());
                    statement.setString(3, ((allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1) == 1) ? "Morning" : "Replica");
                    statement.setInt(4,(allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1));
                    statement.setString(5,allocate_program_combo.getSelectionModel().getSelectedItem());
                    statement.setString(6,allocate_semester_combo.getSelectionModel().getSelectedItem());
                    insert(statement);
                    statement = DataBaseLayer.connection.prepareStatement("SELECT programsemestersubjectviewID FROM ProgramSemesterSubjectTableView  where title = ? and timeTableTypeID = ? limit 1");
                    statement.setString(1,substring +" "+ allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                    statement.setInt(2,(allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1));
                    resultSet = retrieve(statement);

                    int ID = 0 ;

                    if (resultSet.next()){

                        ID = resultSet.getInt(1);

                    }
                    statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM lectureSubjectTable WHERE title = ? AND courseID = ? AND programSemesterID = ?");
                    statement.setString(1, allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                    statement.setInt(2, courseID);
                    statement.setInt(3, programSemesterID);
                    resultSet = retrieve(statement);

                    if (!resultSet.next()) {

                        statement = DataBaseLayer.connection.prepareStatement("insert into LectureSubjectTable (title,lectureID,courseID,programSemesterID,programSemesterSubjectViewID) values(?,?,?,?,?)");
                        statement.setString(1, allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                        statement.setInt(2, lectureID);
                        statement.setInt(3, courseID);
                        statement.setInt(4, programSemesterID);
                        statement.setInt(5,ID);
                        insert(statement);

                        if (courseCount > 1){

                            statement = DataBaseLayer.connection.prepareStatement("select courseID from courseTable where courseViewID = ? and roomTypeID = ?");
                            statement.setInt(1, courseViewID);
                            statement.setInt(2, 4);

                            resultSet = retrieve(statement);

                             courseID = 0;
                            assert resultSet != null;
                            if (resultSet.next()) {
                                courseID = resultSet.getInt(1);
                            }

                            statement = DataBaseLayer.connection.prepareStatement("insert into LectureSubjectTable (title,lectureID,courseID,programSemesterID,programSemesterSubjectViewID) values(?,?,?,?,?)");
                            statement.setString(1, allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                            statement.setInt(2, lectureID);
                            statement.setInt(3, courseID);
                            statement.setInt(4, programSemesterID);
                            statement.setInt(5,ID);
                            insert(statement);

                        }

                    }


                    if (courseCount > 1){

                        statement = DataBaseLayer.connection.prepareStatement("insert into ProgramSemesterSubjectTable(programSemesterID,lectureSubjectID,title,labID,lab,programsemestersubjectviewID,timetableTypeID,programID) values(?,((select  Max(lectureSubjectID) from lecturesubjecttable ) - 1),?,?,?,?,?,?)");
                        statement.setInt(1, programSemesterID);
                        statement.setString(2,substring +" "+ allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                        statement.setInt(3,0);
                        statement.setString(4,allocate_lab_combo.getSelectionModel().getSelectedItem());
                        statement.setInt(5,ID);
                        statement.setInt(6,(allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1));
                        statement.setInt(7,programID);
                        insert(statement);

                    }

                    statement = DataBaseLayer.connection.prepareStatement("insert into ProgramSemesterSubjectTable(programSemesterID,lectureSubjectID,title,labID,lab,programsemestersubjectviewID,timetabletypeID,programID ) values(?,(select  Max(lectureSubjectID) from lecturesubjecttable ),?,?,?,?,?,?)");
                    statement.setInt(1, programSemesterID);
                    statement.setString(2,substring +" "+ allocate_course_combo.getSelectionModel().getSelectedItem() + " (" + allocate_course_teacher_combo.getSelectionModel().getSelectedItem() + ")");
                    statement.setInt(3,labID);
                    statement.setString(4,allocate_lab_combo.getSelectionModel().getSelectedItem());
                    statement.setInt(5,ID);
                    statement.setInt(6,(allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1));
                    statement.setInt(7,programID);
                    insert(statement);


                }
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        displayAllocateCourseListData();
        allocate_subject_ID_field.setText("");
        allocate_teacher_type_combo.getSelectionModel().clearSelection();
        allocate_course_teacher_combo.getSelectionModel().clearSelection();
        allocate_lab_combo.getSelectionModel().clearSelection();
        allocate_course_combo.getSelectionModel().clearSelection();

    }

    public void allocateSemesterCombo() {

        List<String> list = new ArrayList<>();

        int section = (allocate_course_section_combo.getSelectionModel().getSelectedIndex() + 1);

        try {


            statement = DataBaseLayer.connection.prepareStatement("SELECT  title FROM ProgramSemesterTable WHERE programName = ? and timeTableTypeID = ? ORDER BY SUBSTRING(title, 4, 1);");
            statement.setString(1, allocate_program_combo.getSelectionModel().getSelectedItem());
            statement.setInt(2, section);
            resultSet = retrieve(statement);

            while (resultSet.next()) {


                list.add(resultSet.getString(1));

            }

            allocate_semester_combo.setItems(FXCollections.observableArrayList(list));
        }catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void deleteAllocateCourse(){

        AllocateCourse allocateCourse = allocate_course_table_view.getSelectionModel().getSelectedItem();
        int num = allocate_course_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1 ) < -1) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Select the Record to delete");
            alert.showAndWait();
            return;
        }

        try {

            if (allocate_subject_ID_field.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
                return;

            }
            else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Subject this subject ?" );

                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {


                    statement = DataBaseLayer.connection.prepareStatement("delete from lectureSubjectTable where programSemesterSubjectViewID = ?");
                    statement.setInt(1, Integer.parseInt(allocate_subject_ID_field.getText()));
                    delete(statement);

                    statement = DataBaseLayer.connection.prepareStatement("delete from programSemesterSubjectTableview where programSemesterSubjectviewID = ?");
                    statement.setInt(1, Integer.parseInt(allocate_subject_ID_field.getText()));

                    delete(statement);

                    statement = DataBaseLayer.connection.prepareStatement("delete from programSemesterSubjectTable where programsemestersubjectviewID = ?");
                    statement.setInt(1, Integer.parseInt(allocate_subject_ID_field.getText()));

                    delete(statement);


                }


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Deleted Successfully!");
                alert.showAndWait();

            }

            }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }



        displayAllocateCourseListData();
        allocate_subject_ID_field.setText("");
        allocate_teacher_type_combo.getSelectionModel().clearSelection();
        allocate_course_teacher_combo.getSelectionModel().clearSelection();
        allocate_lab_combo.getSelectionModel().clearSelection();
        allocate_course_combo.getSelectionModel().clearSelection();



    }


    public void searchAllocateCourse(){


        FilteredList<AllocateCourse> filter = new FilteredList<>(allocateCourseListData, e -> true);

        search_allocate.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(allocate -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(allocate.getSubjectID()).toLowerCase().contains(searchKey)){
                    return true;
                }else if(allocate.getSubjectTitle().toLowerCase().contains(searchKey)){
                    return true;
                }else if(allocate.getLab() != null && allocate.getLab().toLowerCase().contains(searchKey.toLowerCase())) {
                    return true;
                } else if (allocate.getType().toLowerCase().contains(searchKey)) {

                    return true;

                }
                else{

                    return false;
                }


            });
        });

        SortedList<AllocateCourse> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(allocate_course_table_view.comparatorProperty());
        allocate_course_table_view.setItems(sortedList);

    }

    public ObservableList<GenerateTimeTable> timeTableListData()  {

        ObservableList<GenerateTimeTable>  listData = FXCollections.observableArrayList();
        connection = DataBaseLayer.connect();

        GenerateTimeTable generateTimeTable;
        try {
            statement = DataBaseLayer.connection.prepareStatement("Select timeTableID,dayTitle,slotTitle,subjectTitle from timetabledetailstable order by timeTableID,dayTitle,slotTitle");
            resultSet = retrieve(statement);

            while (resultSet.next()){

                generateTimeTable = new GenerateTimeTable(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),resultSet.getString(4));

                listData.add(generateTimeTable);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;


    }


    public void displayGenerateTimeTableListData(){

        timeTableListData =  timeTableListData() ;
        time_table_ID_col.setCellValueFactory(new PropertyValueFactory<>("timeTableID"));
        time_table_day_col.setCellValueFactory(new PropertyValueFactory<>("day"));
        time_table_slot_col.setCellValueFactory(new PropertyValueFactory<>("slot"));
        time_table_subject_col.setCellValueFactory(new PropertyValueFactory<>("title"));

        time_table_view_table.setItems(timeTableListData);

    }

    public void searchTimeTable(){


        FilteredList<GenerateTimeTable> filter = new FilteredList<>(timeTableListData, e -> true);

        search_time_table.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(timeTable -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Check if the program ID contains the search key
                if (String.valueOf(timeTable.getTimeTableID()).toLowerCase().contains(searchKey)){
                    return true;
                }else if(timeTable.getDay().toLowerCase().contains(searchKey)){
                    return true;
                }else if(timeTable.getSlot() != null && timeTable.getSlot().toLowerCase().contains(searchKey.toLowerCase())) {
                    return true;
                } else if (timeTable.getTitle().toLowerCase().contains(searchKey)) {

                    return true;

                }
                else{

                    return false;
                }


            });
        });

        SortedList<GenerateTimeTable> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(time_table_view_table.comparatorProperty());
        time_table_view_table.setItems(sortedList);

    }

    @FXML
    public void generate(ActionEvent event) {

        // Preflight: catch every missing prerequisite up front and show them
        // together, instead of letting the stored procedure abort on the first
        // one (or, for an under-constrained class, spin).
        if (guidance != null && !guidance.confirmGenerate()) {
            return;
        }

        progressIndicator.setVisible(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                    rootWindow.setDisable(true);
                });

                generateTimetable();

                return null;
            }

            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    progressIndicator.setProgress(1);
                    rootWindow.setDisable(false);

                });
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    rootWindow.setDisable(false);
                    progressIndicator.setVisible(false);
                    progressIndicator.setProgress(0);
                });
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void generateTimetable() {

        DataBaseLayer.disconnect();
        DataBaseLayer.connect();

        try {

            Connection connection = DataBaseLayer.getConnection();
            String storedProcedureCall = "{CALL GenerateTimeTableForAllSessions(?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.execute();

            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Time Table Generated Successfully....");
                alert.showAndWait();
                displayGenerateTimeTableListData();
            });

        } catch (SQLException e) {

            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            });
        }
    }

    public void reset_btn(){

        try {


            statement = DataBaseLayer.connection.prepareStatement("delete from timetabledetailstable");
            delete(statement);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Time Tables Reset Successfully....");
            alert.showAndWait();

        }catch (SQLException e){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
        displayGenerateTimeTableListData();



    }



}


