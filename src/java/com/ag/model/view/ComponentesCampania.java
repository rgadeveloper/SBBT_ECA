/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author arodriguezr
 */
public class ComponentesCampania {
    private String idMacro;
    private String idCliente;   
    private String idComponente;

    public ComponentesCampania() {
    }

    public ComponentesCampania(String idMacro, String idCliente, String idComponente) {
        this.idMacro = idMacro;
        this.idCliente = idCliente;
        this.idComponente = idComponente;
    }
    
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(String idComponente) {
        this.idComponente = idComponente;
    }

    public String getIdMacro() {
        return idMacro;
    }

    public void setIdMacro(String idMacro) {
        this.idMacro = idMacro;
    }
    
}
