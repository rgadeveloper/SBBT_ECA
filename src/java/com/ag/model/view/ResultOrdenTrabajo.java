/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import com.ag.model.OrdenTrabajo;
import com.ag.model.Tbltipo;

/**
 *
 * @author depazan
 */
public class ResultOrdenTrabajo {
    private String idCampania;
    private String nombre;
    private String periodo;
    private Tbltipo tipo; 
    private OrdenTrabajo ordenTrabajo;

    public ResultOrdenTrabajo() {
    }

    public ResultOrdenTrabajo(String idCampania, String nombre, String periodo, Tbltipo tipo, OrdenTrabajo ordenTrabajo) {
        this.idCampania = idCampania;
        this.nombre = nombre;
        this.periodo = periodo;
        this.tipo = tipo;
        this.ordenTrabajo = ordenTrabajo;
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

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
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
