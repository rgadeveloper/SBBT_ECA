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
@Table(name = "REL_COMPONENTE")
@NamedQueries({
    @NamedQuery(name = "RelComponente.findAll", query = "SELECT r FROM RelComponente r"),
    @NamedQuery(name = "RelComponente.findByUsuario", query = "SELECT r FROM RelComponente r WHERE r.usuario = :usuario"),
    @NamedQuery(name = "RelComponente.findByPrograma", query = "SELECT r FROM RelComponente r WHERE r.programa = :programa"),
    @NamedQuery(name = "RelComponente.findByFechaModif", query = "SELECT r FROM RelComponente r WHERE r.fechaModif = :fechaModif"),
    @NamedQuery(name = "RelComponente.findByIdComponente", query = "SELECT r FROM RelComponente r WHERE r.relComponentePK.idComponente = :idComponente"),
    @NamedQuery(name = "RelComponente.findByIdComponenteHijo", query = "SELECT r FROM RelComponente r WHERE r.relComponentePK.idComponenteHijo = :idComponenteHijo"),
    @NamedQuery(name = "RelComponente.findByPeriodoIni", query = "SELECT r FROM RelComponente r WHERE r.relComponentePK.periodoIni = :periodoIni"),
    @NamedQuery(name = "RelComponente.findByPeriodoFin", query = "SELECT r FROM RelComponente r WHERE r.periodoFin = :periodoFin")})
public class RelComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RelComponentePK relComponentePK;
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
    @JoinColumn(name = "ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;
    @JoinColumn(name = "ESTADO_HIJO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado1;
    @JoinColumn(name = "ESTADO_PADRE", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado2;
    @JoinColumn(name = "ID_COMPONENTE_HIJO", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente1;

    public RelComponente() {
    }

    public RelComponente(RelComponentePK relComponentePK) {
        this.relComponentePK = relComponentePK;
    }

    public RelComponente(RelComponentePK relComponentePK, String usuario, String programa, Date fechaModif, int periodoFin) {
        this.relComponentePK = relComponentePK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.periodoFin = periodoFin;
    }

    public RelComponente(BigInteger idComponente, BigInteger idComponenteHijo, int periodoIni) {
        this.relComponentePK = new RelComponentePK(idComponente, idComponenteHijo, periodoIni);
    }

    public RelComponentePK getRelComponentePK() {
        return relComponentePK;
    }

    public void setRelComponentePK(RelComponentePK relComponentePK) {
        this.relComponentePK = relComponentePK;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstado1() {
        return estado1;
    }

    public void setEstado1(Estado estado1) {
        this.estado1 = estado1;
    }

    public Estado getEstado2() {
        return estado2;
    }

    public void setEstado2(Estado estado2) {
        this.estado2 = estado2;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public Componente getComponente1() {
        return componente1;
    }

    public void setComponente1(Componente componente1) {
        this.componente1 = componente1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relComponentePK != null ? relComponentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelComponente)) {
            return false;
        }
        RelComponente other = (RelComponente) object;
        if ((this.relComponentePK == null && other.relComponentePK != null) || (this.relComponentePK != null && !this.relComponentePK.equals(other.relComponentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.RelComponente[relComponentePK=" + relComponentePK + "]";
    }

}
