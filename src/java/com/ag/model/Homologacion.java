/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ptorres
 */
@Entity
@Table(name = "CEO_HOMOLOGACION")
@NamedQueries({
    @NamedQuery(name = "Homologacion.findAll", query = "SELECT h FROM Homologacion h"),
    @NamedQuery(name = "Homologacion.findByUsuario", query = "SELECT h FROM Homologacion h WHERE h.usuario = :usuario"),
    @NamedQuery(name = "Homologacion.findByPrograma", query = "SELECT h FROM Homologacion h WHERE h.programa = :programa"),
    @NamedQuery(name = "Homologacion.findByFechaModif", query = "SELECT h FROM Homologacion h WHERE h.fechaModif = :fechaModif"),
    @NamedQuery(name = "Homologacion.findByTipo_ceo", query = "SELECT h FROM Homologacion h WHERE h.tipo_ceo = :tipo_ceo"),
    @NamedQuery(name = "Homologacion.findByTipo_simbal", query = "SELECT h FROM Homologacion h WHERE h.tipo_simbal = :tipo_simbal"),
    @NamedQuery(name = "Homologacion.findByGrupo", query = "SELECT h FROM Homologacion h WHERE h.grupo = :grupo"),
    @NamedQuery(name = "Homologacion.findByEstado", query = "SELECT h FROM Homologacion h WHERE h.estado = :estado"),})
public class Homologacion implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "TIPO_CEO")
    private String tipo_ceo;
    @Basic(optional = false)
    @Column(name = "TIPO_SIMBAL")
    @Id
    private String tipo_simbal;
    @Basic(optional = false)
    @Column(name = "GRUPO")
    private String grupo;     
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;

   
    public String getTipo_ceo() {
        return tipo_ceo;
    }

    public void setTipo_ceo(String tipo_ceo) {
        this.tipo_ceo = tipo_ceo;
    }

    public String getTipo_simbal() {
        return tipo_simbal;
    }

    public void setTipo_simbal(String tipo_simbal) {
        this.tipo_simbal = tipo_simbal;
    }
    
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the programa
     */
    public String getPrograma() {
        return programa;
    }

    /**
     * @param programa the programa to set
     */
    public void setPrograma(String programa) {
        this.programa = programa;
    }

    /**
     * @return the fechaModif
     */
    public Date getFechaModif() {
        return fechaModif;
    }

    /**
     * @param fechaModif the fechaModif to set
     */
    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
