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
@Table(name = "INT_AMARRE_TRAFO_SUM")
@NamedQueries({
    @NamedQuery(name = "IntAmarreTrafoSum.findAll", query = "SELECT i FROM IntAmarreTrafoSum i"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByIdTrafo", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.intAmarreTrafoSumPK.idTrafo = :idTrafo"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByIdSuministro", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.intAmarreTrafoSumPK.idSuministro = :idSuministro"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByFechaModif", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.fechaModif = :fechaModif"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByPeriodoIni", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.intAmarreTrafoSumPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByPeriodoFin", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.intAmarreTrafoSumPK.periodoFin = :periodoFin"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByProcesado", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.procesado = :procesado"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByFechaProceso", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "IntAmarreTrafoSum.findByDescProceso", query = "SELECT i FROM IntAmarreTrafoSum i WHERE i.descProceso = :descProceso")})
public class IntAmarreTrafoSum implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntAmarreTrafoSumPK intAmarreTrafoSumPK;
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

    public IntAmarreTrafoSum() {
    }

    public IntAmarreTrafoSum(IntAmarreTrafoSumPK intAmarreTrafoSumPK) {
        this.intAmarreTrafoSumPK = intAmarreTrafoSumPK;
    }

    public IntAmarreTrafoSum(String idTrafo, String idSuministro, int periodoIni, int periodoFin) {
        this.intAmarreTrafoSumPK = new IntAmarreTrafoSumPK(idTrafo, idSuministro, periodoIni, periodoFin);
    }

    public IntAmarreTrafoSumPK getIntAmarreTrafoSumPK() {
        return intAmarreTrafoSumPK;
    }

    public void setIntAmarreTrafoSumPK(IntAmarreTrafoSumPK intAmarreTrafoSumPK) {
        this.intAmarreTrafoSumPK = intAmarreTrafoSumPK;
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
        hash += (intAmarreTrafoSumPK != null ? intAmarreTrafoSumPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntAmarreTrafoSum)) {
            return false;
        }
        IntAmarreTrafoSum other = (IntAmarreTrafoSum) object;
        if ((this.intAmarreTrafoSumPK == null && other.intAmarreTrafoSumPK != null) || (this.intAmarreTrafoSumPK != null && !this.intAmarreTrafoSumPK.equals(other.intAmarreTrafoSumPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntAmarreTrafoSum[intAmarreTrafoSumPK=" + intAmarreTrafoSumPK + "]";
    }

}
