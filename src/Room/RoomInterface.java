package Room;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RoomInterface {

    private final RoomDAL roomDAL = new RoomDAL();

    public void start(Stage mainStage) {
        mainStage.setTitle("Room Management");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER); // Center the buttons

        // Create buttons
        Button addRoomButton = new Button("Add Room");
        Button deleteRoomButton = new Button("Delete Room");
        Button updateRoomButton = new Button("Update Room");

        // Set button size to be the same for all
        addRoomButton.setPrefSize(200, 50);
        deleteRoomButton.setPrefSize(200, 50);
        updateRoomButton.setPrefSize(200, 50);

        // Set button actions
        addRoomButton.setOnAction(e -> showAddRoomWindow());
        deleteRoomButton.setOnAction(e -> showDeleteRoomWindow());
        updateRoomButton.setOnAction(e -> showUpdateRoomWindow());

        mainLayout.getChildren().addAll(addRoomButton, deleteRoomButton, updateRoomButton);

        // Create an Image object and set the background image
        Image backgroundImage = new Image("file:Resources/background.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        mainLayout.setBackground(new Background(bgImage));

        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add("file:resources/styles.css"); // External CSS file
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showAddRoomWindow() {
        Stage stage = new Stage();
        stage.setTitle("Add Room");

        GridPane gridPane = createGridPane();

        ComboBox<Room.RoomType> roomTypeComboBox = new ComboBox<>();
        roomTypeComboBox.getItems().setAll(Room.RoomType.values());
        roomTypeComboBox.setValue(Room.RoomType.S1); // Default to S1

        TextField numberOfRoomsField = new TextField();
        TextField pricePerNightField = new TextField();
        TextField roomImageField = new TextField();

        // Style for input fields
        numberOfRoomsField.setStyle("-fx-font-size: 16px;");
        pricePerNightField.setStyle("-fx-font-size: 16px;");
        roomImageField.setStyle("-fx-font-size: 16px;");
        numberOfRoomsField.setPrefSize(250, 30);
        pricePerNightField.setPrefSize(250, 30);
        roomImageField.setPrefSize(250, 30);

        gridPane.add(new Label("Room Type (Primary Key):"), 0, 0);
        gridPane.add(roomTypeComboBox, 1, 0);
        gridPane.add(new Label("Number of Rooms:"), 0, 1);
        gridPane.add(numberOfRoomsField, 1, 1);
        gridPane.add(new Label("Price per Night:"), 0, 2);
        gridPane.add(pricePerNightField, 1, 2);
        gridPane.add(new Label("Room Image URL:"), 0, 3);
        gridPane.add(roomImageField, 1, 3);

        Button addButton = new Button("Add Room");
        addButton.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
        addButton.setPrefSize(250, 60);
        gridPane.add(addButton, 1, 4);

        addButton.setOnAction(e -> {
            try {
                Room.RoomType roomType = roomTypeComboBox.getValue();
                String numberOfRooms = numberOfRoomsField.getText();
                String pricePerNight = pricePerNightField.getText();
                String roomImage = roomImageField.getText();

                if (roomType == null || numberOfRooms.isEmpty() || pricePerNight.isEmpty() || roomImage.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
                    return;
                }

                Room newRoom = new Room(roomImage, Double.parseDouble(pricePerNight), roomType,
                        Integer.parseInt(numberOfRooms));
                roomDAL.addRoom(newRoom);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Room added successfully.");
                stage.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add room: " + ex.getMessage());
            }
        });

        stage.setScene(new Scene(gridPane, 400, 300));
        stage.show();
    }

    public void showDeleteRoomWindow() {
        Stage stage = new Stage();
        stage.setTitle("Delete Room");

        GridPane gridPane = createGridPane();

        ComboBox<Room.RoomType> roomTypeComboBox = new ComboBox<>();
        roomTypeComboBox.getItems().setAll(Room.RoomType.values());
        roomTypeComboBox.setValue(Room.RoomType.S1); // Default to S1

        gridPane.add(new Label("Room Type (Primary Key):"), 0, 0);
        gridPane.add(roomTypeComboBox, 1, 0);

        Button deleteButton = new Button("Delete Room");
        deleteButton.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setPrefSize(250, 60);
        gridPane.add(deleteButton, 1, 1);

        deleteButton.setOnAction(e -> {
            try {
                Room.RoomType roomType = roomTypeComboBox.getValue();

                if (roomType == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Room Type is required.");
                    return;
                }

                roomDAL.deleteRoom(roomType);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Room deleted successfully.");
                stage.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete room: " + ex.getMessage());
            }
        });

        stage.setScene(new Scene(gridPane, 400, 200));
        stage.show();
    }

    public void showUpdateRoomWindow() {
        Stage stage = new Stage();
        stage.setTitle("Update Room");

        GridPane gridPane = createGridPane();

        ComboBox<Room.RoomType> roomTypeComboBox = new ComboBox<>();
        roomTypeComboBox.getItems().setAll(Room.RoomType.values());
        roomTypeComboBox.setValue(Room.RoomType.S1); // Default to S1

        TextField numberOfRoomsField = new TextField();
        TextField pricePerNightField = new TextField();
        TextField roomImageField = new TextField();

        // Style for input fields
        numberOfRoomsField.setStyle("-fx-font-size: 16px;");
        pricePerNightField.setStyle("-fx-font-size: 16px;");
        roomImageField.setStyle("-fx-font-size: 16px;");
        numberOfRoomsField.setPrefSize(250, 30);
        pricePerNightField.setPrefSize(250, 30);
        roomImageField.setPrefSize(250, 30);

        gridPane.add(new Label("Room Type (Primary Key):"), 0, 0);
        gridPane.add(roomTypeComboBox, 1, 0);
        gridPane.add(new Label("Number of Rooms:"), 0, 1);
        gridPane.add(numberOfRoomsField, 1, 1);
        gridPane.add(new Label("Price per Night:"), 0, 2);
        gridPane.add(pricePerNightField, 1, 2);
        gridPane.add(new Label("Room Image URL:"), 0, 3);
        gridPane.add(roomImageField, 1, 3);

        Button updateButton = new Button("Update Room");
        updateButton.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setPrefSize(250, 60);
        gridPane.add(updateButton, 1, 4);

        updateButton.setOnAction(e -> {
            try {
                Room.RoomType roomType = roomTypeComboBox.getValue();
                String numberOfRooms = numberOfRoomsField.getText();
                String pricePerNight = pricePerNightField.getText();
                String roomImage = roomImageField.getText();

                if (roomType == null || numberOfRooms.isEmpty() || pricePerNight.isEmpty() || roomImage.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
                    return;
                }

                Room updatedRoom = new Room(roomImage, Double.parseDouble(pricePerNight), roomType,
                        Integer.parseInt(numberOfRooms));
                roomDAL.updateRoom(roomType, updatedRoom);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Room updated successfully.");
                stage.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update room: " + ex.getMessage());
            }
        });

        stage.setScene(new Scene(gridPane, 400, 300));
        stage.show();
    }

    // Helper method to create a common grid pane layout
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        return gridPane;
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
