package Room;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RoomInterface extends Application {

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Room Management");

        // Create the main layout
        VBox layout = new VBox(10);

        // Create buttons for different actions
        Button addButton = new Button("Add Room");
        Button updateButton = new Button("Update Room");
        Button deleteButton = new Button("Delete Room");
        Button viewButton = new Button("View All Rooms");
        Button backButton = new Button("Back to Main Menu");

        // Set up button actions
        addButton.setOnAction(e -> showAddRoomWindow());
        updateButton.setOnAction(e -> showUpdateRoomWindow());
        deleteButton.setOnAction(e -> showDeleteRoomWindow());
        viewButton.setOnAction(e -> showViewRoomsWindow());
        backButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(addButton, updateButton, deleteButton, viewButton, backButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddRoomWindow() {
        Stage addStage = new Stage();
        addStage.setTitle("Add Room");

        VBox layout = new VBox(10);
        TextField idField = new TextField();
        idField.setPromptText("Enter Room ID");

        TextField typeField = new TextField();
        typeField.setPromptText("Enter Room Type (S1/S2/D1/D2/E1/E2/F3/F4)");

        TextField numberOfRoomsField = new TextField();
        numberOfRoomsField.setPromptText("Enter Number of Rooms");

        TextField priceField = new TextField();
        priceField.setPromptText("Enter Price Per Night");

        TextField imageField = new TextField();
        imageField.setPromptText("Enter Room Image URL");

        Button addButton = new Button("Add Room");
        addButton.setOnAction(e -> {
            try {
                addRoom(idField.getText(), typeField.getText(), numberOfRoomsField.getText(), priceField.getText(), imageField.getText());
                addStage.close();
            } catch (SQLException ex) {
                showError("Database Error: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, typeField, numberOfRoomsField, priceField, imageField, addButton);

        Scene scene = new Scene(layout, 300, 250);
        addStage.setScene(scene);
        addStage.show();
    }

    private void addRoom(String id, String type, String numberOfRoomsStr, String pricePerNightStr, String roomImage) throws SQLException {
        Room.RoomType roomType = Room.RoomType.valueOf(type.toUpperCase());
        int numberOfRooms = Integer.parseInt(numberOfRoomsStr);
        double pricePerNight = Double.parseDouble(pricePerNightStr);

        Room room = new Room(id, roomImage, pricePerNight, roomType, numberOfRooms);
        RoomDAL.addRoom(room);
        showInfo("Room added successfully!");
    }

    private void showUpdateRoomWindow() {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Room");

        VBox layout = new VBox(10);
        TextField idField = new TextField();
        idField.setPromptText("Enter Room ID");

        TextField typeField = new TextField();
        typeField.setPromptText("Enter Room Type (S1/S2/D1/D2/E1/E2/F3/F4)");

        TextField numberOfRoomsField = new TextField();
        numberOfRoomsField.setPromptText("Enter Updated Number of Rooms");

        TextField priceField = new TextField();
        priceField.setPromptText("Enter Updated Price Per Night");

        TextField imageField = new TextField();
        imageField.setPromptText("Enter Updated Room Image URL");

        Button updateButton = new Button("Update Room");
        updateButton.setOnAction(e -> {
            try {
                updateRoom(idField.getText(), typeField.getText(), numberOfRoomsField.getText(), priceField.getText(), imageField.getText());
                updateStage.close();
            } catch (SQLException ex) {
                showError("Database Error: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, typeField, numberOfRoomsField, priceField, imageField, updateButton);

        Scene scene = new Scene(layout, 300, 250);
        updateStage.setScene(scene);
        updateStage.show();
    }

    private void updateRoom(String roomId, String type, String numberOfRoomsStr, String pricePerNightStr, String roomImage) throws SQLException {
        Room.RoomType roomType = Room.RoomType.valueOf(type.toUpperCase());
        int numberOfRooms = Integer.parseInt(numberOfRoomsStr);
        double pricePerNight = Double.parseDouble(pricePerNightStr);

        Room updatedRoom = new Room(roomId, roomImage, pricePerNight, roomType, numberOfRooms);
        RoomDAL.updateRoom(roomId, updatedRoom);
        showInfo("Room updated successfully!");
    }

    private void showDeleteRoomWindow() {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete Room");

        VBox layout = new VBox(10);
        TextField idField = new TextField();
        idField.setPromptText("Enter Room ID to delete");

        Button deleteButton = new Button("Delete Room");
        deleteButton.setOnAction(e -> {
            try {
                deleteRoom(idField.getText());
                deleteStage.close();
            } catch (SQLException ex) {
                showError("Database Error: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, deleteButton);

        Scene scene = new Scene(layout, 300, 150);
        deleteStage.setScene(scene);
        deleteStage.show();
    }

    private void deleteRoom(String roomId) throws SQLException {
        RoomDAL.deleteRoom(roomId);
        showInfo("Room deleted successfully!");
    }

    private void showViewRoomsWindow() {
        Stage viewStage = new Stage();
        viewStage.setTitle("View All Rooms");

        VBox layout = new VBox(10);
        ListView<String> listView = new ListView<>();

        try {
            for (Room room : RoomDAL.getRooms()) {
                listView.getItems().add(room.toString());
            }
        } catch (SQLException ex) {
            showError("Database Error: " + ex.getMessage());
        }

        layout.getChildren().add(listView);

        Scene scene = new Scene(layout, 400, 300);
        viewStage.setScene(scene);
        viewStage.show();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
