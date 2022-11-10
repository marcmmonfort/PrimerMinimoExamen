package Entities;

import Entities.*;
import Entities.ValueObjects.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Usuario {

    String usuarioId;
    List<Partida> partidasJugadas;

    public Usuario(String usuarioId) {
        this.usuarioId = usuarioId;
        this.partidasJugadas = new ArrayList<>();;
    }

    public Usuario() {}

    public void nuevaPartidaAcabada(Partida partida){
        this.partidasJugadas.add(partida);
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Partida> getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(List<Partida> partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }
}
