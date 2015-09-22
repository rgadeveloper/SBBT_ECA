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
public class IntAmarreTrafoSumPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_TRAFO")
    private String idTrafo;
    @Basic(optional = false)
    @Column(name = "ID_SUMINISTRO")
    private String idSuministro;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;
    @Basic(optional = false)
    @Column(name = "PERIODO_FIN")
    private int periodoFin;

    public IntAmarreTrafoSumPK() {
    }

    public IntAmarreTrafoSumPK(String idTrafo, String idSuministro, int periodoIni, int periodoFin) {
        this.idTrafo = idTrafo;
        this.idSuministro = idSuministro;
        this.periodoIni = periodoIni;
        this.periodoFin = periodoFin;
    }

    public String getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo(String idTrafo) {
        this.idTrafo = idTrafo;
    }

    public String getIdSuministro() {
        return idSuministro;
    }

    public void setIdSuministro(String idSuministro) {
        this.idSuministro = idSuministro;
    }

    public int getPeriodoIni() {
        return periodoIni;
    }

    public void setPeriodoIni(int periodoIni) {
        this.periodoIni = periodoIni;
    }

    public int getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(int periodoFin) {
        this.periodoFin = periodoFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrafo != null ? idTrafo.hashCode() : 0);
        hash += (idSuministro != null ? idSuministro.hashCode() : 0);
        hash += (int) periodoIni;
        hash += (int) periodoFin;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntAmarreTrafoSumPK)) {
            return false;
        }
        IntAmarreTrafoSumPK other = (IntAmarreTrafoSumPK) object;
        if ((this.idTrafo == null && other.idTrafo != null) || (this.idTrafo != null && !this.idTrafo.equals(other.idTrafo))) {
            return false;
        }
        if ((this.idSuministro == null && other.idSuministro != null) || (this.idSuministro != null && !this.idSuministro.equals(other.idSuministro))) {
            return false;
        }
        if (this.periodoIni != other.periodoIni) {
            return false;
        }
        if (this.periodoFin != other.periodoFin) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntAmarreTrafoSumPK[idTrafo=" + idTrafo + ", idSuministro=" + idSuministro + ", periodoIni=" + periodoIni + ", periodoFin=" + periodoFin + "]";
    }

}
