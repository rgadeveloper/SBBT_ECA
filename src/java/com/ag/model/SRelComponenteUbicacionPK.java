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
public class SRelComponenteUbicacionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_ZONA")
    private BigInteger idZona;
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;

    public SRelComponenteUbicacionPK() {
    }

    public SRelComponenteUbicacionPK(BigInteger idZona, BigInteger idComponente, int periodoIni) {
        this.idZona = idZona;
        this.idComponente = idComponente;
        this.periodoIni = periodoIni;
    }

    public BigInteger getIdZona() {
        return idZona;
    }

    public void setIdZona(BigInteger idZona) {
        this.idZona = idZona;
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
        hash += (idZona != null ? idZona.hashCode() : 0);
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        hash += (int) periodoIni;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRelComponenteUbicacionPK)) {
            return false;
        }
        SRelComponenteUbicacionPK other = (SRelComponenteUbicacionPK) object;
        if ((this.idZona == null && other.idZona != null) || (this.idZona != null && !this.idZona.equals(other.idZona))) {
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
        return "com.ag.model.SRelComponenteUbicacionPK[idZona=" + idZona + ", idComponente=" + idComponente + ", periodoIni=" + periodoIni + "]";
    }

}
