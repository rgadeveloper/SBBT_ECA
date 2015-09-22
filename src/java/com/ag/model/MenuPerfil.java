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
@Table(name = "MENU_PERFIL")
@NamedQueries({
    @NamedQuery(name = "MenuPerfil.findAll", query = "SELECT m FROM MenuPerfil m"),
    @NamedQuery(name = "MenuPerfil.findByUsuario", query = "SELECT m FROM MenuPerfil m WHERE m.usuario = :usuario"),
    @NamedQuery(name = "MenuPerfil.findByPrograma", query = "SELECT m FROM MenuPerfil m WHERE m.programa = :programa"),
    @NamedQuery(name = "MenuPerfil.findByFechaModif", query = "SELECT m FROM MenuPerfil m WHERE m.fechaModif = :fechaModif"),
    @NamedQuery(name = "MenuPerfil.findByCodigoMenu", query = "SELECT m FROM MenuPerfil m WHERE m.menuPerfilPK.codigoMenu = :codigoMenu"),
    @NamedQuery(name = "MenuPerfil.findByCodigoPerfil", query = "SELECT m FROM MenuPerfil m WHERE m.menuPerfilPK.codigoPerfil = :codigoPerfil"),
    @NamedQuery(name = "MenuPerfil.findByEstado", query = "SELECT m FROM MenuPerfil m WHERE m.estado = :estado")})
public class MenuPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MenuPerfilPK menuPerfilPK;
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
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "CODIGO_PERFIL", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;
    @JoinColumn(name = "CODIGO_MENU", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Menu menu;

    public MenuPerfil() {
    }

    public MenuPerfil(MenuPerfilPK menuPerfilPK) {
        this.menuPerfilPK = menuPerfilPK;
    }

    public MenuPerfil(MenuPerfilPK menuPerfilPK, String usuario, String programa, Date fechaModif) {
        this.menuPerfilPK = menuPerfilPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
    }

    public MenuPerfil(String codigoMenu, String codigoPerfil) {
        this.menuPerfilPK = new MenuPerfilPK(codigoMenu, codigoPerfil);
    }

    public MenuPerfilPK getMenuPerfilPK() {
        return menuPerfilPK;
    }

    public void setMenuPerfilPK(MenuPerfilPK menuPerfilPK) {
        this.menuPerfilPK = menuPerfilPK;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuPerfilPK != null ? menuPerfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuPerfil)) {
            return false;
        }
        MenuPerfil other = (MenuPerfil) object;
        if ((this.menuPerfilPK == null && other.menuPerfilPK != null) || (this.menuPerfilPK != null && !this.menuPerfilPK.equals(other.menuPerfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.MenuPerfil[menuPerfilPK=" + menuPerfilPK + "]";
    }

}
