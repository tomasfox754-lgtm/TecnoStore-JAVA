package com.tecnostore.modelo;

import java.math.BigDecimal;

public class Celular {

    private int id;
    private String marca;
    private String modelo;
    private BigDecimal precio;
    private int stock;
    private String sistemaOperativo;
    private CategoriaGama gama;

    public Celular(int id, String marca, String modelo,
                   BigDecimal precio, int stock,
                   String sistemaOperativo, CategoriaGama gama) {

        this.id = id;
        this.marca = validarTexto(marca, "marca");
        this.modelo = validarTexto(modelo, "modelo");
        this.sistemaOperativo =
                validarTexto(sistemaOperativo, "sistema operativo");

        if (gama == null) {
            throw new IllegalArgumentException("La gama es obligatoria.");
        }

        this.gama = gama;
        setPrecio(precio);
        setStock(stock);
    }

    private static String validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " es obligatorio."
            );
        }
        return texto.trim();
    }

    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.signum() <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo."
            );
        }
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public CategoriaGama getGama() {
        return gama;
    }

    @Override
    public String toString() {
        return id + " | " + marca + " " + modelo
                + " | Precio: " + precio
                + " | Stock: " + stock
                + " | SO: " + sistemaOperativo
                + " | Gama: " + gama;
    }
}