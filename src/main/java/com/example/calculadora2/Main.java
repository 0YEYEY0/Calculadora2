package com.example.calculadora2;


public class Main {
    public static void main(String[] args) {
        String expresion = "((5 > 3) & !(2 < 4)) | ((1 == 1) & (3 != 2))";
        ArbolExpresion arbol = new ArbolExpresion(expresion);
        double resultado = arbol.evaluar();
        System.out.println("Resultado: " + resultado);
    }
}

