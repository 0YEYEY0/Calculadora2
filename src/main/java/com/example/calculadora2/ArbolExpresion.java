package com.example.calculadora2;


import java.util.Stack;

public class ArbolExpresion {
    private Nodo raiz;

    public ArbolExpresion(String expresion) {
        expresion = expresion.replace("**", "Math.pow");
        raiz = construirArbol(expresion);
    }

    private Nodo construirArbol(String expresion) {
        Stack<Nodo> stack = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expresion.length() && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    sb.append(expresion.charAt(i));
                    i++;
                }
                stack.push(new Nodo(Double.parseDouble(sb.toString())));
                i--;
            } else if (c == '(') {
                operadores.push(c);
            } else if (c == ')') {
                while (!operadores.isEmpty() && operadores.peek() != '(') {
                    procesarOperador(stack, operadores);
                }
                operadores.pop();
            } else if (esOperador(c)) {
                while (!operadores.isEmpty() && precedencia(c) <= precedencia(operadores.peek())) {
                    procesarOperador(stack, operadores);
                }
                operadores.push(c);
            }
        }

        while (!operadores.isEmpty()) {
            procesarOperador(stack, operadores);
        }

        return stack.pop();
    }

    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '&' || c == '|' || c == '!' || c == '^';
    }

    private int precedencia(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/' || c == '%') {
            return 2;
        } else if (c == '&' || c == '|' || c == '^') {
            return 3;
        } else if (c == '!') {
            return 4;
        }
        return 0;
    }

    private void procesarOperador(Stack<Nodo> stack, Stack<Character> operadores) {
        char operador = operadores.pop();
        if (operador == '!' || operador == '&' || operador == '|') {
            Nodo derecho = stack.pop();
            Nodo nuevoNodo = new Nodo(0);
            switch (operador) {
                case '!':
                    nuevoNodo.valor = (derecho.valor == 0) ? 1 : 0;
                    break;
                case '&':
                    Nodo izquierdoAnd = stack.pop();
                    nuevoNodo.valor = (izquierdoAnd.valor != 0 && derecho.valor != 0) ? 1 : 0;
                    break;
                case '|':
                    Nodo izquierdoOr = stack.pop();
                    nuevoNodo.valor = (izquierdoOr.valor != 0 || derecho.valor != 0) ? 1 : 0;
                    break;
            }
            stack.push(nuevoNodo);
        } else {
            Nodo derecho = stack.pop();
            Nodo izquierdo = stack.pop();
            Nodo nuevoNodo = new Nodo(operador);
            nuevoNodo.izquierdo = izquierdo;
            nuevoNodo.derecho = derecho;
            stack.push(nuevoNodo);
        }
    }


    public double evaluar() {
        return evaluar(raiz);
    }

    private double evaluar(Nodo nodo) {
        if (nodo.operador == 0) {
            return nodo.valor;
        } else {
            double izquierdo = evaluar(nodo.izquierdo);
            double derecho = evaluar(nodo.derecho);
            switch (nodo.operador) {
                case '+':
                    return izquierdo + derecho;
                case '-':
                    return izquierdo - derecho;
                case '*':
                    return izquierdo * derecho;
                case '/':
                    if (derecho != 0) {
                        return izquierdo / derecho;
                    } else {
                        throw new ArithmeticException("División por cero");
                    }
                case '%':
                    if (derecho != 0) {
                        return izquierdo % derecho;
                    } else {
                        throw new ArithmeticException("Módulo por cero");
                    }
                default:
                    throw new IllegalArgumentException("Operador desconocido: " + nodo.operador);
            }
        }
    }
}




