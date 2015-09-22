/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "RANGO_CLASIFICACION")
@NamedQueries({
    @NamedQuery(name = "RangoClasificacion.findAll", query = "SELECT r FROM RangoClasificacion r"),
    @NamedQuery(name = "RangoClasificacion.findByUsuario", query = "SELECT r FROM RangoClasificacion r WHERE r.usuario = :usuario"),
    @NamedQuery(name = "RangoClasificacion.findByPrograma", query = "SELECT r FROM RangoClasificacion r WHERE r.programa = :programa"),
    @NamedQuery(name = "RangoClasificacion.findByFechaModif", query = "SELECT r FROM RangoClasificacion r WHERE r.fechaModif = :fechaModif"),
    @NamedQuery(name = "RangoClasificacion.findByIdRango", query = "SELECT r FROM RangoClasificacion r WHERE r.idRango = :idRango"),
    @NamedQuery(name = "RangoClasificacion.findByPorcMinimo", query = "SELECT r FROM RangoClasificacion r WHERE r.porcMinimo = :porcMinimo"),
    @NamedQuery(name = "RangoClasificacion.findByPorcMaximo", query = "SELECT r FROM RangoClasificacion r WHERE r.porcMaximo = :porcMaximo"),
    @NamedQuery(name = "RangoClasificacion.findByDescripcion", query = "SELECT r FROM RangoClasificacion r WHERE r.descripcion = :descripcion")})
public class RangoClasificacion implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RANGO")
    private BigDecimal idRango;
    @Column(name = "PORC_MINIMO")
    private BigDecimal porcMinimo;
    @Column(name = "PORC_MAXIMO")
    private BigDecimal porcMaximo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "rangoClasificacion")
    private List<Balances> balancesList;
    @JoinColumn(name = "ID_TIPO_COMPONENTE", referencedColumnName = "ID_TIPO_COMPONENTE")
    @ManyToOne(optional = false)
    private TipoComponente tipoComponente;
    @JoinColumn(name = "ID_COLOR", referencedColumnName = "ID_COLOR")
    @ManyToOne(optional = false)
    private Color color;
    @OneToMany(mappedBy = "rangoClasificacion")
    private List<SBalances> sBalancesList;

    public RangoClasificacion() {
    }

    public RangoClasificacion(BigDecimal idRango) {
        this.idRango = idRango;
    }

    public RangoClasificacion(BigDecimal idRango, String usuario, String programa, Date fechaModif, String descripcion) {
        this.idRango = idRango;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.descripcion = descripcion;
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

    public BigDecimal getIdRango() {
        return idRango;
    }

    public void setIdRango(BigDecimal idRango) {
        this.idRango = idRango;
    }

    public BigDecimal getPorcMinimo() {
        return porcMinimo;
    }

    public void setPorcMinimo(BigDecimal porcMinimo) {
        this.porcMinimo = porcMinimo;
    }

    public BigDecimal getPorcMaximo() {
        return porcMaximo;
    }

    public void setPorcMaximo(BigDecimal porcMaximo) {
        this.porcMaximo = porcMaximo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Balances> getBalancesList() {
        return balancesList;
    }

    public void setBalancesList(List<Balances> balancesList) {
        this.balancesList = balancesList;
    }

    public TipoComponente getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(TipoComponente tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<SBalances> getSBalancesList() {
        return sBalancesList;
    }

    public void setSBalancesList(List<SBalances> sBalancesList) {
        this.sBalancesList = sBalancesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRango != null ? idRango.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RangoClasificacion)) {
            return false;
        }
        RangoClasificacion other = (RangoClasificacion) object;
        if ((this.idRango == null && other.idRango != null) || (this.idRango != null && !this.idRango.equals(other.idRango))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.RangoClasificacion[idRango=" + idRango + "]";
    }

}
