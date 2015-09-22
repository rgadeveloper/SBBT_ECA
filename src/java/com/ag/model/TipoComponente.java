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
@Table(name = "TIPO_COMPONENTE")
@NamedQueries({
    @NamedQuery(name = "TipoComponente.findAll", query = "SELECT t FROM TipoComponente t"),
    @NamedQuery(name = "TipoComponente.findByUsuario", query = "SELECT t FROM TipoComponente t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "TipoComponente.findByPrograma", query = "SELECT t FROM TipoComponente t WHERE t.programa = :programa"),
    @NamedQuery(name = "TipoComponente.findByFechaModif", query = "SELECT t FROM TipoComponente t WHERE t.fechaModif = :fechaModif"),
    @NamedQuery(name = "TipoComponente.findByIdTipoComponente", query = "SELECT t FROM TipoComponente t WHERE t.idTipoComponente = :idTipoComponente"),
    @NamedQuery(name = "TipoComponente.findByDescripcion", query = "SELECT t FROM TipoComponente t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoComponente.findByImagen", query = "SELECT t FROM TipoComponente t WHERE t.imagen = :imagen"),
    @NamedQuery(name = "TipoComponente.findByZoom", query = "SELECT t FROM TipoComponente t WHERE t.zoom = :zoom")})
public class TipoComponente implements Serializable {
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
    @Column(name = "ID_TIPO_COMPONENTE")
    private Short idTipoComponente;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "IMAGEN")
    private String imagen;
    @Column(name = "ZOOM")
    private String zoom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoComponente")
    private List<SComponente> sComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoComponente")
    private List<Medida> medidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoComponente")
    private List<Componente> componenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoComponente")
    private List<RangoClasificacion> rangoClasificacionList;
    @JoinColumn(name = "TIPO_ARBOL", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoComponente")
    private List<SMedida> sMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoComponente")
    private List<ZonaGeografica> zonaGeograficaList;

    public TipoComponente() {
    }

    public TipoComponente(Short idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public TipoComponente(Short idTipoComponente, String usuario, String programa, Date fechaModif, String descripcion) {
        this.idTipoComponente = idTipoComponente;
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

    public Short getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(Short idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public List<SComponente> getSComponenteList() {
        return sComponenteList;
    }

    public void setSComponenteList(List<SComponente> sComponenteList) {
        this.sComponenteList = sComponenteList;
    }

    public List<Medida> getMedidaList() {
        return medidaList;
    }

    public void setMedidaList(List<Medida> medidaList) {
        this.medidaList = medidaList;
    }

    public List<Componente> getComponenteList() {
        return componenteList;
    }

    public void setComponenteList(List<Componente> componenteList) {
        this.componenteList = componenteList;
    }

    public List<RangoClasificacion> getRangoClasificacionList() {
        return rangoClasificacionList;
    }

    public void setRangoClasificacionList(List<RangoClasificacion> rangoClasificacionList) {
        this.rangoClasificacionList = rangoClasificacionList;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    public List<SMedida> getSMedidaList() {
        return sMedidaList;
    }

    public void setSMedidaList(List<SMedida> sMedidaList) {
        this.sMedidaList = sMedidaList;
    }

    public List<ZonaGeografica> getZonaGeograficaList() {
        return zonaGeograficaList;
    }

    public void setZonaGeograficaList(List<ZonaGeografica> zonaGeograficaList) {
        this.zonaGeograficaList = zonaGeograficaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoComponente != null ? idTipoComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoComponente)) {
            return false;
        }
        TipoComponente other = (TipoComponente) object;
        if ((this.idTipoComponente == null && other.idTipoComponente != null) || (this.idTipoComponente != null && !this.idTipoComponente.equals(other.idTipoComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.TipoComponente[idTipoComponente=" + idTipoComponente + "]";
    }

}
