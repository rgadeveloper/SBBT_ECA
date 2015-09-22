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
@Table(name = "S_COMPONENTE")
@NamedQueries({
    @NamedQuery(name = "SComponente.findAll", query = "SELECT s FROM SComponente s"),
    @NamedQuery(name = "SComponente.findByUsuario", query = "SELECT s FROM SComponente s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SComponente.findByPrograma", query = "SELECT s FROM SComponente s WHERE s.programa = :programa"),
    @NamedQuery(name = "SComponente.findByFechaModif", query = "SELECT s FROM SComponente s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SComponente.findByIdComponente", query = "SELECT s FROM SComponente s WHERE s.idComponente = :idComponente"),
    @NamedQuery(name = "SComponente.findByNombre", query = "SELECT s FROM SComponente s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SComponente.findByDireccion", query = "SELECT s FROM SComponente s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "SComponente.findByCoordX", query = "SELECT s FROM SComponente s WHERE s.coordX = :coordX"),
    @NamedQuery(name = "SComponente.findByCoordY", query = "SELECT s FROM SComponente s WHERE s.coordY = :coordY"),
    @NamedQuery(name = "SComponente.findByIdCliente", query = "SELECT s FROM SComponente s WHERE s.idCliente = :idCliente"),
    @NamedQuery(name = "SComponente.findByTipoProvisional", query = "SELECT s FROM SComponente s WHERE s.tipoProvisional = :tipoProvisional")})
public class SComponente implements Serializable {
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
    @Column(name = "ID_COMPONENTE")
    private BigDecimal idComponente;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "COORD_X")
    private String coordX;
    @Column(name = "COORD_Y")
    private String coordY;
    @Column(name = "ID_CLIENTE")
    private String idCliente;
    @Basic(optional = false)
    @Column(name = "TIPO_PROVISIONAL")
    private String tipoProvisional;
    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID_ZONA")
    @ManyToOne
    private ZonaGeografica zonaGeografica;
    @JoinColumn(name = "ID_TIPO_COMPONENTE", referencedColumnName = "ID_TIPO_COMPONENTE")
    @ManyToOne(optional = false)
    private TipoComponente tipoComponente;
    @JoinColumn(name = "TIPO_PROPIETARIO", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @JoinColumn(name = "LOCALIZACION", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo1;
    @JoinColumn(name = "ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;
    @JoinColumn(name = "ESTADO_SERVICIO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SRelComponenteMedida> sRelComponenteMedidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SRelComponente> sRelComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente1")
    private List<SRelComponente> sRelComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SHistRelComponenteUbica> sHistRelComponenteUbicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SHistRelComponente> sHistRelComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente1")
    private List<SHistRelComponente> sHistRelComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SCargaExtra> sCargaExtraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SRelComponenteUbicacion> sRelComponenteUbicacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SAtrComponente> sAtrComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sComponente")
    private List<SMedida> sMedidaList;

    public SComponente() {
    }

    public SComponente(BigDecimal idComponente) {
        this.idComponente = idComponente;
    }

    public SComponente(BigDecimal idComponente, String usuario, String programa, Date fechaModif, String nombre, String direccion, String tipoProvisional) {
        this.idComponente = idComponente;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipoProvisional = tipoProvisional;
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

    public BigDecimal getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigDecimal idComponente) {
        this.idComponente = idComponente;
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

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoProvisional() {
        return tipoProvisional;
    }

    public void setTipoProvisional(String tipoProvisional) {
        this.tipoProvisional = tipoProvisional;
    }

    public ZonaGeografica getZonaGeografica() {
        return zonaGeografica;
    }

    public void setZonaGeografica(ZonaGeografica zonaGeografica) {
        this.zonaGeografica = zonaGeografica;
    }

    public TipoComponente getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(TipoComponente tipoComponente) {
        this.tipoComponente = tipoComponente;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstado1() {
        return estado1;
    }

    public void setEstado1(Estado estado1) {
        this.estado1 = estado1;
    }

    public List<SRelComponenteMedida> getSRelComponenteMedidaList() {
        return sRelComponenteMedidaList;
    }

    public void setSRelComponenteMedidaList(List<SRelComponenteMedida> sRelComponenteMedidaList) {
        this.sRelComponenteMedidaList = sRelComponenteMedidaList;
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

    public List<SHistRelComponenteUbica> getSHistRelComponenteUbicaList() {
        return sHistRelComponenteUbicaList;
    }

    public void setSHistRelComponenteUbicaList(List<SHistRelComponenteUbica> sHistRelComponenteUbicaList) {
        this.sHistRelComponenteUbicaList = sHistRelComponenteUbicaList;
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

    public List<SCargaExtra> getSCargaExtraList() {
        return sCargaExtraList;
    }

    public void setSCargaExtraList(List<SCargaExtra> sCargaExtraList) {
        this.sCargaExtraList = sCargaExtraList;
    }

    public List<SRelComponenteUbicacion> getSRelComponenteUbicacionList() {
        return sRelComponenteUbicacionList;
    }

    public void setSRelComponenteUbicacionList(List<SRelComponenteUbicacion> sRelComponenteUbicacionList) {
        this.sRelComponenteUbicacionList = sRelComponenteUbicacionList;
    }

    public List<SAtrComponente> getSAtrComponenteList() {
        return sAtrComponenteList;
    }

    public void setSAtrComponenteList(List<SAtrComponente> sAtrComponenteList) {
        this.sAtrComponenteList = sAtrComponenteList;
    }

    public List<SMedida> getSMedidaList() {
        return sMedidaList;
    }

    public void setSMedidaList(List<SMedida> sMedidaList) {
        this.sMedidaList = sMedidaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SComponente)) {
            return false;
        }
        SComponente other = (SComponente) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SComponente[idComponente=" + idComponente + "]";
    }

}
