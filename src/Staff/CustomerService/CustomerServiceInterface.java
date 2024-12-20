package Staff.CustomerService;

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

public class CustomerServiceInterface extends Application {

    private static final CustomerServiceDAL customerServiceDAL = new CustomerServiceDAL();
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

        Button addCustomerServiceButton = new Button("Add Customer Service");
        Button viewCustomerServicesButton = new Button("View Customer Services");
        Button updateCustomerServiceButton = new Button("Update Customer Service");
        Button deleteCustomerServiceButton = new Button("Delete Customer Service");
        Button backButton = new Button("Back");

        addCustomerServiceButton.setOnAction(e -> addCustomerService(stage));
        viewCustomerServicesButton.setOnAction(e -> viewCustomerServices(stage));
        updateCustomerServiceButton.setOnAction(e -> updateCustomerService(stage));
        deleteCustomerServiceButton.setOnAction(e -> deleteCustomerService(stage));
        backButton.setOnAction(e -> goBack(stage));

        mainLayout.getChildren().addAll(title, addCustomerServiceButton, viewCustomerServicesButton,
                updateCustomerServiceButton, deleteCustomerServiceButton, backButton);
        return mainLayout;
    }

    private void addCustomerService(Stage stage) {
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
        TextField customerSatisfactionScore = createTextField(gridPane, "CustomerService Skills:", 6, 0);
        TextField languagesSpoken = createTextField(gridPane, "Certifications:", 6, 1);
        TextField shift = createTextField(gridPane, "Work Location:", 7, 0);
        TextField feedbackReceived = createTextField(gridPane, "Received Feedback:", 7, 1);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);

        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 12);

        backButton.setOnAction(e -> stage.setScene(initialScene));

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
                customerService
                        .setCustomerSatisfactionScore(new java.math.BigDecimal(customerSatisfactionScore.getText()));
                customerService.setLanguagesSpoken(languagesSpoken.getText());
                customerService.setShift(shift.getText());
                customerService.setFeedbackReceived(feedbackReceived.getText());

                if (customerServiceDAL.insertCustomerService(customerService)) {
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

    private void updateCustomerService(Stage stage) {
        GridPane gridPane = createFormPane();

        Label idLabel = new Label("CustomerService ID:");
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
        TextField customerSatisfactionScoreField = createTextField(gridPane, "Customer Satisfaction Score:", 5, 1);
        TextField languagesSpokenField = createTextField(gridPane, "Languages Spoken:", 6, 0);
        TextField shiftField = createTextField(gridPane, "Shift:", 6, 1);
        TextField feedbackReceivedField = createTextField(gridPane, "Received Feedback:", 7, 0);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 1, 12);

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
                customerService.setCustomerSatisfactionScore(new java.math.BigDecimal(customerSatisfactionScore));
                customerService.setLanguagesSpoken(languagesSpoken);
                customerService.setShift(shift);
                customerService.setFeedbackReceived(feedbackReceived);

                if (customerServiceDAL.updateCustomerService(customerService)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "CustomerService updated successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update CustomerService.");
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

    private void viewCustomerServices(Stage stage) {
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
                CustomerService customerService = customerServiceDAL.getCustomerServiceById(id);
                if (customerService != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Customer Service Details", customerService.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Customer Service found with ID: " + id);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });

        viewAllButton.setOnAction(e -> {
            List<CustomerService> customerServices = customerServiceDAL.getAllCustomerServices();
            if (customerServices.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "View All CustomerServices",
                        "No CustomerService records found.");
            } else {
                StringBuilder details = new StringBuilder();
                customerServices.forEach(customerService -> details.append(customerService).append("\n"));
                showAlert(Alert.AlertType.INFORMATION, "View All CustomerServices", details.toString());
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

    private void deleteCustomerService(Stage stage) {
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
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete CustomerService.");
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