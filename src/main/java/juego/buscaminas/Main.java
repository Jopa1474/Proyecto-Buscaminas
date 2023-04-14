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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import juego.buscaminas.ControlArduino.ControlArduino;
import juego.buscaminas.ControlArduino.InicializarControl;

import javax.swing.*;
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
    static ControlArduino controlArduino;

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

        timeline.play();
    }

    public static void main(String[] args) {
        InicializarControl inicializarControl = new InicializarControl();
        tablero = new Tablero(Filas,Columns,Minas);
        tablero.imprimirTablero();
        Thread arduinoThread = new Thread(() -> {
            controlArduino = new ControlArduino();
            System.out.println("Conexión con Arduino establecida.");
        });
        arduinoThread.start();

        tablero.seteCasillaAbierta(new Consumer<Casilla>() {
            @Override
            public void accept(Casilla casilla) {
                buttons[casilla.getPosicionFila()][casilla.getPosicionColumna()].setDisable(true);
                buttons[casilla.getPosicionFila()][casilla.getPosicionColumna()].setText(String.valueOf(casilla.getNumPista()));
                //controlArduino.setBuzzerCasilla(true);
                //controlArduino.setLed(true);
            }
        });
        tablero.setePartidaPerdida(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> casillass) {
                for (Casilla casillaConMina: casillass){
                    buttons[casillaConMina.getPosicionFila()][casillaConMina.getPosicionColumna()].setDisable(true);
                    buttons[casillaConMina.getPosicionFila()][casillaConMina.getPosicionColumna()].setText("*");
                    //controlArduino.setBuzzerMina(true);
                    JOptionPane.showMessageDialog(null,"Has perdido!");
                    System.exit(0);

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
        //System.out.println(posFila+","+posColumna);
        tablero.selecCasilla(posFila, posColumna);
        tablero.DummyLevel();
    }

    //https://www.youtube.com/watch?v=pD-cWNrQogY&t=14s&ab_channel=latincoder
    //Codigo cronometro
    private static void delaySegundo(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void Cronometro(){
        int minutos = 0;
        int segundos = 0;
        for (minutos = 0; minutos < 60; minutos++){
            for (segundos = 0; segundos < 60; segundos++) {

                delaySegundo();
            }
        }
    }



}