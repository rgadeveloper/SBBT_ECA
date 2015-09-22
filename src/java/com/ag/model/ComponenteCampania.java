/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "COMPONENTE_CAMPANIA")
@NamedQueries({
    @NamedQuery(name = "ComponenteCampania.findAll", query = "SELECT c FROM ComponenteCampania c"),
    @NamedQuery(name = "ComponenteCampania.findByIdComponente", query = "SELECT c FROM ComponenteCampania c WHERE c.componenteCampaniaPK.idComponente = :idComponente"),
    @NamedQuery(name = "ComponenteCampania.findByIdCampania", query = "SELECT c FROM ComponenteCampania c WHERE c.componenteCampaniaPK.idCampania = :idCampania"),
    @NamedQuery(name = "ComponenteCampania.findByIdMacro", query = "SELECT c FROM ComponenteCampania c WHERE c.idMacro = :idMacro"),
    @NamedQuery(name = "ComponenteCampania.findByProcesado", query = "SELECT c FROM ComponenteCampania c WHERE c.procesado = :procesado"),
    @NamedQuery(name = "ComponenteCampania.findByActividad", query = "SELECT c FROM ComponenteCampania c WHERE c.actividad = :actividad")})
public class ComponenteCampania implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponenteCampaniaPK componenteCampaniaPK;
    @Column(name = "ID_MACRO")
    private String idMacro;
    @Column(name = "PROCESADO")
    private String procesado;
    @Column(name = "ACTIVIDAD")
    private String actividad;
    @JoinColumn(name = "ID_TIPO_COMPONENTE", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente;
    @JoinColumn(name = "ID_CAMPANIA", referencedColumnName = "ID_CAMPANIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Campania campania;

    public ComponenteCampania() {
    }

    public ComponenteCampania(ComponenteCampaniaPK componenteCampaniaPK) {
        this.componenteCampaniaPK = componenteCampaniaPK;
    }

    public ComponenteCampania(BigInteger idComponente, BigInteger idCampania) {
        this.componenteCampaniaPK = new ComponenteCampaniaPK(idComponente, idCampania);
    }

    public ComponenteCampaniaPK getComponenteCampaniaPK() {
        return componenteCampaniaPK;
    }

    public void setComponenteCampaniaPK(ComponenteCampaniaPK componenteCampaniaPK) {
        this.componenteCampaniaPK = componenteCampaniaPK;
    }

    public String getIdMacro() {
        return idMacro;
    }

    public void setIdMacro(String idMacro) {
        this.idMacro = idMacro;
    }

    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public Campania getCampania() {
        return campania;
    }

    public void setCampania(Campania campania) {
        this.campania = campania;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (componenteCampaniaPK != null ? componenteCampaniaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponenteCampania)) {
            return false;
        }
        ComponenteCampania other = (ComponenteCampania) object;
        if ((this.componenteCampaniaPK == null && other.componenteCampaniaPK != null) || (this.componenteCampaniaPK != null && !this.componenteCampaniaPK.equals(other.componenteCampaniaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.ComponenteCampania[componenteCampaniaPK=" + componenteCampaniaPK + "]";
    }

}
