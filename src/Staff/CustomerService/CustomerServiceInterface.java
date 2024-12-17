package Staff.CustomerService;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;

public class CustomerServiceInterface extends Application {

    private static final CustomerServiceDAL customerServiceDAL = new CustomerServiceDAL();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Customer Service Management System");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        Label title = new Label("Customer Service Management System");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addCustomerServiceButton = new Button("Add Customer Service");
        Button viewCustomerServiceByIdButton = new Button("View Customer Service by ID");
        Button viewAllCustomerServicesButton = new Button("View All Customer Services");
        Button updateCustomerServiceButton = new Button("Update Customer Service");
        Button deleteCustomerServiceButton = new Button("Delete Customer Service");
        Button exitButton = new Button("Exit");

        // Event Handlers
        addCustomerServiceButton.setOnAction(e -> addCustomerService(stage));
        viewCustomerServiceByIdButton.setOnAction(e -> viewCustomerServiceById(stage));
        viewAllCustomerServicesButton.setOnAction(e -> viewAllCustomerServices(stage));
        updateCustomerServiceButton.setOnAction(e -> updateCustomerService(stage));
        deleteCustomerServiceButton.setOnAction(e -> deleteCustomerService(stage));
        exitButton.setOnAction(e -> stage.close());

        mainLayout.getChildren().addAll(title, addCustomerServiceButton, viewCustomerServiceByIdButton,
                viewAllCustomerServicesButton,
                updateCustomerServiceButton, deleteCustomerServiceButton, exitButton);
        String cssFile = getClass().getResource("/Resources/styles.css").toExternalForm();

