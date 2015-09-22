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
public class MedidaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_COMPONENTE")
    private short idTipoComponente;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private int periodo;

    public MedidaPK() {
    }

    public MedidaPK(BigInteger idComponente, short idTipoComponente, int periodo) {
        this.idComponente = idComponente;
        this.idTipoComponente = idTipoComponente;
        this.periodo = periodo;
    }

    public BigInteger getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigInteger idComponente) {
        this.idComponente = idComponente;
    }

    public short getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(short idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
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
        hash += (int) idTipoComponente;
        hash += (int) periodo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedidaPK)) {
            return false;
        }
        MedidaPK other = (MedidaPK) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if (this.idTipoComponente != other.idTipoComponente) {
            return false;
        }
        if (this.periodo != other.periodo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.MedidaPK[idComponente=" + idComponente + ", idTipoComponente=" + idTipoComponente + ", periodo=" + periodo + "]";
    }

}
