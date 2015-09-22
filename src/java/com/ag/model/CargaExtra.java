/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CARGA_EXTRA")
@NamedQueries({
    @NamedQuery(name = "CargaExtra.findAll", query = "SELECT c FROM CargaExtra c"),
    @NamedQuery(name = "CargaExtra.findByUsuario", query = "SELECT c FROM CargaExtra c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "CargaExtra.findByPrograma", query = "SELECT c FROM CargaExtra c WHERE c.programa = :programa"),
    @NamedQuery(name = "CargaExtra.findByFechaModif", query = "SELECT c FROM CargaExtra c WHERE c.fechaModif = :fechaModif"),
    @NamedQuery(name = "CargaExtra.findByTipoCarga", query = "SELECT c FROM CargaExtra c WHERE c.cargaExtraPK.tipoCarga = :tipoCarga"),
    @NamedQuery(name = "CargaExtra.findByIdComponente", query = "SELECT c FROM CargaExtra c WHERE c.cargaExtraPK.idComponente = :idComponente"),
    @NamedQuery(name = "CargaExtra.findByPeriodo", query = "SELECT c FROM CargaExtra c WHERE c.cargaExtraPK.periodo = :periodo"),
    @NamedQuery(name = "CargaExtra.findByConsumo", query = "SELECT c FROM CargaExtra c WHERE c.consumo = :consumo")})
public class CargaExtra implements Serializable {
  
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CargaExtraPK cargaExtraPK;
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
    @Column(name = "CONSUMO")
    private BigDecimal consumo;
    @JoinColumn(name = "TIPO_CARGA", referencedColumnName = "TIPO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente;

    public CargaExtra() {
    }

    public CargaExtra(CargaExtraPK cargaExtraPK) {
        this.cargaExtraPK = cargaExtraPK;
    }

    public CargaExtra(CargaExtraPK cargaExtraPK, String usuario, String programa, Date fechaModif, BigDecimal consumo) {
        this.cargaExtraPK = cargaExtraPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.consumo = consumo;
    }

    public CargaExtra(String tipoCarga, BigInteger idComponente, int periodo) {
        this.cargaExtraPK = new CargaExtraPK(tipoCarga, idComponente, periodo);
    }

    public CargaExtraPK getCargaExtraPK() {
        return cargaExtraPK;
    }

    public void setCargaExtraPK(CargaExtraPK cargaExtraPK) {
        this.cargaExtraPK = cargaExtraPK;
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

    public BigDecimal getConsumo() {
        return consumo;
    }

    public void setConsumo(BigDecimal consumo) {
        this.consumo = consumo;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
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
        hash += (cargaExtraPK != null ? cargaExtraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargaExtra)) {
            return false;
        }
        CargaExtra other = (CargaExtra) object;
        if ((this.cargaExtraPK == null && other.cargaExtraPK != null) || (this.cargaExtraPK != null && !this.cargaExtraPK.equals(other.cargaExtraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.CargaExtra[cargaExtraPK=" + cargaExtraPK + "]";
    }

}
