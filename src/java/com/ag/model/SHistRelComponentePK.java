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
public class SHistRelComponentePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE_HIJO")
    private BigInteger idComponenteHijo;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;
    @Basic(optional = false)
    @Column(name = "ID_SIMULACION")
    private long idSimulacion;

    public SHistRelComponentePK() {
    }

    public SHistRelComponentePK(BigInteger idComponente, BigInteger idComponenteHijo, int periodoIni, long idSimulacion) {
        this.idComponente = idComponente;
        this.idComponenteHijo = idComponenteHijo;
        this.periodoIni = periodoIni;
        this.idSimulacion = idSimulacion;
    }

    public BigInteger getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigInteger idComponente) {
        this.idComponente = idComponente;
    }

    public BigInteger getIdComponenteHijo() {
        return idComponenteHijo;
    }

    public void setIdComponenteHijo(BigInteger idComponenteHijo) {
        this.idComponenteHijo = idComponenteHijo;
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
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        hash += (idComponenteHijo != null ? idComponenteHijo.hashCode() : 0);
        hash += (int) periodoIni;
        hash += (int) idSimulacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SHistRelComponentePK)) {
            return false;
        }
        SHistRelComponentePK other = (SHistRelComponentePK) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if ((this.idComponenteHijo == null && other.idComponenteHijo != null) || (this.idComponenteHijo != null && !this.idComponenteHijo.equals(other.idComponenteHijo))) {
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
        return "com.ag.model.SHistRelComponentePK[idComponente=" + idComponente + ", idComponenteHijo=" + idComponenteHijo + ", periodoIni=" + periodoIni + ", idSimulacion=" + idSimulacion + "]";
    }

}
