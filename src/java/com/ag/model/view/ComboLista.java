/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model.view;

/**
 *
 * @author Larry
 */
public class ComboLista {
    private String id;
    private String descripcion;

    public ComboLista(String id,String descripcion){
        this.id = id;
        this.descripcion = descripcion;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString(){
        return descripcion;
    }


}
