package Staff.Technical;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TechnicalInterface extends Application {

    private static final TechnicalDAL technicalDAL = new TechnicalDAL();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Technical Management System");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        Label title = new Label("Technical Management System");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addTechnicalButton = new Button("Add Technical");
        Button viewTechnicalByIdButton = new Button("View Technical by ID");
        Button viewAllTechnicalsButton = new Button("View All Technicals");
        Button updateTechnicalButton = new Button("Update Technical");
        Button deleteTechnicalButton = new Button("Delete Technical");
        Button exitButton = new Button("Exit");

        // Event Handlers
        addTechnicalButton.setOnAction(e -> addTechnical(stage));
        viewTechnicalByIdButton.setOnAction(e -> viewTechnicalById(stage));
        viewAllTechnicalsButton.setOnAction(e -> viewAllTechnicals(stage));
        updateTechnicalButton.setOnAction(e -> updateTechnical(stage));
        deleteTechnicalButton.setOnAction(e -> deleteTechnical(stage));
        exitButton.setOnAction(e -> stage.close());

        mainLayout.getChildren().addAll(title, addTechnicalButton, viewTechnicalByIdButton, viewAllTechnicalsButton,
                updateTechnicalButton, deleteTechnicalButton, exitButton);
        String cssFile = getClass().getResource("/resources/styles.css").toExternalForm();

        Scene scene = new Scene(mainLayout, 400, 300);
        scene.getStylesheets().add(cssFile);

        stage.setScene(scene);

        stage.show();
    }

    private void addTechnical(Stage stage) {
        Stage addStage = new Stage();
        addStage.setTitle("Add Technical");

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
        TextField technicalSkillsField = createTextField(gridPane, "Technical Skills:", 12);
        TextField certificationsField = createTextField(gridPane, "Certifications:", 13);
        TextField workLocationField = createTextField(gridPane, "Work Location:", 14);
        TextField lastTrainingDateField = createTextField(gridPane, "Last Training Date (yyyy-mm-dd):", 15);

        // Submit button
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 16);

        submitButton.setOnAction(e -> {
            try {
                Technical technical = new Technical();
                technical.setId(Integer.parseInt(staffIdField.getText()));
                technical.setFirstName(firstNameField.getText());
                technical.setLastName(lastNameField.getText());
                technical.setEmail(emailField.getText());
                technical.setPhoneNumber(phoneNumberField.getText());
                technical.setAddress(addressField.getText());
                technical.setHireDate(java.sql.Date.valueOf(hireDateField.getText()));
                technical.setSalary(new java.math.BigDecimal(salaryField.getText()));
                technical.setStatus(statusField.getText());
                technical.setDepartment(departmentField.getText());
                technical.setJobTitle(jobTitleField.getText());
                technical.setWorkingHours(workingHoursField.getText());
                technical.setTechnicalSkills(technicalSkillsField.getText());
                technical.setCertifications(certificationsField.getText());
                technical.setWorkLocation(workLocationField.getText());
                technical.setLastTrainingDate(java.sql.Date.valueOf(lastTrainingDateField.getText()));

                if (technicalDAL.insertTechnical(technical)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Technical added successfully.");
                    addStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add technical.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(gridPane, 600, 600);
        addStage.setScene(scene);
        addStage.show();
    }

    private void viewTechnicalById(Stage stage) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("View Technical by ID");
        inputDialog.setHeaderText("Enter Technical ID:");
        inputDialog.setContentText("ID:");

        inputDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Technical technical = technicalDAL.getTechnicalById(id);
                if (technical != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Technical Details", technical.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No technical found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });
    }

    private void viewAllTechnicals(Stage stage) {
        // Implementation for viewing all technicals
        List<Technical> technicals = technicalDAL.getAllTechnicals();
        if (technicals.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "View All Technicals", "No technical records found.");
        } else {
            StringBuilder details = new StringBuilder();
            technicals.forEach(technical -> details.append(technical).append("\n"));
            showAlert(Alert.AlertType.INFORMATION, "View All Technicals", details.toString());
        }
    }

    private void updateTechnical(Stage stage) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Technical");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Labels and Input Fields
        Label idLabel = new Label("Technical ID:");
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

        Label skillsLabel = new Label("Technical Skills:");
        TextField skillsField = new TextField();
        gridPane.add(skillsLabel, 0, 12);
        gridPane.add(skillsField, 1, 12);

        Label certificationsLabel = new Label("Certifications:");
        TextField certificationsField = new TextField();
        gridPane.add(certificationsLabel, 0, 13);
        gridPane.add(certificationsField, 1, 13);

        Label locationLabel = new Label("Work Location:");
        TextField locationField = new TextField();
        gridPane.add(locationLabel, 0, 14);
        gridPane.add(locationField, 1, 14);

        Label trainingDateLabel = new Label("Last Training Date (yyyy-mm-dd):");
        TextField trainingDateField = new TextField();
        gridPane.add(trainingDateLabel, 0, 15);
        gridPane.add(trainingDateField, 1, 15);

        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 0, 16, 2, 1);

        fetchButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            Technical technical = technicalDAL.getTechnicalById(id);
            if (technical != null) {
                firstNameField.setText(technical.getFirstName());
                lastNameField.setText(technical.getLastName());
                emailField.setText(technical.getEmail());
                phoneField.setText(technical.getPhoneNumber());
                addressField.setText(technical.getAddress());
                hireDateField.setText(technical.getHireDate().toString());
                salaryField.setText(technical.getSalary().toString());
                statusField.setText(technical.getStatus());
                departmentField.setText(technical.getDepartment());
                jobTitleField.setText(technical.getJobTitle());
                workingHoursField.setText(technical.getWorkingHours());
                skillsField.setText(technical.getTechnicalSkills());
                certificationsField.setText(technical.getCertifications());
                locationField.setText(technical.getWorkLocation());
                trainingDateField.setText(technical.getLastTrainingDate().toString());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No technical found with ID " + id);
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
            String skills = skillsField.getText();
            String certifications = certificationsField.getText();
            String location = locationField.getText();
            String trainingDate = trainingDateField.getText();

            Technical technical = technicalDAL.getTechnicalById(id);
            if (technical != null) {
                technical.setFirstName(firstName);
                technical.setLastName(lastName);
                technical.setEmail(email);
                technical.setPhoneNumber(phone);
                technical.setAddress(address);
                technical.setHireDate(java.sql.Date.valueOf(hireDate));
                technical.setSalary(new java.math.BigDecimal(salary));
                technical.setStatus(status);
                technical.setDepartment(department);
                technical.setJobTitle(jobTitle);
                technical.setWorkingHours(workingHours);
                technical.setTechnicalSkills(skills);
                technical.setCertifications(certifications);
                technical.setWorkLocation(location);
                technical.setLastTrainingDate(java.sql.Date.valueOf(trainingDate));

                if (technicalDAL.updateTechnical(technical)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Technical updated successfully.");
                    updateStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update technical.");
                }
            }
        });

        Scene scene = new Scene(gridPane, 500, 400);
        updateStage.setScene(scene);
        updateStage.show();
    }

    private void deleteTechnical(Stage stage) {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete Technical");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label idLabel = new Label("Enter Technical ID:");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");

        vbox.getChildren().addAll(idLabel, idField, deleteButton);

        deleteButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            if (technicalDAL.deleteTechnical(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Technical deleted successfully.");
                deleteStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete technical.");
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
