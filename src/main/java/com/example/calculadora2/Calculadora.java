package com.example.calculadora2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Calculadora extends Application {

    private TextField textField;
    private Label labelResultado;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculadora");

        // Campo de texto
        textField = new TextField();
        textField.setPromptText("Escribe una expresión");
        textField.setLayoutX(10);
        textField.setLayoutY(10);

        // Label
        labelResultado = new Label("Resultado:");
        labelResultado.setLayoutX(10);
        labelResultado.setLayoutY(90);

        // Botón
        Button button1 = new Button("Calcular");
        button1.setLayoutX(10);
        button1.setLayoutY(50);

        Button button2 = new Button("Camara");
        button2.setLayoutX(80);
        button2.setLayoutY(50);

        Button button3 = new Button("Historial");
        button3.setLayoutX(150);
        button3.setLayoutY(50);

        // Acción para el Botón
        button1.setOnAction(e -> calcularExpresion());
        button2.setOnAction(e -> new camara());


        // Pane
        Pane pane = new Pane();
        pane.getChildren().addAll(textField, labelResultado, button1, button2,button3);
        // Crear la escena
        Scene scene = new Scene(pane, 300, 200);

        // Agregar la escena a la ventana principal
        primaryStage.setScene(scene);

        // Mostrar la ventana
        primaryStage.show();
    }

    private void calcularExpresion() {
        String expresion = textField.getText();

        try (Socket socket = new Socket("localhost", 12345)) { // Conéctate al servidor en el mismo equipo
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Enviar la expresión al servidor
            out.writeObject(expresion);

            // Recibir el resultado del servidor
            double resultado = (Double) in.readObject();

            // Mostrar el resultado en el Label
            labelResultado.setText("Resultado: " + resultado);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}



