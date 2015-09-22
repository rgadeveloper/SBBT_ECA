/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author larry.obispo
 */
@Embeddable
public class IntTrafoCargaExtraPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_TRAFO")
    private String idTrafo;
    @Basic(optional = false)
    @Column(name = "TIPO_CARGA")
    private String tipoCarga;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;
    @Basic(optional = false)
    @Column(name = "PERIODO_FIN")
    private String periodoFin;

    public IntTrafoCargaExtraPK() {
    }

    public IntTrafoCargaExtraPK(String idTrafo, String tipoCarga, int periodoIni, String periodoFin) {
        this.idTrafo = idTrafo;
        this.tipoCarga = tipoCarga;
        this.periodoIni = periodoIni;
        this.periodoFin = periodoFin;
    }

    public String getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo(String idTrafo) {
        this.idTrafo = idTrafo;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public int getPeriodoIni() {
        return periodoIni;
    }

    public void setPeriodoIni(int periodoIni) {
        this.periodoIni = periodoIni;
    }

    public String getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(String periodoFin) {
        this.periodoFin = periodoFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrafo != null ? idTrafo.hashCode() : 0);
        hash += (tipoCarga != null ? tipoCarga.hashCode() : 0);
        hash += (int) periodoIni;
        hash += (periodoFin != null ? periodoFin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntTrafoCargaExtraPK)) {
            return false;
        }
        IntTrafoCargaExtraPK other = (IntTrafoCargaExtraPK) object;
        if ((this.idTrafo == null && other.idTrafo != null) || (this.idTrafo != null && !this.idTrafo.equals(other.idTrafo))) {
            return false;
        }
        if ((this.tipoCarga == null && other.tipoCarga != null) || (this.tipoCarga != null && !this.tipoCarga.equals(other.tipoCarga))) {
            return false;
        }
        if (this.periodoIni != other.periodoIni) {
            return false;
        }
        if ((this.periodoFin == null && other.periodoFin != null) || (this.periodoFin != null && !this.periodoFin.equals(other.periodoFin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntTrafoCargaExtraPK[idTrafo=" + idTrafo + ", tipoCarga=" + tipoCarga + ", periodoIni=" + periodoIni + ", periodoFin=" + periodoFin + "]";
    }

}
