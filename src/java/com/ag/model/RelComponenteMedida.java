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
@Table(name = "REL_COMPONENTE_MEDIDA")
@NamedQueries({
    @NamedQuery(name = "RelComponenteMedida.findAll", query = "SELECT r FROM RelComponenteMedida r"),
    @NamedQuery(name = "RelComponenteMedida.findByUsuario", query = "SELECT r FROM RelComponenteMedida r WHERE r.usuario = :usuario"),
    @NamedQuery(name = "RelComponenteMedida.findByPrograma", query = "SELECT r FROM RelComponenteMedida r WHERE r.programa = :programa"),
    @NamedQuery(name = "RelComponenteMedida.findByFechaModif", query = "SELECT r FROM RelComponenteMedida r WHERE r.fechaModif = :fechaModif"),
    @NamedQuery(name = "RelComponenteMedida.findByIdComponenteMedida", query = "SELECT r FROM RelComponenteMedida r WHERE r.relComponenteMedidaPK.idComponenteMedida = :idComponenteMedida"),
    @NamedQuery(name = "RelComponenteMedida.findByIdComponente", query = "SELECT r FROM RelComponenteMedida r WHERE r.relComponenteMedidaPK.idComponente = :idComponente"),
    @NamedQuery(name = "RelComponenteMedida.findByFecha", query = "SELECT r FROM RelComponenteMedida r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "RelComponenteMedida.findByIdEstadoCompo", query = "SELECT r FROM RelComponenteMedida r WHERE r.idEstadoCompo = :idEstadoCompo"),
    @NamedQuery(name = "RelComponenteMedida.findByIdEstadoCompoMed", query = "SELECT r FROM RelComponenteMedida r WHERE r.idEstadoCompoMed = :idEstadoCompoMed"),
    @NamedQuery(name = "RelComponenteMedida.findByIdEstado", query = "SELECT r FROM RelComponenteMedida r WHERE r.idEstado = :idEstado"),
    @NamedQuery(name = "RelComponenteMedida.findByPeriodoIni", query = "SELECT r FROM RelComponenteMedida r WHERE r.relComponenteMedidaPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "RelComponenteMedida.findByPeriodoFin", query = "SELECT r FROM RelComponenteMedida r WHERE r.periodoFin = :periodoFin")})
public class RelComponenteMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RelComponenteMedidaPK relComponenteMedidaPK;
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
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "ID_ESTADO_COMPO")
    private String idEstadoCompo;
    @Basic(optional = false)
    @Column(name = "ID_ESTADO_COMPO_MED")
    private String idEstadoCompoMed;
    @Basic(optional = false)
    @Column(name = "ID_ESTADO")
    private String idEstado;
    @Basic(optional = false)
    @Column(name = "PERIODO_FIN")
    private int periodoFin;
    @JoinColumn(name = "ID_COMPONENTE_MEDIDA", referencedColumnName = "ID_COMPONENTE_MEDIDA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ComponenteMedida componenteMedida;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente;

    public RelComponenteMedida() {
    }

    public RelComponenteMedida(RelComponenteMedidaPK relComponenteMedidaPK) {
        this.relComponenteMedidaPK = relComponenteMedidaPK;
    }

    public RelComponenteMedida(RelComponenteMedidaPK relComponenteMedidaPK, String usuario, String programa, Date fechaModif, Date fecha, String idEstadoCompo, String idEstadoCompoMed, String idEstado, int periodoFin) {
        this.relComponenteMedidaPK = relComponenteMedidaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.fecha = fecha;
        this.idEstadoCompo = idEstadoCompo;
        this.idEstadoCompoMed = idEstadoCompoMed;
        this.idEstado = idEstado;
        this.periodoFin = periodoFin;
    }

    public RelComponenteMedida(BigInteger idComponenteMedida, BigInteger idComponente, int periodoIni) {
        this.relComponenteMedidaPK = new RelComponenteMedidaPK(idComponenteMedida, idComponente, periodoIni);
    }

    public RelComponenteMedidaPK getRelComponenteMedidaPK() {
        return relComponenteMedidaPK;
    }

    public void setRelComponenteMedidaPK(RelComponenteMedidaPK relComponenteMedidaPK) {
        this.relComponenteMedidaPK = relComponenteMedidaPK;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdEstadoCompo() {
        return idEstadoCompo;
    }

    public void setIdEstadoCompo(String idEstadoCompo) {
        this.idEstadoCompo = idEstadoCompo;
    }

    public String getIdEstadoCompoMed() {
        return idEstadoCompoMed;
    }

    public void setIdEstadoCompoMed(String idEstadoCompoMed) {
        this.idEstadoCompoMed = idEstadoCompoMed;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public int getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(int periodoFin) {
        this.periodoFin = periodoFin;
    }

    public ComponenteMedida getComponenteMedida() {
        return componenteMedida;
    }

    public void setComponenteMedida(ComponenteMedida componenteMedida) {
        this.componenteMedida = componenteMedida;
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
        hash += (relComponenteMedidaPK != null ? relComponenteMedidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelComponenteMedida)) {
            return false;
        }
        RelComponenteMedida other = (RelComponenteMedida) object;
        if ((this.relComponenteMedidaPK == null && other.relComponenteMedidaPK != null) || (this.relComponenteMedidaPK != null && !this.relComponenteMedidaPK.equals(other.relComponenteMedidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.RelComponenteMedida[relComponenteMedidaPK=" + relComponenteMedidaPK + "]";
    }

}
