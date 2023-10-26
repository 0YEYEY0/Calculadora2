package com.example.calculadora2;

import java.util.Date;

public class Historial {
    private String expresion;
    private double resultado;
    private Date fecha;

    public Historial(String expresion, double resultado, Date fecha) {
        this.expresion = expresion;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public String getExpresion() {
        return expresion;
    }

    public double getResultado() {
        return resultado;
    }

    public Date getFecha() {
        return fecha;
    }
}

