/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
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
@Table(name = "CICLO")
@NamedQueries({
    @NamedQuery(name = "Ciclo.findAll", query = "SELECT c FROM Ciclo c"),
    @NamedQuery(name = "Ciclo.findByUsuario", query = "SELECT c FROM Ciclo c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Ciclo.findByPrograma", query = "SELECT c FROM Ciclo c WHERE c.programa = :programa"),
    @NamedQuery(name = "Ciclo.findByFechaModif", query = "SELECT c FROM Ciclo c WHERE c.fechaModif = :fechaModif"),
    @NamedQuery(name = "Ciclo.findByIdCiclo", query = "SELECT c FROM Ciclo c WHERE c.idCiclo = :idCiclo"),
    @NamedQuery(name = "Ciclo.findByDescripcion", query = "SELECT c FROM Ciclo c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Ciclo.findByFechaInicio", query = "SELECT c FROM Ciclo c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Ciclo.findByFechaFin", query = "SELECT c FROM Ciclo c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "Ciclo.findByPeriodo", query = "SELECT c FROM Ciclo c WHERE c.periodo = :periodo")})
public class Ciclo implements Serializable {
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
    @Column(name = "ID_CICLO")
    private String idCiclo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private int periodo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciclo")
    private List<AtrComponente> atrComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciclo")
    private List<SAtrComponente> sAtrComponenteList;

    public Ciclo() {
    }

    public Ciclo(String idCiclo) {
        this.idCiclo = idCiclo;
    }

    public Ciclo(String idCiclo, String usuario, String programa, Date fechaModif, String descripcion, Date fechaInicio, Date fechaFin, int periodo) {
        this.idCiclo = idCiclo;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.periodo = periodo;
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

    public String getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(String idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public List<AtrComponente> getAtrComponenteList() {
        return atrComponenteList;
    }

    public void setAtrComponenteList(List<AtrComponente> atrComponenteList) {
        this.atrComponenteList = atrComponenteList;
    }

    public List<SAtrComponente> getSAtrComponenteList() {
        return sAtrComponenteList;
    }

    public void setSAtrComponenteList(List<SAtrComponente> sAtrComponenteList) {
        this.sAtrComponenteList = sAtrComponenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiclo != null ? idCiclo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciclo)) {
            return false;
        }
        Ciclo other = (Ciclo) object;
        if ((this.idCiclo == null && other.idCiclo != null) || (this.idCiclo != null && !this.idCiclo.equals(other.idCiclo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Ciclo[idCiclo=" + idCiclo + "]";
    }

}
