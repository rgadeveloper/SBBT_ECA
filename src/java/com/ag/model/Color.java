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
@Table(name = "COLOR")
@NamedQueries({
    @NamedQuery(name = "Color.findAll", query = "SELECT c FROM Color c"),
    @NamedQuery(name = "Color.findByUsuario", query = "SELECT c FROM Color c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Color.findByPrograma", query = "SELECT c FROM Color c WHERE c.programa = :programa"),
    @NamedQuery(name = "Color.findByFechaModif", query = "SELECT c FROM Color c WHERE c.fechaModif = :fechaModif"),
    @NamedQuery(name = "Color.findByIdColor", query = "SELECT c FROM Color c WHERE c.idColor = :idColor"),
    @NamedQuery(name = "Color.findByDescripcion", query = "SELECT c FROM Color c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Color.findByRojo", query = "SELECT c FROM Color c WHERE c.rojo = :rojo"),
    @NamedQuery(name = "Color.findByVerde", query = "SELECT c FROM Color c WHERE c.verde = :verde"),
    @NamedQuery(name = "Color.findByAzul", query = "SELECT c FROM Color c WHERE c.azul = :azul")})
public class Color implements Serializable {
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
    @Column(name = "ID_COLOR")
    private Long idColor;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ROJO")
    private short rojo;
    @Basic(optional = false)
    @Column(name = "VERDE")
    private short verde;
    @Basic(optional = false)
    @Column(name = "AZUL")
    private short azul;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "color")
    private List<RangoClasificacion> rangoClasificacionList;

    public Color() {
    }

    public Color(Long idColor) {
        this.idColor = idColor;
    }

    public Color(Long idColor, String usuario, String programa, Date fechaModif, String descripcion, short rojo, short verde, short azul) {
        this.idColor = idColor;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.descripcion = descripcion;
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
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

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getRojo() {
        return rojo;
    }

    public void setRojo(short rojo) {
        this.rojo = rojo;
    }

    public short getVerde() {
        return verde;
    }

    public void setVerde(short verde) {
        this.verde = verde;
    }

    public short getAzul() {
        return azul;
    }

    public void setAzul(short azul) {
        this.azul = azul;
    }

    public List<RangoClasificacion> getRangoClasificacionList() {
        return rangoClasificacionList;
    }

    public void setRangoClasificacionList(List<RangoClasificacion> rangoClasificacionList) {
        this.rangoClasificacionList = rangoClasificacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColor != null ? idColor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Color)) {
            return false;
        }
        Color other = (Color) object;
        if ((this.idColor == null && other.idColor != null) || (this.idColor != null && !this.idColor.equals(other.idColor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Color[idColor=" + idColor + "]";
    }

}
