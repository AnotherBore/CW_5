package org.etit.cw_5.VisualControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.Tour;
import org.etit.cw_5.DataBaseController;

public class EditTourController {

    public Button btnOK;
    public TextField tfPeople;
    public DatePicker dpTourDate;
    public ComboBox cbGuide;
    public ComboBox cbTourType;

    private Tour dataSender;

    public void setData(Tour tour){
        this.dataSender = tour;
        tfPeople.setText(String.valueOf(dataSender.getPeople()));
        dpTourDate.setValue(dataSender.getDate().toLocalDate());
        ObservableList<String> guides = DataBaseController.getGuides();
        ObservableList<String> types = FXCollections.observableArrayList(
                "Утренняя",
                "Дневная",
                "Вечерняя",
                "Ночная"
        );
        cbTourType.setItems(types);
        cbTourType.setValue(dataSender.getType());
        cbGuide.setItems(guides);
        cbGuide.setValue(dataSender.getGuide());
    }


    public void btnOkonClick(ActionEvent actionEvent) {
        Alert alert;

        try{
            int people = Integer.parseInt(tfPeople.getText());
            var correct = DataBaseController.updateTour(dataSender.getId(),
                    cbGuide.getValue().toString(),
                    dpTourDate.getValue(), people, cbTourType.getValue().toString());
            if(correct){
                Stage stage = (Stage)tfPeople.getScene().getWindow();
                stage.close();
            }
        }catch (NumberFormatException e){
            alert = new Alert(Alert.AlertType.ERROR, "Количество посетителей не является числом");
            alert.setHeaderText("Экскурсию нельзя изменить");
            alert.setTitle("Ошибка изменения");
            alert.showAndWait();
        }
    }

    public void tfTypeOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfPeople.getText().isEmpty());
    }

    public void tfPeopleOnKeyRealeased(KeyEvent keyEvent) {
        btnOK.setDisable(tfPeople.getText().isEmpty());
    }
}
