package Entities;

import Entities.*;
import Entities.ValueObjects.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;

public class Usuario {
    String userId;
    String username;
    String userSurname;
    String birthDate;
    double userCoins;
    List<ObjetoTienda> objectsBought;
    Credenciales credentials;

    public Usuario(String userId, String username, String userSurname, String birthDate, Credenciales credentials) {
        this.userId = userId;
        this.username = username;
        this.userSurname = userSurname;
        this.birthDate = birthDate;
        this.userCoins = 50;
        this.objectsBought = new LinkedList<>();
        this.credentials = credentials;
    }

    public Usuario() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public double getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(double userCoins) {
        this.userCoins = userCoins;
    }

    public List<ObjetoTienda> getObjectsBought() {
        return objectsBought;
    }

    public void setObjectsBought(List<ObjetoTienda> objectsBought) {
        this.objectsBought = objectsBought;
    }

    public Credenciales getCredentials() {
        return credentials;
    }

    public void setCredentials(Credenciales credentials) {
        this.credentials = credentials;
    }

    public void descontarDinero(double dinero){
        this.userCoins = this.userCoins - dinero;
    }

    public void addObjetoComprado(ObjetoTienda obj){
        this.objectsBought.add(obj);
    }
}
