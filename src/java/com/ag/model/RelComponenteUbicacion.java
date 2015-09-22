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
@Table(name = "REL_COMPONENTE_UBICACION")
@NamedQueries({
    @NamedQuery(name = "RelComponenteUbicacion.findAll", query = "SELECT r FROM RelComponenteUbicacion r"),
    @NamedQuery(name = "RelComponenteUbicacion.findByUsuario", query = "SELECT r FROM RelComponenteUbicacion r WHERE r.usuario = :usuario"),
    @NamedQuery(name = "RelComponenteUbicacion.findByPrograma", query = "SELECT r FROM RelComponenteUbicacion r WHERE r.programa = :programa"),
    @NamedQuery(name = "RelComponenteUbicacion.findByFechaModif", query = "SELECT r FROM RelComponenteUbicacion r WHERE r.fechaModif = :fechaModif"),
    @NamedQuery(name = "RelComponenteUbicacion.findByIdZona", query = "SELECT r FROM RelComponenteUbicacion r WHERE r.relComponenteUbicacionPK.idZona = :idZona"),
    @NamedQuery(name = "RelComponenteUbicacion.findByIdComponente", query = "SELECT r FROM RelComponenteUbicacion r WHERE r.relComponenteUbicacionPK.idComponente = :idComponente"),
    @NamedQuery(name = "RelComponenteUbicacion.findByPeriodoIni", query = "SELECT r FROM RelComponenteUbicacion r WHERE r.relComponenteUbicacionPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "RelComponenteUbicacion.findByPeriodoFin", query = "SELECT r FROM RelComponenteUbicacion r WHERE r.periodoFin = :periodoFin")})
public class RelComponenteUbicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RelComponenteUbicacionPK relComponenteUbicacionPK;
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
    private Componente componente;

    public RelComponenteUbicacion() {
    }

    public RelComponenteUbicacion(RelComponenteUbicacionPK relComponenteUbicacionPK) {
        this.relComponenteUbicacionPK = relComponenteUbicacionPK;
    }

    public RelComponenteUbicacion(RelComponenteUbicacionPK relComponenteUbicacionPK, String usuario, String programa, Date fechaModif, int periodoFin) {
        this.relComponenteUbicacionPK = relComponenteUbicacionPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.periodoFin = periodoFin;
    }

    public RelComponenteUbicacion(BigInteger idZona, BigInteger idComponente, int periodoIni) {
        this.relComponenteUbicacionPK = new RelComponenteUbicacionPK(idZona, idComponente, periodoIni);
    }

    public RelComponenteUbicacionPK getRelComponenteUbicacionPK() {
        return relComponenteUbicacionPK;
    }

    public void setRelComponenteUbicacionPK(RelComponenteUbicacionPK relComponenteUbicacionPK) {
        this.relComponenteUbicacionPK = relComponenteUbicacionPK;
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

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relComponenteUbicacionPK != null ? relComponenteUbicacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelComponenteUbicacion)) {
            return false;
        }
        RelComponenteUbicacion other = (RelComponenteUbicacion) object;
        if ((this.relComponenteUbicacionPK == null && other.relComponenteUbicacionPK != null) || (this.relComponenteUbicacionPK != null && !this.relComponenteUbicacionPK.equals(other.relComponenteUbicacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.RelComponenteUbicacion[relComponenteUbicacionPK=" + relComponenteUbicacionPK + "]";
    }

}
