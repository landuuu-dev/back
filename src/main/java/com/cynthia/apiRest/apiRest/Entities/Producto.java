package com.cynthia.apiRest.apiRest.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255, nullable = false)
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
    private double precio;
    @Column(length = 1000)
    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;
    private String imagenPrincipal;
    private String imagenExtra1;
    private String imagenExtra2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(String imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public String getImagenExtra1() {
        return imagenExtra1;
    }

    public void setImagenExtra1(String imagenExtra1) {
        this.imagenExtra1 = imagenExtra1;
    }

    public String getImagenExtra2() {
        return imagenExtra2;
    }

    public void setImagenExtra2(String imagenExtra2) {
        this.imagenExtra2 = imagenExtra2;
    }
}
