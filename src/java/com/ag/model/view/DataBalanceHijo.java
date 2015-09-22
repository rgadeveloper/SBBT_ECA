/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author 85154220
 */
public class DataBalanceHijo {
    private String idComponente;
    private String nombre;
    private String tipo;
    private String perdidas;
    private String idCliente;
    
    public DataBalanceHijo(String idComponente,String nombre,String tipo,String perdidas,String idCliente){
        this.idComponente = idComponente;
        this.nombre       = nombre;
        this.tipo         = tipo;
        this.perdidas     = perdidas;
        this.idCliente    = idCliente;
    }

    /**
     * @return the idComponente
     */
    public String getIdComponente() {
        return idComponente;
    }

    /**
     * @param idComponente the idComponente to set
     */
    public void setIdComponente(String idComponente) {
        this.idComponente = idComponente;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the perdidas
     */
    public String getPerdidas() {
        return perdidas;
    }

    /**
     * @param perdidas the perdidas to set
     */
    public void setPerdidas(String perdidas) {
        this.perdidas = perdidas;
    }

    /**
     * @return the idCliente
     */
    public String getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    
}
