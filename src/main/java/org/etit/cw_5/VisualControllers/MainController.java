package org.etit.cw_5.VisualControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.Exhibit;
import org.etit.cw_5.Classes.Hall;
import org.etit.cw_5.DataBaseController;
import org.etit.cw_5.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

//    public TableView tvStaff;
//
//    public TableColumn<String, Staff>  tcStaffName;
//    public TableColumn<String, Staff> tcStaffPatr;
//    public TableColumn<String, Staff>  tcStaffSurname;
//    public TableColumn<Long, Staff>  tcStaffInn;
//    public TableColumn<String, Staff>  tcStaffPhone;
//    public TableColumn<Date, Staff> tcStaffBD;
//    public TableColumn<Character, Staff> tcStaffSex;
//    public TableColumn<String, Staff> tcStaffPost;
//    Staff staffDel;

    public TableView tvExhibits;
    public TableColumn tcExibitsTitle;
    public TableColumn tcExibitsAuthor;
    public TableColumn tcExibitsHall;
    public TableColumn tcExibitsInPos;
    public TableColumn tcExibitsEOW;
    public TableColumn tcExibitsType;
    public Button btnAddHall;
    public TextField tfHallNumber;
    public Button btnSearch;
    public TextField tfSearch;
    public Button btnAddExhibit;
    public Button btnDeleteHall;
    public Button btnDeleteExhibit;

    private Exhibit deletableExhibit;
    private Exhibit editableExhibit;

    public TableView tvHalls;
    public TableColumn tcHallsNumber;
    public TableColumn tcHallsName;
    public TextField tfHallName;
    private Hall deletableHall;
    private Hall editableHall;
    private int privilegeLevel;


    private void editExhibit() throws IOException {
        Stage editStage = new Stage();
        editStage.initModality(Modality.NONE);


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("editExhibit.fxml"));
        Parent root = fxmlLoader.load();

        EditExhibitController controller = fxmlLoader.getController();
        controller.setData(editableExhibit);

        Scene scene = new Scene(root);
        editStage.setTitle("Редактирование экспоната");
        editStage.setScene(scene);
        editStage.showAndWait();

        loadTables();
    }

    private void editHall() throws IOException {
        Stage editStage = new Stage();
        editStage.initModality(Modality.NONE);


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("editHall.fxml"));
        Parent root = fxmlLoader.load();

        EditHallController controller = fxmlLoader.getController();
        controller.setData(editableHall);

        Scene scene = new Scene(root);
        editStage.setTitle("Редактирование зала");
        editStage.setScene(scene);
        editStage.showAndWait();

        loadTables();
    }
    private void loadTables(){
        tcExibitsAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tcExibitsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcExibitsHall.setCellValueFactory(new PropertyValueFactory<>("hall"));
        tcExibitsEOW.setCellValueFactory(new PropertyValueFactory<>("eow"));
        tcExibitsInPos.setCellValueFactory(new PropertyValueFactory<>("inPos"));
        tcExibitsType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tvExhibits.setItems(DataBaseController.getExhibit());

        tcHallsNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcHallsName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tvHalls.setItems(DataBaseController.getHall());
    }

    public void btnHallDeleteClick(ActionEvent actionEvent) {
        if(deletableHall!=null){
            var correct = DataBaseController.deleteHall(deletableHall.getId());
            if(!correct){
                Alert alert = new Alert(Alert.AlertType.ERROR, "В зале имеются экспонаты");
                alert.setHeaderText("Зал нельзя удалить");
                alert.setTitle("Ошибка удаления");
                alert.showAndWait();
            }
            deletableHall = null;
            loadTables();
        }
    }
    public void btnExhibitDeleteClick(ActionEvent actionEvent) {
        if(deletableExhibit !=null){
            DataBaseController.deleteExhibit(deletableExhibit.getId());
            deletableExhibit = null;
            loadTables();
        }
    }

    public void btnAddHallClick(ActionEvent actionEvent) {
        int id;
        Alert alert;
        try{
            id=Integer.parseInt(tfHallNumber.getText());
            var correct = DataBaseController.addHall(Integer.parseInt(tfHallNumber.getText()), tfHallName.getText());
            if(correct){
                tfHallName.setText("");
                tfHallNumber.setText("");
                loadTables();
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR, "Зал с таким номером существует");
                alert.setHeaderText("Зал нельзя добавить");
                alert.setTitle("Ошибка добавления");
                alert.showAndWait();
            }
        }catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR, "Номер зала не является числом");
            alert.setHeaderText("Зал нельзя добавить");
            alert.setTitle("Ошибка добавления");
            alert.showAndWait();
        }
    }

    public void tfHallNameKeyReleased(KeyEvent keyEvent) {
        btnAddHall.setDisable(tfHallName.getText().isEmpty() || tfHallNumber.getText().isEmpty());
    }

    public void tfHallNumberKeyReleased(KeyEvent keyEvent) {
        btnAddHall.setDisable(tfHallName.getText().isEmpty() || tfHallNumber.getText().isEmpty());
    }

    public void btnAddOnClick(ActionEvent actionEvent) throws IOException {
        Stage editStage = new Stage();
        editStage.initModality(Modality.NONE);


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addExhibit.fxml"));
        Parent root = fxmlLoader.load();

        AddExhibitController controller = fxmlLoader.getController();
        controller.setData();

        Scene scene = new Scene(root);
        editStage.setTitle("Добавление экспоната");
        editStage.setScene(scene);
        editStage.showAndWait();

        loadTables();
    }

    public void btnSearchOnClick(ActionEvent actionEvent) {
        String searchtext = tfSearch.getText();
        ObservableList<Exhibit> exh = tvExhibits.getItems();
        exh.stream()
                .filter(item -> item.getTitle().matches("(.*)"+searchtext+"(.*)"))
                .findAny()
                .ifPresent(item -> {
                    tvExhibits.getSelectionModel().select(item);
                    tvExhibits.scrollTo(item);
                });

    }

    public void tfSearchOnKeyRealesed(KeyEvent keyEvent) {
        btnSearch.setDisable(tfSearch.getText().isEmpty());
    }

    public void setData(int ans) {
        privilegeLevel=ans;
        if(privilegeLevel==1){
            tfHallNumber.setVisible(false);
            tfHallName.setVisible(false);
            btnAddHall.setVisible(false);
            btnDeleteExhibit.setVisible(false);
            btnDeleteHall.setVisible(false);
            btnAddExhibit.setVisible(false);
        }
        else if(privilegeLevel==0){
            tvHalls.setRowFactory(tv -> {
                TableRow<Hall> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        editableHall = row.getItem();
                        try {
                            editHall();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                return row ;
            });

            tvExhibits.setRowFactory(tv -> {
                TableRow<Exhibit> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        editableExhibit = row.getItem();
                        try {
                            editExhibit();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                return row ;
            });
        }

        loadTables();

        var selectionModelHall = tvHalls.getSelectionModel();
        selectionModelHall.selectedItemProperty().addListener(new ChangeListener<Hall>() {
            @Override
            public void changed(ObservableValue<? extends Hall> observableValue, Hall hall, Hall sel) {
                if(sel!=null){
                    deletableHall = sel;
                }
            }
        });

        var selectionModelExhibit = tvExhibits.getSelectionModel();
        selectionModelExhibit.selectedItemProperty().addListener(new ChangeListener<Exhibit>() {
            public void changed(ObservableValue<? extends Exhibit> observableValue, Exhibit exhibit, Exhibit sel) {
                if(sel!=null){
                    deletableExhibit = sel;
                }
            }
        });
    }
}