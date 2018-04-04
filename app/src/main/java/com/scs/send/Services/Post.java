package com.scs.send.Services;

/**
 * Created by Ac-Ad on 25/3/2018.
 */
public class Post {
    // Atributos
    private String titulo;
    private String descripcion;
    private String imagen;
    private int id;
    private String estado;



    public Post(String titulo, String descripcion, String imagen,int id,String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.id=id;
        this.estado=estado;
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

    public void setEstado(String estado) { this.estado = estado;  }

    public String getEstado() { return estado; }
}