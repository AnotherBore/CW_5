package org.etit.cw_5.VisualControllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.Hall;
import org.etit.cw_5.DataBaseController;

import java.net.URL;
import java.util.ResourceBundle;

public class EditHallController {

    public int number;
    public String name;
    public TextField tfHallName;
    public TextField tfHallNumber;
    public Button btnOK;
    private Hall dataSender;

    public void setData(Hall hall){
        this.dataSender = hall;
        tfHallNumber.setText(String.valueOf(dataSender.getId()));
        tfHallName.setText(dataSender.getName());
    }

    public void btnOkClick(ActionEvent actionEvent) {
        Alert alert;
        try{
            number = Integer.parseInt(tfHallNumber.getText());
            name = tfHallName.getText();
            var correct = DataBaseController.editHall(dataSender.getId(), number, name);
            if(correct){
                Stage stage = (Stage)tfHallName.getScene().getWindow();
                stage.close();
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR, "Зал с таким номером существует");
                alert.setHeaderText("Зал нельзя изменить");
                alert.setTitle("Ошибка изменения");
                alert.showAndWait();
            }
        }catch (NumberFormatException e){
            alert = new Alert(Alert.AlertType.ERROR, "Номер зала не является числом");
            alert.setHeaderText("Зал нельзя изменить");
            alert.setTitle("Ошибка изменения");
            alert.showAndWait();
        }
    }


    public void tfHallNumberKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfHallName.getText().isEmpty() || tfHallNumber.getText().isEmpty());
    }

    public void tfHallNameKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfHallName.getText().isEmpty() || tfHallNumber.getText().isEmpty());
    }
}
