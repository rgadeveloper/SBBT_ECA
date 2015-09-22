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
@Table(name = "S_REL_COMPONENTE_UBICACION")
@NamedQueries({
    @NamedQuery(name = "SRelComponenteUbicacion.findAll", query = "SELECT s FROM SRelComponenteUbicacion s"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByUsuario", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByPrograma", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.programa = :programa"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByFechaModif", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByIdZona", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.sRelComponenteUbicacionPK.idZona = :idZona"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByIdComponente", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.sRelComponenteUbicacionPK.idComponente = :idComponente"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByPeriodoIni", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.sRelComponenteUbicacionPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByPeriodoFin", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.periodoFin = :periodoFin"),
    @NamedQuery(name = "SRelComponenteUbicacion.findByIdSimulacion", query = "SELECT s FROM SRelComponenteUbicacion s WHERE s.idSimulacion = :idSimulacion")})
public class SRelComponenteUbicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SRelComponenteUbicacionPK sRelComponenteUbicacionPK;
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
    @Column(name = "ID_SIMULACION")
    private Long idSimulacion;
    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID_ZONA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ZonaGeografica zonaGeografica;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SComponente sComponente;

    public SRelComponenteUbicacion() {
    }

    public SRelComponenteUbicacion(SRelComponenteUbicacionPK sRelComponenteUbicacionPK) {
        this.sRelComponenteUbicacionPK = sRelComponenteUbicacionPK;
    }

    public SRelComponenteUbicacion(SRelComponenteUbicacionPK sRelComponenteUbicacionPK, String usuario, String programa, Date fechaModif, int periodoFin) {
        this.sRelComponenteUbicacionPK = sRelComponenteUbicacionPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.periodoFin = periodoFin;
    }

    public SRelComponenteUbicacion(BigInteger idZona, BigInteger idComponente, int periodoIni) {
        this.sRelComponenteUbicacionPK = new SRelComponenteUbicacionPK(idZona, idComponente, periodoIni);
    }

    public SRelComponenteUbicacionPK getSRelComponenteUbicacionPK() {
        return sRelComponenteUbicacionPK;
    }

    public void setSRelComponenteUbicacionPK(SRelComponenteUbicacionPK sRelComponenteUbicacionPK) {
        this.sRelComponenteUbicacionPK = sRelComponenteUbicacionPK;
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

    public Long getIdSimulacion() {
        return idSimulacion;
    }

    public void setIdSimulacion(Long idSimulacion) {
        this.idSimulacion = idSimulacion;
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
        hash += (sRelComponenteUbicacionPK != null ? sRelComponenteUbicacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRelComponenteUbicacion)) {
            return false;
        }
        SRelComponenteUbicacion other = (SRelComponenteUbicacion) object;
        if ((this.sRelComponenteUbicacionPK == null && other.sRelComponenteUbicacionPK != null) || (this.sRelComponenteUbicacionPK != null && !this.sRelComponenteUbicacionPK.equals(other.sRelComponenteUbicacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SRelComponenteUbicacion[sRelComponenteUbicacionPK=" + sRelComponenteUbicacionPK + "]";
    }

}
