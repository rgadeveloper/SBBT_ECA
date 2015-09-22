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
@Table(name = "ESTADO")
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
    @NamedQuery(name = "Estado.findByUsuario", query = "SELECT e FROM Estado e WHERE e.usuario = :usuario"),
    @NamedQuery(name = "Estado.findByPrograma", query = "SELECT e FROM Estado e WHERE e.programa = :programa"),
    @NamedQuery(name = "Estado.findByFechaModif", query = "SELECT e FROM Estado e WHERE e.fechaModif = :fechaModif"),
    @NamedQuery(name = "Estado.findByIdEstado", query = "SELECT e FROM Estado e WHERE e.idEstado = :idEstado"),
    @NamedQuery(name = "Estado.findByDescripcion", query = "SELECT e FROM Estado e WHERE e.descripcion = :descripcion")})
public class Estado implements Serializable {
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
    @Column(name = "ID_ESTADO")
    private String idEstado;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<SComponente> sComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado1")
    private List<SComponente> sComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<SAtrComponenteMedida> sAtrComponenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<AtrComponenteMedida> atrComponenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<SRelComponente> sRelComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado1")
    private List<SRelComponente> sRelComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado2")
    private List<SRelComponente> sRelComponenteList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<Componente> componenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado1")
    private List<Componente> componenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<RelComponente> relComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado1")
    private List<RelComponente> relComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado2")
    private List<RelComponente> relComponenteList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<SHistRelComponente> sHistRelComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado1")
    private List<SHistRelComponente> sHistRelComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado2")
    private List<SHistRelComponente> sHistRelComponenteList2;
    @OneToMany(mappedBy = "estado")
    private List<Balances> balancesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<AtrComponente> atrComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado1")
    private List<AtrComponente> atrComponenteList1;
    @OneToMany(mappedBy = "estado1")
    private List<OrdenTrabajo> ordenTrabajoList;
    @OneToMany(mappedBy = "estado2")
    private List<OrdenTrabajo> ordenTrabajoList1;
    @OneToMany(mappedBy = "estado3")
    private List<OrdenTrabajo> ordenTrabajoList2;
    @OneToMany(mappedBy = "estado4")
    private List<OrdenTrabajo> ordenTrabajoList3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<Perfil> perfilList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<SAtrComponente> sAtrComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado1")
    private List<SAtrComponente> sAtrComponenteList1;
    @OneToMany(mappedBy = "estado")
    private List<SBalances> sBalancesList;
    @JoinColumn(name = "GRUPO_ESTADO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Grupo grupo;

    public Estado() {
    }

    public Estado(String idEstado) {
        this.idEstado = idEstado;
    }

    public Estado(String idEstado, String usuario, String programa, Date fechaModif, String descripcion) {
        this.idEstado = idEstado;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.descripcion = descripcion;
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

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<SComponente> getSComponenteList() {
        return sComponenteList;
    }

    public void setSComponenteList(List<SComponente> sComponenteList) {
        this.sComponenteList = sComponenteList;
    }

    public List<SComponente> getSComponenteList1() {
        return sComponenteList1;
    }

    public void setSComponenteList1(List<SComponente> sComponenteList1) {
        this.sComponenteList1 = sComponenteList1;
    }

    public List<SAtrComponenteMedida> getSAtrComponenteMedidaList() {
        return sAtrComponenteMedidaList;
    }

    public void setSAtrComponenteMedidaList(List<SAtrComponenteMedida> sAtrComponenteMedidaList) {
        this.sAtrComponenteMedidaList = sAtrComponenteMedidaList;
    }

    public List<AtrComponenteMedida> getAtrComponenteMedidaList() {
        return atrComponenteMedidaList;
    }

    public void setAtrComponenteMedidaList(List<AtrComponenteMedida> atrComponenteMedidaList) {
        this.atrComponenteMedidaList = atrComponenteMedidaList;
    }

    public List<SRelComponente> getSRelComponenteList() {
        return sRelComponenteList;
    }

    public void setSRelComponenteList(List<SRelComponente> sRelComponenteList) {
        this.sRelComponenteList = sRelComponenteList;
    }

    public List<SRelComponente> getSRelComponenteList1() {
        return sRelComponenteList1;
    }

    public void setSRelComponenteList1(List<SRelComponente> sRelComponenteList1) {
        this.sRelComponenteList1 = sRelComponenteList1;
    }

    public List<SRelComponente> getSRelComponenteList2() {
        return sRelComponenteList2;
    }

    public void setSRelComponenteList2(List<SRelComponente> sRelComponenteList2) {
        this.sRelComponenteList2 = sRelComponenteList2;
    }

    public List<Componente> getComponenteList() {
        return componenteList;
    }

    public void setComponenteList(List<Componente> componenteList) {
        this.componenteList = componenteList;
    }

    public List<Componente> getComponenteList1() {
        return componenteList1;
    }

    public void setComponenteList1(List<Componente> componenteList1) {
        this.componenteList1 = componenteList1;
    }

    public List<RelComponente> getRelComponenteList() {
        return relComponenteList;
    }

    public void setRelComponenteList(List<RelComponente> relComponenteList) {
        this.relComponenteList = relComponenteList;
    }

    public List<RelComponente> getRelComponenteList1() {
        return relComponenteList1;
    }

    public void setRelComponenteList1(List<RelComponente> relComponenteList1) {
        this.relComponenteList1 = relComponenteList1;
    }

    public List<RelComponente> getRelComponenteList2() {
        return relComponenteList2;
    }

    public void setRelComponenteList2(List<RelComponente> relComponenteList2) {
        this.relComponenteList2 = relComponenteList2;
    }

    public List<SHistRelComponente> getSHistRelComponenteList() {
        return sHistRelComponenteList;
    }

    public void setSHistRelComponenteList(List<SHistRelComponente> sHistRelComponenteList) {
        this.sHistRelComponenteList = sHistRelComponenteList;
    }

    public List<SHistRelComponente> getSHistRelComponenteList1() {
        return sHistRelComponenteList1;
    }

    public void setSHistRelComponenteList1(List<SHistRelComponente> sHistRelComponenteList1) {
        this.sHistRelComponenteList1 = sHistRelComponenteList1;
    }

    public List<SHistRelComponente> getSHistRelComponenteList2() {
        return sHistRelComponenteList2;
    }

    public void setSHistRelComponenteList2(List<SHistRelComponente> sHistRelComponenteList2) {
        this.sHistRelComponenteList2 = sHistRelComponenteList2;
    }

    public List<Balances> getBalancesList() {
        return balancesList;
    }

    public void setBalancesList(List<Balances> balancesList) {
        this.balancesList = balancesList;
    }

    public List<AtrComponente> getAtrComponenteList() {
        return atrComponenteList;
    }

    public void setAtrComponenteList(List<AtrComponente> atrComponenteList) {
        this.atrComponenteList = atrComponenteList;
    }

    public List<AtrComponente> getAtrComponenteList1() {
        return atrComponenteList1;
    }

    public void setAtrComponenteList1(List<AtrComponente> atrComponenteList1) {
        this.atrComponenteList1 = atrComponenteList1;
    }

    public List<OrdenTrabajo> getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(List<OrdenTrabajo> ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    public List<OrdenTrabajo> getOrdenTrabajoList1() {
        return ordenTrabajoList1;
    }

    public void setOrdenTrabajoList1(List<OrdenTrabajo> ordenTrabajoList1) {
        this.ordenTrabajoList1 = ordenTrabajoList1;
    }

    public List<OrdenTrabajo> getOrdenTrabajoList2() {
        return ordenTrabajoList2;
    }

    public void setOrdenTrabajoList2(List<OrdenTrabajo> ordenTrabajoList2) {
        this.ordenTrabajoList2 = ordenTrabajoList2;
    }

    public List<OrdenTrabajo> getOrdenTrabajoList3() {
        return ordenTrabajoList3;
    }

    public void setOrdenTrabajoList3(List<OrdenTrabajo> ordenTrabajoList3) {
        this.ordenTrabajoList3 = ordenTrabajoList3;
    }

    public List<Perfil> getPerfilList() {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList) {
        this.perfilList = perfilList;
    }

    public List<SAtrComponente> getSAtrComponenteList() {
        return sAtrComponenteList;
    }

    public void setSAtrComponenteList(List<SAtrComponente> sAtrComponenteList) {
        this.sAtrComponenteList = sAtrComponenteList;
    }

    public List<SAtrComponente> getSAtrComponenteList1() {
        return sAtrComponenteList1;
    }

    public void setSAtrComponenteList1(List<SAtrComponente> sAtrComponenteList1) {
        this.sAtrComponenteList1 = sAtrComponenteList1;
    }

    public List<SBalances> getSBalancesList() {
        return sBalancesList;
    }

    public void setSBalancesList(List<SBalances> sBalancesList) {
        this.sBalancesList = sBalancesList;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Estado[idEstado=" + idEstado + "]";
    }

}
