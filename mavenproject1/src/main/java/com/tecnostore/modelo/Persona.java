package com.tecnostore.modelo;

public abstract class Persona {

    private final String nombre;
    private final String telefono;

    protected Persona(String nombre, String telefono) {
        this.nombre = validarTexto(nombre, "nombre", 100);
        this.telefono = validarTexto(telefono, "telefono", 20);
    }

    protected static String validarTexto(
            String texto, String campo, int maximo
    ) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " es obligatorio."
            );
        }

        String valor = texto.trim();

        if (valor.length() > maximo) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " admite maximo "
                    + maximo + " caracteres."
            );
        }

        return valor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }
}