/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author larry.obispo
 */
@Embeddable
public class IntMacromedidoresPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_CLIENTE")
    private String idCliente;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private int periodo;

    public IntMacromedidoresPK() {
    }

    public IntMacromedidoresPK(String idCliente, int periodo) {
        this.idCliente = idCliente;
        this.periodo = periodo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        hash += (int) periodo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntMacromedidoresPK)) {
            return false;
        }
        IntMacromedidoresPK other = (IntMacromedidoresPK) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        if (this.periodo != other.periodo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntMacromedidoresPK[idCliente=" + idCliente + ", periodo=" + periodo + "]";
    }

}
