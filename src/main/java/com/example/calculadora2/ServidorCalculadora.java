package com.example.calculadora2;

import com.example.calculadora2.ArbolExpresion;
import com.example.calculadora2.Historial;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ServidorCalculadora {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor en línea. Esperando conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());
                // Manejar la solicitud del cliente en un nuevo hilo
                new ClienteHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClienteHandler extends Thread {
        private final Socket clientSocket;

        public ClienteHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ) {
                while (true) {
                    String expresion = (String) in.readObject();

                    if (expresion.equalsIgnoreCase("exit")) {
                        break; // Terminar el hilo si el cliente envía "exit"
                    }

                    // Evaluar la expresión y enviar el resultado al cliente
                    ArbolExpresion arbol = new ArbolExpresion(expresion);
                    double resultado = arbol.evaluar();
                    out.writeObject(resultado);

                    // Crear una nueva entrada de registro
                    Historial historial = new Historial(expresion, resultado, new Date());

                    // Guardar la entrada de registro en un archivo CSV en el servidor
                    guardarRegistroEnCSVEnServidor(historial);
                }

                System.out.println("Cliente desconectado desde " + clientSocket.getInetAddress());
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void guardarRegistroEnCSVEnServidor(Historial historial) {
            String csvFileName = "Historial.csv";

            try (FileWriter writer = new FileWriter(csvFileName, true)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(historial.getFecha());

                String csvLine = String.format("%s,%.2f,%s%n", historial.getExpresion(), historial.getResultado(), formattedDate);
                writer.write(csvLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


