package org.etit.cw_5.VisualControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.etit.cw_5.Classes.Tour;
import org.etit.cw_5.DataBaseController;

public class ShowThemesController {
    public TextArea taHalls;

    public void setData(Tour tour){
        String themes = DataBaseController.showHalls(tour.getId());
        if(themes.isEmpty()){
            taHalls.setText("Это экскурсия без посещения залов");
        }else{
            taHalls.setText(themes);
        }
    }

    public void btnOkOnCLick(ActionEvent actionEvent) {
        Stage stage = (Stage)taHalls.getScene().getWindow();
        stage.close();
    }
}
