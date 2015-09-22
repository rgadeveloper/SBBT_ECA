/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ultimate
 */
@Embeddable
public class BalanceEnergiaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERIODO")
    private int periodo;

    public BalanceEnergiaPK() {
    }

    public BalanceEnergiaPK(BigInteger idComponente, int periodo) {
        this.idComponente = idComponente;
        this.periodo = periodo;
    }

    public BigInteger getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigInteger idComponente) {
        this.idComponente = idComponente;
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
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        hash += (int) periodo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BalanceEnergiaPK)) {
            return false;
        }
        BalanceEnergiaPK other = (BalanceEnergiaPK) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if (this.periodo != other.periodo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.BalanceEnergiaPK[ idComponente=" + idComponente + ", periodo=" + periodo + " ]";
    }
    
}
