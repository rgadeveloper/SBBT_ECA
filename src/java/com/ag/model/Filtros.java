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
 * @author Larry
 */
@Entity
@Table(name = "FILTROS")
@NamedQueries({
    @NamedQuery(name = "Filtros.findAll", query = "SELECT f FROM Filtros f"),
    @NamedQuery(name = "Filtros.findByUsuario", query = "SELECT f FROM Filtros f WHERE f.usuario = :usuario"),
    @NamedQuery(name = "Filtros.findByPrograma", query = "SELECT f FROM Filtros f WHERE f.programa = :programa"),
    @NamedQuery(name = "Filtros.findByFechaModif", query = "SELECT f FROM Filtros f WHERE f.fechaModif = :fechaModif"),
    @NamedQuery(name = "Filtros.findByIdFiltro", query = "SELECT f FROM Filtros f WHERE f.idFiltro = :idFiltro"),
    @NamedQuery(name = "Filtros.findByValor1", query = "SELECT f FROM Filtros f WHERE f.valor1 = :valor1"),
    @NamedQuery(name = "Filtros.findByValor2", query = "SELECT f FROM Filtros f WHERE f.valor2 = :valor2")})
public class Filtros implements Serializable {
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
    @Column(name = "ID_FILTRO")
    private BigDecimal idFiltro;
    @Basic(optional = false)
    @Column(name = "VALOR1")
    private String valor1;
    @Column(name = "VALOR2")
    private String valor2;
    @JoinColumn(name = "ID_VARIABLE", referencedColumnName = "ID_VARIABLE")
    @ManyToOne(optional = false)
    private Variables variables;
    @JoinColumn(name = "TIPO_OPERADOR_2", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo;
    @JoinColumn(name = "TIPO_OPERADOR_LOGICO", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo1;
    @JoinColumn(name = "TIPO_FILTRO", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo2;
    @JoinColumn(name = "TIPO_OPERADOR", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo3;
    @JoinColumn(name = "ID_CRITERIO", referencedColumnName = "ID_CRITERIO")
    @ManyToOne(optional = false)
    private Criterio criterio;
    
    private boolean selected = false;

    public Filtros() {
    }

    public Filtros(BigDecimal idFiltro) {
        this.idFiltro = idFiltro;
    }

    public Filtros(BigDecimal idFiltro, String usuario, String programa, Date fechaModif, String valor1) {
        this.idFiltro = idFiltro;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.valor1 = valor1;
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

    public BigDecimal getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(BigDecimal idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    public Tbltipo getTbltipo1() {
        return tbltipo1;
    }

    public void setTbltipo1(Tbltipo tbltipo1) {
        this.tbltipo1 = tbltipo1;
    }

    public Tbltipo getTbltipo2() {
        return tbltipo2;
    }

    public void setTbltipo2(Tbltipo tbltipo2) {
        this.tbltipo2 = tbltipo2;
    }

    public Tbltipo getTbltipo3() {
        return tbltipo3;
    }

    public void setTbltipo3(Tbltipo tbltipo3) {
        this.tbltipo3 = tbltipo3;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFiltro != null ? idFiltro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filtros)) {
            return false;
        }
        Filtros other = (Filtros) object;
        if ((this.idFiltro == null && other.idFiltro != null) || (this.idFiltro != null && !this.idFiltro.equals(other.idFiltro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Filtros[idFiltro=" + idFiltro + "]";
    }

    

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
