package com.example.gamero.youth_ird;

import java.io.Serializable;

class FeedsVo implements Serializable {
    private String id_publicacion,imagen,titulo, usuario, create_at, like, mensaje;

    FeedsVo(String id_publicacion, String imagen, String titulo, String usuario, String create_at, String like, String mensaje) {
        this.id_publicacion = id_publicacion;
        this.imagen = imagen;
        this.titulo = titulo;
        this.usuario = usuario;
        this.create_at = create_at;

        this.like = like;
        this.mensaje = mensaje;
    }

    public FeedsVo() {
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(String id_publicacion) {
        this.id_publicacion = id_publicacion;
    }
}
