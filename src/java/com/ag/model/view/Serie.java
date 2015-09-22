/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author arodriguezr
 */
public class Serie {
     private String periodo;
     private long valorMes;
     private long valorMovil;

     public Serie() {
     }

     public Serie(String periodo, long valorMes, long valorMovil) {
        this.periodo = periodo;
        this.valorMes = valorMes;
        this.valorMovil = valorMovil;
     }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public long getValorMes() {
        return valorMes;
    }

    public void setValorMes(long valorMes) {
        this.valorMes = valorMes;
    }

    public long getValorMovil() {
        return valorMovil;
    }

    public void setValorMovil(long valorMovil) {
        this.valorMovil = valorMovil;
    }
     
}
