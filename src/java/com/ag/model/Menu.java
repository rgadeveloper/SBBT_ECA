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
@Table(name = "MENU")
@NamedQueries({
  @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
  @NamedQuery(name = "Menu.findByUsuario", query = "SELECT m FROM Menu m WHERE m.usuario = :usuario"),
  @NamedQuery(name = "Menu.findByPrograma", query = "SELECT m FROM Menu m WHERE m.programa = :programa"),
  @NamedQuery(name = "Menu.findByFechaModif", query = "SELECT m FROM Menu m WHERE m.fechaModif = :fechaModif"),
  @NamedQuery(name = "Menu.findByCodigo", query = "SELECT m FROM Menu m WHERE m.codigo = :codigo"),
  @NamedQuery(name = "Menu.findByCodPadre", query = "SELECT m FROM Menu m WHERE m.codPadre = :codPadre"),
  @NamedQuery(name = "Menu.findByNombre", query = "SELECT m FROM Menu m WHERE m.nombre = :nombre"),
  @NamedQuery(name = "Menu.findByOrden", query = "SELECT m FROM Menu m WHERE m.orden = :orden"),
  @NamedQuery(name = "Menu.findByIcono", query = "SELECT m FROM Menu m WHERE m.icono = :icono"),
  @NamedQuery(name = "Menu.findByUrl", query = "SELECT m FROM Menu m WHERE m.url = :url")})
public class Menu implements Serializable {

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
  @Column(name = "COD_PADRE")
  private String codPadre;
  @Basic(optional = false)
  @Column(name = "NOMBRE")
  private String nombre;
  @Column(name = "ORDEN")
  private Long orden;
  @Column(name = "ICONO")
  private String icono;
  @Column(name = "URL")
  private String url;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
  private List<MenuPerfil> menuPerfilList;

  @Column(name = "ONCLICK")
  private String OnClick;

  public Menu() {
  }

  public Menu(String codigo) {
    this.codigo = codigo;
  }

  public Menu(String codigo, String usuario, String programa, Date fechaModif, String nombre) {
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

  public String getCodPadre() {
    return codPadre;
  }

  public void setCodPadre(String codPadre) {
    this.codPadre = codPadre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getOrden() {
    return orden;
  }

  public void setOrden(Long orden) {
    this.orden = orden;
  }

  public String getIcono() {
    return icono;
  }

  public void setIcono(String icono) {
    this.icono = icono;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<MenuPerfil> getMenuPerfilList() {
    return menuPerfilList;
  }

  public void setMenuPerfilList(List<MenuPerfil> menuPerfilList) {
    this.menuPerfilList = menuPerfilList;
  }

  public String getOnClick() {
    return OnClick;
  }

  public void setOnClick(String OnClick) {
    this.OnClick = OnClick;
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
    if (!(object instanceof Menu)) {
      return false;
    }
    Menu other = (Menu) object;
    if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ag.model.Menu[codigo=" + codigo + "]";
  }

}
