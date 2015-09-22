/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author larry.obispo
 */
@Embeddable
public class UsuarioPerfilPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CODIGO_USUARIO")
    private String codigoUsuario;
    @Basic(optional = false)
    @Column(name = "CODIGO_PERFIL")
    private String codigoPerfil;
    @Basic(optional = false)
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    public UsuarioPerfilPK() {
    }

    public UsuarioPerfilPK(String codigoUsuario, String codigoPerfil, Date fechaAlta) {
        this.codigoUsuario = codigoUsuario;
        this.codigoPerfil = codigoPerfil;
        this.fechaAlta = fechaAlta;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(String codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoUsuario != null ? codigoUsuario.hashCode() : 0);
        hash += (codigoPerfil != null ? codigoPerfil.hashCode() : 0);
        hash += (fechaAlta != null ? fechaAlta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPerfilPK)) {
            return false;
        }
        UsuarioPerfilPK other = (UsuarioPerfilPK) object;
        if ((this.codigoUsuario == null && other.codigoUsuario != null) || (this.codigoUsuario != null && !this.codigoUsuario.equals(other.codigoUsuario))) {
            return false;
        }
        if ((this.codigoPerfil == null && other.codigoPerfil != null) || (this.codigoPerfil != null && !this.codigoPerfil.equals(other.codigoPerfil))) {
            return false;
        }
        if ((this.fechaAlta == null && other.fechaAlta != null) || (this.fechaAlta != null && !this.fechaAlta.equals(other.fechaAlta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.UsuarioPerfilPK[codigoUsuario=" + codigoUsuario + ", codigoPerfil=" + codigoPerfil + ", fechaAlta=" + fechaAlta + "]";
    }

}
