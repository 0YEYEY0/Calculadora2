package com.example.calculadora2;


class Nodo {
    char operador;
    double valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(char operador) {
        this.operador = operador;
        this.izquierdo = null;
        this.derecho = null;
    }

    public Nodo(double valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}
