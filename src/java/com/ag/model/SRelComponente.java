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
 * @author Larry
 */
@Entity
@Table(name = "S_REL_COMPONENTE")
@NamedQueries({
    @NamedQuery(name = "SRelComponente.findAll", query = "SELECT s FROM SRelComponente s"),
    @NamedQuery(name = "SRelComponente.findByUsuario", query = "SELECT s FROM SRelComponente s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SRelComponente.findByPrograma", query = "SELECT s FROM SRelComponente s WHERE s.programa = :programa"),
    @NamedQuery(name = "SRelComponente.findByFechaModif", query = "SELECT s FROM SRelComponente s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SRelComponente.findByIdComponente", query = "SELECT s FROM SRelComponente s WHERE s.sRelComponentePK.idComponente = :idComponente"),
    @NamedQuery(name = "SRelComponente.findByIdComponenteHijo", query = "SELECT s FROM SRelComponente s WHERE s.sRelComponentePK.idComponenteHijo = :idComponenteHijo"),
    @NamedQuery(name = "SRelComponente.findByPeriodoIni", query = "SELECT s FROM SRelComponente s WHERE s.sRelComponentePK.periodoIni = :periodoIni"),
    @NamedQuery(name = "SRelComponente.findByPeriodoFin", query = "SELECT s FROM SRelComponente s WHERE s.periodoFin = :periodoFin"),
    @NamedQuery(name = "SRelComponente.findByIdSimulacion", query = "SELECT s FROM SRelComponente s WHERE s.idSimulacion = :idSimulacion")})
public class SRelComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SRelComponentePK sRelComponentePK;
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
    @JoinColumn(name = "ID_COMPONENTE_HIJO", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SComponente sComponente;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SComponente sComponente1;
    @JoinColumn(name = "ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;
    @JoinColumn(name = "ESTADO_HIJO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado1;
    @JoinColumn(name = "ESTADO_PADRE", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado2;

    public SRelComponente() {
    }

    public SRelComponente(SRelComponentePK sRelComponentePK) {
        this.sRelComponentePK = sRelComponentePK;
    }

    public SRelComponente(SRelComponentePK sRelComponentePK, String usuario, String programa, Date fechaModif, int periodoFin) {
        this.sRelComponentePK = sRelComponentePK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.periodoFin = periodoFin;
    }

    public SRelComponente(BigInteger idComponente, BigInteger idComponenteHijo, int periodoIni) {
        this.sRelComponentePK = new SRelComponentePK(idComponente, idComponenteHijo, periodoIni);
    }

    public SRelComponentePK getSRelComponentePK() {
        return sRelComponentePK;
    }

    public void setSRelComponentePK(SRelComponentePK sRelComponentePK) {
        this.sRelComponentePK = sRelComponentePK;
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

    public SComponente getSComponente() {
        return sComponente;
    }

    public void setSComponente(SComponente sComponente) {
        this.sComponente = sComponente;
    }

    public SComponente getSComponente1() {
        return sComponente1;
    }

    public void setSComponente1(SComponente sComponente1) {
        this.sComponente1 = sComponente1;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sRelComponentePK != null ? sRelComponentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRelComponente)) {
            return false;
        }
        SRelComponente other = (SRelComponente) object;
        if ((this.sRelComponentePK == null && other.sRelComponentePK != null) || (this.sRelComponentePK != null && !this.sRelComponentePK.equals(other.sRelComponentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SRelComponente[sRelComponentePK=" + sRelComponentePK + "]";
    }

}
