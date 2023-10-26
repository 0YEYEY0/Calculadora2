package com.example.calculadora2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
                }

                System.out.println("Cliente desconectado desde " + clientSocket.getInetAddress());
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

