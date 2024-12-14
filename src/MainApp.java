import Room.RoomInterface;  // Import RoomInterface
import GuestStay.GuestStayInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;  // Correct import for Image

public class MainApp extends Application {

    @Override
    public void start(Stage guestStayStage) {
        // Set up the main layout
        StackPane root = new StackPane();

        // Add a background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:Resources/background.jpg"),  // Path to your background image
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(backgroundImage));

        // Main interface content
        VBox mainMenu = new VBox(10);  // Spacing of 10px
        mainMenu.setStyle("-fx-alignment: center;");

        Text title = new Text("Hotel Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #1f2937;");

        Button manageRoomsButton = new Button("Manage Rooms");
        manageRoomsButton.setOnAction(e -> openManageRooms());

        Button manageGuestStaysButton = new Button("Manage Guest Stays");
        manageGuestStaysButton.setOnAction(e -> openManageGuestStays(guestStayStage));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            System.out.println("Exiting the system. Goodbye!");
            guestStayStage.close();
        });

        // Add buttons and title to the menu
        mainMenu.getChildren().addAll(title, manageRoomsButton, manageGuestStaysButton, exitButton);

        root.getChildren().add(mainMenu);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);  // Width: 800px, Height: 600px

        // Add the CSS file to the scene
        scene.getStylesheets().add("file:resources/styles.css");

        // Configure and show the stage
        guestStayStage.setScene(scene);
        guestStayStage.setTitle("Hotel Management System");
        guestStayStage.show();
    }

    private void openManageRooms() {
        System.out.println("Opening Room Management...");
        // Create a new Stage for RoomInterface
        Stage roomStage = new Stage();
        RoomInterface roomInterface = new RoomInterface();
        roomInterface.start(roomStage);  // Pass the new Stage to start() method
    }

    private void openManageGuestStays(Stage guestStayStage) {
        System.out.println("Opening Guest Stays Management...");
        // Load the Guest Stay interface
        GuestStayInterface guestStayApp = new GuestStayInterface();
        guestStayApp.start(guestStayStage);
    }

    public static void main(String[] args) {
        launch(args);  // Launch JavaFX application
    }
}
