/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author larry.obispo
 */
@Embeddable
public class FuncionPerfilPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CODIGO_FUNCION")
    private String codigoFuncion;
    @Basic(optional = false)
    @Column(name = "CODIGO_PERFIL")
    private String codigoPerfil;

    public FuncionPerfilPK() {
    }

    public FuncionPerfilPK(String codigoFuncion, String codigoPerfil) {
        this.codigoFuncion = codigoFuncion;
        this.codigoPerfil = codigoPerfil;
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }

    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public String getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(String codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFuncion != null ? codigoFuncion.hashCode() : 0);
        hash += (codigoPerfil != null ? codigoPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncionPerfilPK)) {
            return false;
        }
        FuncionPerfilPK other = (FuncionPerfilPK) object;
        if ((this.codigoFuncion == null && other.codigoFuncion != null) || (this.codigoFuncion != null && !this.codigoFuncion.equals(other.codigoFuncion))) {
            return false;
        }
        if ((this.codigoPerfil == null && other.codigoPerfil != null) || (this.codigoPerfil != null && !this.codigoPerfil.equals(other.codigoPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.FuncionPerfilPK[codigoFuncion=" + codigoFuncion + ", codigoPerfil=" + codigoPerfil + "]";
    }

}
