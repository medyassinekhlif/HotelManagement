package Staff.Operational;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class OperationalInterface extends Application {

    private static final OperationalDAL operationalDAL = new OperationalDAL();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Operational Management System");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        Label title = new Label("Operational Management System");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addOperationalButton = new Button("Add Operational");
        Button viewOperationalByIdButton = new Button("View Operational by ID");
        Button viewAllOperationalsButton = new Button("View All Operationals");
        Button updateOperationalButton = new Button("Update Operational");
        Button deleteOperationalButton = new Button("Delete Operational");
        Button exitButton = new Button("Exit");

        // Event Handlers
        addOperationalButton.setOnAction(e -> addOperational(stage));
        viewOperationalByIdButton.setOnAction(e -> viewOperationalById(stage));
        viewAllOperationalsButton.setOnAction(e -> viewAllOperationals(stage));
        updateOperationalButton.setOnAction(e -> updateOperational(stage));
        deleteOperationalButton.setOnAction(e -> deleteOperational(stage));
        exitButton.setOnAction(e -> stage.close());

        mainLayout.getChildren().addAll(title, addOperationalButton, viewOperationalByIdButton,
                viewAllOperationalsButton,
                updateOperationalButton, deleteOperationalButton, exitButton);

        String cssFile = getClass().getResource("/Resources/styles.css").toExternalForm();
        Scene scene = new Scene(mainLayout, 400, 300);
        scene.getStylesheets().add(cssFile);

        stage.setScene(scene);
        stage.show();
    }

    private void addOperational(Stage stage) {
        Stage addStage = new Stage();
        addStage.setTitle("Add Operational");

        GridPane gridPane = createFormPane();

        // Create form fields
        TextField staffIdField = createTextField(gridPane, "Staff ID:", 0);
        TextField firstNameField = createTextField(gridPane, "First Name:", 1);
        TextField lastNameField = createTextField(gridPane, "Last Name:", 2);
        TextField emailField = createTextField(gridPane, "Email:", 3);
        TextField phoneNumberField = createTextField(gridPane, "Phone Number:", 4);
        TextField addressField = createTextField(gridPane, "Address:", 5);
        TextField hireDateField = createTextField(gridPane, "Hire Date (yyyy-mm-dd):", 6);
        TextField salaryField = createTextField(gridPane, "Salary:", 7);
        TextField statusField = createTextField(gridPane, "Status:", 8);
        TextField departmentField = createTextField(gridPane, "Department:", 9);
        TextField jobTitleField = createTextField(gridPane, "Job Title:", 10);
        TextField workingHoursField = createTextField(gridPane, "Working Hours:", 11);
        TextField shiftTypeField = createTextField(gridPane, "Shift Type:", 12);
        TextField locationField = createTextField(gridPane, "Location:", 13);
        TextField responsibilityLevelField = createTextField(gridPane, "Responsibility Level:", 14);
        TextField taskCountField = createTextField(gridPane, "Task Count:", 15);
        TextField performanceRatingField = createTextField(gridPane, "Performance Rating:", 16);

        // Submit button
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 17);

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
                    addStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add operational.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(gridPane, 600, 600);
        addStage.setScene(scene);
        addStage.show();
    }

    private void viewOperationalById(Stage stage) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("View Operational by ID");
        inputDialog.setHeaderText("Enter Operational ID:");
        inputDialog.setContentText("ID:");

        inputDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Operational operational = operationalDAL.getOperationalById(id);
                if (operational != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Operational Details", operational.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No operational found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });
    }

    private void viewAllOperationals(Stage stage) {
        // Implementation for viewing all operationals
        List<Operational> operationals = operationalDAL.getAllOperationals();
        if (operationals.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "View All Operationals", "No operational records found.");
        } else {
            StringBuilder details = new StringBuilder();
            operationals.forEach(operational -> details.append(operational).append("\n"));
            showAlert(Alert.AlertType.INFORMATION, "View All Operationals", details.toString());
        }
    }

    private void updateOperational(Stage stage) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Operational");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Labels and Input Fields
        Label idLabel = new Label("Operational ID:");
        TextField idField = new TextField();
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);

        Button fetchButton = new Button("Fetch Details");
        gridPane.add(fetchButton, 2, 0);

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(firstNameField, 1, 1);

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        gridPane.add(lastNameLabel, 0, 2);
        gridPane.add(lastNameField, 1, 2);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailField, 1, 3);

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();
        gridPane.add(phoneLabel, 0, 4);
        gridPane.add(phoneField, 1, 4);

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        gridPane.add(addressLabel, 0, 5);
        gridPane.add(addressField, 1, 5);

        Label hireDateLabel = new Label("Hire Date (yyyy-mm-dd):");
        TextField hireDateField = new TextField();
        gridPane.add(hireDateLabel, 0, 6);
        gridPane.add(hireDateField, 1, 6);

        Label salaryLabel = new Label("Salary:");
        TextField salaryField = new TextField();
        gridPane.add(salaryLabel, 0, 7);
        gridPane.add(salaryField, 1, 7);

        Label statusLabel = new Label("Status:");
        TextField statusField = new TextField();
        gridPane.add(statusLabel, 0, 8);
        gridPane.add(statusField, 1, 8);

        Label departmentLabel = new Label("Department:");
        TextField departmentField = new TextField();
        gridPane.add(departmentLabel, 0, 9);
        gridPane.add(departmentField, 1, 9);

        Label jobTitleLabel = new Label("Job Title:");
        TextField jobTitleField = new TextField();
        gridPane.add(jobTitleLabel, 0, 10);
        gridPane.add(jobTitleField, 1, 10);

        Label workingHoursLabel = new Label("Working Hours:");
        TextField workingHoursField = new TextField();
        gridPane.add(workingHoursLabel, 0, 11);
        gridPane.add(workingHoursField, 1, 11);

        Label shiftTypeLabel = new Label("Operational Shift Type:");
        TextField shiftTypeField = new TextField();
        gridPane.add(shiftTypeLabel, 0, 12);
        gridPane.add(shiftTypeField, 1, 12);

        Label locationLabel = new Label("Location:");
        TextField locationField = new TextField();
        gridPane.add(locationLabel, 0, 13);
        gridPane.add(locationField, 1, 13);

        Label responsibilityLevelLabel = new Label("Responsibility Level:");
        TextField responsibilityLevelField = new TextField();
        gridPane.add(responsibilityLevelLabel, 0, 14);
        gridPane.add(responsibilityLevelField, 1, 14);

        Label taskCountLabel = new Label("Task Count:");
        TextField taskCountField = new TextField();
        gridPane.add(taskCountLabel, 0, 15);
        gridPane.add(taskCountField, 1, 15);

        Label performanceRatingLabel = new Label("Performance Rating:");
        TextField performanceRatingField = new TextField();
        gridPane.add(performanceRatingLabel, 0, 16);
        gridPane.add(performanceRatingField, 1, 16);

        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 0, 16, 2, 1);

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
                locationField.setText(operational.getResponsibilityLevel());
                taskCountField.setText(Integer.toString(operational.getTaskCount()));
                performanceRatingField.setText(operational.getPerformanceRating().toString());

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No operational found with ID " + id);
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
            String responsibilityLevel = locationField.getText();
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
                    updateStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update operational.");
                }
            }
        });

        Scene scene = new Scene(gridPane, 500, 400);
        updateStage.setScene(scene);
        updateStage.show();
    }

    private void deleteOperational(Stage stage) {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete Operational");

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
                deleteStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete operational.");
            }

        });

        Scene scene = new Scene(vbox, 300, 200);
        deleteStage.setScene(scene);
        deleteStage.show();
    }

    private GridPane createFormPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        return gridPane;
    }

    private TextField createTextField(GridPane gridPane, String label, int row) {
        Label lbl = new Label(label);
        TextField textField = new TextField();
        gridPane.add(lbl, 0, row);
        gridPane.add(textField, 1, row);
        return textField;
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
