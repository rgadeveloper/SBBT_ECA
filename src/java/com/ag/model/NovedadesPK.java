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
public class NovedadesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE_MEDIDA")
    private BigInteger idComponenteMedida;
    @Basic(optional = false)
    @Column(name = "TIPO_NOVEDAD")
    private String tipoNovedad;

    public NovedadesPK() {
    }

    public NovedadesPK(BigInteger idComponenteMedida, String tipoNovedad) {
        this.idComponenteMedida = idComponenteMedida;
        this.tipoNovedad = tipoNovedad;
    }

    public BigInteger getIdComponenteMedida() {
        return idComponenteMedida;
    }

    public void setIdComponenteMedida(BigInteger idComponenteMedida) {
        this.idComponenteMedida = idComponenteMedida;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponenteMedida != null ? idComponenteMedida.hashCode() : 0);
        hash += (tipoNovedad != null ? tipoNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadesPK)) {
            return false;
        }
        NovedadesPK other = (NovedadesPK) object;
        if ((this.idComponenteMedida == null && other.idComponenteMedida != null) || (this.idComponenteMedida != null && !this.idComponenteMedida.equals(other.idComponenteMedida))) {
            return false;
        }
        if ((this.tipoNovedad == null && other.tipoNovedad != null) || (this.tipoNovedad != null && !this.tipoNovedad.equals(other.tipoNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.NovedadesPK[idComponenteMedida=" + idComponenteMedida + ", tipoNovedad=" + tipoNovedad + "]";
    }

}
