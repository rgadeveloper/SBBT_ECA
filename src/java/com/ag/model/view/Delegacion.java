/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import java.math.BigDecimal;

/**
 *
 * @author Ultimate
 */
public class Delegacion {
 private BigDecimal codigo;
 private String delegacion;
 private String zona;

    public Delegacion(BigDecimal codigo, String delegacion, String zona) {
        this.codigo = codigo;
        this.delegacion = delegacion;
        this.zona = zona;
    }

    public Delegacion() {
    }

    public BigDecimal getCodigo() {
        return codigo;
    }

    public void setCodigo(BigDecimal codigo) {
        this.codigo = codigo;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

}
