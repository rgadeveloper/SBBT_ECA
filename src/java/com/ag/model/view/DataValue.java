/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author 85154220
 */
public class DataValue {
    private String id;
    private String nombre;
    private String valor;
    
    public DataValue(String id,String nombre, String valor){
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }
    
    public DataValue(String nombre, String valor){
        this.nombre = nombre;
        this.valor = valor;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
