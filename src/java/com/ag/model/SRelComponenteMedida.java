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
@Table(name = "S_REL_COMPONENTE_MEDIDA")
@NamedQueries({
    @NamedQuery(name = "SRelComponenteMedida.findAll", query = "SELECT s FROM SRelComponenteMedida s"),
    @NamedQuery(name = "SRelComponenteMedida.findByUsuario", query = "SELECT s FROM SRelComponenteMedida s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SRelComponenteMedida.findByPrograma", query = "SELECT s FROM SRelComponenteMedida s WHERE s.programa = :programa"),
    @NamedQuery(name = "SRelComponenteMedida.findByFechaModif", query = "SELECT s FROM SRelComponenteMedida s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SRelComponenteMedida.findByIdComponenteMedida", query = "SELECT s FROM SRelComponenteMedida s WHERE s.sRelComponenteMedidaPK.idComponenteMedida = :idComponenteMedida"),
    @NamedQuery(name = "SRelComponenteMedida.findByIdComponente", query = "SELECT s FROM SRelComponenteMedida s WHERE s.sRelComponenteMedidaPK.idComponente = :idComponente"),
    @NamedQuery(name = "SRelComponenteMedida.findByFecha", query = "SELECT s FROM SRelComponenteMedida s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "SRelComponenteMedida.findByIdEstadoCompo", query = "SELECT s FROM SRelComponenteMedida s WHERE s.idEstadoCompo = :idEstadoCompo"),
    @NamedQuery(name = "SRelComponenteMedida.findByIdEstadoCompoMed", query = "SELECT s FROM SRelComponenteMedida s WHERE s.idEstadoCompoMed = :idEstadoCompoMed"),
    @NamedQuery(name = "SRelComponenteMedida.findByIdEstado", query = "SELECT s FROM SRelComponenteMedida s WHERE s.idEstado = :idEstado"),
    @NamedQuery(name = "SRelComponenteMedida.findByPeriodoIni", query = "SELECT s FROM SRelComponenteMedida s WHERE s.sRelComponenteMedidaPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "SRelComponenteMedida.findByPeriodoFin", query = "SELECT s FROM SRelComponenteMedida s WHERE s.periodoFin = :periodoFin")})
public class SRelComponenteMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SRelComponenteMedidaPK sRelComponenteMedidaPK;
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
    private SComponenteMedida sComponenteMedida;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SComponente sComponente;

    public SRelComponenteMedida() {
    }

    public SRelComponenteMedida(SRelComponenteMedidaPK sRelComponenteMedidaPK) {
        this.sRelComponenteMedidaPK = sRelComponenteMedidaPK;
    }

    public SRelComponenteMedida(SRelComponenteMedidaPK sRelComponenteMedidaPK, String usuario, String programa, Date fechaModif, Date fecha, String idEstadoCompo, String idEstadoCompoMed, String idEstado, int periodoFin) {
        this.sRelComponenteMedidaPK = sRelComponenteMedidaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.fecha = fecha;
        this.idEstadoCompo = idEstadoCompo;
        this.idEstadoCompoMed = idEstadoCompoMed;
        this.idEstado = idEstado;
        this.periodoFin = periodoFin;
    }

    public SRelComponenteMedida(BigInteger idComponenteMedida, BigInteger idComponente, int periodoIni) {
        this.sRelComponenteMedidaPK = new SRelComponenteMedidaPK(idComponenteMedida, idComponente, periodoIni);
    }

    public SRelComponenteMedidaPK getSRelComponenteMedidaPK() {
        return sRelComponenteMedidaPK;
    }

    public void setSRelComponenteMedidaPK(SRelComponenteMedidaPK sRelComponenteMedidaPK) {
        this.sRelComponenteMedidaPK = sRelComponenteMedidaPK;
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

    public SComponenteMedida getSComponenteMedida() {
        return sComponenteMedida;
    }

    public void setSComponenteMedida(SComponenteMedida sComponenteMedida) {
        this.sComponenteMedida = sComponenteMedida;
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
        hash += (sRelComponenteMedidaPK != null ? sRelComponenteMedidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SRelComponenteMedida)) {
            return false;
        }
        SRelComponenteMedida other = (SRelComponenteMedida) object;
        if ((this.sRelComponenteMedidaPK == null && other.sRelComponenteMedidaPK != null) || (this.sRelComponenteMedidaPK != null && !this.sRelComponenteMedidaPK.equals(other.sRelComponenteMedidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SRelComponenteMedida[sRelComponenteMedidaPK=" + sRelComponenteMedidaPK + "]";
    }

}
