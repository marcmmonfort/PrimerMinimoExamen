package Entities;

import Entities.*;
import Entities.ValueObjects.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Objects;

public class Usuario {

    String usuarioId;
    List<Partida> partidasJugadas;

    public Usuario(String usuarioId) {
        this.usuarioId = usuarioId;
        this.partidasJugadas = new ArrayList<>();;
    }

    public Usuario() {}

    public InfoPartida dameInfoPartidaUsuario(String juegoId){
        List<InfoNivel> detallesNiveles = new ArrayList<>();
        Partida ultimaPartidaJuego = new Partida();
        for (int i=0; i<this.partidasJugadas.size(); i++){
            if (Objects.equals(this.partidasJugadas.get(i).getJuegoId(), juegoId)){
                ultimaPartidaJuego = this.partidasJugadas.get(i);
            }
        }
        // Ahora tenemos en "ultimaPartidaJuego" la última partida jugada por "usuarioId" en "juegoId".
        int numNivelesJugados = ultimaPartidaJuego.getPuntosPorNivel().size();
        for (int j=0; j<numNivelesJugados; j++){
            InfoNivel detallesNivel = new InfoNivel(j+1, ultimaPartidaJuego.getPuntosPorNivel().get(j), ultimaPartidaJuego.getFechaNivel().get(j));
            detallesNiveles.add(detallesNivel);
        }
        return new InfoPartida(juegoId, this.usuarioId, detallesNiveles);
    }

    public void nuevaPartidaAcabada(Partida partida){
        this.partidasJugadas.add(partida);
    }

    public int damePuntosTotalesEnUnJuego(String juegoId){
        int puntosGlobales = 0;
        for (int i=0; i<this.partidasJugadas.size(); i++){
            if (Objects.equals(this.partidasJugadas.get(i).getJuegoId(), juegoId)){
                puntosGlobales = puntosGlobales + this.partidasJugadas.get(i).getPuntosTotales();
            }
        }
        return puntosGlobales;
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
