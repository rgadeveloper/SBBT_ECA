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
@Table(name = "COMPONENTE_MEDIDA")
@NamedQueries({
    @NamedQuery(name = "ComponenteMedida.findAll", query = "SELECT c FROM ComponenteMedida c"),
    @NamedQuery(name = "ComponenteMedida.findByUsuario", query = "SELECT c FROM ComponenteMedida c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "ComponenteMedida.findByPrograma", query = "SELECT c FROM ComponenteMedida c WHERE c.programa = :programa"),
    @NamedQuery(name = "ComponenteMedida.findByFechaModif", query = "SELECT c FROM ComponenteMedida c WHERE c.fechaModif = :fechaModif"),
    @NamedQuery(name = "ComponenteMedida.findByIdComponenteMedida", query = "SELECT c FROM ComponenteMedida c WHERE c.idComponenteMedida = :idComponenteMedida"),
    @NamedQuery(name = "ComponenteMedida.findByNombre", query = "SELECT c FROM ComponenteMedida c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ComponenteMedida.findByDireccion", query = "SELECT c FROM ComponenteMedida c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "ComponenteMedida.findByNumeroRuedas", query = "SELECT c FROM ComponenteMedida c WHERE c.numeroRuedas = :numeroRuedas"),
    @NamedQuery(name = "ComponenteMedida.findByEstado", query = "SELECT c FROM ComponenteMedida c WHERE c.estado = :estado"),
    @NamedQuery(name = "ComponenteMedida.findByIdComercial", query = "SELECT c FROM ComponenteMedida c WHERE c.idComercial = :idComercial"),
    @NamedQuery(name = "ComponenteMedida.findByLecturaInicial", query = "SELECT c FROM ComponenteMedida c WHERE c.lecturaInicial = :lecturaInicial"),
    @NamedQuery(name = "ComponenteMedida.findByLecturaFinal", query = "SELECT c FROM ComponenteMedida c WHERE c.lecturaFinal = :lecturaFinal")})
public class ComponenteMedida implements Serializable {
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
    @Column(name = "ID_COMPONENTE_MEDIDA")
    private BigDecimal idComponenteMedida;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "NUMERO_RUEDAS")
    private long numeroRuedas;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "ID_COMERCIAL")
    private String idComercial;
    @Column(name = "LECTURA_INICIAL")
    private Long lecturaInicial;
    @Column(name = "LECTURA_FINAL")
    private Long lecturaFinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componenteMedida")
    private List<AtrComponenteMedida> atrComponenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componenteMedida")
    private List<RelComponenteMedida> relComponenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componenteMedida")
    private List<Novedades> novedadesList;
    @JoinColumn(name = "TIPO_MEDIDOR", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;

    public ComponenteMedida() {
    }

    public ComponenteMedida(BigDecimal idComponenteMedida) {
        this.idComponenteMedida = idComponenteMedida;
    }

    public ComponenteMedida(BigDecimal idComponenteMedida, String usuario, String programa, Date fechaModif, String nombre, String direccion, long numeroRuedas, String estado, String idComercial) {
        this.idComponenteMedida = idComponenteMedida;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numeroRuedas = numeroRuedas;
        this.estado = estado;
        this.idComercial = idComercial;
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

    public BigDecimal getIdComponenteMedida() {
        return idComponenteMedida;
    }

    public void setIdComponenteMedida(BigDecimal idComponenteMedida) {
        this.idComponenteMedida = idComponenteMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getNumeroRuedas() {
        return numeroRuedas;
    }

    public void setNumeroRuedas(long numeroRuedas) {
        this.numeroRuedas = numeroRuedas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(String idComercial) {
        this.idComercial = idComercial;
    }

    public Long getLecturaInicial() {
        return lecturaInicial;
    }

    public void setLecturaInicial(Long lecturaInicial) {
        this.lecturaInicial = lecturaInicial;
    }

    public Long getLecturaFinal() {
        return lecturaFinal;
    }

    public void setLecturaFinal(Long lecturaFinal) {
        this.lecturaFinal = lecturaFinal;
    }

    public List<AtrComponenteMedida> getAtrComponenteMedidaList() {
        return atrComponenteMedidaList;
    }

    public void setAtrComponenteMedidaList(List<AtrComponenteMedida> atrComponenteMedidaList) {
        this.atrComponenteMedidaList = atrComponenteMedidaList;
    }

    public List<RelComponenteMedida> getRelComponenteMedidaList() {
        return relComponenteMedidaList;
    }

    public void setRelComponenteMedidaList(List<RelComponenteMedida> relComponenteMedidaList) {
        this.relComponenteMedidaList = relComponenteMedidaList;
    }

    public List<Novedades> getNovedadesList() {
        return novedadesList;
    }

    public void setNovedadesList(List<Novedades> novedadesList) {
        this.novedadesList = novedadesList;
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
        hash += (idComponenteMedida != null ? idComponenteMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponenteMedida)) {
            return false;
        }
        ComponenteMedida other = (ComponenteMedida) object;
        if ((this.idComponenteMedida == null && other.idComponenteMedida != null) || (this.idComponenteMedida != null && !this.idComponenteMedida.equals(other.idComponenteMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.ComponenteMedida[idComponenteMedida=" + idComponenteMedida + "]";
    }

}
