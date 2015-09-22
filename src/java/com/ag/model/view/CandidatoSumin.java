/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author 85154220
 */
public class CandidatoSumin {
    private String idComponente, nombre, idCliente, idClienteTrafo;
    private boolean check;

    public CandidatoSumin() {
    }

    public CandidatoSumin(String idComponente, String nombre, String idCliente, String idClienteTrafo, boolean check) {
        this.idComponente = idComponente;
        this.nombre = nombre;
        this.idCliente = idCliente;
        this.idClienteTrafo = idClienteTrafo;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdClienteTrafo() {
        return idClienteTrafo;
    }

    public void setIdClienteTrafo(String idClienteTrafo) {
        this.idClienteTrafo = idClienteTrafo;
    }

    public String getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(String idComponente) {
        this.idComponente = idComponente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    
}
