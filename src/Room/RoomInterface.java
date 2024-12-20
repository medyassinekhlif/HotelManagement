package Room;

import Main.MainInterface;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class RoomInterface extends Application {

    private static final RoomDAL roomDAL = new RoomDAL();
    private Scene initialScene;
    private static final String stylePath = "file:resources/styles.css";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Room Management System");

        stage.setWidth(800);
        stage.setHeight(600);

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

        Label title = new Label("Room Management System");

        Button addRoomButton = new Button("Add Room");
        Button viewRoomsButton = new Button("View Rooms");
        Button updateRoomButton = new Button("Update Room");
        Button deleteRoomButton = new Button("Delete Room");
        Button backButton = new Button("Back");

        addRoomButton.setOnAction(e -> addRoom(stage));
        viewRoomsButton.setOnAction(e -> viewRooms(stage));
        updateRoomButton.setOnAction(e -> updateRoom(stage));
        deleteRoomButton.setOnAction(e -> deleteRoom(stage));
        backButton.setOnAction(e -> goBack(stage));

        mainLayout.getChildren().addAll(title, addRoomButton, viewRoomsButton,
                updateRoomButton, deleteRoomButton, backButton);
        return mainLayout;
    }

    private void addRoom(Stage stage) {
        GridPane gridPane = createFormPane();

        TextField roomTypeField = createTextField(gridPane, "Room Type:", 0, 0);
        TextField numberOfRoomsField = createTextField(gridPane, "Number of Rooms:", 0, 1);
        TextField pricePerNightField = createTextField(gridPane, "Price Per Night:", 1, 0);
        TextField roomImageField = createTextField(gridPane, "Room Image:", 1, 1);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 3);

        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 3);

        backButton.setOnAction(e -> stage.setScene(initialScene));

        submitButton.setOnAction(e -> {
            try {
                Room room = new Room(roomImageField.getText(), Double.parseDouble(pricePerNightField.getText()),
                        Room.RoomType.valueOf(roomTypeField.getText()), Integer.parseInt(numberOfRoomsField.getText()));
                
                roomDAL.addRoom(room);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Room added successfully.");
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

    private void updateRoom(Stage stage) {
        GridPane gridPane = createFormPane();

        Label roomTypeLabel = new Label("Room Type:");
        TextField roomTypeField = new TextField();
        gridPane.add(roomTypeLabel, 0, 0);
        gridPane.add(roomTypeField, 1, 0);

        Button fetchButton = new Button("Fetch Details");
        gridPane.add(fetchButton, 2, 0);

        TextField numberOfRoomsField = createTextField(gridPane, "Number of Rooms:", 0, 1);
        TextField pricePerNightField = createTextField(gridPane, "Price Per Night:", 1, 0);
        TextField roomImageField = createTextField(gridPane, "Room Image:", 1, 1);

        Button backButton = new Button("Back");
        gridPane.add(backButton, 0, 3);
        Button updateButton = new Button("Update");
        gridPane.add(updateButton, 1, 3);

        fetchButton.setOnAction(e -> {
            try {
                Room.RoomType roomType = Room.RoomType.valueOf(roomTypeField.getText());
                Room room = roomDAL.getRoomById(roomType);
                if (room != null) {
                    numberOfRoomsField.setText(String.valueOf(room.getNumberOfRooms()));
                    pricePerNightField.setText(String.valueOf(room.getPricePerNight()));
                    roomImageField.setText(room.getRoomImage());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Room found with type " + roomType);
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid room type: " + ex.getMessage());
            }
        });

        updateButton.setOnAction(e -> {
            try {
                Room updatedRoom = new Room(roomImageField.getText(), Double.parseDouble(pricePerNightField.getText()),
                        Room.RoomType.valueOf(roomTypeField.getText()), Integer.parseInt(numberOfRoomsField.getText()));
                
                roomDAL.updateRoom(Room.RoomType.valueOf(roomTypeField.getText()), updatedRoom);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Room updated successfully.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> stage.setScene(initialScene));

        setBackground(gridPane);

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void viewRooms(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        TextField roomTypeField = new TextField();
        roomTypeField.setPromptText("Enter Room Type");

        Button viewByIdButton = new Button("View by Type");
        Button viewAllButton = new Button("View All");
        Button backButton = new Button("Back");

        viewByIdButton.setOnAction(e -> {
            try {
                Room.RoomType roomType = Room.RoomType.valueOf(roomTypeField.getText());
                Room room = roomDAL.getRoomById(roomType);
                if (room != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Room Details", room.toString());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No Room found with type: " + roomType);
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid room type: " + ex.getMessage());
            }
        });

        viewAllButton.setOnAction(e -> {
            try {
                List<Room> rooms = roomDAL.getRooms();
                if (rooms.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "View All Rooms", "No room records found.");
                } else {
                    StringBuilder details = new StringBuilder();
                    rooms.forEach(room -> details.append(room).append("\n"));
                    showAlert(Alert.AlertType.INFORMATION, "View All Rooms", details.toString());
                }
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to retrieve rooms: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> stage.setScene(initialScene));

        vbox.getChildren().addAll(roomTypeField, viewByIdButton, viewAllButton, backButton);

        setBackground(vbox);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(stylePath);
        stage.setScene(scene);
        stage.show();
    }

    private void deleteRoom(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label roomTypeLabel = new Label("Enter Room Type:");
        TextField roomTypeField = new TextField();
        Button deleteButton = new Button("Delete");

        vbox.getChildren().addAll(roomTypeLabel, roomTypeField, deleteButton);

        deleteButton.setOnAction(e -> {
            try {
                Room.RoomType roomType = Room.RoomType.valueOf(roomTypeField.getText());
                roomDAL.deleteRoom(roomType);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Room deleted successfully.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid room type: " + ex.getMessage());
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

    private ComboBox<Room.RoomType> createRoomTypeComboBox(GridPane gridPane, String label, int row, int col) {
        Label lbl = new Label(label);
        ComboBox<Room.RoomType> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(Room.RoomType.values());
        gridPane.add(lbl, col * 2, row);
        gridPane.add(comboBox, col * 2 + 1, row);
        return comboBox;
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