import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Scanner;
import Utils.DbConnection;
import Room.RoomInterface;
import GuestStay.GuestStayInterface;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args); // This will call the JavaFX start() method
    }

    @Override
    public void start(Stage primaryStage) {
        // Test the database connection and set up the UI
        if (testDatabaseConnection()) {
            System.out.println("Connected to MySQL DB");

            // Create a simple JavaFX button to simulate the menu (you can replace this with your actual GUI)
            Button btn = new Button("Click to Manage Rooms");
            btn.setOnAction(e -> {
                RoomInterface roomInterface = new RoomInterface();
                roomInterface.manageRooms();
            });

            StackPane root = new StackPane();
            root.getChildren().add(btn);
            Scene scene = new Scene(root, 300, 250);

            primaryStage.setTitle("Hotel Admin");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.out.println("Database connection failed! Exiting...");
        }
    }

    // Method to test the database connection
    private static boolean testDatabaseConnection() {
        try (Connection conn = DbConnection.getConnection()) {
            if (conn != null) {
                return true; // Connection successful
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return false; // Connection failed
    }
}