        Scene scene = new Scene(mainLayout, 400, 300);
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(cssFile);
    }

    private void addCustomerService(Stage stage) {
        Stage addStage = new Stage();
        addStage.setTitle("Add CustomerService");

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
        TextField customerSatisfactionScore = createTextField(gridPane, "CustomerService Skills:", 12);
        TextField languagesSpoken = createTextField(gridPane, "Certifications:", 13);
        TextField shift = createTextField(gridPane, "Work Location:", 14);
        TextField feedbackReceived = createTextField(gridPane, "Last Training Date (yyyy-mm-dd):", 15);

        // Submit button
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 16);

        submitButton.setOnAction(e -> {
            try {
                CustomerService customerService = new CustomerService();
                customerService.setId(Integer.parseInt(staffIdField.getText()));
                customerService.setFirstName(firstNameField.getText());
                customerService.setLastName(lastNameField.getText());
                customerService.setEmail(emailField.getText());
                customerService.setPhoneNumber(phoneNumberField.getText());
                customerService.setAddress(addressField.getText());
                customerService.setHireDate(java.sql.Date.valueOf(hireDateField.getText()));
                customerService.setSalary(new java.math.BigDecimal(salaryField.getText()));
                customerService.setStatus(statusField.getText());
                customerService.setDepartment(departmentField.getText());
                customerService.setJobTitle(jobTitleField.getText());
                customerService.setWorkingHours(workingHoursField.getText());
                customerService.setCustomerSatisfactionScore(new BigDecimal(customerSatisfactionScore.getText()));
                customerService.setLanguagesSpoken(languagesSpoken.getText());
                customerService.setShift(shift.getText());
                customerService.setFeedbackReceived(feedbackReceived.getText());

                if (customerServiceDAL.insertCustomerService(customerService)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Customer Service added successfully.");
                    addStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add Customer Service.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(gridPane, 600, 600);
        addStage.setScene(scene);
        addStage.show();
    }

    private void viewCustomerServiceById(Stage stage) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("View Customer Service by ID");
        inputDialog.setHeaderText("Enter Customer Service ID:");
        inputDialog.setContentText("ID:");

        inputDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                CustomerService customerService = customerServiceDAL.getCustomerServiceById(id);
                if (customerService != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Customer Service Details", customerService.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Customer Service found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });
    }

    private void viewAllCustomerServices(Stage stage) {
        // Implementation for viewing all Customer Services
        List<CustomerService> customerServices = customerServiceDAL.getAllCustomerServices();
        if (customerServices.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "View All CustomerServices", "No CustomerService records found.");
        } else {
            StringBuilder details = new StringBuilder();
            customerServices.forEach(customerService -> details.append(customerService).append("\n"));
            showAlert(Alert.AlertType.INFORMATION, "View All CustomerServices", details.toString());
        }
    }

    private void updateCustomerService(Stage stage) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update CustomerService");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Labels and Input Fields
        Label idLabel = new Label("CustomerService ID:");
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

        Label customerSatisfactionScoreLabel = new Label("Customer Satisfaction Score:");
        TextField customerSatisfactionScoreField = new TextField();
        gridPane.add(customerSatisfactionScoreLabel, 0, 12);
        gridPane.add(customerSatisfactionScoreField, 1, 12);

        Label languagesSpokenLabel = new Label("Languages Spoken:");
        TextField languagesSpokenField = new TextField();
        gridPane.add(languagesSpokenLabel, 0, 13);
        gridPane.add(languagesSpokenField, 1, 13);

        Label shiftLabel = new Label("Shift:");
        TextField shiftField = new TextField();
        gridPane.add(shiftLabel, 0, 14);
        gridPane.add(shiftField, 1, 14);

        Label feedbackReceivedLabel = new Label("Last Training Date (yyyy-mm-dd):");
        TextField feedbackReceivedField = new TextField();
        gridPane.add(feedbackReceivedLabel, 0, 15);
        gridPane.add(feedbackReceivedField, 1, 15);

        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 0, 16, 2, 1);

        fetchButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            CustomerService customerService = customerServiceDAL.getCustomerServiceById(id);
            if (customerService != null) {
                firstNameField.setText(customerService.getFirstName());
                lastNameField.setText(customerService.getLastName());
                emailField.setText(customerService.getEmail());
                phoneField.setText(customerService.getPhoneNumber());
                addressField.setText(customerService.getAddress());
                hireDateField.setText(customerService.getHireDate().toString());
                salaryField.setText(customerService.getSalary().toString());
                statusField.setText(customerService.getStatus());
                departmentField.setText(customerService.getDepartment());
                jobTitleField.setText(customerService.getJobTitle());
                workingHoursField.setText(customerService.getWorkingHours());
                customerSatisfactionScoreField.setText(customerService.getCustomerSatisfactionScore().toString());
                languagesSpokenField.setText(customerService.getLanguagesSpoken());
                shiftField.setText(customerService.getShift());
                feedbackReceivedField.setText(customerService.getFeedbackReceived().toString());
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
            String customerSatisfactionScore = customerSatisfactionScoreField.getText();
            String languagesSpoken = languagesSpokenField.getText();
            String shift = shiftField.getText();
            String feedbackReceived = feedbackReceivedField.getText();

            CustomerService customerService = customerServiceDAL.getCustomerServiceById(id);
            if (customerService != null) {
                customerService.setFirstName(firstName);
                customerService.setLastName(lastName);
                customerService.setEmail(email);
                customerService.setPhoneNumber(phone);
                customerService.setAddress(address);
                customerService.setHireDate(java.sql.Date.valueOf(hireDate));
                customerService.setSalary(new java.math.BigDecimal(salary));
                customerService.setStatus(status);
                customerService.setDepartment(department);
                customerService.setJobTitle(jobTitle);
                customerService.setWorkingHours(workingHours);
                customerService.setCustomerSatisfactionScore(new BigDecimal(customerSatisfactionScore));
                customerService.setLanguagesSpoken(languagesSpoken);
                customerService.setShift(shift);
                customerService.setFeedbackReceived(feedbackReceived);

                if (customerServiceDAL.updateCustomerService(customerService)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "CustomerService updated successfully.");
                    updateStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update CustomerService.");
                }
            }
        });

        Scene scene = new Scene(gridPane, 500, 400);
        updateStage.setScene(scene);
        updateStage.show();
    }

    private void deleteCustomerService(Stage stage) {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete CustomerService");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label idLabel = new Label("Enter CustomerService ID:");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");

        vbox.getChildren().addAll(idLabel, idField, deleteButton);

        deleteButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            if (customerServiceDAL.deleteCustomerService(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "CustomerService deleted successfully.");
                deleteStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete CustomerService.");
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
