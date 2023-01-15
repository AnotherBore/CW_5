module org.etit.cw_5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.etit.cw_5 to javafx.fxml;
    opens org.etit.cw_5.Classes to javafx.base;
    exports org.etit.cw_5;
    exports org.etit.cw_5.VisualControllers;
    opens org.etit.cw_5.VisualControllers to javafx.fxml;
}