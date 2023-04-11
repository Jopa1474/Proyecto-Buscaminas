package juego.buscaminas;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import juego.buscaminas.ControlArduino.InicializarControl;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class Main extends Application {
    static int Filas = 8;
    static int Columns = 8;

    static int Minas = 8;
    static Button[][] buttons;
    Button button;

    static Tablero tablero;
    @Override

    //https://stackoverflow.com/questions/24302636/better-way-for-getting-id-of-the-clicked-object-in-javafx-controller
    //Para asignarle un id al boton que se crea
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
                        bntClick(event);
                    }
                });
                button.setPrefSize(40,40);
                button.setLayoutX(x);
                button.setLayoutY(y);
                root.getChildren().add(button);
                button.setId(i+","+j);
                //button.setText(String.valueOf(i)+","+String.valueOf(j));

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
        InicializarControl inicializarControl = new InicializarControl();
        tablero = new Tablero(Filas,Columns,Minas);
        tablero.imprimirTablero();
        tablero.seteCasillaAbierta(new Consumer<Casilla>() {
            @Override
            public void accept(Casilla casilla) {
                buttons[casilla.getPosicionFila()][casilla.getPosicionColumna()].setDisable(true);
                buttons[casilla.getPosicionFila()][casilla.getPosicionColumna()].setText(String.valueOf(casilla.getNumPista()));
            }
        });
        tablero.setePartidaPerdida(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> casillass) {
                for (Casilla casillaConMina: casillass){
                    buttons[casillaConMina.getPosicionFila()][casillaConMina.getPosicionColumna()].setDisable(true);
                    buttons[casillaConMina.getPosicionFila()][casillaConMina.getPosicionColumna()].setText("*");
                }
            }
        });

        launch();

    }


    public void bntClick(ActionEvent event){
        Button btn = (Button)event.getSource();
        String[] coordenada = btn.getId().split(",");
        int posFila = Integer.parseInt(coordenada[0]);
        int posColumna = Integer.parseInt(coordenada[1]);
        System.out.println(posFila+","+posColumna);
        tablero.selecCasilla(posFila, posColumna);
    }

}