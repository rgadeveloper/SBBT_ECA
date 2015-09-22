/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "USUARIO_PERFIL")
@NamedQueries({
    @NamedQuery(name = "UsuarioPerfil.findAll", query = "SELECT u FROM UsuarioPerfil u"),
    @NamedQuery(name = "UsuarioPerfil.findByUsuario", query = "SELECT u FROM UsuarioPerfil u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "UsuarioPerfil.findByPrograma", query = "SELECT u FROM UsuarioPerfil u WHERE u.programa = :programa"),
    @NamedQuery(name = "UsuarioPerfil.findByFechaModif", query = "SELECT u FROM UsuarioPerfil u WHERE u.fechaModif = :fechaModif"),
    @NamedQuery(name = "UsuarioPerfil.findByCodigoUsuario", query = "SELECT u FROM UsuarioPerfil u WHERE u.usuarioPerfilPK.codigoUsuario = :codigoUsuario"),
    @NamedQuery(name = "UsuarioPerfil.findByCodigoPerfil", query = "SELECT u FROM UsuarioPerfil u WHERE u.usuarioPerfilPK.codigoPerfil = :codigoPerfil"),
    @NamedQuery(name = "UsuarioPerfil.findByFechaAlta", query = "SELECT u FROM UsuarioPerfil u WHERE u.usuarioPerfilPK.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "UsuarioPerfil.findByFechaBaja", query = "SELECT u FROM UsuarioPerfil u WHERE u.fechaBaja = :fechaBaja")})
public class UsuarioPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioPerfilPK usuarioPerfilPK;
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
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @JoinColumn(name = "CODIGO_USUARIO", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;
    @JoinColumn(name = "CODIGO_PERFIL", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(UsuarioPerfilPK usuarioPerfilPK) {
        this.usuarioPerfilPK = usuarioPerfilPK;
    }

    public UsuarioPerfil(UsuarioPerfilPK usuarioPerfilPK, String usuario, String programa, Date fechaModif) {
        this.usuarioPerfilPK = usuarioPerfilPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
    }

    public UsuarioPerfil(String codigoUsuario, String codigoPerfil, Date fechaAlta) {
        this.usuarioPerfilPK = new UsuarioPerfilPK(codigoUsuario, codigoPerfil, fechaAlta);
    }

    public UsuarioPerfilPK getUsuarioPerfilPK() {
        return usuarioPerfilPK;
    }

    public void setUsuarioPerfilPK(UsuarioPerfilPK usuarioPerfilPK) {
        this.usuarioPerfilPK = usuarioPerfilPK;
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

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioPerfilPK != null ? usuarioPerfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPerfil)) {
            return false;
        }
        UsuarioPerfil other = (UsuarioPerfil) object;
        if ((this.usuarioPerfilPK == null && other.usuarioPerfilPK != null) || (this.usuarioPerfilPK != null && !this.usuarioPerfilPK.equals(other.usuarioPerfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.UsuarioPerfil[usuarioPerfilPK=" + usuarioPerfilPK + "]";
    }

}
