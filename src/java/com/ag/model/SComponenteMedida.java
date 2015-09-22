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
@Table(name = "S_COMPONENTE_MEDIDA")
@NamedQueries({
    @NamedQuery(name = "SComponenteMedida.findAll", query = "SELECT s FROM SComponenteMedida s"),
    @NamedQuery(name = "SComponenteMedida.findByUsuario", query = "SELECT s FROM SComponenteMedida s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SComponenteMedida.findByPrograma", query = "SELECT s FROM SComponenteMedida s WHERE s.programa = :programa"),
    @NamedQuery(name = "SComponenteMedida.findByFechaModif", query = "SELECT s FROM SComponenteMedida s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SComponenteMedida.findByIdComponenteMedida", query = "SELECT s FROM SComponenteMedida s WHERE s.idComponenteMedida = :idComponenteMedida"),
    @NamedQuery(name = "SComponenteMedida.findByNombre", query = "SELECT s FROM SComponenteMedida s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SComponenteMedida.findByDireccion", query = "SELECT s FROM SComponenteMedida s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "SComponenteMedida.findByNumeroRuedas", query = "SELECT s FROM SComponenteMedida s WHERE s.numeroRuedas = :numeroRuedas"),
    @NamedQuery(name = "SComponenteMedida.findByEstado", query = "SELECT s FROM SComponenteMedida s WHERE s.estado = :estado"),
    @NamedQuery(name = "SComponenteMedida.findByIdComercial", query = "SELECT s FROM SComponenteMedida s WHERE s.idComercial = :idComercial"),
    @NamedQuery(name = "SComponenteMedida.findByLecturaInicial", query = "SELECT s FROM SComponenteMedida s WHERE s.lecturaInicial = :lecturaInicial"),
    @NamedQuery(name = "SComponenteMedida.findByLecturaFinal", query = "SELECT s FROM SComponenteMedida s WHERE s.lecturaFinal = :lecturaFinal")})
public class SComponenteMedida implements Serializable {
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
    @Column(name = "NUMERO_RUEDAS")
    private Long numeroRuedas;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponenteMedida")
    private List<SAtrComponenteMedida> sAtrComponenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponenteMedida")
    private List<SRelComponenteMedida> sRelComponenteMedidaList;
    @JoinColumn(name = "TIPO_MEDIDOR", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;

    public SComponenteMedida() {
    }

    public SComponenteMedida(BigDecimal idComponenteMedida) {
        this.idComponenteMedida = idComponenteMedida;
    }

    public SComponenteMedida(BigDecimal idComponenteMedida, String usuario, String programa, Date fechaModif, String nombre, String direccion, String estado, String idComercial) {
        this.idComponenteMedida = idComponenteMedida;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
        this.direccion = direccion;
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

    public Long getNumeroRuedas() {
        return numeroRuedas;
    }

    public void setNumeroRuedas(Long numeroRuedas) {
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

    public List<SAtrComponenteMedida> getSAtrComponenteMedidaList() {
        return sAtrComponenteMedidaList;
    }

    public void setSAtrComponenteMedidaList(List<SAtrComponenteMedida> sAtrComponenteMedidaList) {
        this.sAtrComponenteMedidaList = sAtrComponenteMedidaList;
    }

    public List<SRelComponenteMedida> getSRelComponenteMedidaList() {
        return sRelComponenteMedidaList;
    }

    public void setSRelComponenteMedidaList(List<SRelComponenteMedida> sRelComponenteMedidaList) {
        this.sRelComponenteMedidaList = sRelComponenteMedidaList;
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
        if (!(object instanceof SComponenteMedida)) {
            return false;
        }
        SComponenteMedida other = (SComponenteMedida) object;
        if ((this.idComponenteMedida == null && other.idComponenteMedida != null) || (this.idComponenteMedida != null && !this.idComponenteMedida.equals(other.idComponenteMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SComponenteMedida[idComponenteMedida=" + idComponenteMedida + "]";
    }

}
