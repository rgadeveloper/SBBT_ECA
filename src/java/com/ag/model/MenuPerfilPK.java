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
public class MenuPerfilPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CODIGO_MENU")
    private String codigoMenu;
    @Basic(optional = false)
    @Column(name = "CODIGO_PERFIL")
    private String codigoPerfil;

    public MenuPerfilPK() {
    }

    public MenuPerfilPK(String codigoMenu, String codigoPerfil) {
        this.codigoMenu = codigoMenu;
        this.codigoPerfil = codigoPerfil;
    }

    public String getCodigoMenu() {
        return codigoMenu;
    }

    public void setCodigoMenu(String codigoMenu) {
        this.codigoMenu = codigoMenu;
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
        hash += (codigoMenu != null ? codigoMenu.hashCode() : 0);
        hash += (codigoPerfil != null ? codigoPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuPerfilPK)) {
            return false;
        }
        MenuPerfilPK other = (MenuPerfilPK) object;
        if ((this.codigoMenu == null && other.codigoMenu != null) || (this.codigoMenu != null && !this.codigoMenu.equals(other.codigoMenu))) {
            return false;
        }
        if ((this.codigoPerfil == null && other.codigoPerfil != null) || (this.codigoPerfil != null && !this.codigoPerfil.equals(other.codigoPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.MenuPerfilPK[codigoMenu=" + codigoMenu + ", codigoPerfil=" + codigoPerfil + "]";
    }

}
