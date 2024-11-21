package com.diseno.pool;

public class Chisme {
    private String tipo;
    private String titulo;
    private String contenido;

    public Chisme(String tipo, String titulo, String contenido) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getContenido() {
        return contenido;
    }
}
