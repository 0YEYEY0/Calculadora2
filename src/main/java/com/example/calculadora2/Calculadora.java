package com.example.calculadora2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Esta clase representa una aplicación de calculadora con interfaz gráfica.
 */
public class Calculadora extends Application {

    private TextField textField; // Campo de texto para ingresar expresiones
    private Label labelResultado; // Etiqueta para mostrar el resultado

    /**
     * Método principal para iniciar la aplicación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método principal para configurar la interfaz gráfica de la calculadora.
     *
     * @param primaryStage La ventana principal de la aplicación.
     */
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

        // Botón para la cámara
        Button button2 = new Button("Cámara");
        button2.setLayoutX(80);
        button2.setLayoutY(50);

        // Botón para el historial
        Button button3 = new Button("Historial");
        button3.setLayoutX(150);
        button3.setLayoutY(50);

        // Acción para el Botón
        button1.setOnAction(e -> calcularExpresion());
        button2.setOnAction(e -> new camara());
        button3.setOnAction(e -> mostrarHistorial());

        // Pane
        Pane pane = new Pane();
        pane.getChildren().addAll(textField, labelResultado, button1, button2, button3);

        // Crear la escena
        Scene scene = new Scene(pane, 300, 200);

        // Agregar la escena a la ventana principal
        primaryStage.setScene(scene);

        // Mostrar la ventana
        primaryStage.show();
    }

    /**
     * Este método se utiliza para calcular una expresión ingresada por el usuario.
     */
    private void calcularExpresion() {
        String expresion = textField.getText();

        try (Socket socket = new Socket("localhost", 12345)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Enviar la expresión al servidor
            out.writeObject(expresion);

            // Recibir el resultado del servidor
            double resultado = (Double) in.readObject();

            // Mostrar el resultado en el Label
            labelResultado.setText("Resultado: " + resultado);

            // Crear una nueva entrada de registro en el historial
            Historial historial = new Historial(expresion, resultado, new Date());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método se utiliza para mostrar el historial de cálculos almacenado en un archivo CSV.
     */
    private void mostrarHistorial() {
        try {
            String csvFileName = "Historial.csv";
            File file = new File(csvFileName);
            Scanner scanner = new Scanner(file);
            StringBuilder historialText = new StringBuilder();

            while (scanner.hasNextLine()) {
                historialText.append(scanner.nextLine()).append("\n");
            }

            scanner.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Historial");
            alert.setHeaderText(null);
            alert.setContentText(historialText.toString());
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}





