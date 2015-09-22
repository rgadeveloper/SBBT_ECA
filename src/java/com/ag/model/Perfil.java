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
@Table(name = "PERFIL")
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfil.findByUsuario", query = "SELECT p FROM Perfil p WHERE p.usuario = :usuario"),
    @NamedQuery(name = "Perfil.findByPrograma", query = "SELECT p FROM Perfil p WHERE p.programa = :programa"),
    @NamedQuery(name = "Perfil.findByFechaModif", query = "SELECT p FROM Perfil p WHERE p.fechaModif = :fechaModif"),
    @NamedQuery(name = "Perfil.findByCodigo", query = "SELECT p FROM Perfil p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Perfil.findByNombre", query = "SELECT p FROM Perfil p WHERE p.nombre = :nombre")})
public class Perfil implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<MenuPerfil> menuPerfilList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<FuncionPerfil> funcionPerfilList;
    @JoinColumn(name = "ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<UsuarioPerfil> usuarioPerfilList;

    public Perfil() {
    }

    public Perfil(String codigo) {
        this.codigo = codigo;
    }

    public Perfil(String codigo, String usuario, String programa, Date fechaModif, String nombre) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
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

    public List<MenuPerfil> getMenuPerfilList() {
        return menuPerfilList;
    }

    public void setMenuPerfilList(List<MenuPerfil> menuPerfilList) {
        this.menuPerfilList = menuPerfilList;
    }

    public List<FuncionPerfil> getFuncionPerfilList() {
        return funcionPerfilList;
    }

    public void setFuncionPerfilList(List<FuncionPerfil> funcionPerfilList) {
        this.funcionPerfilList = funcionPerfilList;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<UsuarioPerfil> getUsuarioPerfilList() {
        return usuarioPerfilList;
    }

    public void setUsuarioPerfilList(List<UsuarioPerfil> usuarioPerfilList) {
        this.usuarioPerfilList = usuarioPerfilList;
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
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Perfil[codigo=" + codigo + "]";
    }

}
