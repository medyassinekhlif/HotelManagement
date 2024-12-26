package Main;

import Room.RoomInterface;
import GuestStay.GuestStayInterface;
import Staff.Technical.TechnicalInterface;
import Staff.Operational.OperationalInterface;
import Staff.Managerial.ManagerialInterface;
import Staff.CustomerService.CustomerServiceInterface;

import javafx.application.Application;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Map;

public class MainInterface extends Application {

    @Override
    public void start(Stage mainStage) {
        StackPane root = new StackPane();

        Image backgroundImage = new Image("file:Resources/background.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        root.setBackground(new Background(bgImage));

        GridPane mainMenu = new GridPane();
        mainMenu.setVgap(15);
        mainMenu.setHgap(15);
        mainMenu.setAlignment(Pos.CENTER);
        
        Text title = new Text("Hotel Management System");
        title.setId("title");

        
        Text numberOfCustomerServiceText = new Text();
        Text numberOfOperationalText = new Text();
        Text numberOfManagerialText = new Text();
        Text numberOfTechnicalText = new Text();
        Text numberOfGuestsText = new Text();
        Text numberOfRoomsText = new Text();

        try {
            int numberOfCustomerService = MainDAL.getNumberOfCustomerServiceRecords();
            int numberOfOperational = MainDAL.getNumberOfOperationalRecords();
            int numberOfManagerial = MainDAL.getNumberOfManagerialRecords();
            int numberOfTechnical = MainDAL.getNumberOfTechnicalRecords();
            int numberOfGuests = MainDAL.getNumberOfGuests();
            int totalNumberOfRooms = MainDAL.getNumberOfRooms();
            Map<String, Integer> roomsByType = MainDAL.getNumberOfRoomsByType();
            numberOfGuestsText.setText("Total Guests: " + numberOfGuests);

            StringBuilder roomsByTypeString = new StringBuilder("Total Rooms: " + totalNumberOfRooms + "\n");
            for (Map.Entry<String, Integer> entry : roomsByType.entrySet()) {
                roomsByTypeString.append(entry.getKey()).append(": ").append(entry.getValue()).append("  ");
            }
            numberOfRoomsText.setText(roomsByTypeString.toString().trim());

            numberOfCustomerServiceText.setText("Customer Service Staff: " + numberOfCustomerService);
            numberOfOperationalText.setText("Operational Staff: " + numberOfOperational);
            numberOfManagerialText.setText("Managerial Staff: " + numberOfManagerial);
            numberOfTechnicalText.setText("Technical Staff: " + numberOfTechnical);            
            

        } catch (SQLException e) {
            numberOfRoomsText.setText("Error fetching room data.");
            numberOfGuestsText.setText("Error fetching guest data.");
            numberOfCustomerServiceText.setText("Error fetching customer service data.");
            numberOfOperationalText.setText("Error fetching operational data.");
            numberOfManagerialText.setText("Error fetching managerial data.");
            numberOfTechnicalText.setText("Error fetching technical data.");
        }

        Button manageRoomsButton = new Button("Rooms");
        manageRoomsButton.setOnAction(e -> openManageRooms(mainStage));

        Button manageGuestStaysButton = new Button("Guest Stays");
        manageGuestStaysButton.setOnAction(e -> openManageGuestStays(mainStage));

        Button manageTechnicalStaffButton = new Button("Technical Staff");
        manageTechnicalStaffButton.setOnAction(e -> openManageTechnicalStaff(mainStage));

        Button manageOperationalStaffButton = new Button("Operational Staff");
        manageOperationalStaffButton.setOnAction(e -> openManageOperationalStaff(mainStage));

        Button manageManagerialStaffButton = new Button("Managerial Staff");
        manageManagerialStaffButton.setOnAction(e -> openManageManagerialStaff(mainStage));

        Button manageCustomerServiceStaffButton = new Button("Customer Service Staff");
        manageCustomerServiceStaffButton.setOnAction(e -> openManageCustomerServiceStaff(mainStage));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            System.out.println("Exiting the system..");
            mainStage.close();
        });

        mainMenu.add(title, 0, 0, 2, 1);
        mainMenu.add(numberOfRoomsText, 0, 1, 2, 1);
        mainMenu.add(numberOfGuestsText, 0, 2);

        mainMenu.add(numberOfCustomerServiceText, 1, 2);
        mainMenu.add(numberOfOperationalText, 0, 3);
        mainMenu.add(numberOfManagerialText, 1, 3);
        mainMenu.add(numberOfTechnicalText, 0, 4);

        mainMenu.add(manageRoomsButton, 0, 5);
        mainMenu.add(manageGuestStaysButton, 1, 5);
        mainMenu.add(manageTechnicalStaffButton, 0, 6);
        mainMenu.add(manageOperationalStaffButton, 1, 6);
        mainMenu.add(manageManagerialStaffButton, 0, 7);
        mainMenu.add(manageCustomerServiceStaffButton, 1, 7);
        mainMenu.add(exitButton, 0, 8, 2, 1);

        root.getChildren().add(mainMenu);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("file:resources/styles.css");

        mainStage.setScene(scene);
        mainStage.setTitle("Hotel Management System");
        mainStage.setWidth(1000);
        mainStage.setHeight(700);
        mainStage.setResizable(false);
        mainStage.show();
    }

    private void openManageRooms(Stage mainStage) {
        RoomInterface roomInterface = new RoomInterface();
        roomInterface.start(mainStage);
    }

    private void openManageGuestStays(Stage mainStage) {
        GuestStayInterface guestStayApp = new GuestStayInterface();
        guestStayApp.start(mainStage);
    }

    private void openManageTechnicalStaff(Stage mainStage) {
        TechnicalInterface technicalInterface = new TechnicalInterface();
        technicalInterface.start(mainStage);
    }

    private void openManageOperationalStaff(Stage mainStage) {
        OperationalInterface operationalInterface = new OperationalInterface();
        operationalInterface.start(mainStage);
    }

    private void openManageManagerialStaff(Stage mainStage) {
        ManagerialInterface managerialInterface = new ManagerialInterface();
        managerialInterface.start(mainStage);
    }

    private void openManageCustomerServiceStaff(Stage mainStage) {
        CustomerServiceInterface customerServiceInterface = new CustomerServiceInterface();
        customerServiceInterface.start(mainStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}