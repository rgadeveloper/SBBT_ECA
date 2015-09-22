/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.Simulacion;

import java.util.List;

/**
 *
 * @author arodriguezr
 */
public class Simulado {
    private String id;
    //private Date fecha;
    private List<Empresa> empresas;

    public Simulado() {
    }

    public Simulado(String id, List<Empresa> empresas) {
        this.id = id;
        //this.fecha = fecha;
        this.empresas = empresas;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    /*public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }    
}
