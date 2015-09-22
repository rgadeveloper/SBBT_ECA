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
@Table(name = "GRUPO")
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByUsuario", query = "SELECT g FROM Grupo g WHERE g.usuario = :usuario"),
    @NamedQuery(name = "Grupo.findByPrograma", query = "SELECT g FROM Grupo g WHERE g.programa = :programa"),
    @NamedQuery(name = "Grupo.findByFechaModif", query = "SELECT g FROM Grupo g WHERE g.fechaModif = :fechaModif"),
    @NamedQuery(name = "Grupo.findByCodigo", query = "SELECT g FROM Grupo g WHERE g.codigo = :codigo"),
    @NamedQuery(name = "Grupo.findByNombre", query = "SELECT g FROM Grupo g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Grupo.findByEstado", query = "SELECT g FROM Grupo g WHERE g.estado = :estado")})
public class Grupo implements Serializable {
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
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<Variables> variablesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<Estado> estadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<Tbltipo> tbltipoList;

    public Grupo() {
    }

    public Grupo(String codigo) {
        this.codigo = codigo;
    }

    public Grupo(String codigo, String usuario, String programa, Date fechaModif, String nombre, String estado) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
        this.estado = estado;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Variables> getVariablesList() {
        return variablesList;
    }

    public void setVariablesList(List<Variables> variablesList) {
        this.variablesList = variablesList;
    }

    public List<Estado> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<Estado> estadoList) {
        this.estadoList = estadoList;
    }

    public List<Tbltipo> getTbltipoList() {
        return tbltipoList;
    }

    public void setTbltipoList(List<Tbltipo> tbltipoList) {
        this.tbltipoList = tbltipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Grupo[codigo=" + codigo + "]";
    }

}
