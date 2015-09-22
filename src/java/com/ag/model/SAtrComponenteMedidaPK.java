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

/**
 *
 * @author larry.obispo
 */
@Embeddable
public class SAtrComponenteMedidaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_ATR_COMPONENTE")
    private BigInteger idAtrComponente;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;

    public SAtrComponenteMedidaPK() {
    }

    public SAtrComponenteMedidaPK(BigInteger idAtrComponente, int periodoIni) {
        this.idAtrComponente = idAtrComponente;
        this.periodoIni = periodoIni;
    }

    public BigInteger getIdAtrComponente() {
        return idAtrComponente;
    }

    public void setIdAtrComponente(BigInteger idAtrComponente) {
        this.idAtrComponente = idAtrComponente;
    }

    public int getPeriodoIni() {
        return periodoIni;
    }

    public void setPeriodoIni(int periodoIni) {
        this.periodoIni = periodoIni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtrComponente != null ? idAtrComponente.hashCode() : 0);
        hash += (int) periodoIni;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SAtrComponenteMedidaPK)) {
            return false;
        }
        SAtrComponenteMedidaPK other = (SAtrComponenteMedidaPK) object;
        if ((this.idAtrComponente == null && other.idAtrComponente != null) || (this.idAtrComponente != null && !this.idAtrComponente.equals(other.idAtrComponente))) {
            return false;
        }
        if (this.periodoIni != other.periodoIni) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SAtrComponenteMedidaPK[idAtrComponente=" + idAtrComponente + ", periodoIni=" + periodoIni + "]";
    }

}
