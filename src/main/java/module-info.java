module juego.buscaminas {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.desktop;


    opens juego.buscaminas to javafx.fxml;
    exports juego.buscaminas;
}