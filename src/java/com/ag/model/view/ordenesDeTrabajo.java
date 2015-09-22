/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import com.ag.model.OrdenTrabajo;
import com.ag.model.Tbltipo;
import java.util.List;

/**
 *
 * @author depazan
 */
public class ordenesDeTrabajo {
    private String idCampania;
    private String nombre;
    private String periodo;
    private Tbltipo tipo; 
    private List<OrdenTrabajo> ordenes;

    public ordenesDeTrabajo() {
    }

    public ordenesDeTrabajo(String idCampania, String nombre, String periodo, Tbltipo tipo, List<OrdenTrabajo> ordenes) {
        this.idCampania = idCampania;
        this.nombre = nombre;
        this.periodo = periodo;
        this.tipo = tipo;
        this.ordenes = ordenes;
    }    

    public List<OrdenTrabajo> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<OrdenTrabajo> ordenes) {
        this.ordenes = ordenes;
    }
    
    public String getIdCampania() {
        return idCampania;
    }

    public void setIdCampania(String idCampania) {
        this.idCampania = idCampania;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Tbltipo getTipo() {
        return tipo;
    }

    public void setTipo(Tbltipo tipo) {
        this.tipo = tipo;
    }    
}
