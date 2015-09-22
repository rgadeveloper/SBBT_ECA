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
public class IntTrafoMacroPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_TRAFO")
    private String idTrafo;
    @Basic(optional = false)
    @Column(name = "ID_TOTALIZADOR")
    private String idTotalizador;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;
    @Basic(optional = false)
    @Column(name = "PERIODO_FIN")
    private int periodoFin;

    public IntTrafoMacroPK() {
    }

    public IntTrafoMacroPK(String idTrafo, String idTotalizador, int periodoIni, int periodoFin) {
        this.idTrafo = idTrafo;
        this.idTotalizador = idTotalizador;
        this.periodoIni = periodoIni;
        this.periodoFin = periodoFin;
    }

    public String getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo(String idTrafo) {
        this.idTrafo = idTrafo;
    }

    public String getIdTotalizador() {
        return idTotalizador;
    }

    public void setIdTotalizador(String idTotalizador) {
        this.idTotalizador = idTotalizador;
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
        hash += (idTotalizador != null ? idTotalizador.hashCode() : 0);
        hash += (int) periodoIni;
        hash += (int) periodoFin;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntTrafoMacroPK)) {
            return false;
        }
        IntTrafoMacroPK other = (IntTrafoMacroPK) object;
        if ((this.idTrafo == null && other.idTrafo != null) || (this.idTrafo != null && !this.idTrafo.equals(other.idTrafo))) {
            return false;
        }
        if ((this.idTotalizador == null && other.idTotalizador != null) || (this.idTotalizador != null && !this.idTotalizador.equals(other.idTotalizador))) {
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
        return "com.ag.model.IntTrafoMacroPK[idTrafo=" + idTrafo + ", idTotalizador=" + idTotalizador + ", periodoIni=" + periodoIni + ", periodoFin=" + periodoFin + "]";
    }

}
