/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author arodriguezr
 */
public class UbicacionMacroV {
    private String zona;
    private String munSub;
    private String barCir;
    private String delegacion;
    private String areaMt;
    private String cantTrafosAso;
    private String empresa;

    public UbicacionMacroV() {
    }

    public UbicacionMacroV(String zona, String munSub, String barCir, String cantTrafosAso) {
        this.zona = zona;
        this.munSub = munSub;
        this.barCir = barCir;
        this.cantTrafosAso = cantTrafosAso;
    }
    
    public String getBarCir() {
        return barCir;
    }

    public void setBarCir(String barCir) {
        this.barCir = barCir;
    }

    public String getMunSub() {
        return munSub;
    }

    public void setMunSub(String munSub) {
        this.munSub = munSub;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCantTrafosAso() {
        return cantTrafosAso;
    }

    public void setCantTrafosAso(String cantTrafosAso) {
        this.cantTrafosAso = cantTrafosAso;
    }   

    /**
     * @return the delegacion
     */
    public String getDelegacion() {
        return delegacion;
    }

    /**
     * @param delegacion the delegacion to set
     */
    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    /**
     * @return the areaMt
     */
    public String getAreaMt() {
        return areaMt;
    }

    /**
     * @param areaMt the areaMt to set
     */
    public void setAreaMt(String areaMt) {
        this.areaMt = areaMt;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
