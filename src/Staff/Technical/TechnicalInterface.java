package Staff.Technical;

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

public class TechnicalInterface extends Application {

    private static final TechnicalDAL technicalDAL = new TechnicalDAL();
    private Scene initialScene;
    private static final String stylePath = "file:resources/styles.css";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Customer Service Management System");

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

        Label title = new Label("Customer Service Management System");

        Button addTechnicalButton = new Button("Add Customer Service");
        Button viewTechnicalsButton = new Button("View Customer Services");
        Button updateTechnicalButton = new Button("Update Customer Service");
        Button deleteTechnicalButton = new Button("Delete Customer Service");
        Button backButton = new Button("Back");

        addTechnicalButton.setOnAction(e -> addTechnical(stage));
        viewTechnicalsButton.setOnAction(e -> viewTechnicals(stage));
        updateTechnicalButton.setOnAction(e -> updateTechnical(stage));
        deleteTechnicalButton.setOnAction(e -> deleteTechnical(stage));
        backButton.setOnAction(e -> goBack(stage));

        mainLayout.getChildren().addAll(title, addTechnicalButton, viewTechnicalsButton,
                updateTechnicalButton, deleteTechnicalButton, backButton);
        return mainLayout;
    }

    private void addTechnical(Stage stage) {
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
        TextField technicalSkillsField = createTextField(gridPane, "Shift Type:", 6, 0);
        TextField certificationsField = createTextField(gridPane, "Certifications:", 6, 1);
        TextField workLocationField = createTextField(gridPane, "Responsibility Level:", 7, 0);
        TextField LastTrainingDateField = createTextField(gridPane, "Performance Rating:", 7, 1);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 12);

        backButton.setOnAction(e -> stage.setScene(initialScene));

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
                technical.setLastTrainingDate(java.sql.Date.valueOf(LastTrainingDateField.getText()));

                if (technicalDAL.insertTechnical(technical)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Customer Service added successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add Customer Service.");
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

    private void updateTechnical(Stage stage) {
        GridPane gridPane = createFormPane();

        Label idLabel = new Label("Technical ID:");
        TextField idField = new TextField();
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);

        Button fetchButton = new Button("Fetch Details");
        gridPane.add(fetchButton, 2, 0);

        TextField firstNameField = createTextField(gridPane, "First Name:", 0, 0);
        TextField lastNameField = createTextField(gridPane, "Last Name:", 0, 1);
        TextField emailField = createTextField(gridPane, "Email:", 2, 0);
        TextField phoneField = createTextField(gridPane, "Phone Number:", 2, 1);
        TextField addressField = createTextField(gridPane, "Address:", 1, 0);
        TextField hireDateField = createTextField(gridPane, "Hire Date (yyyy-mm-dd):", 1, 1);
        TextField salaryField = createTextField(gridPane, "Salary:", 2, 0);
        TextField statusField = createTextField(gridPane, "Status:", 2, 1);
        TextField departmentField = createTextField(gridPane, "Department:", 3, 0);
        TextField jobTitleField = createTextField(gridPane, "Job Title:", 3, 1);
        TextField workingHoursField = createTextField(gridPane, "Working Hours:", 4, 0);
        TextField technicalSkillsField = createTextField(gridPane, " Technical Skills:", 4, 1);
        TextField certificationsField = createTextField(gridPane, "Certifications:", 5, 0);
        TextField workLocationField = createTextField(gridPane, "work Location:", 5, 1);
        TextField lastTrainingDateField = createTextField(gridPane, "Last Training Date:", 6, 0);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 1, 12);

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
                technicalSkillsField.setText(technical.getTechnicalSkills());
                certificationsField.setText(technical.getCertifications());
                workLocationField.setText(technical.getWorkLocation());
                lastTrainingDateField.setText(technical.getLastTrainingDate().toString());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No Customer Service found with ID " + id);
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
            String technicalSkills = technicalSkillsField.getText();
            String certifications = certificationsField.getText();
            String workLocation = workLocationField.getText();
            String LastTrainingDate = lastTrainingDateField.getText();

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

                technical.setTechnicalSkills(technicalSkills);
                technical.setCertifications(certifications);
                technical.setWorkLocation(workLocation);
                technical.setLastTrainingDate(java.sql.Date.valueOf(LastTrainingDate));

                if (technicalDAL.updateTechnical(technical)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Technical updated successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update Technical.");
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

    private void viewTechnicals(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        TextField idField = new TextField();
        idField.setPromptText("Enter Customer Service ID");

        Button viewByIdButton = new Button("View by ID");
        Button viewAllButton = new Button("View All");
        Button backButton = new Button("Back");

        viewByIdButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Technical technical = technicalDAL.getTechnicalById(id);
                if (technical != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Customer Service Details", technical.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Customer Service found with ID: " + id);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });

        viewAllButton.setOnAction(e -> {
            List<Technical> technicals = technicalDAL.getAllTechnicals();
            if (technicals.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "View All Technicals",
                        "No Technical records found.");
            } else {
                StringBuilder details = new StringBuilder();
                technicals.forEach(technical -> details.append(technical).append("\n"));
                showAlert(Alert.AlertType.INFORMATION, "View All Technicals", details.toString());
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

    private void deleteTechnical(Stage stage) {
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
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Technical.");
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