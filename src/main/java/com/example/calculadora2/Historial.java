package com.example.calculadora2;

import java.util.Date;

/**
 * Esta clase representa una entrada de registro en el historial de cálculos.
 */
public class Historial {
    private String expresion; // La expresión matemática ingresada por el usuario.
    private double resultado; // El resultado de la expresión.
    private Date fecha; // La fecha y hora en la que se realizó el cálculo.

    /**
     * Constructor para crear una nueva entrada de historial.
     *
     * @param expresion La expresión matemática ingresada por el usuario.
     * @param resultado El resultado de la expresión.
     * @param fecha La fecha y hora en la que se realizó el cálculo.
     */
    public Historial(String expresion, double resultado, Date fecha) {
        this.expresion = expresion;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    /**
     * Obtiene la expresión matemática asociada a esta entrada de historial.
     *
     * @return La expresión matemática.
     */
    public String getExpresion() {
        return expresion;
    }

    /**
     * Obtiene el resultado numérico asociado a esta entrada de historial.
     *
     * @return El resultado del cálculo.
     */
    public double getResultado() {
        return resultado;
    }

    /**
     * Obtiene la fecha y hora en la que se realizó el cálculo de esta entrada de historial.
     *
     * @return La fecha y hora del cálculo.
     */
    public Date getFecha() {
        return fecha;
    }
}



