package Entities;

import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;
import java.util.LinkedList;

public class ObjetoTienda {
    String objectId;
    String objectName;
    String objectDescription;
    double objectCoins;

    public ObjetoTienda(String objectId, String objectName, String objectDescription, double objectCoins) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.objectDescription = objectDescription;
        this.objectCoins = objectCoins;
    }

    public ObjetoTienda() {}

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    public void setObjectDescription(String objectDescription) {
        this.objectDescription = objectDescription;
    }

    public double getObjectCoins() {
        return objectCoins;
    }

    public void setObjectCoins(double objectCoins) {
        this.objectCoins = objectCoins;
    }
}
