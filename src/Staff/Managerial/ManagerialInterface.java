package Staff.Managerial;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ManagerialInterface extends Application {

    private static final ManagerialDAL ManagerialDAL = new ManagerialDAL();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Managerial Management System");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        Label title = new Label("Managerial Management System");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addManagerialButton = new Button("Add Managerial");
        Button viewManagerialByIdButton = new Button("View Managerial by ID");
        Button viewAllManagerialsButton = new Button("View All Managerials");
        Button updateManagerialButton = new Button("Update Managerial");
        Button deleteManagerialButton = new Button("Delete Managerial");
        Button exitButton = new Button("Exit");

        // Event Handlers
        addManagerialButton.setOnAction(e -> addManagerial(stage));
        viewManagerialByIdButton.setOnAction(e -> viewManagerialById(stage));
        viewAllManagerialsButton.setOnAction(e -> viewAllManagerials(stage));
        updateManagerialButton.setOnAction(e -> updateManagerial(stage));
        deleteManagerialButton.setOnAction(e -> deleteManagerial(stage));
        exitButton.setOnAction(e -> stage.close());

        mainLayout.getChildren().addAll(title, addManagerialButton, viewManagerialByIdButton, viewAllManagerialsButton,
                updateManagerialButton, deleteManagerialButton, exitButton);

        String cssFile = getClass().getResource("/Resources/styles.css").toExternalForm();
        Scene scene = new Scene(mainLayout, 400, 300);
        scene.getStylesheets().add(cssFile);

        stage.setScene(scene);
        stage.show();
    }

    private void addManagerial(Stage stage) {
        Stage addStage = new Stage();
        addStage.setTitle("Add Managerial");

        GridPane gridPane = createFormPane();

        // Create form fields
        TextField IdField = createTextField(gridPane, "Staff ID:", 0);
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
        TextField officeLocation = createTextField(gridPane, "Managerial Office Location:", 12);
        TextField teamSize = createTextField(gridPane, "Team Size:", 13);
        TextField reportsTo = createTextField(gridPane, "Reports To:", 14);

        // Submit button
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 16);

        submitButton.setOnAction(e -> {
            try {
                Managerial managerial = new Managerial();
                managerial.setId(Integer.parseInt(IdField.getText()));
                managerial.setFirstName(firstNameField.getText());
                managerial.setLastName(lastNameField.getText());
                managerial.setEmail(emailField.getText());
                managerial.setPhoneNumber(phoneNumberField.getText());
                managerial.setAddress(addressField.getText());
                managerial.setHireDate(java.sql.Date.valueOf(hireDateField.getText()));
                managerial.setSalary(new java.math.BigDecimal(salaryField.getText()));
                managerial.setStatus(statusField.getText());
                managerial.setDepartment(departmentField.getText());
                managerial.setJobTitle(jobTitleField.getText());
                managerial.setWorkingHours(workingHoursField.getText());
                managerial.setOfficeLocation(officeLocation.getText());
                managerial.setTeamSize(Integer.parseInt(teamSize.getText()));
                managerial.setReportsTo(reportsTo.getText());

                if (ManagerialDAL.insertManagerial(managerial)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Managerial added successfully.");
                    addStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add managerial.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(gridPane, 600, 600);
        addStage.setScene(scene);
        addStage.show();
    }

    private void viewManagerialById(Stage stage) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("View Managerial by ID");
        inputDialog.setHeaderText("Enter Managerial ID:");
        inputDialog.setContentText("ID:");

        inputDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Managerial managerial = ManagerialDAL.getManagerialById(id);
                if (managerial != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Managerial Details", managerial.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No managerial found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });
    }

    private void viewAllManagerials(Stage stage) {
        // Implementation for viewing all managerials
        List<Managerial> managerials = ManagerialDAL.getAllManagerials();
        if (managerials.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "View All Managerials", "No Managerial records found.");
        } else {
            StringBuilder details = new StringBuilder();
            managerials.forEach(managerial -> details.append(managerial).append("\n"));
            showAlert(Alert.AlertType.INFORMATION, "View All Managerials", details.toString());
        }
    }

    private void updateManagerial(Stage stage) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Managerial");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Labels and Input Fields
        Label idLabel = new Label("Managerial ID:");
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

        Label officeLocationLabel = new Label("Office Location:");
        TextField officeLocationField = new TextField();
        gridPane.add(officeLocationLabel, 0, 12);
        gridPane.add(officeLocationField, 1, 12);

        Label teamSizeLabel = new Label("Team Size:");
        TextField teamSizeField = new TextField();
        gridPane.add(teamSizeLabel, 0, 13);
        gridPane.add(teamSizeField, 1, 13);

        Label reportsToLabel = new Label("ReportsTo :");
        TextField reportsToField = new TextField();
        gridPane.add(reportsToLabel, 0, 14);
        gridPane.add(reportsToField, 1, 14);

        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 0, 16, 2, 1);

        fetchButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            Managerial managerial = ManagerialDAL.getManagerialById(id);
            if (managerial != null) {
                firstNameField.setText(managerial.getFirstName());
                lastNameField.setText(managerial.getLastName());
                emailField.setText(managerial.getEmail());
                phoneField.setText(managerial.getPhoneNumber());
                addressField.setText(managerial.getAddress());
                hireDateField.setText(managerial.getHireDate().toString());
                salaryField.setText(managerial.getSalary().toString());
                statusField.setText(managerial.getStatus());
                departmentField.setText(managerial.getDepartment());
                jobTitleField.setText(managerial.getJobTitle());
                workingHoursField.setText(managerial.getWorkingHours());
                officeLocationField.setText(managerial.getOfficeLocation());
                teamSizeField.setText(Integer.toString(managerial.getTeamSize()));
                reportsToField.setText(managerial.getReportsTo());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No managerial found with ID " + id);
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
            String officeLocation = officeLocationField.getText();
            String teamSize = teamSizeField.getText();
            String reportsTo = reportsToField.getText();

            Managerial managerial = ManagerialDAL.getManagerialById(id);
            if (managerial != null) {
                managerial.setFirstName(firstName);
                managerial.setLastName(lastName);
                managerial.setEmail(email);
                managerial.setPhoneNumber(phone);
                managerial.setAddress(address);
                managerial.setHireDate(java.sql.Date.valueOf(hireDate));
                managerial.setSalary(new java.math.BigDecimal(salary));
                managerial.setStatus(status);
                managerial.setDepartment(department);
                managerial.setJobTitle(jobTitle);
                managerial.setWorkingHours(workingHours);
                managerial.setOfficeLocation(officeLocation);
                managerial.setTeamSize(Integer.parseInt(teamSize));
                managerial.setReportsTo(reportsTo);

                if (ManagerialDAL.updateManagerial(managerial)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Managerial updated successfully.");
                    updateStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update managerial.");
                }
            }
        });

        Scene scene = new Scene(gridPane, 500, 400);
        updateStage.setScene(scene);
        updateStage.show();
    }

    private void deleteManagerial(Stage stage) {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete Managerial");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label idLabel = new Label("Enter Managerial ID:");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");

        vbox.getChildren().addAll(idLabel, idField, deleteButton);

        deleteButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            if (ManagerialDAL.deleteManagerial(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Managerial deleted successfully.");
                deleteStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Managerial.");
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
