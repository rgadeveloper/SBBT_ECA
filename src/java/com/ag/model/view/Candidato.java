/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author 85154220
 */
public class Candidato {
    private String idTrafo, nombre, idMacro, zona, circuito, cantSum, porcPerdida, rango;
    private boolean check;    
    
    public Candidato (String idTrafo, String nombre,String  idMacro, boolean check){
        this.idTrafo = idTrafo;
        this.nombre = nombre;
        this.idMacro = idMacro;
        this.check = check;        
    }

   public Candidato (String idTrafo, String nombre,String  idMacro, boolean check,
                     String zona,String  circuito, String cantSum, String porcPerdida, String rango){
       
        this.idTrafo = idTrafo;
        this.nombre = nombre;
        this.idMacro = idMacro;
        this.check = check;
        this.zona =  zona;
        this.circuito = circuito;
        this.cantSum= cantSum;
        this.porcPerdida= porcPerdida;
        this.rango = rango;


    }

    /**
     * @return the idTrafo
     */
    public String getIdTrafo() {
        return idTrafo;
    }

    /**
     * @param idTrafo the idTrafo to set
     */
    public void setIdTrafo(String idTrafo) {
        this.idTrafo = idTrafo;
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
     * @return the idMacro
     */
    public String getIdMacro() {
        return idMacro;
    }

    /**
     * @param idMacro the idMacro to set
     */
    public void setIdMacro(String idMacro) {
        this.idMacro = idMacro;
    }

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * @return the circuito
     */
    public String getCircuito() {
        return circuito;
    }

    /**
     * @param circuito the circuito to set
     */
    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    /**
     * @return the cantSum
     */
    public String getCantSum() {
        return cantSum;
    }

    /**
     * @param cantSum the cantSum to set
     */
    public void setCantSum(String cantSum) {
        this.cantSum = cantSum;
    }

    /**
     * @return the porcPerdida
     */
    public String getPorcPerdida() {
        return porcPerdida;
    }

    /**
     * @param porcPerdida the porcPerdida to set
     */
    public void setPorcPerdida(String porcPerdida) {
        this.porcPerdida = porcPerdida;
    }

    /**
     * @return the rango
     */
    public String getRango() {
        return rango;
    }

    /**
     * @param rango the rango to set
     */
    public void setRango(String rango) {
        this.rango = rango;
    }

    /**
     * @return the check
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * @param check the check to set
     */
    public void setCheck(boolean check) {
        this.check = check;
    }    
    
}
