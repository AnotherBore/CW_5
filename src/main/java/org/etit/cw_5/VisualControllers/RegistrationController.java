package org.etit.cw_5.VisualControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.User;
import org.etit.cw_5.DataBaseController;
import org.etit.cw_5.Main;

import java.io.IOException;

public class RegistrationController {
    @FXML
    private Label lblError;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtPasswordSecond;
    @FXML
    private TextField txtUsername;


    public void onRegistrationButtonClick() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String passwordSecond = txtPasswordSecond.getText();

        if(username.isEmpty() || password.isEmpty() || !password.equals(passwordSecond)){
            lblError.setText("Проверьте данные в полях");
            return;
        }
        User user = new User(username, password, (byte) 1);
        DataBaseController.addUser(user);

        Stage stage = (Stage) txtUsername.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Вход");
        stage.setScene(scene);
    }
}
