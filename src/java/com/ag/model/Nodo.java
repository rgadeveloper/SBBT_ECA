/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "NODO")
@NamedQueries({
    @NamedQuery(name = "Nodo.findAll", query = "SELECT n FROM Nodo n"),
    @NamedQuery(name = "Nodo.findByCodigo", query = "SELECT n FROM Nodo n WHERE n.codigo = :codigo"),
    @NamedQuery(name = "Nodo.findByColor", query = "SELECT n FROM Nodo n WHERE n.color = :color"),
    @NamedQuery(name = "Nodo.findByCoordenadax", query = "SELECT n FROM Nodo n WHERE n.coordenadax = :coordenadax"),
    @NamedQuery(name = "Nodo.findByCoordenaday", query = "SELECT n FROM Nodo n WHERE n.coordenaday = :coordenaday"),
    @NamedQuery(name = "Nodo.findByNombre", query = "SELECT n FROM Nodo n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "Nodo.findByPerdidas", query = "SELECT n FROM Nodo n WHERE n.perdidas = :perdidas"),
    @NamedQuery(name = "Nodo.findBySelected", query = "SELECT n FROM Nodo n WHERE n.selected = :selected"),
    @NamedQuery(name = "Nodo.findByTipo", query = "SELECT n FROM Nodo n WHERE n.tipo = :tipo")})
public class Nodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "COORDENADAX")
    private String coordenadax;
    @Column(name = "COORDENADAY")
    private String coordenaday;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "PERDIDAS")
    private String perdidas;
    @Basic(optional = false)
    @Column(name = "SELECTED")
    private short selected;
    @Column(name = "TIPO")
    private String tipo;

    public Nodo() {
    }

    public Nodo(String codigo) {
        this.codigo = codigo;
    }

    public Nodo(String codigo, short selected) {
        this.codigo = codigo;
        this.selected = selected;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCoordenadax() {
        return coordenadax;
    }

    public void setCoordenadax(String coordenadax) {
        this.coordenadax = coordenadax;
    }

    public String getCoordenaday() {
        return coordenaday;
    }

    public void setCoordenaday(String coordenaday) {
        this.coordenaday = coordenaday;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(String perdidas) {
        this.perdidas = perdidas;
    }

    public short getSelected() {
        return selected;
    }

    public void setSelected(short selected) {
        this.selected = selected;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Nodo)) {
            return false;
        }
        Nodo other = (Nodo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Nodo[codigo=" + codigo + "]";
    }

}
