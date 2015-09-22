/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "NOVEDADES")
@NamedQueries({
    @NamedQuery(name = "Novedades.findAll", query = "SELECT n FROM Novedades n"),
    @NamedQuery(name = "Novedades.findByUsuario", query = "SELECT n FROM Novedades n WHERE n.usuario = :usuario"),
    @NamedQuery(name = "Novedades.findByPrograma", query = "SELECT n FROM Novedades n WHERE n.programa = :programa"),
    @NamedQuery(name = "Novedades.findByFechaModif", query = "SELECT n FROM Novedades n WHERE n.fechaModif = :fechaModif"),
    @NamedQuery(name = "Novedades.findByIdComponenteMedida", query = "SELECT n FROM Novedades n WHERE n.novedadesPK.idComponenteMedida = :idComponenteMedida"),
    @NamedQuery(name = "Novedades.findByTipoNovedad", query = "SELECT n FROM Novedades n WHERE n.novedadesPK.tipoNovedad = :tipoNovedad"),
    @NamedQuery(name = "Novedades.findByPeriodo", query = "SELECT n FROM Novedades n WHERE n.periodo = :periodo"),
    @NamedQuery(name = "Novedades.findByFecha", query = "SELECT n FROM Novedades n WHERE n.fecha = :fecha")})
public class Novedades implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NovedadesPK novedadesPK;
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
    @Column(name = "PERIODO")
    private int periodo;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "TIPO_NOVEDAD", referencedColumnName = "TIPO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @JoinColumn(name = "ID_COMPONENTE_MEDIDA", referencedColumnName = "ID_COMPONENTE_MEDIDA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ComponenteMedida componenteMedida;

    public Novedades() {
    }

    public Novedades(NovedadesPK novedadesPK) {
        this.novedadesPK = novedadesPK;
    }

    public Novedades(NovedadesPK novedadesPK, String usuario, String programa, Date fechaModif, int periodo, Date fecha) {
        this.novedadesPK = novedadesPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.periodo = periodo;
        this.fecha = fecha;
    }

    public Novedades(BigInteger idComponenteMedida, String tipoNovedad) {
        this.novedadesPK = new NovedadesPK(idComponenteMedida, tipoNovedad);
    }

    public NovedadesPK getNovedadesPK() {
        return novedadesPK;
    }

    public void setNovedadesPK(NovedadesPK novedadesPK) {
        this.novedadesPK = novedadesPK;
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

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    public ComponenteMedida getComponenteMedida() {
        return componenteMedida;
    }

    public void setComponenteMedida(ComponenteMedida componenteMedida) {
        this.componenteMedida = componenteMedida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (novedadesPK != null ? novedadesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Novedades)) {
            return false;
        }
        Novedades other = (Novedades) object;
        if ((this.novedadesPK == null && other.novedadesPK != null) || (this.novedadesPK != null && !this.novedadesPK.equals(other.novedadesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Novedades[novedadesPK=" + novedadesPK + "]";
    }

}
