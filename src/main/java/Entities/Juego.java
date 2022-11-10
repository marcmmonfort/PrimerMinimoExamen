package Entities;

import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;

public class Juego {

    String juegoId;
    String juegoDescripcion;
    Integer numeroNivelesJuego;

    public Juego(String juegoId, String juegoDescripcion, Integer numeroNivelesJuego) {
        this.juegoId = juegoId;
        this.juegoDescripcion = juegoDescripcion;
        this.numeroNivelesJuego = numeroNivelesJuego;
    }

    public Juego() {}

    public String getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(String juegoId) {
        this.juegoId = juegoId;
    }

    public String getJuegoDescripcion() {
        return juegoDescripcion;
    }

    public void setJuegoDescripcion(String juegoDescripcion) {
        this.juegoDescripcion = juegoDescripcion;
    }

    public Integer getNumeroNivelesJuego() {
        return numeroNivelesJuego;
    }

    public void setNumeroNivelesJuego(Integer numeroNivelesJuego) {
        this.numeroNivelesJuego = numeroNivelesJuego;
    }
}
