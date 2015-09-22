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
@Table(name = "S_VISTA_AMARRES")
@NamedQueries({
    @NamedQuery(name = "SVistaAmarres.findAll", query = "SELECT s FROM SVistaAmarres s"),
    @NamedQuery(name = "SVistaAmarres.findByUsuario", query = "SELECT s FROM SVistaAmarres s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SVistaAmarres.findByPrograma", query = "SELECT s FROM SVistaAmarres s WHERE s.programa = :programa"),
    @NamedQuery(name = "SVistaAmarres.findByFechaModif", query = "SELECT s FROM SVistaAmarres s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SVistaAmarres.findByIdPadre", query = "SELECT s FROM SVistaAmarres s WHERE s.idPadre = :idPadre"),
    @NamedQuery(name = "SVistaAmarres.findByIdHijo", query = "SELECT s FROM SVistaAmarres s WHERE s.idHijo = :idHijo"),
    @NamedQuery(name = "SVistaAmarres.findByPeriodoIni", query = "SELECT s FROM SVistaAmarres s WHERE s.periodoIni = :periodoIni"),
    @NamedQuery(name = "SVistaAmarres.findByPeriodoFin", query = "SELECT s FROM SVistaAmarres s WHERE s.periodoFin = :periodoFin"),
    @NamedQuery(name = "SVistaAmarres.findByEstadoPadre", query = "SELECT s FROM SVistaAmarres s WHERE s.estadoPadre = :estadoPadre"),
    @NamedQuery(name = "SVistaAmarres.findByEstadoHijo", query = "SELECT s FROM SVistaAmarres s WHERE s.estadoHijo = :estadoHijo"),
    @NamedQuery(name = "SVistaAmarres.findByIdTipoPadre", query = "SELECT s FROM SVistaAmarres s WHERE s.idTipoPadre = :idTipoPadre"),
    @NamedQuery(name = "SVistaAmarres.findByIdTipoHijo", query = "SELECT s FROM SVistaAmarres s WHERE s.idTipoHijo = :idTipoHijo"),
    @NamedQuery(name = "SVistaAmarres.findByIdVistaAmarre", query = "SELECT s FROM SVistaAmarres s WHERE s.idVistaAmarre = :idVistaAmarre"),
    @NamedQuery(name = "SVistaAmarres.findByIdSimulacion", query = "SELECT s FROM SVistaAmarres s WHERE s.idSimulacion = :idSimulacion")})
public class SVistaAmarres implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "ID_PADRE")
    private String idPadre;
    @Basic(optional = false)
    @Column(name = "ID_HIJO")
    private String idHijo;
    @Column(name = "PERIODO_INI")
    private Integer periodoIni;
    @Column(name = "PERIODO_FIN")
    private Integer periodoFin;
    @Column(name = "ESTADO_PADRE")
    private String estadoPadre;
    @Column(name = "ESTADO_HIJO")
    private String estadoHijo;
    @Column(name = "ID_TIPO_PADRE")
    private Short idTipoPadre;
    @Column(name = "ID_TIPO_HIJO")
    private Short idTipoHijo;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_VISTA_AMARRE")
    private BigDecimal idVistaAmarre;
    @Column(name = "ID_SIMULACION")
    private Long idSimulacion;

    public SVistaAmarres() {
    }

    public SVistaAmarres(BigDecimal idVistaAmarre) {
        this.idVistaAmarre = idVistaAmarre;
    }

    public SVistaAmarres(BigDecimal idVistaAmarre, String usuario, String programa, Date fechaModif, String idPadre, String idHijo) {
        this.idVistaAmarre = idVistaAmarre;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.idPadre = idPadre;
        this.idHijo = idHijo;
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

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public String getIdHijo() {
        return idHijo;
    }

    public void setIdHijo(String idHijo) {
        this.idHijo = idHijo;
    }

    public Integer getPeriodoIni() {
        return periodoIni;
    }

    public void setPeriodoIni(Integer periodoIni) {
        this.periodoIni = periodoIni;
    }

    public Integer getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(Integer periodoFin) {
        this.periodoFin = periodoFin;
    }

    public String getEstadoPadre() {
        return estadoPadre;
    }

    public void setEstadoPadre(String estadoPadre) {
        this.estadoPadre = estadoPadre;
    }

    public String getEstadoHijo() {
        return estadoHijo;
    }

    public void setEstadoHijo(String estadoHijo) {
        this.estadoHijo = estadoHijo;
    }

    public Short getIdTipoPadre() {
        return idTipoPadre;
    }

    public void setIdTipoPadre(Short idTipoPadre) {
        this.idTipoPadre = idTipoPadre;
    }

    public Short getIdTipoHijo() {
        return idTipoHijo;
    }

    public void setIdTipoHijo(Short idTipoHijo) {
        this.idTipoHijo = idTipoHijo;
    }

    public BigDecimal getIdVistaAmarre() {
        return idVistaAmarre;
    }

    public void setIdVistaAmarre(BigDecimal idVistaAmarre) {
        this.idVistaAmarre = idVistaAmarre;
    }

    public Long getIdSimulacion() {
        return idSimulacion;
    }

    public void setIdSimulacion(Long idSimulacion) {
        this.idSimulacion = idSimulacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVistaAmarre != null ? idVistaAmarre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SVistaAmarres)) {
            return false;
        }
        SVistaAmarres other = (SVistaAmarres) object;
        if ((this.idVistaAmarre == null && other.idVistaAmarre != null) || (this.idVistaAmarre != null && !this.idVistaAmarre.equals(other.idVistaAmarre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SVistaAmarres[idVistaAmarre=" + idVistaAmarre + "]";
    }

}
