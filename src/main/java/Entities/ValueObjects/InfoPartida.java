package Entities.ValueObjects;

import Entities.*;
import Entities.ValueObjects.*;
import Main.*;
import Managers.*;
import Services.*;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class InfoPartida {
    String juegoId;
    String usuarioId;
    List<InfoNivel> detallesNiveles;

    public InfoPartida(String juegoId, String usuarioId, List<InfoNivel> detallesNiveles) {
        this.juegoId = juegoId;
        this.usuarioId = usuarioId;
        this.detallesNiveles = detallesNiveles;
    }

    public InfoPartida() {}

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

    public List<InfoNivel> getDetallesNiveles() {
        return detallesNiveles;
    }

    public void setDetallesNiveles(List<InfoNivel> detallesNiveles) {
        this.detallesNiveles = detallesNiveles;
    }
}
