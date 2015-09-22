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
public class BalancesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private int periodo;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_COMPONENTE")
    private short idTipoComponente;

    public BalancesPK() {
    }

    public BalancesPK(BigInteger idComponente, int periodo, short idTipoComponente) {
        this.idComponente = idComponente;
        this.periodo = periodo;
        this.idTipoComponente = idTipoComponente;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        hash += (int) periodo;
        hash += (int) idTipoComponente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BalancesPK)) {
            return false;
        }
        BalancesPK other = (BalancesPK) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if (this.periodo != other.periodo) {
            return false;
        }
        if (this.idTipoComponente != other.idTipoComponente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.BalancesPK[idComponente=" + idComponente + ", periodo=" + periodo + ", idTipoComponente=" + idTipoComponente + "]";
    }

}
