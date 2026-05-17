package com.tfg.rinconmusica.vo;

public class ValoracionRequest {

    private Integer usuarioId;
    private Integer cancionId;
    private Integer puntuacion;
    private String comentario;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getCancionId() {
        return cancionId;
    }

    public void setCancionId(Integer cancionId) {
        this.cancionId = cancionId;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}