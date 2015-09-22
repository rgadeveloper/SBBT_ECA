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
@Table(name = "S_CARGA_EXTRA")
@NamedQueries({
    @NamedQuery(name = "SCargaExtra.findAll", query = "SELECT s FROM SCargaExtra s"),
    @NamedQuery(name = "SCargaExtra.findByUsuario", query = "SELECT s FROM SCargaExtra s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SCargaExtra.findByPrograma", query = "SELECT s FROM SCargaExtra s WHERE s.programa = :programa"),
    @NamedQuery(name = "SCargaExtra.findByFechaModif", query = "SELECT s FROM SCargaExtra s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SCargaExtra.findByTipoCarga", query = "SELECT s FROM SCargaExtra s WHERE s.sCargaExtraPK.tipoCarga = :tipoCarga"),
    @NamedQuery(name = "SCargaExtra.findByIdComponente", query = "SELECT s FROM SCargaExtra s WHERE s.sCargaExtraPK.idComponente = :idComponente"),
    @NamedQuery(name = "SCargaExtra.findByPeriodo", query = "SELECT s FROM SCargaExtra s WHERE s.sCargaExtraPK.periodo = :periodo"),
    @NamedQuery(name = "SCargaExtra.findByConsumo", query = "SELECT s FROM SCargaExtra s WHERE s.consumo = :consumo")})
public class SCargaExtra implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SCargaExtraPK sCargaExtraPK;
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
    private SComponente sComponente;

    public SCargaExtra() {
    }

    public SCargaExtra(SCargaExtraPK sCargaExtraPK) {
        this.sCargaExtraPK = sCargaExtraPK;
    }

    public SCargaExtra(SCargaExtraPK sCargaExtraPK, String usuario, String programa, Date fechaModif, BigDecimal consumo) {
        this.sCargaExtraPK = sCargaExtraPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.consumo = consumo;
    }

    public SCargaExtra(String tipoCarga, BigInteger idComponente, int periodo) {
        this.sCargaExtraPK = new SCargaExtraPK(tipoCarga, idComponente, periodo);
    }

    public SCargaExtraPK getSCargaExtraPK() {
        return sCargaExtraPK;
    }

    public void setSCargaExtraPK(SCargaExtraPK sCargaExtraPK) {
        this.sCargaExtraPK = sCargaExtraPK;
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

    public SComponente getSComponente() {
        return sComponente;
    }

    public void setSComponente(SComponente sComponente) {
        this.sComponente = sComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sCargaExtraPK != null ? sCargaExtraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SCargaExtra)) {
            return false;
        }
        SCargaExtra other = (SCargaExtra) object;
        if ((this.sCargaExtraPK == null && other.sCargaExtraPK != null) || (this.sCargaExtraPK != null && !this.sCargaExtraPK.equals(other.sCargaExtraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SCargaExtra[sCargaExtraPK=" + sCargaExtraPK + "]";
    }

}
