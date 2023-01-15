package org.etit.cw_5.VisualControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.etit.cw_5.DataBaseController;

import java.time.LocalDate;

public class AddExhibitController {
    public TextField tfTitle;
    public TextField tfAuthor;
    public ComboBox cbHall;
    public DatePicker dpInPos;
    public TextField tfEow;

    public Button btnOK;
    public TextField tfType;

    public void setData(){
        ObservableList<String> hallNames = FXCollections.observableArrayList();
        var halls = DataBaseController.getHall();
        for(int i = 0; i < halls.size(); i++){
            hallNames.add(halls.get(i).getName());
        }
        cbHall.setItems(hallNames);
        cbHall.setValue(hallNames.get(0));
        dpInPos.setValue(LocalDate.now());
    }

    public void tfEowOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfTitle.getText().isEmpty() || tfAuthor.getText().isEmpty() ||
                tfType.getText().isEmpty() || tfEow.getText().isEmpty());
    }

    public void tfTypeOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfTitle.getText().isEmpty() || tfAuthor.getText().isEmpty() ||
                tfType.getText().isEmpty() || tfEow.getText().isEmpty());
    }

    public void tfTItleOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfTitle.getText().isEmpty() || tfAuthor.getText().isEmpty() ||
                tfType.getText().isEmpty() || tfEow.getText().isEmpty());
    }

    public void tfAuthorOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfTitle.getText().isEmpty() || tfAuthor.getText().isEmpty() ||
                tfType.getText().isEmpty() || tfEow.getText().isEmpty());
    }

    public void btnOkonClick(ActionEvent actionEvent) {
        Alert alert;
        int eow = Integer.parseInt(tfEow.getText());
        try{
            var correct = DataBaseController.addExhibit( tfTitle.getText(),
                    tfAuthor.getText(), cbHall.getSelectionModel().getSelectedIndex() + 1,
                    tfType.getText(), eow, dpInPos.getValue());
            if(correct){
                Stage stage = (Stage)tfTitle.getScene().getWindow();
                stage.close();
            }
        }catch (NumberFormatException e){
            alert = new Alert(Alert.AlertType.ERROR, "Номер зала не является числом");
            alert.setHeaderText("Зал нельзя изменить");
            alert.setTitle("Ошибка изменения");
            alert.showAndWait();
        }
    }
}
