package org.etit.cw_5.VisualControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.User;
import org.etit.cw_5.DataBaseController;
import org.etit.cw_5.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;

    @FXML
    protected void onEnterButtonClick() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        User user = DataBaseController.authorisation(username, password);
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("main.fxml"));
        Parent root = fxmlLoader.load();

        MainController controller = fxmlLoader.getController();
        controller.setData(user);

        Scene scene = new Scene(root);
        Image applicationIcon = new Image(Main.class.getResourceAsStream("app_icon_32x32.png"));
        newStage.getIcons().add(applicationIcon);

        if(user.getPrivilege()!=-1){
            if(user.getPrivilege()==0){
                newStage.setTitle("Режим администратора");
                newStage.setScene(scene);
                newStage.show();
                stage.close();
            }
            else if(user.getPrivilege()==1){
                newStage.setTitle("Режим пользователя");
                newStage.setScene(scene);
            }

            newStage.show();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Проверьте корректность введенных данных");
            alert.setHeaderText("Неверные данные");
            alert.setTitle("Ошибка входа");
            alert.showAndWait();
        }

    }


    public void onRegistrationLinkClick() throws IOException {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Регистрация");
        stage.setScene(scene);
    }
}
