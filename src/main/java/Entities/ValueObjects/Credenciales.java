package Entities.ValueObjects;

import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;

public class Credenciales {
    String email;
    String password;

    public Credenciales(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credenciales() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
