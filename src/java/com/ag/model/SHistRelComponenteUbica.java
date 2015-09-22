/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "S_HIST_REL_COMPONENTE_UBICA")
@NamedQueries({
    @NamedQuery(name = "SHistRelComponenteUbica.findAll", query = "SELECT s FROM SHistRelComponenteUbica s"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByUsuario", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByPrograma", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.programa = :programa"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByFechaModif", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByIdZona", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.sHistRelComponenteUbicaPK.idZona = :idZona"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByIdComponente", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.sHistRelComponenteUbicaPK.idComponente = :idComponente"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByPeriodoIni", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.sHistRelComponenteUbicaPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByPeriodoFin", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.periodoFin = :periodoFin"),
    @NamedQuery(name = "SHistRelComponenteUbica.findByIdSimulacion", query = "SELECT s FROM SHistRelComponenteUbica s WHERE s.sHistRelComponenteUbicaPK.idSimulacion = :idSimulacion")})
public class SHistRelComponenteUbica implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SHistRelComponenteUbicaPK sHistRelComponenteUbicaPK;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Basic(optional = false)
    @Column(name = "PERIODO_FIN")
    private int periodoFin;
    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID_ZONA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ZonaGeografica zonaGeografica;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SComponente sComponente;

    public SHistRelComponenteUbica() {
    }

    public SHistRelComponenteUbica(SHistRelComponenteUbicaPK sHistRelComponenteUbicaPK) {
        this.sHistRelComponenteUbicaPK = sHistRelComponenteUbicaPK;
    }

    public SHistRelComponenteUbica(SHistRelComponenteUbicaPK sHistRelComponenteUbicaPK, String usuario, String programa, Date fechaModif, int periodoFin) {
        this.sHistRelComponenteUbicaPK = sHistRelComponenteUbicaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.periodoFin = periodoFin;
    }

    public SHistRelComponenteUbica(BigInteger idZona, BigInteger idComponente, int periodoIni, long idSimulacion) {
        this.sHistRelComponenteUbicaPK = new SHistRelComponenteUbicaPK(idZona, idComponente, periodoIni, idSimulacion);
    }

    public SHistRelComponenteUbicaPK getSHistRelComponenteUbicaPK() {
        return sHistRelComponenteUbicaPK;
    }

    public void setSHistRelComponenteUbicaPK(SHistRelComponenteUbicaPK sHistRelComponenteUbicaPK) {
        this.sHistRelComponenteUbicaPK = sHistRelComponenteUbicaPK;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public int getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(int periodoFin) {
        this.periodoFin = periodoFin;
    }

    public ZonaGeografica getZonaGeografica() {
        return zonaGeografica;
    }

    public void setZonaGeografica(ZonaGeografica zonaGeografica) {
        this.zonaGeografica = zonaGeografica;
    }

    public SComponente getSComponente() {
        return sComponente;
    }

    public void setSComponente(SComponente sComponente) {
        this.sComponente = sComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sHistRelComponenteUbicaPK != null ? sHistRelComponenteUbicaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SHistRelComponenteUbica)) {
            return false;
        }
        SHistRelComponenteUbica other = (SHistRelComponenteUbica) object;
        if ((this.sHistRelComponenteUbicaPK == null && other.sHistRelComponenteUbicaPK != null) || (this.sHistRelComponenteUbicaPK != null && !this.sHistRelComponenteUbicaPK.equals(other.sHistRelComponenteUbicaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SHistRelComponenteUbica[sHistRelComponenteUbicaPK=" + sHistRelComponenteUbicaPK + "]";
    }

}
