/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author 85154220
 */
public class PadreHijo {
    private String idHijo;
    private String nombreHijo;
    private String idPadre;
    private String nombrePadre;
    
    public PadreHijo(String idHijo,String nombreHijo,String idPadre,String nombrePadre){
        this.idPadre = idPadre;
        this.nombrePadre = nombrePadre;
        this.idHijo = idHijo;
        this.nombreHijo = nombreHijo;
    }
    

    /**
     * @return the idHijo
     */
    public String getIdHijo() {
        return idHijo;
    }

    /**
     * @param idHijo the idHijo to set
     */
    public void setIdHijo(String idHijo) {
        this.idHijo = idHijo;
    }

    /**
     * @return the nombreHijo
     */
    public String getNombreHijo() {
        return nombreHijo;
    }

    /**
     * @param nombreHijo the nombreHijo to set
     */
    public void setNombreHijo(String nombreHijo) {
        this.nombreHijo = nombreHijo;
    }

    /**
     * @return the idPadre
     */
    public String getIdPadre() {
        return idPadre;
    }

    /**
     * @param idPadre the idPadre to set
     */
    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    /**
     * @return the nombrePadre
     */
    public String getNombrePadre() {
        return nombrePadre;
    }

    /**
     * @param nombrePadre the nombrePadre to set
     */
    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }
}
