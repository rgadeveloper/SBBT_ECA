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
@Table(name = "FUNCION")
@NamedQueries({
    @NamedQuery(name = "Funcion.findAll", query = "SELECT f FROM Funcion f"),
    @NamedQuery(name = "Funcion.findByUsuario", query = "SELECT f FROM Funcion f WHERE f.usuario = :usuario"),
    @NamedQuery(name = "Funcion.findByPrograma", query = "SELECT f FROM Funcion f WHERE f.programa = :programa"),
    @NamedQuery(name = "Funcion.findByFechaModif", query = "SELECT f FROM Funcion f WHERE f.fechaModif = :fechaModif"),
    @NamedQuery(name = "Funcion.findByCodigo", query = "SELECT f FROM Funcion f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "Funcion.findByNombre", query = "SELECT f FROM Funcion f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Funcion.findByFechaAlta", query = "SELECT f FROM Funcion f WHERE f.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "Funcion.findByFechaBaja", query = "SELECT f FROM Funcion f WHERE f.fechaBaja = :fechaBaja")})
public class Funcion implements Serializable {
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
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcion")
    private List<FuncionPerfil> funcionPerfilList;
    @JoinColumn(name = "CODIGO_TIPO", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo;

    public Funcion() {
    }

    public Funcion(String codigo) {
        this.codigo = codigo;
    }

    public Funcion(String codigo, String usuario, String programa, Date fechaModif, String nombre, Date fechaAlta) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
        this.fechaAlta = fechaAlta;
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public List<FuncionPerfil> getFuncionPerfilList() {
        return funcionPerfilList;
    }

    public void setFuncionPerfilList(List<FuncionPerfil> funcionPerfilList) {
        this.funcionPerfilList = funcionPerfilList;
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
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcion)) {
            return false;
        }
        Funcion other = (Funcion) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Funcion[codigo=" + codigo + "]";
    }

}
