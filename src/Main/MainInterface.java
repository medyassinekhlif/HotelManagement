package Main;

import Room.RoomInterface;
import GuestStay.GuestStayInterface;
import Staff.Technical.TechnicalInterface;
import Staff.Operational.OperationalInterface;
import Staff.Managerial.ManagerialInterface;
import Staff.CustomerService.CustomerServiceInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.SQLException;

public class MainInterface extends Application {

    @Override
    public void start(Stage mainStage) {
        // Set up the main layout
        StackPane root = new StackPane();

        // Add a background image
        Image backgroundImage = new Image("file:Resources/background.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        root.setBackground(new Background(bgImage));

        // Main layout using GridPane for better button alignment
        GridPane mainMenu = new GridPane();
        mainMenu.setVgap(15); // Vertical gap between elements
        mainMenu.setHgap(15); // Horizontal gap between elements
        mainMenu.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Text title = new Text("Hotel Management System");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-fill:rgb(230, 240, 255);");

        Text numberOfRoomsText = new Text();
        Text numberOfGuestsText = new Text();

        // Fetch room and guest data from database
        try {
            int numberOfRooms = MainDAL.getNumberOfRooms();
            int numberOfGuests = MainDAL.getNumberOfGuests();
            numberOfRoomsText.setText("Total Rooms: " + numberOfRooms);
            numberOfGuestsText.setText("Total Guests: " + numberOfGuests);
        } catch (SQLException e) {
            numberOfRoomsText.setText("Error fetching room data.");
            numberOfGuestsText.setText("Error fetching guest data.");
        }

        // Set number of rooms and guests text to large white font
        numberOfRoomsText.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        numberOfGuestsText.setStyle("-fx-font-size: 20px; -fx-fill: white;");

        // Button styling
        Button manageRoomsButton = new Button("Manage Rooms");
        manageRoomsButton.setOnAction(e -> openManageRooms(mainStage));
        manageRoomsButton.setPrefSize(200, 50); // Set uniform size for all buttons

        Button manageGuestStaysButton = new Button("Manage Guest Stays");
        manageGuestStaysButton.setOnAction(e -> openManageGuestStays(mainStage));
        manageGuestStaysButton.setPrefSize(200, 50);

        Button manageTechnicalStaffButton = new Button("Manage Technical Staff");
        manageTechnicalStaffButton.setOnAction(e -> openManageTechnicalStaff(mainStage));
        manageTechnicalStaffButton.setPrefSize(200, 50);

        Button manageOperationalStaffButton = new Button("Manage Operational Staff");
        manageOperationalStaffButton.setOnAction(e -> openManageOperationalStaff(mainStage));
        manageOperationalStaffButton.setPrefSize(200, 50);

        Button manageManagerialStaffButton = new Button("Manage Managerial Staff");
        manageManagerialStaffButton.setOnAction(e -> openManageManagerialStaff(mainStage));
        manageManagerialStaffButton.setPrefSize(200, 50);

        Button manageCustomerServiceStaffButton = new Button("Manage Customer Service Staff");
        manageCustomerServiceStaffButton.setOnAction(e -> openManageCustomerServiceStaff(mainStage));
        manageCustomerServiceStaffButton.setPrefSize(200, 50);

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        exitButton.setOnAction(e -> {
            System.out.println("Exiting the system. Goodbye!");
            mainStage.close();
        });
        exitButton.setPrefSize(200, 50);

        // Add elements to the GridPane
        mainMenu.add(title, 0, 0, 2, 1); // Title spanning two columns
        mainMenu.add(numberOfRoomsText, 0, 1);
        mainMenu.add(numberOfGuestsText, 1, 1);
        mainMenu.add(manageRoomsButton, 0, 2);
        mainMenu.add(manageGuestStaysButton, 1, 2);
        mainMenu.add(manageTechnicalStaffButton, 0, 3);
        mainMenu.add(manageOperationalStaffButton, 1, 3);
        mainMenu.add(manageManagerialStaffButton, 0, 4);
        mainMenu.add(manageCustomerServiceStaffButton, 1, 4);
        mainMenu.add(exitButton, 0, 5, 2, 1); // Exit button spanning two columns

        root.getChildren().add(mainMenu);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("file:resources/styles.css");

        // Configure and show the stage
        mainStage.setScene(scene);
        mainStage.setTitle("Hotel Management System");
        mainStage.show();
    }

    private void openManageRooms(Stage mainStage) {
        System.out.println("Opening Room Management...");
        RoomInterface roomInterface = new RoomInterface();
        roomInterface.start(mainStage);
    }

    private void openManageGuestStays(Stage mainStage) {
        System.out.println("Opening Guest Stays Management...");
        GuestStayInterface guestStayApp = new GuestStayInterface();
        guestStayApp.start(mainStage);
    }

    private void openManageTechnicalStaff(Stage mainStage) {
        System.out.println("Opening Technical Staff Management...");
        TechnicalInterface technicalInterface = new TechnicalInterface();
        technicalInterface.start(mainStage);
    }

    private void openManageOperationalStaff(Stage mainStage) {
        System.out.println("Opening Operational Staff Management...");
        OperationalInterface operationalInterface = new OperationalInterface();
        operationalInterface.start(mainStage);
    }

    private void openManageManagerialStaff(Stage mainStage) {
        System.out.println("Opening Managerial Staff Management...");
        ManagerialInterface managerialInterface = new ManagerialInterface();
        managerialInterface.start(mainStage);
    }

    private void openManageCustomerServiceStaff(Stage mainStage) {
        System.out.println("Opening Customer Service Staff Management...");
        CustomerServiceInterface customerServiceInterface = new CustomerServiceInterface();
        customerServiceInterface.start(mainStage);
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }
}
