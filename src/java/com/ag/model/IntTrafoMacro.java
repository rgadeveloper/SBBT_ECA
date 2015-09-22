/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "INT_TRAFO_MACRO")
@NamedQueries({
    @NamedQuery(name = "IntTrafoMacro.findAll", query = "SELECT i FROM IntTrafoMacro i"),
    @NamedQuery(name = "IntTrafoMacro.findByIdTrafo", query = "SELECT i FROM IntTrafoMacro i WHERE i.intTrafoMacroPK.idTrafo = :idTrafo"),
    @NamedQuery(name = "IntTrafoMacro.findByIdTotalizador", query = "SELECT i FROM IntTrafoMacro i WHERE i.intTrafoMacroPK.idTotalizador = :idTotalizador"),
    @NamedQuery(name = "IntTrafoMacro.findByFechaModif", query = "SELECT i FROM IntTrafoMacro i WHERE i.fechaModif = :fechaModif"),
    @NamedQuery(name = "IntTrafoMacro.findByPeriodoIni", query = "SELECT i FROM IntTrafoMacro i WHERE i.intTrafoMacroPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "IntTrafoMacro.findByPeriodoFin", query = "SELECT i FROM IntTrafoMacro i WHERE i.intTrafoMacroPK.periodoFin = :periodoFin"),
    @NamedQuery(name = "IntTrafoMacro.findByProcesado", query = "SELECT i FROM IntTrafoMacro i WHERE i.procesado = :procesado"),
    @NamedQuery(name = "IntTrafoMacro.findByFechaProceso", query = "SELECT i FROM IntTrafoMacro i WHERE i.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "IntTrafoMacro.findByDescProceso", query = "SELECT i FROM IntTrafoMacro i WHERE i.descProceso = :descProceso")})
public class IntTrafoMacro implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntTrafoMacroPK intTrafoMacroPK;
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Column(name = "PROCESADO")
    private Short procesado;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "DESC_PROCESO")
    private String descProceso;

    public IntTrafoMacro() {
    }

    public IntTrafoMacro(IntTrafoMacroPK intTrafoMacroPK) {
        this.intTrafoMacroPK = intTrafoMacroPK;
    }

    public IntTrafoMacro(String idTrafo, String idTotalizador, int periodoIni, int periodoFin) {
        this.intTrafoMacroPK = new IntTrafoMacroPK(idTrafo, idTotalizador, periodoIni, periodoFin);
    }

    public IntTrafoMacroPK getIntTrafoMacroPK() {
        return intTrafoMacroPK;
    }

    public void setIntTrafoMacroPK(IntTrafoMacroPK intTrafoMacroPK) {
        this.intTrafoMacroPK = intTrafoMacroPK;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public Short getProcesado() {
        return procesado;
    }

    public void setProcesado(Short procesado) {
        this.procesado = procesado;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getDescProceso() {
        return descProceso;
    }

    public void setDescProceso(String descProceso) {
        this.descProceso = descProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intTrafoMacroPK != null ? intTrafoMacroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntTrafoMacro)) {
            return false;
        }
        IntTrafoMacro other = (IntTrafoMacro) object;
        if ((this.intTrafoMacroPK == null && other.intTrafoMacroPK != null) || (this.intTrafoMacroPK != null && !this.intTrafoMacroPK.equals(other.intTrafoMacroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntTrafoMacro[intTrafoMacroPK=" + intTrafoMacroPK + "]";
    }

}
