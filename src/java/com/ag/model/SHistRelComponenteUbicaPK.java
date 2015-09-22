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
public class SHistRelComponenteUbicaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_ZONA")
    private BigInteger idZona;
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;
    @Basic(optional = false)
    @Column(name = "ID_SIMULACION")
    private long idSimulacion;

    public SHistRelComponenteUbicaPK() {
    }

    public SHistRelComponenteUbicaPK(BigInteger idZona, BigInteger idComponente, int periodoIni, long idSimulacion) {
        this.idZona = idZona;
        this.idComponente = idComponente;
        this.periodoIni = periodoIni;
        this.idSimulacion = idSimulacion;
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

    public long getIdSimulacion() {
        return idSimulacion;
    }

    public void setIdSimulacion(long idSimulacion) {
        this.idSimulacion = idSimulacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZona != null ? idZona.hashCode() : 0);
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        hash += (int) periodoIni;
        hash += (int) idSimulacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SHistRelComponenteUbicaPK)) {
            return false;
        }
        SHistRelComponenteUbicaPK other = (SHistRelComponenteUbicaPK) object;
        if ((this.idZona == null && other.idZona != null) || (this.idZona != null && !this.idZona.equals(other.idZona))) {
            return false;
        }
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if (this.periodoIni != other.periodoIni) {
            return false;
        }
        if (this.idSimulacion != other.idSimulacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SHistRelComponenteUbicaPK[idZona=" + idZona + ", idComponente=" + idComponente + ", periodoIni=" + periodoIni + ", idSimulacion=" + idSimulacion + "]";
    }

}
