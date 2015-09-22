/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "LOG_AUDITORIA")
@NamedQueries({
    @NamedQuery(name = "LogAuditoria.findAll", query = "SELECT l FROM LogAuditoria l"),
    @NamedQuery(name = "LogAuditoria.findByUsuario", query = "SELECT l FROM LogAuditoria l WHERE l.usuario = :usuario"),
    @NamedQuery(name = "LogAuditoria.findByPrograma", query = "SELECT l FROM LogAuditoria l WHERE l.programa = :programa"),
    @NamedQuery(name = "LogAuditoria.findByFechaModif", query = "SELECT l FROM LogAuditoria l WHERE l.fechaModif = :fechaModif"),
    @NamedQuery(name = "LogAuditoria.findByIdLog", query = "SELECT l FROM LogAuditoria l WHERE l.idLog = :idLog"),
    @NamedQuery(name = "LogAuditoria.findByDescripcion", query = "SELECT l FROM LogAuditoria l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "LogAuditoria.findByFecha", query = "SELECT l FROM LogAuditoria l WHERE l.fecha = :fecha"),
    @NamedQuery(name = "LogAuditoria.findByProgramaEjecucion", query = "SELECT l FROM LogAuditoria l WHERE l.programaEjecucion = :programaEjecucion")})
public class LogAuditoria implements Serializable {
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
    @Column(name = "ID_LOG")
    private BigDecimal idLog;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "PROGRAMA_EJECUCION")
    private String programaEjecucion;
    @JoinColumn(name = "TIPO", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;

    public LogAuditoria() {
    }

    public LogAuditoria(BigDecimal idLog) {
        this.idLog = idLog;
    }

    public LogAuditoria(BigDecimal idLog, String usuario, String programa, Date fechaModif, String descripcion, Date fecha) {
        this.idLog = idLog;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.descripcion = descripcion;
        this.fecha = fecha;
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

    public BigDecimal getIdLog() {
        return idLog;
    }

    public void setIdLog(BigDecimal idLog) {
        this.idLog = idLog;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getProgramaEjecucion() {
        return programaEjecucion;
    }

    public void setProgramaEjecucion(String programaEjecucion) {
        this.programaEjecucion = programaEjecucion;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogAuditoria)) {
            return false;
        }
        LogAuditoria other = (LogAuditoria) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.LogAuditoria[idLog=" + idLog + "]";
    }

}
