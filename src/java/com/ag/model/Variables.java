/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "VARIABLES")
@NamedQueries({
    @NamedQuery(name = "Variables.findAll", query = "SELECT v FROM Variables v"),
    @NamedQuery(name = "Variables.findByUsuario", query = "SELECT v FROM Variables v WHERE v.usuario = :usuario"),
    @NamedQuery(name = "Variables.findByPrograma", query = "SELECT v FROM Variables v WHERE v.programa = :programa"),
    @NamedQuery(name = "Variables.findByFechaModif", query = "SELECT v FROM Variables v WHERE v.fechaModif = :fechaModif"),
    @NamedQuery(name = "Variables.findByIdVariable", query = "SELECT v FROM Variables v WHERE v.idVariable = :idVariable"),
    @NamedQuery(name = "Variables.findByNombreVariable", query = "SELECT v FROM Variables v WHERE v.nombreVariable = :nombreVariable"),
    @NamedQuery(name = "Variables.findByTabla", query = "SELECT v FROM Variables v WHERE v.tabla = :tabla"),
    @NamedQuery(name = "Variables.findByCampo", query = "SELECT v FROM Variables v WHERE v.campo = :campo")})
public class Variables implements Serializable {
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
    @Column(name = "ID_VARIABLE")
    private BigDecimal idVariable;
    @Basic(optional = false)
    @Column(name = "NOMBRE_VARIABLE")
    private String nombreVariable;
    @Basic(optional = false)
    @Column(name = "TABLA")
    private String tabla;
    @Basic(optional = false)
    @Column(name = "CAMPO")
    private String campo;
    @JoinColumn(name = "TIPO_VARIABLE", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @JoinColumn(name = "ID_GRUPO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Grupo grupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variables")
    private List<Filtros> filtrosList;

    public Variables() {
    }

    public Variables(BigDecimal idVariable) {
        this.idVariable = idVariable;
    }

    public Variables(BigDecimal idVariable, String usuario, String programa, Date fechaModif, String nombreVariable, String tabla, String campo) {
        this.idVariable = idVariable;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombreVariable = nombreVariable;
        this.tabla = tabla;
        this.campo = campo;
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

    public BigDecimal getIdVariable() {
        return idVariable;
    }

    public void setIdVariable(BigDecimal idVariable) {
        this.idVariable = idVariable;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public void setNombreVariable(String nombreVariable) {
        this.nombreVariable = nombreVariable;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<Filtros> getFiltrosList() {
        return filtrosList;
    }

    public void setFiltrosList(List<Filtros> filtrosList) {
        this.filtrosList = filtrosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVariable != null ? idVariable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Variables)) {
            return false;
        }
        Variables other = (Variables) object;
        if ((this.idVariable == null && other.idVariable != null) || (this.idVariable != null && !this.idVariable.equals(other.idVariable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Variables[idVariable=" + idVariable + "]";
    }

}
