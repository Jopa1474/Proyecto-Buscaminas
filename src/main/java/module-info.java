module juego.buscaminas {
    requires javafx.controls;
    requires javafx.fxml;


    opens juego.buscaminas to javafx.fxml;
    exports juego.buscaminas;
}