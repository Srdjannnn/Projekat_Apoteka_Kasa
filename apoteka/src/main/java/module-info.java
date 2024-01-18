module apoteka {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop; 
    requires java.sql; 
    opens apoteka to javafx.fxml;
    exports apoteka;
}
