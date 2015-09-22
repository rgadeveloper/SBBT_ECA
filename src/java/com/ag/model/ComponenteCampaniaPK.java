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
public class ComponenteCampaniaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Basic(optional = false)
    @Column(name = "ID_CAMPANIA")
    private BigInteger idCampania;

    public ComponenteCampaniaPK() {
    }

    public ComponenteCampaniaPK(BigInteger idComponente, BigInteger idCampania) {
        this.idComponente = idComponente;
        this.idCampania = idCampania;
    }

    public BigInteger getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigInteger idComponente) {
        this.idComponente = idComponente;
    }

    public BigInteger getIdCampania() {
        return idCampania;
    }

    public void setIdCampania(BigInteger idCampania) {
        this.idCampania = idCampania;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        hash += (idCampania != null ? idCampania.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponenteCampaniaPK)) {
            return false;
        }
        ComponenteCampaniaPK other = (ComponenteCampaniaPK) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        if ((this.idCampania == null && other.idCampania != null) || (this.idCampania != null && !this.idCampania.equals(other.idCampania))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.ComponenteCampaniaPK[idComponente=" + idComponente + ", idCampania=" + idCampania + "]";
    }

}
