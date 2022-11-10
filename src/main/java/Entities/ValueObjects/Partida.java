package Entities.ValueObjects;

import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Partida {

    String juegoId;
    String usuarioId;
    Integer nivelActual;
    List<Integer> puntosPorNivel;
    Integer puntosTotales;
    List<String> fechaNivel;
    boolean partidaEnCurso;

    public Partida(String juegoId, String usuarioId) {
        this.juegoId = juegoId;
        this.usuarioId = usuarioId;
        this.nivelActual = 1; // Se empieza por nivel 1 siempre.
        this.puntosPorNivel =  new ArrayList<>();
        this.puntosTotales = 50;
        this.fechaNivel = new ArrayList<>();
        this.partidaEnCurso = true;
    }

    public Partida() {}

    public void aumentarNivel(String fecha, int nuevosPuntos){
        this.nivelActual = this.nivelActual + 1;
        this.puntosTotales = this.puntosTotales + nuevosPuntos;
        puntosPorNivel.add(nuevosPuntos);
        fechaNivel.add(fecha);
    }

    public void finalizarPartida(){
        this.puntosTotales = this.puntosTotales + 100;
        this.partidaEnCurso = false;
    }

    public String getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(String juegoId) {
        this.juegoId = juegoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(Integer nivelActual) {
        this.nivelActual = nivelActual;
    }

    public List<Integer> getPuntosPorNivel() {
        return puntosPorNivel;
    }

    public void setPuntosPorNivel(List<Integer> puntosPorNivel) {
        this.puntosPorNivel = puntosPorNivel;
    }

    public Integer getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(Integer puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public List<String> getFechaNivel() {
        return fechaNivel;
    }

    public void setFechaNivel(List<String> fechaNivel) {
        this.fechaNivel = fechaNivel;
    }

    public boolean isPartidaEnCurso() {
        return partidaEnCurso;
    }

    public void setPartidaEnCurso(boolean partidaEnCurso) {
        this.partidaEnCurso = partidaEnCurso;
    }
}
