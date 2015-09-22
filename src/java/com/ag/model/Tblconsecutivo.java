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
 * @author larry.obispo
 */
@Entity
@Table(name = "TBLCONSECUTIVO")
@NamedQueries({
    @NamedQuery(name = "Tblconsecutivo.findAll", query = "SELECT t FROM Tblconsecutivo t"),
    @NamedQuery(name = "Tblconsecutivo.findByUsuario", query = "SELECT t FROM Tblconsecutivo t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "Tblconsecutivo.findByPrograma", query = "SELECT t FROM Tblconsecutivo t WHERE t.programa = :programa"),
    @NamedQuery(name = "Tblconsecutivo.findByFechaModif", query = "SELECT t FROM Tblconsecutivo t WHERE t.fechaModif = :fechaModif"),
    @NamedQuery(name = "Tblconsecutivo.findByNombreTabla", query = "SELECT t FROM Tblconsecutivo t WHERE t.nombreTabla = :nombreTabla"),
    @NamedQuery(name = "Tblconsecutivo.findByConsecutivo", query = "SELECT t FROM Tblconsecutivo t WHERE t.consecutivo = :consecutivo"),
    @NamedQuery(name = "Tblconsecutivo.findByTipo", query = "SELECT t FROM Tblconsecutivo t WHERE t.tipo = :tipo")})
public class Tblconsecutivo implements Serializable {
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
    @Column(name = "NOMBRE_TABLA")
    private String nombreTabla;
    @Basic(optional = false)
    @Column(name = "CONSECUTIVO")
    private long consecutivo;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;

    public Tblconsecutivo() {
    }

    public Tblconsecutivo(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public Tblconsecutivo(String nombreTabla, String usuario, String programa, Date fechaModif, long consecutivo, String tipo) {
        this.nombreTabla = nombreTabla;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.consecutivo = consecutivo;
        this.tipo = tipo;
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

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(long consecutivo) {
        this.consecutivo = consecutivo;
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
        hash += (nombreTabla != null ? nombreTabla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblconsecutivo)) {
            return false;
        }
        Tblconsecutivo other = (Tblconsecutivo) object;
        if ((this.nombreTabla == null && other.nombreTabla != null) || (this.nombreTabla != null && !this.nombreTabla.equals(other.nombreTabla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Tblconsecutivo[nombreTabla=" + nombreTabla + "]";
    }

}
