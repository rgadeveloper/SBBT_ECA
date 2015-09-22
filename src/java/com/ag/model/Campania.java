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
@Table(name = "CAMPANIA")
@NamedQueries({
    @NamedQuery(name = "Campania.findAll", query = "SELECT c FROM Campania c"),
    @NamedQuery(name = "Campania.findByUsuario", query = "SELECT c FROM Campania c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Campania.findByPrograma", query = "SELECT c FROM Campania c WHERE c.programa = :programa"),
    @NamedQuery(name = "Campania.findByFechaModif", query = "SELECT c FROM Campania c WHERE c.fechaModif = :fechaModif"),
    @NamedQuery(name = "Campania.findByIdCampania", query = "SELECT c FROM Campania c WHERE c.idCampania = :idCampania"),
    @NamedQuery(name = "Campania.findByDescripcion", query = "SELECT c FROM Campania c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Campania.findByFechaInicio", query = "SELECT c FROM Campania c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Campania.findByFechaFin", query = "SELECT c FROM Campania c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "Campania.findByPeriodo", query = "SELECT c FROM Campania c WHERE c.periodo = :periodo")})
public class Campania implements Serializable {
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
    @Column(name = "ID_CAMPANIA")
    private BigDecimal idCampania;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campania")
    private List<OrdenTrabajo> ordenTrabajoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campania")
    private List<ComponenteCampania> componenteCampaniaList;
    @JoinColumn(name = "TIPO_CAMPANIA", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;

    public Campania() {
    }

    public Campania(BigDecimal idCampania) {
        this.idCampania = idCampania;
    }

    public Campania(BigDecimal idCampania, String usuario, String programa, Date fechaModif, String descripcion, Date fechaInicio, Date fechaFin, int periodo) {
        this.idCampania = idCampania;
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

    public BigDecimal getIdCampania() {
        return idCampania;
    }

    public void setIdCampania(BigDecimal idCampania) {
        this.idCampania = idCampania;
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

    public List<OrdenTrabajo> getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(List<OrdenTrabajo> ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    public List<ComponenteCampania> getComponenteCampaniaList() {
        return componenteCampaniaList;
    }

    public void setComponenteCampaniaList(List<ComponenteCampania> componenteCampaniaList) {
        this.componenteCampaniaList = componenteCampaniaList;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCampania != null ? idCampania.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campania)) {
            return false;
        }
        Campania other = (Campania) object;
        if ((this.idCampania == null && other.idCampania != null) || (this.idCampania != null && !this.idCampania.equals(other.idCampania))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Campania[idCampania=" + idCampania + "]";
    }

}
