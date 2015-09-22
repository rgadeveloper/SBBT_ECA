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
public class SRelComponenteMedidaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE_MEDIDA")
    private BigInteger idComponenteMedida;
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;

    public SRelComponenteMedidaPK() {
    }

    public SRelComponenteMedidaPK(BigInteger idComponenteMedida, BigInteger idComponente, int periodoIni) {
        this.idComponenteMedida = idComponenteMedida;
        this.idComponente = idComponente;
        this.periodoIni = periodoIni;
    }

    public BigInteger getIdComponenteMedida() {
        return idComponenteMedida;
    }

    public void setIdComponenteMedida(BigInteger idComponenteMedida) {
        this.idComponenteMedida = idComponenteMedida;
    }

    public BigInteger getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigInteger idComponente) {
        this.idComponente = idComponente;
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
        hash += (idComponenteMedida != null ? idComponenteMedida.hashCode() : 0);
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        hash += (int) periodoIni;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRelComponenteMedidaPK)) {
            return false;
        }
        SRelComponenteMedidaPK other = (SRelComponenteMedidaPK) object;
        if ((this.idComponenteMedida == null && other.idComponenteMedida != null) || (this.idComponenteMedida != null && !this.idComponenteMedida.equals(other.idComponenteMedida))) {
            return false;
        }
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if (this.periodoIni != other.periodoIni) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SRelComponenteMedidaPK[idComponenteMedida=" + idComponenteMedida + ", idComponente=" + idComponente + ", periodoIni=" + periodoIni + "]";
    }

}
