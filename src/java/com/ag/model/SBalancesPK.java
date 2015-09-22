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
public class SBalancesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private int periodo;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_COMPONENTE")
    private short idTipoComponente;
    @Basic(optional = false)
    @Column(name = "ID_SIMULACION")
    private long idSimulacion;

    public SBalancesPK() {
    }

    public SBalancesPK(BigInteger idComponente, int periodo, short idTipoComponente, long idSimulacion) {
        this.idComponente = idComponente;
        this.periodo = periodo;
        this.idTipoComponente = idTipoComponente;
        this.idSimulacion = idSimulacion;
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

    public short getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(short idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
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
        hash += (int) periodo;
        hash += (int) idTipoComponente;
        hash += (int) idSimulacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SBalancesPK)) {
            return false;
        }
        SBalancesPK other = (SBalancesPK) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if (this.periodo != other.periodo) {
            return false;
        }
        if (this.idTipoComponente != other.idTipoComponente) {
            return false;
        }
        if (this.idSimulacion != other.idSimulacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SBalancesPK[idComponente=" + idComponente + ", periodo=" + periodo + ", idTipoComponente=" + idTipoComponente + ", idSimulacion=" + idSimulacion + "]";
    }

}
