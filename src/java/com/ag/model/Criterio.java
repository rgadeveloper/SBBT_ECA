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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "CRITERIO")
@NamedQueries({
    @NamedQuery(name = "Criterio.findAll", query = "SELECT c FROM Criterio c"),
    @NamedQuery(name = "Criterio.findByUsuario", query = "SELECT c FROM Criterio c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Criterio.findByPrograma", query = "SELECT c FROM Criterio c WHERE c.programa = :programa"),
    @NamedQuery(name = "Criterio.findByFechaModif", query = "SELECT c FROM Criterio c WHERE c.fechaModif = :fechaModif"),
    @NamedQuery(name = "Criterio.findByIdCriterio", query = "SELECT c FROM Criterio c WHERE c.idCriterio = :idCriterio"),
    @NamedQuery(name = "Criterio.findByDescripcion", query = "SELECT c FROM Criterio c WHERE c.descripcion = :descripcion")})
public class Criterio implements Serializable {
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
    @Column(name = "ID_CRITERIO")
    private BigDecimal idCriterio;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criterio")
    private List<Filtros> filtrosList;

    public Criterio() {
    }

    public Criterio(BigDecimal idCriterio) {
        this.idCriterio = idCriterio;
    }

    public Criterio(BigDecimal idCriterio, String usuario, String programa, Date fechaModif, String descripcion) {
        this.idCriterio = idCriterio;
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

    public BigDecimal getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(BigDecimal idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Filtros> getFiltrosList() {
        return filtrosList;
    }

    public void setFiltrosList(List<Filtros> filtrosList) {
        this.filtrosList = filtrosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCriterio != null ? idCriterio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criterio)) {
            return false;
        }
        Criterio other = (Criterio) object;
        if ((this.idCriterio == null && other.idCriterio != null) || (this.idCriterio != null && !this.idCriterio.equals(other.idCriterio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Criterio[idCriterio=" + idCriterio + "]";
    }

}
