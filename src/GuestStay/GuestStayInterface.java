package GuestStay;

import Main.MainInterface;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Room.Room;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GuestStayInterface extends Application {

    private static final String stylePath = "file:resources/styles.css";
    private Scene initialScene;

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

        Button addGuestStayButton = new Button("Add Customer Service");
        Button viewGuestStaysButton = new Button("View Customer Services");
        Button updateGuestStayButton = new Button("Update Customer Service");
        Button deleteGuestStayButton = new Button("Delete Customer Service");
        Button backButton = new Button("Back");

        addGuestStayButton.setOnAction(e -> addGuestStay(stage));
        viewGuestStaysButton.setOnAction(e -> viewGuestStays(stage));
        updateGuestStayButton.setOnAction(e -> updateGuestStay(stage));
        deleteGuestStayButton.setOnAction(e -> deleteGuestStay(stage));
        backButton.setOnAction(e -> goBack(stage));

        mainLayout.getChildren().addAll(title, addGuestStayButton, viewGuestStaysButton,
                updateGuestStayButton, deleteGuestStayButton, backButton);
        return mainLayout;
    }

    private void addGuestStay(Stage stage) {
        GridPane gridPane = createFormPane();
        setBackground(gridPane);

        TextField idInfoField = createTextField(gridPane, "ID Info:", 0, 0);
        TextField dateOfBirthField = createTextField(gridPane, "Date of Birth (yyyy-MM-dd):", 0, 1);
        TextField fullNameField = createTextField(gridPane, "Full Name:", 1, 0);
        TextField contactInfoField = createTextField(gridPane, "Contact Info:", 1, 1);
        TextField addressField = createTextField(gridPane, "Address:", 2, 0);
        TextField guestIdField = createTextField(gridPane, "Guest ID:", 2, 1);
        TextField bookerIdField = createTextField(gridPane, "Booker ID:", 3, 0);
        ComboBox<Room.RoomType> roomTypeBox = createRoomTypeComboBox(gridPane, "Room Type:", 3, 1);
        TextField dateInField = createTextField(gridPane, "Check-In Date (yyyy-MM-dd):", 4, 0);
        TextField dateOutField = createTextField(gridPane, "Check-Out Date (yyyy-MM-dd):", 4, 1);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);

        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 12);

        backButton.setOnAction(e -> stage.setScene(initialScene));

        submitButton.setOnAction(e -> {
            try {
                GuestStay guestStay = new GuestStay(
                        idInfoField.getText(),
                        parseDate(dateOfBirthField.getText()),
                        fullNameField.getText(),
                        contactInfoField.getText(),
                        addressField.getText(),
                        Integer.parseInt(guestIdField.getText()),
                        Integer.parseInt(bookerIdField.getText()),
                        roomTypeBox.getValue(),
                        parseDate(dateInField.getText()),
                        parseDate(dateOutField.getText())
                );

                GuestStayDAL.addGuestStay(guestStay);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Guest stay added successfully!");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(gridPane, 600, 400);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void updateGuestStay(Stage stage) {
        GridPane gridPane = createFormPane();
        setBackground(gridPane);

        TextField idField = createTextField(gridPane, "Guest ID:", 0, 0);
        Button fetchButton = new Button("Fetch Details");
        gridPane.add(fetchButton, 3, 12);

        TextField idInfoField = createTextField(gridPane, "ID Info:", 1, 0);
        TextField dateOfBirthField = createTextField(gridPane, "Date of Birth (yyyy-MM-dd):", 1, 1);
        TextField fullNameField = createTextField(gridPane, "Full Name:", 2, 0);
        TextField contactInfoField = createTextField(gridPane, "Contact Info:", 2, 1);
        TextField addressField = createTextField(gridPane, "Address:", 3, 0);
        TextField guestIdField = createTextField(gridPane, "Guest ID:", 3, 1);
        TextField bookerIdField = createTextField(gridPane, "Booker ID:", 4, 0);
        ComboBox<Room.RoomType> roomTypeBox = createRoomTypeComboBox(gridPane, "Room Type:", 4, 1);
        TextField dateInField = createTextField(gridPane, "Check-In Date (yyyy-MM-dd):", 5, 0);
        TextField dateOutField = createTextField(gridPane, "Check-Out Date (yyyy-MM-dd):", 5, 1);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 12);

        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 1, 12);

        fetchButton.setOnAction(e -> {
            try {
                int guestId = Integer.parseInt(idField.getText());
                GuestStay guestStay = GuestStayDAL.getGuestStayById(guestId);
                if (guestStay != null) {
                    idInfoField.setText(guestStay.getIdInfo());
                    dateOfBirthField.setText(formatDate(guestStay.getDateOfBirth()));
                    fullNameField.setText(guestStay.getFullName());
                    contactInfoField.setText(guestStay.getContactInfo());
                    addressField.setText(guestStay.getAddress());
                    guestIdField.setText(String.valueOf(guestStay.getGuestId()));
                    bookerIdField.setText(String.valueOf(guestStay.getBookerId()));
                    roomTypeBox.setValue(guestStay.getRoomType());
                    dateInField.setText(formatDate(guestStay.getDateIn()));
                    dateOutField.setText(formatDate(guestStay.getDateOut()));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Guest Stay found with ID " + guestId);
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        updateButton.setOnAction(e -> {
            try {
                GuestStay updatedGuestStay = new GuestStay(
                        idInfoField.getText(),
                        parseDate(dateOfBirthField.getText()),
                        fullNameField.getText(),
                        contactInfoField.getText(),
                        addressField.getText(),
                        Integer.parseInt(guestIdField.getText()),
                        Integer.parseInt(bookerIdField.getText()),
                        roomTypeBox.getValue(),
                        parseDate(dateInField.getText()),
                        parseDate(dateOutField.getText())
                );

                GuestStayDAL.updateGuestStay(updatedGuestStay);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Guest stay updated successfully!");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> stage.setScene(initialScene));

        Scene scene = new Scene(gridPane, 600, 400);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void viewGuestStays(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        setBackground(vbox);

        TextField idField = new TextField();
        idField.setPromptText("Enter Guest Stay ID");

        Button viewByIdButton = new Button("View by ID");
        Button viewAllButton = new Button("View All");
        Button backButton = new Button("Back");

        viewByIdButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                GuestStay guestStay = GuestStayDAL.getGuestStayById(id);
                if (guestStay != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Guest Stay Details", guestStay.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Guest Stay found with ID: " + id);
                }
            } catch (NumberFormatException | SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID format or database error.");
            }
        });

        viewAllButton.setOnAction(e -> {
            try {
                List<GuestStay> guestStays = GuestStayDAL.getGuestStays();
                if (guestStays.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "View All Guest Stays", "No guest stay records found.");
                } else {
                    StringBuilder details = new StringBuilder();
                    guestStays.forEach(guestStay -> details.append(guestStay).append("\n"));
                    showAlert(Alert.AlertType.INFORMATION, "View All Guest Stays", details.toString());
                }
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to retrieve guest stays: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> stage.setScene(initialScene));

        vbox.getChildren().addAll(idField, viewByIdButton, viewAllButton, backButton);

        Scene scene = new Scene(vbox, 600, 400);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void deleteGuestStay(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        setBackground(vbox);

        Label idLabel = new Label("Enter Guest Stay ID:");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");

        vbox.getChildren().addAll(idLabel, idField, deleteButton);

        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                GuestStayDAL.deleteGuestStay(id);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Guest Stay deleted successfully.");
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete guest stay: " + ex.getMessage());
            }
        });

        Button backButton = new Button("Back");
        vbox.getChildren().add(backButton);
        backButton.setOnAction(e -> stage.setScene(initialScene));

        Scene scene = new Scene(vbox, 600, 400);
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

    private ComboBox<Room.RoomType> createRoomTypeComboBox(GridPane gridPane, String label, int row, int col) {
        Label lbl = new Label(label);
        ComboBox<Room.RoomType> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(Room.RoomType.values());
        gridPane.add(lbl, col * 2, row);
        gridPane.add(comboBox, col * 2 + 1, row);
        return comboBox;
    }

    private Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    private void setBackground(Pane pane) {
        Image backgroundImage = new Image("file:resources/background.jpg");
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