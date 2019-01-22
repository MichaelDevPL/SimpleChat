package Client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Pane paneLayout;

    @FXML
    private TextField server_ip;

    @FXML
    private TextField port;

    @FXML
    private TextField name;

    @FXML
    private String sPort;

    public void onClick() throws IOException{

        System.out.println("Clicked");

        ClientData.setIp(server_ip.getText());
        this.sPort = port.getText();
        ClientData.setName(name.getText());
        ClientData.setPort(Integer.parseInt(sPort));

        Stage stage;
        stage = (Stage) server_ip.getScene().getWindow();
        Parent root = FXMLLoader.load(LoginController.class.getResource("/fxml/room.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle(ClientData.getName());
        stage.setOnCloseRequest(e-> {
            RoomController.th.stop();
            System.exit(0);
        });
        stage.setResizable(false);

        stage.show();
    }


}
