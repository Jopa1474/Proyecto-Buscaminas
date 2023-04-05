package juego.buscaminas;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    int Filas = 8;
    int Columns = 8;
    Button[][] buttons;
    Button button;
    Casilla[][] casillas;
    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        AnchorPane root = fxmlLoader.<AnchorPane>load();
        buttons = new Button[Filas][Columns];

        int x = 140;
        int y = 90;
        // Crear los botones y agregarlos al GridPane
        for (int i = 0; i < Filas; i++) {
            for (int j = 0; j < Columns; j++) {

                button = new Button();
                buttons[i][j] = button;
                int finalI = i;
                int finalJ = j;
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (casillas[finalI][finalJ].isMina()){
                            button.setText("*");
                        }else{
                            button.setText("1");
                        }
                    }
                });
                button.setPrefSize(40,40);
                button.setLayoutX(x);
                button.setLayoutY(y);
                root.getChildren().add(button);
                button.setText(String.valueOf(i)+","+String.valueOf(j));

                y += 40;

            }
            x += 40;
            y = 90;
        }
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        Tablero tablero = new Tablero(8,8,8);
        tablero.imprimirTablero();
    }
}