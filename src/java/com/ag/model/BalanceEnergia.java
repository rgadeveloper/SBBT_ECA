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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ultimate
 */
@Entity
@Table(name = "BALANCE_ENERGIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BalanceEnergia.findAll", query = "SELECT b FROM BalanceEnergia b"),
    @NamedQuery(name = "BalanceEnergia.findByUsuario", query = "SELECT b FROM BalanceEnergia b WHERE b.usuario = :usuario"),
    @NamedQuery(name = "BalanceEnergia.findByPrograma", query = "SELECT b FROM BalanceEnergia b WHERE b.programa = :programa"),
    @NamedQuery(name = "BalanceEnergia.findByFechaModif", query = "SELECT b FROM BalanceEnergia b WHERE b.fechaModif = :fechaModif"),
    @NamedQuery(name = "BalanceEnergia.findByIdComponente", query = "SELECT b FROM BalanceEnergia b WHERE b.balanceEnergiaPK.idComponente = :idComponente"),
    @NamedQuery(name = "BalanceEnergia.findByIdComercial", query = "SELECT b FROM BalanceEnergia b WHERE b.idComercial = :idComercial"),
    @NamedQuery(name = "BalanceEnergia.findByPeriodo", query = "SELECT b FROM BalanceEnergia b WHERE b.balanceEnergiaPK.periodo = :periodo"),
    @NamedQuery(name = "BalanceEnergia.findByEnergiaEnt", query = "SELECT b FROM BalanceEnergia b WHERE b.energiaEnt = :energiaEnt"),
    @NamedQuery(name = "BalanceEnergia.findByEnergiaSal", query = "SELECT b FROM BalanceEnergia b WHERE b.energiaSal = :energiaSal"),
    @NamedQuery(name = "BalanceEnergia.findByEnergiaPerdida", query = "SELECT b FROM BalanceEnergia b WHERE b.energiaPerdida = :energiaPerdida"),
    @NamedQuery(name = "BalanceEnergia.findByPorcPerdTotal", query = "SELECT b FROM BalanceEnergia b WHERE b.porcPerdTotal = :porcPerdTotal")})
public class BalanceEnergia implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BalanceEnergiaPK balanceEnergiaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ID_COMERCIAL")
    private String idComercial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENERGIA_ENT")
    private BigDecimal energiaEnt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENERGIA_SAL")
    private BigDecimal energiaSal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENERGIA_PERDIDA")
    private BigDecimal energiaPerdida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PORC_PERD_TOTAL")
    private BigDecimal porcPerdTotal;

    public BalanceEnergia() {
    }

    public BalanceEnergia(BalanceEnergiaPK balanceEnergiaPK) {
        this.balanceEnergiaPK = balanceEnergiaPK;
    }

    public BalanceEnergia(BalanceEnergiaPK balanceEnergiaPK, String usuario, String programa, Date fechaModif, 
            String idComercial, BigDecimal energiaEnt, BigDecimal energiaSal, BigDecimal energiaPerdida, BigDecimal porcPerdTotal) {
        this.balanceEnergiaPK = balanceEnergiaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.idComercial = idComercial;
        this.energiaEnt = energiaEnt;
        this.energiaSal = energiaSal;
        this.energiaPerdida = energiaPerdida;
        this.porcPerdTotal = porcPerdTotal;
    }

    public BalanceEnergia(BigInteger idComponente, int periodo) {
        this.balanceEnergiaPK = new BalanceEnergiaPK(idComponente, periodo);
    }

    public BalanceEnergiaPK getBalanceEnergiaPK() {
        return balanceEnergiaPK;
    }

    public void setBalanceEnergiaPK(BalanceEnergiaPK balanceEnergiaPK) {
        this.balanceEnergiaPK = balanceEnergiaPK;
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

    public String getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(String idComercial) {
        this.idComercial = idComercial;
    }

    public BigDecimal getEnergiaEnt() {
        return energiaEnt;
    }

    public void setEnergiaEnt(BigDecimal energiaEnt) {
        this.energiaEnt = energiaEnt;
    }

    public BigDecimal getEnergiaSal() {
        return energiaSal;
    }

    public void setEnergiaSal(BigDecimal energiaSal) {
        this.energiaSal = energiaSal;
    }

    public BigDecimal getEnergiaPerdida() {
        return energiaPerdida;
    }

    public void setEnergiaPerdida(BigDecimal energiaPerdida) {
        this.energiaPerdida = energiaPerdida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (balanceEnergiaPK != null ? balanceEnergiaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BalanceEnergia)) {
            return false;
        }
        BalanceEnergia other = (BalanceEnergia) object;
        if ((this.balanceEnergiaPK == null && other.balanceEnergiaPK != null) || 
                (this.balanceEnergiaPK != null && !this.balanceEnergiaPK.equals(other.balanceEnergiaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.BalanceEnergia[ balanceEnergiaPK=" + balanceEnergiaPK + " ]";
    }

    public BigDecimal getPorcPerdTotal() {
        return porcPerdTotal;
    }

    public void setPorcPerdTotal(BigDecimal porcPerdTotal) {
        this.porcPerdTotal = porcPerdTotal;
    }
    
}
