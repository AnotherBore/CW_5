package org.etit.cw_5.VisualControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.Exhibit;
import org.etit.cw_5.Classes.Hall;
import org.etit.cw_5.Classes.Tour;
import org.etit.cw_5.Classes.User;
import org.etit.cw_5.DataBaseController;
import org.etit.cw_5.Main;

import java.io.IOException;

public class MainController {
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
    public TableView tvTours;
    public TableColumn tcTourGuide;
    public TableColumn tcTourDate;
    public TableColumn tcTourPeople;
    public TableColumn tcTourType;
    public Button btnAddTour;
    public Button btnDeleteTour;
    private Tour deletableTour;
    private Tour editableTour;

    private Exhibit deletableExhibit;
    private Exhibit editableExhibit;

    public TableView tvHalls;
    public TableColumn tcHallsNumber;
    public TableColumn tcHallsName;
    public TextField tfHallName;
    private Hall deletableHall;
    private Hall editableHall;
    private User user;
    private boolean isGuide;

    public void setData(User user) {
        this.user=user;
        if(user.getPrivilege()==1){
            tfHallNumber.setVisible(false);
            tfHallName.setVisible(false);
            btnAddHall.setVisible(false);
            btnDeleteExhibit.setVisible(false);
            btnDeleteHall.setVisible(false);
            btnAddExhibit.setVisible(false);
            btnAddTour.setVisible(false);
            btnDeleteTour.setVisible(false);

        }
        else if(user.getPrivilege()==0){
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

            tvTours.setRowFactory(tv -> {
                TableRow<Tour> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        editableTour = row.getItem();
                        try {
                            editTour();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }else if(event.getButton() == MouseButton.SECONDARY && (! row.isEmpty()) && isGuide){
                        editableTour = row.getItem();
                        try {
                            showThemes();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                return row ;
            });

            var selectionModelHall = tvHalls.getSelectionModel();
            selectionModelHall.selectedItemProperty().addListener(new ChangeListener<Hall>() {
                @Override
                public void changed(ObservableValue<? extends Hall> observableValue, Hall hall, Hall sel) {
                    if(sel!=null){
                        deletableHall = sel;
                    }
                }
            });

            var selectionModelTours = tvTours.getSelectionModel();
            selectionModelTours.selectedItemProperty().addListener(new ChangeListener<Tour>() {
                @Override
                public void changed(ObservableValue<? extends Tour> observableValue, Tour hall, Tour sel) {
                    if(sel!=null){
                        deletableTour = sel;
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

        loadTables();

        tvTours.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.SECONDARY && (! row.isEmpty()) && isGuide){
                    editableTour = row.getItem();
                    try {
                        showThemes();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }

    private void showThemes() throws IOException{
        Stage editStage = new Stage();
        editStage.initModality(Modality.NONE);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("showThemes.fxml"));
        Parent root = fxmlLoader.load();

        ShowThemesController controller = fxmlLoader.getController();
        controller.setData(editableTour);

        Scene scene = new Scene(root);
        editStage.setTitle("Охват залов");
        editStage.setScene(scene);
        editStage.showAndWait();

        loadTables();
    }

    private void editTour() throws IOException{
        Stage editStage = new Stage();
        editStage.initModality(Modality.NONE);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("editTour.fxml"));
        Parent root = fxmlLoader.load();

        EditTourController controller = fxmlLoader.getController();
        controller.setData(editableTour);

        Scene scene = new Scene(root);
        editStage.setTitle("Редактирование экскурсии");
        editStage.setScene(scene);
        editStage.showAndWait();

        loadTables();
    }

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

        tcTourGuide.setCellValueFactory(new PropertyValueFactory<>("guide"));
        tcTourDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcTourPeople.setCellValueFactory(new PropertyValueFactory<>("people"));
        tcTourType.setCellValueFactory(new PropertyValueFactory<>("type"));

        isGuide = DataBaseController.isGuide(user.getStaff());
        if(isGuide){
            tvTours.setItems(DataBaseController.getTours(user.getStaff()));
        }else{
            tvTours.setItems(DataBaseController.getTours());
        }

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
            var correct = DataBaseController.addHall(id, tfHallName.getText());
            if(correct){
                tfHallName.setText("");
                tfHallNumber.setText("");
                btnAddHall.setDisable(true);
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

    public void btnTourDeleteClick(ActionEvent actionEvent) {
        if(deletableTour !=null){
            DataBaseController.deleteTour(deletableTour.getId());
            deletableTour = null;
            loadTables();
        }
    }

    public void btnAddTourOnClick(ActionEvent actionEvent) throws IOException {
        Stage editStage = new Stage();
        editStage.initModality(Modality.NONE);


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addTour.fxml"));
        Parent root = fxmlLoader.load();

        AddTourController controller = fxmlLoader.getController();
        controller.setData();

        Scene scene = new Scene(root);
        editStage.setTitle("Добавление экскурсии");
        editStage.setScene(scene);
        editStage.showAndWait();

        loadTables();
    }
}