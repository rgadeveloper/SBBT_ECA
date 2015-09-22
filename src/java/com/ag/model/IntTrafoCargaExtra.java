/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "INT_TRAFO_CARGA_EXTRA")
@NamedQueries({
    @NamedQuery(name = "IntTrafoCargaExtra.findAll", query = "SELECT i FROM IntTrafoCargaExtra i"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByIdTrafo", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.intTrafoCargaExtraPK.idTrafo = :idTrafo"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByFechaModif", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.fechaModif = :fechaModif"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByTipoCarga", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.intTrafoCargaExtraPK.tipoCarga = :tipoCarga"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByPeriodoIni", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.intTrafoCargaExtraPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByPeriodoFin", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.intTrafoCargaExtraPK.periodoFin = :periodoFin"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByConsumo", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.consumo = :consumo"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByProcesado", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.procesado = :procesado"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByFechaProceso", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "IntTrafoCargaExtra.findByDescProceso", query = "SELECT i FROM IntTrafoCargaExtra i WHERE i.descProceso = :descProceso")})
public class IntTrafoCargaExtra implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntTrafoCargaExtraPK intTrafoCargaExtraPK;
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Column(name = "CONSUMO")
    private BigDecimal consumo;
    @Column(name = "PROCESADO")
    private Short procesado;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "DESC_PROCESO")
    private String descProceso;

    public IntTrafoCargaExtra() {
    }

    public IntTrafoCargaExtra(IntTrafoCargaExtraPK intTrafoCargaExtraPK) {
        this.intTrafoCargaExtraPK = intTrafoCargaExtraPK;
    }

    public IntTrafoCargaExtra(String idTrafo, String tipoCarga, int periodoIni, String periodoFin) {
        this.intTrafoCargaExtraPK = new IntTrafoCargaExtraPK(idTrafo, tipoCarga, periodoIni, periodoFin);
    }

    public IntTrafoCargaExtraPK getIntTrafoCargaExtraPK() {
        return intTrafoCargaExtraPK;
    }

    public void setIntTrafoCargaExtraPK(IntTrafoCargaExtraPK intTrafoCargaExtraPK) {
        this.intTrafoCargaExtraPK = intTrafoCargaExtraPK;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public BigDecimal getConsumo() {
        return consumo;
    }

    public void setConsumo(BigDecimal consumo) {
        this.consumo = consumo;
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
        hash += (intTrafoCargaExtraPK != null ? intTrafoCargaExtraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntTrafoCargaExtra)) {
            return false;
        }
        IntTrafoCargaExtra other = (IntTrafoCargaExtra) object;
        if ((this.intTrafoCargaExtraPK == null && other.intTrafoCargaExtraPK != null) || (this.intTrafoCargaExtraPK != null && !this.intTrafoCargaExtraPK.equals(other.intTrafoCargaExtraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntTrafoCargaExtra[intTrafoCargaExtraPK=" + intTrafoCargaExtraPK + "]";
    }

}
