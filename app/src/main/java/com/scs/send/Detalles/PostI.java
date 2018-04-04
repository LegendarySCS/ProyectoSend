package com.scs.send.Detalles;

/**
 * Created by Ac-Ad on 25/3/2018.
 */
public class PostI {
    // Atributos
    private String titulo;
    private String descripcion;
    private String imagen;
    private String categoria;
    private int id;



    public PostI(String titulo, String descripcion, String imagen,String categoria,int id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.categoria=categoria;
        this.id=id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return imagen;
    }

    public String getImagen() {
        return imagen;
    }


    public void setUrl(String imagen) {
        this.imagen = imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}