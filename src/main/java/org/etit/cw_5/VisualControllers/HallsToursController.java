package org.etit.cw_5.VisualControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.etit.cw_5.Classes.Hall;
import org.etit.cw_5.Classes.Tour;
import org.etit.cw_5.DataBaseController;

public class HallsToursController {
    public ComboBox cbHall;
    public TableColumn tcHallsNumber;
    public TableColumn tcHallsName;
    public TableView tvHalls;
    Tour tour;
    Hall deletableHall;
    ObservableList<Hall> hallsTours;

    public void setData(Tour tour) {
        this.tour = tour;
        loadtables();
        ObservableList<String> hallNames = FXCollections.observableArrayList();
        var halls = DataBaseController.getHall();
        for(int i = 0; i < halls.size(); i++){
            hallNames.add(halls.get(i).getName());
        }
        cbHall.setItems(hallNames);
        cbHall.setValue(hallNames.get(0));

        var selectionModelHall = tvHalls.getSelectionModel();
        selectionModelHall.selectedItemProperty().addListener(new ChangeListener<Hall>() {
            @Override
            public void changed(ObservableValue<? extends Hall> observableValue, Hall hall, Hall sel) {
                if(sel!=null){
                    deletableHall = sel;
                }
            }
        });
    }

    private void loadtables(){
        tcHallsNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcHallsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        hallsTours = DataBaseController.getHallsTours(tour.getId());
        tvHalls.setItems(hallsTours);

    }
    public void btnAddHallClick(ActionEvent actionEvent) {
        boolean correct = true;
        for(int i = 0; i < hallsTours.size(); i++){
            if(hallsTours.get(i).getName().equals(cbHall.getValue().toString())){
              correct=false;
            }
        }
        if(correct){
            DataBaseController.addHallsTours(cbHall.getValue().toString(), tour.getId());
            loadtables();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Такой зал уже добавлен");
            alert.setHeaderText("Зал нельзя добавить");
            alert.setTitle("Ошибка добавления");
            alert.showAndWait();
        }
    }

    public void btnHallDeleteClick(ActionEvent actionEvent) {
        DataBaseController.deleteHallsTours(deletableHall.getId());
        loadtables();
    }
}
