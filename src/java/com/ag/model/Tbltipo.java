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
@Table(name = "TBLTIPO")
@NamedQueries({
    @NamedQuery(name = "Tbltipo.findAll", query = "SELECT t FROM Tbltipo t"),
    @NamedQuery(name = "Tbltipo.findByUsuario", query = "SELECT t FROM Tbltipo t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "Tbltipo.findByPrograma", query = "SELECT t FROM Tbltipo t WHERE t.programa = :programa"),
    @NamedQuery(name = "Tbltipo.findByFechaModif", query = "SELECT t FROM Tbltipo t WHERE t.fechaModif = :fechaModif"),
    @NamedQuery(name = "Tbltipo.findByTipo", query = "SELECT t FROM Tbltipo t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Tbltipo.findByNombre", query = "SELECT t FROM Tbltipo t WHERE t.nombre = :nombre")})
public class Tbltipo implements Serializable {
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
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<SComponente> sComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo1")
    private List<SComponente> sComponenteList1;
    @OneToMany(mappedBy = "tbltipo")
    private List<Medida> medidaList;
    @OneToMany(mappedBy = "tbltipo1")
    private List<Medida> medidaList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<SComponenteMedida> sComponenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<Componente> componenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo1")
    private List<Componente> componenteList1;
    @OneToMany(mappedBy = "tbltipo")
    private List<Funcion> funcionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<CargaExtra> cargaExtraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<SCargaExtra> sCargaExtraList;
    @OneToMany(mappedBy = "tbltipo")
    private List<Balances> balancesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<AtrComponente> atrComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo1")
    private List<AtrComponente> atrComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo2")
    private List<AtrComponente> atrComponenteList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo3")
    private List<AtrComponente> atrComponenteList3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo4")
    private List<AtrComponente> atrComponenteList4;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo5")
    private List<AtrComponente> atrComponenteList5;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo6")
    private List<AtrComponente> atrComponenteList6;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo7")
    private List<AtrComponente> atrComponenteList7;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<LogAuditoria> logAuditoriaList;
    @OneToMany(mappedBy = "tbltipo")
    private List<OrdenTrabajo> ordenTrabajoList;
    @OneToMany(mappedBy = "tbltipo1")
    private List<OrdenTrabajo> ordenTrabajoList1;
    @OneToMany(mappedBy = "tbltipo2")
    private List<OrdenTrabajo> ordenTrabajoList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<SAtrComponente> sAtrComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo1")
    private List<SAtrComponente> sAtrComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo2")
    private List<SAtrComponente> sAtrComponenteList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo3")
    private List<SAtrComponente> sAtrComponenteList3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo4")
    private List<SAtrComponente> sAtrComponenteList4;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo5")
    private List<SAtrComponente> sAtrComponenteList5;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo6")
    private List<SAtrComponente> sAtrComponenteList6;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo7")
    private List<SAtrComponente> sAtrComponenteList7;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<Variables> variablesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<TipoComponente> tipoComponenteList;
    @OneToMany(mappedBy = "tbltipo")
    private List<SBalances> sBalancesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<ComponenteCampania> componenteCampaniaList;
    @OneToMany(mappedBy = "tbltipo")
    private List<SMedida> sMedidaList;
    @OneToMany(mappedBy = "tbltipo1")
    private List<SMedida> sMedidaList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<Novedades> novedadesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<Filtros> filtrosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo1")
    private List<Filtros> filtrosList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo2")
    private List<Filtros> filtrosList2;
    @OneToMany(mappedBy = "tbltipo3")
    private List<Filtros> filtrosList3;
    @JoinColumn(name = "COD_GRUPO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Grupo grupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<ComponenteMedida> componenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbltipo")
    private List<Campania> campaniaList;

    public Tbltipo() {
    }

    public Tbltipo(String tipo) {
        this.tipo = tipo;
    }

    public Tbltipo(String tipo, String usuario, String programa, Date fechaModif, String nombre) {
        this.tipo = tipo;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<Medida> getMedidaList() {
        return medidaList;
    }

    public void setMedidaList(List<Medida> medidaList) {
        this.medidaList = medidaList;
    }

    public List<Medida> getMedidaList1() {
        return medidaList1;
    }

    public void setMedidaList1(List<Medida> medidaList1) {
        this.medidaList1 = medidaList1;
    }

    public List<SComponenteMedida> getSComponenteMedidaList() {
        return sComponenteMedidaList;
    }

    public void setSComponenteMedidaList(List<SComponenteMedida> sComponenteMedidaList) {
        this.sComponenteMedidaList = sComponenteMedidaList;
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

    public List<Funcion> getFuncionList() {
        return funcionList;
    }

    public void setFuncionList(List<Funcion> funcionList) {
        this.funcionList = funcionList;
    }

    public List<CargaExtra> getCargaExtraList() {
        return cargaExtraList;
    }

    public void setCargaExtraList(List<CargaExtra> cargaExtraList) {
        this.cargaExtraList = cargaExtraList;
    }

    public List<SCargaExtra> getSCargaExtraList() {
        return sCargaExtraList;
    }

    public void setSCargaExtraList(List<SCargaExtra> sCargaExtraList) {
        this.sCargaExtraList = sCargaExtraList;
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

    public List<AtrComponente> getAtrComponenteList2() {
        return atrComponenteList2;
    }

    public void setAtrComponenteList2(List<AtrComponente> atrComponenteList2) {
        this.atrComponenteList2 = atrComponenteList2;
    }

    public List<AtrComponente> getAtrComponenteList3() {
        return atrComponenteList3;
    }

    public void setAtrComponenteList3(List<AtrComponente> atrComponenteList3) {
        this.atrComponenteList3 = atrComponenteList3;
    }

    public List<AtrComponente> getAtrComponenteList4() {
        return atrComponenteList4;
    }

    public void setAtrComponenteList4(List<AtrComponente> atrComponenteList4) {
        this.atrComponenteList4 = atrComponenteList4;
    }

    public List<AtrComponente> getAtrComponenteList5() {
        return atrComponenteList5;
    }

    public void setAtrComponenteList5(List<AtrComponente> atrComponenteList5) {
        this.atrComponenteList5 = atrComponenteList5;
    }

    public List<AtrComponente> getAtrComponenteList6() {
        return atrComponenteList6;
    }

    public void setAtrComponenteList6(List<AtrComponente> atrComponenteList6) {
        this.atrComponenteList6 = atrComponenteList6;
    }

    public List<AtrComponente> getAtrComponenteList7() {
        return atrComponenteList7;
    }

    public void setAtrComponenteList7(List<AtrComponente> atrComponenteList7) {
        this.atrComponenteList7 = atrComponenteList7;
    }

    public List<LogAuditoria> getLogAuditoriaList() {
        return logAuditoriaList;
    }

    public void setLogAuditoriaList(List<LogAuditoria> logAuditoriaList) {
        this.logAuditoriaList = logAuditoriaList;
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

    public List<SAtrComponente> getSAtrComponenteList2() {
        return sAtrComponenteList2;
    }

    public void setSAtrComponenteList2(List<SAtrComponente> sAtrComponenteList2) {
        this.sAtrComponenteList2 = sAtrComponenteList2;
    }

    public List<SAtrComponente> getSAtrComponenteList3() {
        return sAtrComponenteList3;
    }

    public void setSAtrComponenteList3(List<SAtrComponente> sAtrComponenteList3) {
        this.sAtrComponenteList3 = sAtrComponenteList3;
    }

    public List<SAtrComponente> getSAtrComponenteList4() {
        return sAtrComponenteList4;
    }

    public void setSAtrComponenteList4(List<SAtrComponente> sAtrComponenteList4) {
        this.sAtrComponenteList4 = sAtrComponenteList4;
    }

    public List<SAtrComponente> getSAtrComponenteList5() {
        return sAtrComponenteList5;
    }

    public void setSAtrComponenteList5(List<SAtrComponente> sAtrComponenteList5) {
        this.sAtrComponenteList5 = sAtrComponenteList5;
    }

    public List<SAtrComponente> getSAtrComponenteList6() {
        return sAtrComponenteList6;
    }

    public void setSAtrComponenteList6(List<SAtrComponente> sAtrComponenteList6) {
        this.sAtrComponenteList6 = sAtrComponenteList6;
    }

    public List<SAtrComponente> getSAtrComponenteList7() {
        return sAtrComponenteList7;
    }

    public void setSAtrComponenteList7(List<SAtrComponente> sAtrComponenteList7) {
        this.sAtrComponenteList7 = sAtrComponenteList7;
    }

    public List<Variables> getVariablesList() {
        return variablesList;
    }

    public void setVariablesList(List<Variables> variablesList) {
        this.variablesList = variablesList;
    }

    public List<TipoComponente> getTipoComponenteList() {
        return tipoComponenteList;
    }

    public void setTipoComponenteList(List<TipoComponente> tipoComponenteList) {
        this.tipoComponenteList = tipoComponenteList;
    }

    public List<SBalances> getSBalancesList() {
        return sBalancesList;
    }

    public void setSBalancesList(List<SBalances> sBalancesList) {
        this.sBalancesList = sBalancesList;
    }

    public List<ComponenteCampania> getComponenteCampaniaList() {
        return componenteCampaniaList;
    }

    public void setComponenteCampaniaList(List<ComponenteCampania> componenteCampaniaList) {
        this.componenteCampaniaList = componenteCampaniaList;
    }

    public List<SMedida> getSMedidaList() {
        return sMedidaList;
    }

    public void setSMedidaList(List<SMedida> sMedidaList) {
        this.sMedidaList = sMedidaList;
    }

    public List<SMedida> getSMedidaList1() {
        return sMedidaList1;
    }

    public void setSMedidaList1(List<SMedida> sMedidaList1) {
        this.sMedidaList1 = sMedidaList1;
    }

    public List<Novedades> getNovedadesList() {
        return novedadesList;
    }

    public void setNovedadesList(List<Novedades> novedadesList) {
        this.novedadesList = novedadesList;
    }

    public List<Filtros> getFiltrosList() {
        return filtrosList;
    }

    public void setFiltrosList(List<Filtros> filtrosList) {
        this.filtrosList = filtrosList;
    }

    public List<Filtros> getFiltrosList1() {
        return filtrosList1;
    }

    public void setFiltrosList1(List<Filtros> filtrosList1) {
        this.filtrosList1 = filtrosList1;
    }

    public List<Filtros> getFiltrosList2() {
        return filtrosList2;
    }

    public void setFiltrosList2(List<Filtros> filtrosList2) {
        this.filtrosList2 = filtrosList2;
    }

    public List<Filtros> getFiltrosList3() {
        return filtrosList3;
    }

    public void setFiltrosList3(List<Filtros> filtrosList3) {
        this.filtrosList3 = filtrosList3;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<ComponenteMedida> getComponenteMedidaList() {
        return componenteMedidaList;
    }

    public void setComponenteMedidaList(List<ComponenteMedida> componenteMedidaList) {
        this.componenteMedidaList = componenteMedidaList;
    }

    public List<Campania> getCampaniaList() {
        return campaniaList;
    }

    public void setCampaniaList(List<Campania> campaniaList) {
        this.campaniaList = campaniaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipo != null ? tipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tbltipo)) {
            return false;
        }
        Tbltipo other = (Tbltipo) object;
        if ((this.tipo == null && other.tipo != null) || (this.tipo != null && !this.tipo.equals(other.tipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Tbltipo[tipo=" + tipo + "]";
    }

}
