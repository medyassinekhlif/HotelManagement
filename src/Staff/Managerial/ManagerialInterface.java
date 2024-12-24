package Staff.Managerial;

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

public class ManagerialInterface extends Application {

    private static final ManagerialDAL managerialDAL = new ManagerialDAL();
    private Scene initialScene;
    private static final String stylePath = "file:resources/styles.css";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Managerial Management System");

        stage.setWidth(1000);
        stage.setHeight(700);

        VBox mainLayout = createMainLayout(stage);
        setBackground(mainLayout);

        initialScene = new Scene(mainLayout);
        initialScene.getStylesheets().add(stylePath);
        stage.setScene(initialScene);
        stage.show();
    }

    private VBox createMainLayout(Stage stage) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        Label title = new Label("Managerial Management System");

        Button addManagerialButton = new Button("Add Managerial");
        Button viewManagerialsButton = new Button("View Managerials");
        Button updateManagerialButton = new Button("Update Managerial");
        Button deleteManagerialButton = new Button("Delete Managerial");
        Button backButton = new Button("Back");

        addManagerialButton.setOnAction(e -> addManagerial(stage));
        viewManagerialsButton.setOnAction(e -> viewManagerials(stage));
        updateManagerialButton.setOnAction(e -> updateManagerial(stage));
        deleteManagerialButton.setOnAction(e -> deleteManagerial(stage));
        backButton.setOnAction(e -> goBack(stage));

        mainLayout.getChildren().addAll(title, addManagerialButton, viewManagerialsButton,
                updateManagerialButton, deleteManagerialButton, backButton);
        return mainLayout;
    }

    private void addManagerial(Stage stage) {
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
        TextField officeLocationField = createTextField(gridPane, "Office Location:", 6, 0);
        TextField teamSizeField = createTextField(gridPane, "Team Size:", 6, 1);
        TextField reportsToField = createTextField(gridPane, "Reports To:", 7, 0);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 12);

        backButton.setOnAction(e -> stage.setScene(initialScene));

        submitButton.setOnAction(e -> {
            try {
                Managerial managerial = new Managerial();
                managerial.setId(Integer.parseInt(staffIdField.getText()));
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
                managerial.setOfficeLocation(officeLocationField.getText());
                managerial.setTeamSize(Integer.parseInt(teamSizeField.getText()));
                managerial.setReportsTo(reportsToField.getText());

                if (managerialDAL.insertManagerial(managerial)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Managerial added successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add Managerial.");
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

    private void updateManagerial(Stage stage) {
        GridPane gridPane = createFormPane();

        TextField idField = createTextField(gridPane, "ID:", 0, 0);

        Button fetchButton = new Button("Fetch Details");
        gridPane.add(fetchButton, 3, 12);

        TextField firstNameField = createTextField(gridPane, "First Name:", 1, 0);
        TextField lastNameField = createTextField(gridPane, "Last Name:", 1, 1);
        TextField emailField = createTextField(gridPane, "Email:", 2, 0);
        TextField phoneField = createTextField(gridPane, "Phone Number:", 2, 1);
        TextField addressField = createTextField(gridPane, "Address:", 3, 0);
        TextField hireDateField = createTextField(gridPane, "Hire Date (yyyy-mm-dd):", 3, 1);
        TextField salaryField = createTextField(gridPane, "Salary:", 4, 0);
        TextField statusField = createTextField(gridPane, "Status:", 4, 1);
        TextField departmentField = createTextField(gridPane, "Department:", 5, 0);
        TextField jobTitleField = createTextField(gridPane, "Job Title:", 5, 1);
        TextField workingHoursField = createTextField(gridPane, "Working Hours:", 6, 0);
        TextField officeLocationField = createTextField(gridPane, "Office Location:", 6, 1);
        TextField teamSizeField = createTextField(gridPane, "Team Size:", 7, 0);
        TextField reportsToField = createTextField(gridPane, "Reports To:", 7, 1);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);
        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 1, 12);

        fetchButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            Managerial managerial = managerialDAL.getManagerialById(id);
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
                showAlert(Alert.AlertType.ERROR, "Error", "No Managerial found with ID " + id);
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

            Managerial managerial = managerialDAL.getManagerialById(id);
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

                if (managerialDAL.updateManagerial(managerial)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Managerial updated successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update Managerial.");
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

    private void viewManagerials(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        TextField idField = new TextField();
        idField.setPromptText("Enter Managerial ID");

        Button viewByIdButton = new Button("View by ID");
        Button viewAllButton = new Button("View All");
        Button backButton = new Button("Back");

        viewByIdButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Managerial managerial = managerialDAL.getManagerialById(id);
                if (managerial != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Managerial Details", managerial.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Managerial found with ID: " + id);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format.");
            }
        });

        viewAllButton.setOnAction(e -> {
            List<Managerial> managerials = managerialDAL.getAllManagerials();
            if (managerials.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "View All Managerials",
                        "No Managerial records found.");
            } else {
                StringBuilder details = new StringBuilder();
                managerials.forEach(managerial -> details.append(managerial).append("\n"));
                showAlert(Alert.AlertType.INFORMATION, "View All Managerials", details.toString());
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

    private void deleteManagerial(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label idLabel = new Label("Enter Managerial ID:");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");

        vbox.getChildren().addAll(idLabel, idField, deleteButton);

        deleteButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            if (managerialDAL.deleteManagerial(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Managerial deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Managerial.");
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

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();

        col1.setPercentWidth(25);
        col2.setPercentWidth(25);
        col3.setPercentWidth(25);
        col4.setPercentWidth(25);

        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        return gridPane;
    }

    private TextField createTextField(GridPane gridPane, String label, int row, int col) {
        Label lbl = new Label(label);
        TextField textField = new TextField();
        gridPane.add(lbl, col * 2, row);
        gridPane.add(textField, col * 2 + 1, row);
        GridPane.setHgrow(textField, Priority.ALWAYS);
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