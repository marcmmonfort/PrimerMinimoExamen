package Entities.ValueObjects;

import Entities.*;
import Entities.ValueObjects.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;


public class InfoPasoNivel {
    String usuarioId;
    int puntosLogrados;
    String fechaCambioNivel;

    public InfoPasoNivel(String usuarioId, int puntosLogrados, String fechaCambioNivel) {
        this.usuarioId = usuarioId;
        this.puntosLogrados = puntosLogrados;
        this.fechaCambioNivel = fechaCambioNivel;
    }

    public InfoPasoNivel() {}

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPuntosLogrados() {
        return puntosLogrados;
    }

    public void setPuntosLogrados(int puntosLogrados) {
        this.puntosLogrados = puntosLogrados;
    }

    public String getFechaCambioNivel() {
        return fechaCambioNivel;
    }

    public void setFechaCambioNivel(String fechaCambioNivel) {
        this.fechaCambioNivel = fechaCambioNivel;
    }
}
