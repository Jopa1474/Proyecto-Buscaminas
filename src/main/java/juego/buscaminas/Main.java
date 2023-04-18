package juego.buscaminas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import juego.buscaminas.ControlArduino.ControlArduino;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * Clase main que inicializa tanto al la clase Tablero como a la clase ControlArduino
 */
public class Main extends Application {
    static int Filas = 8;
    static int Columns = 8;
    static int Minas = 8;
    static Button[][] buttons;
    Button button;
    Button btnDummyLvl;
    Button btnAdvancedLvl;
    Button btnPista;
    Label lblCronometro;
    private int segundos = 0;
    private Timeline timeline;
    //static ControlArduino controlArduino;

    static Tablero tablero;
    @Override


    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        AnchorPane root = fxmlLoader.<AnchorPane>load();
        buttons = new Button[Filas][Columns];

        lblCronometro = new Label();
        lblCronometro.setText("0:0");
        lblCronometro.setLayoutX(515);
        lblCronometro.setLayoutY(40);
        root.getChildren().add(lblCronometro);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    segundos++;
                    int horas = segundos / 3600;
                    int minutos = (segundos % 3600) / 60;
                    int segs = segundos % 60;
                    lblCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segs));
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);


        btnDummyLvl = new Button();
        btnDummyLvl.setText("Dummy Level");
        btnDummyLvl.setLayoutX(118);
        btnDummyLvl.setLayoutY(461);
        root.getChildren().add(btnDummyLvl);
        btnDummyLvl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tablero.setDummyLevel();
            }
        });

        btnAdvancedLvl = new Button();
        btnAdvancedLvl.setText("Advanced Level");
        btnAdvancedLvl.setLayoutX(213);
        btnAdvancedLvl.setLayoutY(461);
        root.getChildren().add(btnAdvancedLvl);
        btnAdvancedLvl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        btnPista = new Button();
        btnPista.setText("5");
        btnPista.setLayoutX(504);
        btnPista.setLayoutY(461);
        root.getChildren().add(btnPista);
        btnPista.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        //https://stackoverflow.com/questions/24302636/better-way-for-getting-id-of-the-clicked-object-in-javafx-controller
        //Para asignarle un id al boton que se crea
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
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY){
                            button.setText("|>");
                        }
                    }
                });
                button.setPrefSize(40,40);
                button.setLayoutX(x);
                button.setLayoutY(y);
                root.getChildren().add(button);
                button.setId(i+","+j);

                y += 40;
            }
            x += 40;
            y = 90;
        }
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        timeline.play();
    }

    /**
     * Metodo main del proyecto Buscaminas
     * @param args
     */
    public static void main(String[] args) {
        tablero = new Tablero(Filas,Columns,Minas);
        tablero.imprimirTablero();
        Thread arduinoThread = new Thread(() -> {
            ControlArduino controlArduino = new ControlArduino();
            System.out.println("Conexión con Arduino establecida.");
        });
        arduinoThread.start();

        tablero.seteCasillaAbierta(new Consumer<Casilla>() { //Cuando se presiona una caasilla que no es mina, se realiza este evento
            @Override
            public void accept(Casilla casilla) {
                buttons[casilla.getPosicionFila()][casilla.getPosicionColumna()].setDisable(true);
                buttons[casilla.getPosicionFila()][casilla.getPosicionColumna()].setText(String.valueOf(casilla.getNumPista()));
                //controlArduino.setBuzzerCasilla(true);
                //controlArduino.setLed(true);
            }
        });
        tablero.setePartidaPerdida(new Consumer<List<Casilla>>() { //Cuando se presiona una mina, se realiza este evento de partida perdida
            @Override
            public void accept(List<Casilla> casillass) {
                for (Casilla casillaConMina: casillass){
                    buttons[casillaConMina.getPosicionFila()][casillaConMina.getPosicionColumna()].setDisable(true);
                    buttons[casillaConMina.getPosicionFila()][casillaConMina.getPosicionColumna()].setText("*");
                    //controlArduino.setBuzzerMina(true);
                    //System.exit(0);
                }
            }
        });
        launch();
    }

    /**
     * Metodo que se activa cada vez que le damos click a alguna casilla del tablero
     * @param mouseEvent
     */
    public void bntClick(ActionEvent event){
        Button btn = (Button)event.getSource(); //Obtenemos a que esta asociado el boton
        String[] coordenada = btn.getId().split(","); //Obtenemos el id del boton
        int posFila = Integer.parseInt(coordenada[0]); //Obtenemos la fila y la coordenada del boton
        int posColumna = Integer.parseInt(coordenada[1]);
        tablero.selecCasilla(posFila, posColumna); //Usamos el metodo para seleccionar casillas en base a las coordenadas obtenidas
        //tablero.turnoComputador();
        tablero.DummyLevel();
    }
}