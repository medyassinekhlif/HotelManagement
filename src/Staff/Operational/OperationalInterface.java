package Staff.Operational;

import Main.MainInterface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class OperationalInterface extends Application {

    private static final OperationalDAL operationalDAL = new OperationalDAL();
    private Scene initialScene;
    private static final String stylePath = "file:resources/styles.css";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Operational Management System");

        // Set the width and height of the stage
        stage.setWidth(800);
        stage.setHeight(600);

        VBox mainLayout = createMainLayout(stage);

        // Set the same background as MainInterface
        setBackground(mainLayout);

        initialScene = new Scene(mainLayout);
        initialScene.getStylesheets().add(stylePath);
        stage.setScene(initialScene);
        stage.show();
    }

    private VBox createMainLayout(Stage stage) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        Label title = new Label("Operational Management System");

        Button addOperationalButton = new Button("Add Operational");
        Button viewOperationalsButton = new Button("View Operationals");
        Button updateOperationalButton = new Button("Update Operational");
        Button deleteOperationalButton = new Button("Delete Operational");
        Button backButton = new Button("Back");

        addOperationalButton.setOnAction(e -> addOperational(stage));
        viewOperationalsButton.setOnAction(e -> viewOperationals(stage));
        updateOperationalButton.setOnAction(e -> updateOperational(stage));
        deleteOperationalButton.setOnAction(e -> deleteOperational(stage));
        backButton.setOnAction(e -> goBack(stage));

        mainLayout.getChildren().addAll(title, addOperationalButton, viewOperationalsButton,
                updateOperationalButton, deleteOperationalButton, backButton);
        return mainLayout;
    }

    private void addOperational(Stage stage) {
        GridPane gridPane = createFormPane();

        TextField staffIdField = createTextField(gridPane, "Staff ID:", 0, 0);
        TextField firstNameField = createTextField(gridPane, "First Name:", 0, 1);
        TextField lastNameField = createTextField(gridPane, "Last Name:", 1, 0);
        TextField emailField = createTextField(gridPane, "Email:", 1, 1);
        TextField phoneNumberField = createTextField(gridPane, "Phone Number:", 2, 0);
        TextField addressField = createTextField(gridPane, "Address:", 2, 1);
        TextField hireDateField = createTextField(gridPane, "Hire Date (yyyy-mm-dd):", 3, 0);
        TextField salaryField = createTextField(gridPane, "Salary:", 3, 1);
        TextField statusField = createTextField(gridPane, "Status:", 4, 0);
        TextField departmentField = createTextField(gridPane, "Department:", 4, 1);
        TextField jobTitleField = createTextField(gridPane, "Job Title:", 5, 0);
        TextField workingHoursField = createTextField(gridPane, "Working Hours:", 5, 1);
        TextField shiftTypeField = createTextField(gridPane, "Shift Type:", 6, 0);
        TextField locationField = createTextField(gridPane, "Location:", 6, 1);
        TextField responsibilityLevelField = createTextField(gridPane, "Responsibility Level:", 7, 0);
        TextField taskCountField = createTextField(gridPane, "Task Count:", 7, 1);
        TextField performanceRatingField = createTextField(gridPane, "Performance Rating:", 8, 0);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 12);

        backButton.setOnAction(e -> stage.setScene(initialScene));

        submitButton.setOnAction(e -> {
            try {
                Operational operational = new Operational();
                operational.setId(Integer.parseInt(staffIdField.getText()));
                operational.setFirstName(firstNameField.getText());
                operational.setLastName(lastNameField.getText());
                operational.setEmail(emailField.getText());
                operational.setPhoneNumber(phoneNumberField.getText());
                operational.setAddress(addressField.getText());
                operational.setHireDate(java.sql.Date.valueOf(hireDateField.getText()));
                operational.setSalary(new java.math.BigDecimal(salaryField.getText()));
                operational.setStatus(statusField.getText());
                operational.setDepartment(departmentField.getText());
                operational.setJobTitle(jobTitleField.getText());
                operational.setWorkingHours(workingHoursField.getText());
                operational.setShiftType(shiftTypeField.getText());
                operational.setLocation(locationField.getText());
                operational.setResponsibilityLevel(responsibilityLevelField.getText());

                operational.setTaskCount(Integer.parseInt(taskCountField.getText()));
                operational.setPerformanceRating(new java.math.BigDecimal(performanceRatingField.getText()));

                if (operationalDAL.insertOperational(operational)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Operational added successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add Operational.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        setBackground(gridPane);

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void updateOperational(Stage stage) {
        GridPane gridPane = createFormPane();

        Label idLabel = new Label("Operational ID:");
        TextField idField = new TextField();
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);

        Button fetchButton = new Button("Fetch Details");
        gridPane.add(fetchButton, 2, 0);

        TextField firstNameField = createTextField(gridPane, "First Name:", 0, 0);
        TextField lastNameField = createTextField(gridPane, "Last Name:", 0, 1);
        TextField emailField = createTextField(gridPane, "Email:", 1, 0);
        TextField phoneField = createTextField(gridPane, "Phone Number:", 1, 1);
        TextField addressField = createTextField(gridPane, "Address:", 2, 0);
        TextField hireDateField = createTextField(gridPane, "Hire Date (yyyy-mm-dd):", 2, 1);
        TextField salaryField = createTextField(gridPane, "Salary:", 3, 0);
        TextField statusField = createTextField(gridPane, "Status:", 3, 1);
        TextField departmentField = createTextField(gridPane, "Department:", 4, 0);
        TextField jobTitleField = createTextField(gridPane, "Job Title:", 4, 1);
        TextField workingHoursField = createTextField(gridPane, "Working Hours:", 5, 0);
        TextField shiftTypeField = createTextField(gridPane, "Shit Type:", 5, 1);
        TextField locationField = createTextField(gridPane, "Location:", 6, 0);
        TextField responsibilityLevelField = createTextField(gridPane, "Responsibility Level:", 6, 1);
        TextField taskCountField = createTextField(gridPane, "taskCount:", 7, 1);
        TextField performanceRatingField = createTextField(gridPane, "Performance Rating:", 8, 0);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 1, 12);

        fetchButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            Operational operational = operationalDAL.getOperationalById(id);
            if (operational != null) {
                firstNameField.setText(operational.getFirstName());
                lastNameField.setText(operational.getLastName());
                emailField.setText(operational.getEmail());
                phoneField.setText(operational.getPhoneNumber());
                addressField.setText(operational.getAddress());
                hireDateField.setText(operational.getHireDate().toString());
                salaryField.setText(operational.getSalary().toString());
                statusField.setText(operational.getStatus());
                departmentField.setText(operational.getDepartment());
                jobTitleField.setText(operational.getJobTitle());
                workingHoursField.setText(operational.getWorkingHours());
                shiftTypeField.setText(operational.getShiftType());
                locationField.setText(operational.getLocation());
                responsibilityLevelField.setText(operational.getResponsibilityLevel());

                taskCountField.setText(Integer.toString(operational.getTaskCount()));
                performanceRatingField.setText(operational.getPerformanceRating().toString());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No Operational found with ID " + id);
            }
        });

        updateButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String hireDate = hireDateField.getText();
            String salary = salaryField.getText();
            String status = statusField.getText();
            String department = departmentField.getText();
            String jobTitle = jobTitleField.getText();
            String workingHours = workingHoursField.getText();
            String shiftType = shiftTypeField.getText();
            String location = locationField.getText();
            String responsibilityLevel = responsibilityLevelField.getText();
            String taskCount = taskCountField.getText();
            String performanceRating = performanceRatingField.getText();

            Operational operational = operationalDAL.getOperationalById(id);
            if (operational != null) {
                operational.setFirstName(firstName);
                operational.setLastName(lastName);
                operational.setEmail(email);
                operational.setPhoneNumber(phone);
                operational.setAddress(address);
                operational.setHireDate(java.sql.Date.valueOf(hireDate));
                operational.setSalary(new java.math.BigDecimal(salary));
                operational.setStatus(status);
                operational.setDepartment(department);
                operational.setJobTitle(jobTitle);
                operational.setWorkingHours(workingHours);
                operational.setShiftType(shiftType);
                operational.setLocation(location);
                operational.setResponsibilityLevel(responsibilityLevel);
                operational.setTaskCount(Integer.parseInt(taskCount));
                operational.setPerformanceRating(new java.math.BigDecimal(performanceRating));

                if (operationalDAL.updateOperational(operational)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Operational updated successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update Operational.");
                }
            }
        });

        backButton.setOnAction(e -> stage.setScene(initialScene));

        setBackground(gridPane);

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void viewOperationals(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        TextField idField = new TextField();
        idField.setPromptText("Enter Operational ID");

        Button viewByIdButton = new Button("View by ID");
        Button viewAllButton = new Button("View All");
        Button backButton = new Button("Back");

        viewByIdButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Operational operational = operationalDAL.getOperationalById(id);
                if (operational != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Operational Details", operational.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Operational found with ID: " + id);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });

        viewAllButton.setOnAction(e -> {
            List<Operational> operationals = operationalDAL.getAllOperationals();
            if (operationals.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "View All Operationals",
                        "No Operational records found.");
            } else {
                StringBuilder details = new StringBuilder();
                operationals.forEach(operational -> details.append(operational).append("\n"));
                showAlert(Alert.AlertType.INFORMATION, "View All Operationals", details.toString());
            }
        });

        backButton.setOnAction(e -> stage.setScene(initialScene));

        vbox.getChildren().addAll(idField, viewByIdButton, viewAllButton, backButton);

        setBackground(vbox);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void deleteOperational(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label idLabel = new Label("Enter Operational ID:");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");

        vbox.getChildren().addAll(idLabel, idField, deleteButton);

        deleteButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            if (operationalDAL.deleteOperational(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Operational deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Operational.");
            }
        });

        Button backButton = new Button("Back");
        vbox.getChildren().add(backButton);
        backButton.setOnAction(e -> stage.setScene(initialScene));

        setBackground(vbox);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void goBack(Stage stage) {
        MainInterface mainInterface = new MainInterface();
        mainInterface.start(stage);
    }

    private GridPane createFormPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        return gridPane;
    }

    private TextField createTextField(GridPane gridPane, String label, int row, int col) {
        Label lbl = new Label(label);
        TextField textField = new TextField();
        gridPane.add(lbl, col * 2, row);
        gridPane.add(textField, col * 2 + 1, row);
        return textField;
    }

    private void setBackground(Pane pane) {
        Image backgroundImage = new Image("file:Resources/background.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        pane.setBackground(new Background(bgImage));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}