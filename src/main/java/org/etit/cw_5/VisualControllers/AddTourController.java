package org.etit.cw_5.VisualControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.Tour;
import org.etit.cw_5.DataBaseController;

import java.time.LocalDate;

public class AddTourController {

    public ComboBox cbGuide;
    public DatePicker dpTourDate;
    public TextField tfPeople;

    public Button btnOK;
    public ComboBox cbTourType;
    private Tour dataSender;

    public void setData(){
        dpTourDate.setValue(LocalDate.now());
        ObservableList<String> guides = DataBaseController.getGuides();
        ObservableList<String> types = FXCollections.observableArrayList(
                "Утренняя",
                "Дневная",
                "Вечерняя",
                "Ночная"
        );
        cbTourType.setItems(types);
        cbTourType.setValue(types.get(0));

        cbGuide.setItems(guides);
        cbGuide.setValue(guides.get(0));
    }

    public void tfPeopleOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfPeople.getText().isEmpty());
    }

    public void tfTypeOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfPeople.getText().isEmpty());
    }

    public void btnOkonClick(ActionEvent actionEvent) {
        Alert alert;
        try{
            int people = Integer.parseInt(tfPeople.getText());
            var correct = DataBaseController.addTour(
                    cbGuide.getValue().toString(),
                    dpTourDate.getValue(), people, cbTourType.getValue().toString());
            if(correct){
                Stage stage = (Stage)tfPeople.getScene().getWindow();
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
