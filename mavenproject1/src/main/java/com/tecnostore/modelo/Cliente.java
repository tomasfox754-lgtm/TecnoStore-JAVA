package com.tecnostore.modelo;

public class Cliente {

    private final int id;
    private final String nombre;
    private final String identificacion;
    private final String correo;
    private final String telefono;

    public Cliente(int id, String nombre, String identificacion,
                   String correo, String telefono) {

        this.id = id;
        this.nombre = validarTexto(nombre, "nombre", 100);
        this.identificacion = validarTexto(
                identificacion, "identificación", 30
        );
        this.correo = validarTexto(correo, "correo", 100);
        this.telefono = validarTexto(telefono, "teléfono", 20);

        if (!this.correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException(
                    "El formato del correo no es válido."
            );
        }
    }

    private static String validarTexto(
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
                    "El campo " + campo + " admite máximo "
                    + maximo + " caracteres."
            );
        }

        return valor;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return id + " | " + nombre
                + " | Identificación: " + identificacion
                + " | Correo: " + correo
                + " | Teléfono: " + telefono;
    }
}