/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author Ultimate
 */
public class TipoComponenteVer {
    private String nombre;
    private String direccion;
    private String tipoCompo;
    private String nombreCompo;
    private String pini;
    private String pfin;
    private String estado;

    public TipoComponenteVer() {
    }

    public TipoComponenteVer(String nombre, String direccion, String tipoCompo, String nombreCompo, String pini, String pfin, String estado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipoCompo = tipoCompo;
        this.nombreCompo = nombreCompo;
        this.pini = pini;
        this.pfin = pfin;
        this.estado = estado;
    }

   
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoCompo() {
        return tipoCompo;
    }

    public void setTipoCompo(String tipoCompo) {
        this.tipoCompo = tipoCompo;
    }

    public String getNombreCompo() {
        return nombreCompo;
    }

    public void setNombreCompo(String nombreCompo) {
        this.nombreCompo = nombreCompo;
    }

    public String getPini() {
        return pini;
    }

    public void setPini(String pini) {
        this.pini = pini;
    }

    public String getPfin() {
        return pfin;
    }

    public void setPfin(String pfin) {
        this.pfin = pfin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
