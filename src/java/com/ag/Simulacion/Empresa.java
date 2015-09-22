/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.Simulacion;

/**
 *
 * @author arodriguezr
 */
public class Empresa {
    private String nombre;    
    private String idComercialElectrico;
    private String idComercialGeografico;
    private String perdidaElectrico;
    private String perdidaGeografico;

    public Empresa() {
    }

    public Empresa(String nombre, String idComercialElectrico, String idComercialGeografico, String perdidaElectrico, String perdidaGeografico) {
        this.nombre = nombre;
        this.idComercialElectrico = idComercialElectrico;
        this.idComercialGeografico = idComercialGeografico;
        this.perdidaElectrico = perdidaElectrico;
        this.perdidaGeografico = perdidaGeografico;
    }

    public String getIdComercialElectrico() {
        return idComercialElectrico;
    }

    public void setIdComercialElectrico(String idComercialElectrico) {
        this.idComercialElectrico = idComercialElectrico;
    }

    public String getIdComercialGeografico() {
        return idComercialGeografico;
    }

    public void setIdComercialGeografico(String idComercialGeografico) {
        this.idComercialGeografico = idComercialGeografico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPerdidaElectrico() {
        return perdidaElectrico;
    }

    public void setPerdidaElectrico(String perdidaElectrico) {
        this.perdidaElectrico = perdidaElectrico;
    }

    public String getPerdidaGeografico() {
        return perdidaGeografico;
    }

    public void setPerdidaGeografico(String perdidaGeografico) {
        this.perdidaGeografico = perdidaGeografico;
    }
    
}
