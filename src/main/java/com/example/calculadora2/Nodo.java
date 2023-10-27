package com.example.calculadora2;


/**
 * Esta clase representa un nodo en un árbol de expresión matemática utilizado para almacenar operadores y valores.
 */
class Nodo {
    char operador;   // El operador almacenado en el nodo (0 si es un valor).
    double valor;    // El valor numérico almacenado en el nodo.
    Nodo izquierdo;  // El nodo izquierdo (subárbol izquierdo) del nodo actual.
    Nodo derecho;    // El nodo derecho (subárbol derecho) del nodo actual.

    /**
     * Constructor para crear un nodo con un operador especificado.
     *
     * @param operador El operador a almacenar en el nodo.
     */
    public Nodo(char operador) {
        this.operador = operador;
        this.izquierdo = null;
        this.derecho = null;
    }

    /**
     * Constructor para crear un nodo con un valor numérico especificado.
     *
     * @param valor El valor numérico a almacenar en el nodo.
     */
    public Nodo(double valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}
