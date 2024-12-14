package GuestStay;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Room.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuestStayInterface extends Application {

    @Override
    public void start(Stage guestStayStage) {
        guestStayStage.setTitle("Guest Stay Booking");

        // Create a GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Input fields
        TextField idInfoField = new TextField();
        idInfoField.setPromptText("Guest CIN");

        TextField dateOfBirthField = new TextField();
        dateOfBirthField.setPromptText("Date of Birth (yyyy-MM-dd)");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField contactInfoField = new TextField();
        contactInfoField.setPromptText("Contact Info");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField guestIdField = new TextField();
        guestIdField.setPromptText("Guest ID");

        TextField bookerIdField = new TextField();
        bookerIdField.setPromptText("Booker ID");

        ComboBox<String> roomTypeBox = new ComboBox<>();
        roomTypeBox.getItems().addAll("S1", "S2", "D1", "D2", "E1", "E2", "F3", "F4");
        roomTypeBox.setPromptText("Room Type");

        TextField dateInField = new TextField();
        dateInField.setPromptText("Check-In Date (yyyy-MM-dd)");

        TextField dateOutField = new TextField();
        dateOutField.setPromptText("Check-Out Date (yyyy-MM-dd)");

        // Add components to grid
        grid.add(new Label("Guest CIN:"), 0, 0);
        grid.add(idInfoField, 1, 0);

        grid.add(new Label("Date of Birth:"), 0, 1);
        grid.add(dateOfBirthField, 1, 1);

        grid.add(new Label("Full Name:"), 0, 2);
        grid.add(fullNameField, 1, 2);

        grid.add(new Label("Contact Info:"), 0, 3);
        grid.add(contactInfoField, 1, 3);

        grid.add(new Label("Address:"), 0, 4);
        grid.add(addressField, 1, 4);

        grid.add(new Label("Guest ID:"), 0, 5);
        grid.add(guestIdField, 1, 5);

        grid.add(new Label("Booker ID:"), 0, 6);
        grid.add(bookerIdField, 1, 6);

        grid.add(new Label("Room Type:"), 0, 7);
        grid.add(roomTypeBox, 1, 7);

        grid.add(new Label("Check-In Date:"), 0, 8);
        grid.add(dateInField, 1, 8);

        grid.add(new Label("Check-Out Date:"), 0, 9);
        grid.add(dateOutField, 1, 9);

        // Button
        Button submitButton = new Button("Book Stay");
        grid.add(submitButton, 1, 10);

        // Handle button click
        submitButton.setOnAction(e -> {
            try {
                String idInfo = idInfoField.getText().trim();
                Date dateOfBirth = parseDate(dateOfBirthField.getText().trim());
                String fullName = fullNameField.getText().trim();
                String contactInfo = contactInfoField.getText().trim();
                String address = addressField.getText().trim();
                int guestId = Integer.parseInt(guestIdField.getText().trim());
                int bookerId = Integer.parseInt(bookerIdField.getText().trim());
                Room.RoomType roomType = Room.RoomType.valueOf(roomTypeBox.getValue());
                Date dateIn = parseDate(dateInField.getText().trim());
                Date dateOut = parseDate(dateOutField.getText().trim());

                if (!dateIn.before(dateOut)) {
                    throw new IllegalArgumentException("Check-In Date must be before Check-Out Date.");
                }

                GuestStay guestStay = new GuestStay(idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId,
                        roomType, dateIn, dateOut);

                GuestStayDAL.addGuestStay(guestStay);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Guest stay booked successfully!");
            } catch (IllegalArgumentException | ParseException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error occurred.");
                ex.printStackTrace();
            }
        });

        // Show scene
        Scene scene = new Scene(grid, 400, 450);
        guestStayStage.setScene(scene);
        guestStayStage.show();
    }

    private Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
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
