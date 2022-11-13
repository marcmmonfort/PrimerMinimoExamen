package Entities.ValueObjects;

import Entities.*;
import Entities.ValueObjects.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class InfoNivel {
    Integer nivelId;
    Integer puntosNivel;
    String fechaNivel;

    public InfoNivel(Integer nivelId, Integer puntosNivel, String fechaNivel) {
        this.nivelId = nivelId;
        this.puntosNivel = puntosNivel;
        this.fechaNivel = fechaNivel;
    }

    public InfoNivel() {}

    public Integer getNivelId() {
        return nivelId;
    }

    public void setNivelId(Integer nivelId) {
        this.nivelId = nivelId;
    }

    public Integer getPuntosNivel() {
        return puntosNivel;
    }

    public void setPuntosNivel(Integer puntosNivel) {
        this.puntosNivel = puntosNivel;
    }

    public String getFechaNivel() {
        return fechaNivel;
    }

    public void setFechaNivel(String fechaNivel) {
        this.fechaNivel = fechaNivel;
    }
}
